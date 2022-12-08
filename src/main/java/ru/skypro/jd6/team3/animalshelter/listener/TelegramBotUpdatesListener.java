package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
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
            Boolean potentialOwnerDetected;
            Boolean volunteerDetected;
            if (update.message() != null) {
                Long userIdFromMessage = update.message().chat().id();
                volunteerDetected = volunteerService.find(userIdFromMessage);
                potentialOwnerDetected = potentialOwnerService.find(userIdFromMessage);
                String messageText = "";
                if (update.message().text() != null) {
                    messageText = update.message().text();
                }
                if (!potentialOwnerDetected && !volunteerDetected) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        mainMenuService.send(userIdFromMessage, "Добро пожаловать " + update.message().from().username() + "!");
                    }
                }
                if (potentialOwnerDetected) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        mainMenuService.send(userIdFromMessage, "Добро пожаловать " +
                                potentialOwnerService.get(userIdFromMessage).getName() + "!");
                        potentialOwnerService.setLocationInMenu(userIdFromMessage, mainMenuService.toString());
                    }
                    if (messageText.equalsIgnoreCase("/close")
                            && potentialOwnerService.get(userIdFromMessage).getVolunteer() != null) {
                        String locationInMenu = closeChatByOwner(userIdFromMessage);
                        switch (locationInMenu) {
                            case ("MainMenu"):
                                mainMenuService.send(userIdFromMessage, "Добро пожаловать " +
                                        potentialOwnerService.get(userIdFromMessage).getName() + "!");
                                break;
                            case ("NewUserMenu"):
                                newUserMenuService.send(userIdFromMessage, "Консультация с новым пользователем");
                                break;
                            case ("PotentialOwnerMenu"):
                                potentialOwnerMenuService.send(userIdFromMessage, "Консультация с потенциальным хозяином");
                                break;
                        }
                    }
                    if (potentialOwnerService.get(userIdFromMessage).getLocationInMenu().equals("Записать контактные данные") &&
                            potentialOwnerService.get(userIdFromMessage).getName().isBlank()
                            && !messageText.isBlank()) {
                        if (messageText.matches("^[а-яА-Я]+\\s\\+*[0-9]+$")) {
                            potentialOwnerService.add(userIdFromMessage, messageText, "Консультация с новым пользователем");
                            sendMessage(userIdFromMessage, "Данные успешно записаны");
                            newUserMenuService.send(userIdFromMessage, "Консультация с новым пользователем");
                        } else {
                            sendMessage(userIdFromMessage, "Введите данные корректно: сначала имя кириллицей, затем номер");
                        }
                    }
                    if (messageText.charAt(0) != '/' && potentialOwnerService.get(userIdFromMessage).getVolunteer() != null) {
                        PotentialOwner potentialOwner = potentialOwnerService.get(userIdFromMessage);
                        sendMessage(potentialOwner.getVolunteer().getId(), messageText);
                    }
                }
                if (volunteerDetected) {
                    if (messageText.equalsIgnoreCase("/start")) {
                        sendMessage(userIdFromMessage, "Добро пожаловать волонтер!");
                    }
                    if (messageText.equalsIgnoreCase("/close") && volunteerService.get(userIdFromMessage).isBusy()) {
                        closeChatByVolunteer(userIdFromMessage);
                    }
                    if (messageText.charAt(0) != '/' && volunteerService.get(userIdFromMessage).getPotentialOwner() != null) {
                        Volunteer volunteer = volunteerService.get(userIdFromMessage);
                        sendMessage(volunteer.getPotentialOwner().getId(), messageText);
                    }
                }
            }
            if (update.callbackQuery() != null) {
                Long userIdFromCallBackQuery = update.callbackQuery().message().chat().id();
                //potentialOwnerDetected = potentialOwnerService.find(userIdFromCallBackQuery);
                PotentialOwner potentialOwner = potentialOwnerService.get(userIdFromCallBackQuery);
                if (potentialOwner != null && !potentialOwner.getName().isBlank()) {
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Узнать информацию о приюте")) {
                        newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                        potentialOwnerService.setLocationInMenu(userIdFromCallBackQuery, newUserMenuService.toString());
                    }

                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Как взять собаку из приюта")) {
                        potentialOwnerMenuService.send(userIdFromCallBackQuery, "Консультация с потенциальным хозяином");
                        potentialOwnerService.setLocationInMenu(userIdFromCallBackQuery, potentialOwnerMenuService.toString());
                    }
                    if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                        sendMessage(userIdFromCallBackQuery, "Ваши данные уже в базе");
                        newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                    }
                    if (potentialOwnerMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                        openChat(userIdFromCallBackQuery, potentialOwnerMenuService.toString());
                    }

                } else if (potentialOwner != null && potentialOwner.getName().isBlank()) {
                    if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                        sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона!!!!!");
                        potentialOwnerService.setLocationInMenu(userIdFromCallBackQuery, update.callbackQuery().data());
                    }
                }
                    if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                        potentialOwnerService.add(userIdFromCallBackQuery, update.callbackQuery().data());
                        sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона");
                    }
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Узнать информацию о приюте")) {
                        newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                    }
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Прислать отчет о питомце") ||
                            mainMenuService.buttonTap(update.callbackQuery()).equals("Как взять собаку из приюта")) {
                        sendMessage(userIdFromCallBackQuery, "Сначала оставьте контактные данные");
                    }
                    if (mainMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                        openChat(userIdFromCallBackQuery, mainMenuService.toString());
                    }
                    if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Позвать волонтера")) {
                        openChat(userIdFromCallBackQuery, newUserMenuService.toString());
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