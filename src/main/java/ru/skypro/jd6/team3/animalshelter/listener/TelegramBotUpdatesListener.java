package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.service.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    private final MainMenuService mainMenuService;

    private final NewUserMenuService newUserMenuService;

    private final PotentialOwnerMenuService potentialOwnerMenuService;

    private final PotentialOwnerService potentialOwnerService;

    private final VolunteerService volunteerService;

    private final PetService petService;

    private final ReportService reportService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MainMenuService mainMenuService,
                                      NewUserMenuService newUserMenuService, PotentialOwnerMenuService potentialOwnerMenuService,
                                      PotentialOwnerService potentialOwnerService, VolunteerService volunteerService,
                                      PetService petService, ReportService reportService) {
        this.telegramBot = telegramBot;
        this.mainMenuService = mainMenuService;
        this.newUserMenuService = newUserMenuService;
        this.potentialOwnerMenuService = potentialOwnerMenuService;
        this.potentialOwnerService = potentialOwnerService;
        this.volunteerService = volunteerService;
        this.petService = petService;
        this.reportService = reportService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null) {
                Long userIdFromMessage = update.message().chat().id();
                String messageText = "";
                if (update.message().text() != null) {
                    messageText = update.message().text();
                }
                PotentialOwner potentialOwner = potentialOwnerService.get(userIdFromMessage);
                Volunteer volunteer = volunteerService.get(userIdFromMessage);
                if (potentialOwner == null || volunteer == null) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        mainMenuService.send(userIdFromMessage, "Добро пожаловать " + update.message().from().username() + "!");
                    }
                }
                if (potentialOwner != null) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        potentialOwnerService.setLocationInMenu(userIdFromMessage, mainMenuService.toString());
                        if (potentialOwner.getName().isBlank()) {
                            mainMenuService.send(userIdFromMessage, "Добро пожаловать " + update.message().from().username() + "!");
                        } else {
                            mainMenuService.send(userIdFromMessage, "Добро пожаловать " + potentialOwner.getName() + "!");
                        }
                    }
                    if (messageText.equalsIgnoreCase("/stop")) {
                        if (potentialOwner.getName().isBlank() || !petService.existsPetByPotentialOwner(potentialOwner)) {
                            potentialOwnerService.delete(userIdFromMessage);
                        }
                    }
                    if (messageText.equalsIgnoreCase("/close")
                            && volunteerService.existsByPotentialOwner(potentialOwner)) {
                        String locationInMenu = closeChatBy(potentialOwner);
                        if (locationInMenu.equals("Консультация без регистрации")) {
                            potentialOwnerService.delete(potentialOwner.getId());
                        } else {
                            switch (locationInMenu) {
                                case ("MainMenu"):
                                    mainMenuService.send(userIdFromMessage, "Добро пожаловать " +
                                            potentialOwner.getName() + "!");
                                    break;
                                case ("NewUserMenu"):
                                    newUserMenuService.send(userIdFromMessage, "Консультация с новым пользователем");
                                    break;
                                case ("PotentialOwnerMenu"):
                                    potentialOwnerMenuService.send(userIdFromMessage, "Консультация с потенциальным хозяином");
                                    break;
                            }
                        }
                    }
                    if (potentialOwner.getLocationInMenu().equals("Записать контактные данные") &&
                            potentialOwner.getName().isBlank() && !messageText.isBlank()) {
                        if (messageText.matches("^[а-яА-Я]+\\s\\+*[0-9]+$")) {
                            potentialOwnerService.addContactData(userIdFromMessage, messageText, "Консультация с новым пользователем");
                            sendMessage(userIdFromMessage, "Данные успешно записаны");
                            newUserMenuService.send(userIdFromMessage, "Консультация с новым пользователем");
                        } else {
                            sendMessage(userIdFromMessage, "Введите данные корректно: сначала имя кириллицей, затем номер");
                        }
                    }
                    if (volunteerService.existsByPotentialOwner(potentialOwner) && messageText.charAt(0) != '/' ) {
                        sendMessage(volunteerService.findByPotentialOwner(potentialOwner.getId()).getId(), messageText);
                    }
                    if (potentialOwner.getLocationInMenu().equals("Прислать отчет о питомце")) {
                        if (update.message().photo() != null) {
                            reportService.uploadReportPhoto(update.message().photo(), potentialOwner);
                        }
                        if (!messageText.isBlank() && messageText.charAt(0) != '/') {
                            reportService.uploadReportText(messageText, potentialOwner);
                        }
                        if (reportService.existsBy(potentialOwner)) {
                            Report report = reportService.getLastBy(potentialOwner);
                            if (report.getPhotoId() == null) {
                                sendMessage(userIdFromMessage, "Пожалуйста, пришлите фото");
                            }
                            if (report.getReportText() == null) {
                                sendMessage(userIdFromMessage, "Пожалуйста, напишите отчет");
                            }
                        }
                        if (reportService.reportCompleted(potentialOwner)) {
                            potentialOwnerService.setLocationInMenu(userIdFromMessage, "MainMenu");
                            sendMessage(userIdFromMessage, "Ваш отчет отправлен");
                        }
                    }
                }
                if (volunteer != null) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        sendMessage(userIdFromMessage, "Добро пожаловать волонтер!");
                    }
                    if (messageText.equalsIgnoreCase("/close") && volunteer.getPotentialOwner() != null) {
                        String locationInMenu = closeChatBy(volunteer);
                        if (locationInMenu.equals("Консультация без регистрации")) {
                            potentialOwnerService.delete(volunteer.getPotentialOwner().getId());
                        }
                    }
                    if (messageText.matches("^/notify\\s89\\d{9}")) {
                        String[] str = messageText.split(" ");
                        PotentialOwner potentialOwner1 = potentialOwnerService.findByPhone(str[1]);
                        if (potentialOwner1 != null) {
                            sendMessage(potentialOwner1.getId(), "Ты плохо заполняешь отчет!");
                        } else {

                        }
                    }
                    if (messageText.charAt(0) != '/' && volunteer.getPotentialOwner() != null) {
                        sendMessage(volunteer.getPotentialOwner().getId(), messageText);
                    }
                }
            }
            if (update.callbackQuery() != null) {
                Long userIdFromCallBackQuery = update.callbackQuery().message().chat().id();
                PotentialOwner potentialOwner = potentialOwnerService.get(userIdFromCallBackQuery);
                if (potentialOwner != null) {
                        if (mainMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                            openChat(userIdFromCallBackQuery, mainMenuService.toString());
                        }
                        if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                            openChat(userIdFromCallBackQuery, newUserMenuService.toString());
                        }
                        if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                            openChat(userIdFromCallBackQuery, potentialOwnerMenuService.toString());
                        }
                        if (mainMenuService.buttonTap(update.callbackQuery()).equals("Как взять собаку из приюта")) {
                            potentialOwnerMenuService.send(userIdFromCallBackQuery, "Консультация с потенциальным хозяином");
                            potentialOwnerService.setLocationInMenu(userIdFromCallBackQuery, potentialOwnerMenuService.toString());
                        }
                        if (mainMenuService.buttonTap(update.callbackQuery()).equals("Узнать информацию о приюте")) {
                            newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                            potentialOwnerService.setLocationInMenu(userIdFromCallBackQuery, newUserMenuService.toString());
                        }
                        if (!potentialOwner.getName().isBlank()) {
                            if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                                sendMessage(userIdFromCallBackQuery, "Ваши данные уже в базе");
                                newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                            }
                        } else  {
                            if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                                sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона!");
                                potentialOwnerService.setLocationInMenu(userIdFromCallBackQuery, update.callbackQuery().data());
                            }
                        }
                        if (petService.existsPetByPotentialOwner(potentialOwner)) {
                            if (mainMenuService.buttonTap(update.callbackQuery()).equals("Прислать отчет о питомце")) {
                                potentialOwnerService.setLocationInMenu(potentialOwner.getId(), "Прислать отчет о питомце");
                                sendMessage(userIdFromCallBackQuery, reportService.getReportForm());
                                sendMessage(userIdFromCallBackQuery, "Прикрепите фото, напишите отчет и нажмите кнопку отправить");
                                //Код Яна
                            }
                            if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Список проверенных кинологов")) {
                                //sendMessage(userIdFromCallBackQuery, cynologistService.getAll());
                            }
                        } else {
                            if (mainMenuService.buttonTap(update.callbackQuery()).equals("Прислать отчет о питомце") ||
                                    potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Список проверенных кинологов")) {
                                sendMessage(userIdFromCallBackQuery, "За вами пока не числится животное");
                            }
                        }
                }
                if (potentialOwner == null) {
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                        openChat(userIdFromCallBackQuery, "Консультация без регистрации");
                    }
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Прислать отчет о питомце") ||
                            potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Список проверенных кинологов")) {
                        sendMessage(userIdFromCallBackQuery, "Для начала оставьте свои данные");
                    }
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Узнать информацию о приюте")) {
                        newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                    }
                    if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                        potentialOwnerService.add(userIdFromCallBackQuery, update.callbackQuery().data());
                        sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона");
                    }
                    if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                        potentialOwnerService.add(userIdFromCallBackQuery, update.callbackQuery().data());
                        sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона");
                    }
                    if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                        openChat(userIdFromCallBackQuery, "Консультация без регистрации");
                    }
                }
                if (mainMenuService.buttonTap(update.callbackQuery()).equals("Как взять собаку из приюта")) {
                    potentialOwnerMenuService.send(userIdFromCallBackQuery, "Консультация с потенциальным хозяином");
                }
                if (newUserMenuService.buttonTap(update.callbackQuery()).equals("О приюте")) {
                    sendMessage(userIdFromCallBackQuery, newUserMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Техника безопасности на территории приюта")) {
                    sendMessage(userIdFromCallBackQuery, newUserMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Расписание и адрес")) {
/*                      sendMessage(userIdFromCallBackQuery, shelterService.getShelterDescription());
                        sendMessage(userIdFromCallBackQuery, shelterService.getShelterLocation());
                        sendPhoto(userIdFromCallBackQuery, shelterService.getLocationPhoto(), "Схема проезда");*/
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Правила знакомства с животным")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Список необходимых документов")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Рекомендации по транспортировке")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Обустройство дома для щенка")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Обустройство дома для собаки")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Обустройство дома для собаки с ограниченным возможностями")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Советы кинолога")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
                if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Причины отказа")) {
                    sendMessage(userIdFromCallBackQuery, potentialOwnerMenuService.get(update.callbackQuery().data()).getCallBack());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 0 16 * * *")
    public void notifyOwnersAboutReport() {
        List<Long> ids = reportService.reportTimer();
        ids.forEach(id -> {
            sendMessage(id, "Сегодня необходимо отправить отчет");
        });
    }

    public void sendMessage(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what);
        telegramBot.execute(sm);
    }

    public void sendPhoto(Long id, byte[] photo, String text) {
        SendPhoto msg = new SendPhoto(id, photo).caption(text);
        telegramBot.execute(msg);
    }

    public void openChat(Long id, String locationInMenu) {
        if (volunteerService.existsByPotentialOwner(null)) {
            sendMessage(id, "Волонтер скоро Вам ответит");
            PotentialOwner potentialOwner;
            if (potentialOwnerService.find(id)) {
                potentialOwner = potentialOwnerService.get(id);
            } else {
                potentialOwner = new PotentialOwner();
                potentialOwner.setId(id);
            }
            Volunteer volunteer = volunteerService.get(volunteerService.getFree().getId());
            potentialOwner.setLocationInMenu(locationInMenu);
            potentialOwnerService.save(potentialOwner);
            volunteer.setPotentialOwner(potentialOwner);
            volunteerService.save(volunteer);
            sendMessage(volunteer.getId(),
                    potentialOwner + " ждет от тебя помощи");
        } else {
            sendMessage(id, "Все волонтеры заняты, попробуйте позже");
        }
    }

    public String closeChatBy(Object botUser) {
        PotentialOwner potentialOwner;
        Volunteer volunteer;
        Long id;
        if (botUser.equals(PotentialOwner.class)) {
            potentialOwner = (PotentialOwner) botUser;
            volunteer = volunteerService.findByPotentialOwner(potentialOwner.getId());
            volunteer.setPotentialOwner(null);
            volunteerService.save(volunteer);
            sendMessage(volunteer.getId(), "Пользователь закрыл чат");
            id = potentialOwner.getId();
        } else {
            volunteer = (Volunteer) botUser;
            potentialOwner = volunteer.getPotentialOwner();
            volunteer.setPotentialOwner(null);
            volunteerService.save(volunteer);
            sendMessage(potentialOwner.getId(), "Волонтер закрыл чат");
            id = volunteer.getId();
        }
        sendMessage(id, "Вы закрыли чат");
        return potentialOwner.getLocationInMenu();
    }


}