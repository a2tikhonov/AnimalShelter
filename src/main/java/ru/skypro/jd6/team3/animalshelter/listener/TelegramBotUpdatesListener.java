package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InputFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.service.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    private final MainMenuService mainMenuService;

    private final NewUserMenuService newUserMenuService;

    private final PotentialOwnerMenuService potentialOwnerMenuService;

    private final PotentialOwnerService potentialOwnerService;

    private final VolunteerService volunteerService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MainMenuService mainMenuService,
                                      NewUserMenuService newUserMenuService,
                                      PotentialOwnerMenuService potentialOwnerMenuService,
                                      PotentialOwnerService potentialOwnerService,
                                      VolunteerService volunteerService) {
        this.telegramBot = telegramBot;
        this.mainMenuService = mainMenuService;
        this.newUserMenuService = newUserMenuService;
        this.potentialOwnerMenuService = potentialOwnerMenuService;
        this.potentialOwnerService = potentialOwnerService;
        this.volunteerService = volunteerService;
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
                        if (potentialOwner.getName().isBlank() || potentialOwner.getPet() != null) {
                            potentialOwnerService.delete(userIdFromMessage);
                        }
                    }
                    if (messageText.equalsIgnoreCase("/close")
                            && potentialOwner.getVolunteer() != null) {
                        String locationInMenu = closeChatByOwner(userIdFromMessage);
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
                    if (messageText.charAt(0) != '/' && potentialOwner.getVolunteer() != null) {
                        sendMessage(potentialOwner.getVolunteer().getId(), messageText);
                    }
                }
                if (volunteer != null) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        sendMessage(userIdFromMessage, "Добро пожаловать волонтер!");
                    }
                    if (messageText.equalsIgnoreCase("/close") && volunteer.isBusy()) {
                        closeChatByVolunteer(userIdFromMessage);
                        if (volunteer.getPotentialOwner().getLocationInMenu().equals("Консультация без регистрации")) {
                            potentialOwnerService.delete(potentialOwner.getId());
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
                    if (potentialOwner.getPet() != null) {
                        if (mainMenuService.buttonTap(update.callbackQuery()).equals("Прислать отчет о питомце")) {
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
                        openChat(userIdFromCallBackQuery, mainMenuService.toString());
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
                        openChat(userIdFromCallBackQuery, potentialOwnerMenuService.toString());
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


    private void greetMessage(Update update) {
        SendMessage responseMessage = new SendMessage(update.message().chat().id(), "С возвращением, @" + update.message().chat().username() + "\n !");
        telegramBot.execute(responseMessage);
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
        if (volunteerService.isAnyFree()) {
            sendMessage(id, "Волонтер скоро Вам ответит");
            PotentialOwner potentialOwner = potentialOwnerService.get(id);
            Volunteer volunteer = volunteerService.get(volunteerService.getFree().getId());
            potentialOwner.setVolunteer(volunteer);
            potentialOwner.setLocationInMenu(locationInMenu);
            potentialOwnerService.save(potentialOwner);
            volunteer.setPotentialOwner(potentialOwner);
            volunteer.setBusy(true);
            volunteerService.save(volunteer);
            sendMessage(potentialOwner.getVolunteer().getId(),
                    potentialOwner + " ждет от тебя помощи");
        } else {
            sendMessage(id, "Все волонтеры заняты, попробуйте позже");
        }
    }

    public String closeChatByOwner(Long id) {
        PotentialOwner potentialOwner = potentialOwnerService.get(id);
        Volunteer volunteer = volunteerService.get(potentialOwner.getVolunteer().getId());
        potentialOwner.setVolunteer(null);
        volunteer.setPotentialOwner(null);
        volunteer.setBusy(false);
        potentialOwnerService.save(potentialOwner);
        volunteerService.save(volunteer);
        sendMessage(volunteer.getId(), "Пользователь закрыл чат");
        sendMessage(id, "Вы закрыли чат");
        return potentialOwner.getLocationInMenu();
    }

    public String closeChatByVolunteer(Long id) {
        Volunteer volunteer = volunteerService.get(id);
        PotentialOwner potentialOwner = potentialOwnerService.get(volunteer.getPotentialOwner().getId());
        potentialOwner.setVolunteer(null);
        volunteer.setPotentialOwner(null);
        volunteer.setBusy(false);
        volunteerService.save(volunteer);
        potentialOwnerService.save(potentialOwner);
        sendMessage(potentialOwner.getId(), "Волонтер закрыл чат");
        sendMessage(id, "Вы закрыли чат");
        return potentialOwner.getLocationInMenu();
    }


}