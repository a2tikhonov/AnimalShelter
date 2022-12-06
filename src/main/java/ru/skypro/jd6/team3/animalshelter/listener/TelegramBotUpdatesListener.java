package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;
import ru.skypro.jd6.team3.animalshelter.service.NewUserMenuService;
import ru.skypro.jd6.team3.animalshelter.service.PotentialOwnerService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final MainMenuService mainMenuService;

    private final NewUserMenuService newUserMenuService;

    private final PotentialOwnerService potentialOwnerService;

    Boolean potentialOwnerDetected = false;





    public TelegramBotUpdatesListener(TelegramBot telegramBot, MainMenuService mainMenuService,
                                      NewUserMenuService newUserMenuService, PotentialOwnerService potentialOwnerService) {
        this.telegramBot = telegramBot;
        this.newUserMenuService = newUserMenuService;
        this.mainMenuService = mainMenuService;
        this.potentialOwnerService = potentialOwnerService;
    }

    private static final Pattern pattern = Pattern.compile("([0-9.:\\s]{16})(\\s)([\\W+|\\w]+)");

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            potentialOwnerDetected = false;
            if (update.message() != null) {
                Long userIdFromMessage = update.message().chat().id();
                potentialOwnerDetected = potentialOwnerService.find(userIdFromMessage);
                String messageText = update.message().text();
                if (messageText.equalsIgnoreCase("/start")) {
                    mainMenuService.send(userIdFromMessage, "Добро пожаловать!");
                }
                if (potentialOwnerDetected && potentialOwnerService.get(userIdFromMessage).getPhone().isBlank()
                        && !update.message().text().isBlank()) {
                    String msg = update.message().text();
                    if (msg.matches("^[а-яА-Я]+\\s\\+[0-9]+$")) {
                        potentialOwnerService.add(userIdFromMessage, msg);
                    }
                }
            }
            if (update.callbackQuery() != null) {
                Long userIdFromCallBackQuery = update.callbackQuery().message().chat().id();
                potentialOwnerDetected = potentialOwnerService.find(userIdFromCallBackQuery);
                if (mainMenuService.buttonTap(update.callbackQuery()).equals("Узнать информацию о приюте")) {
                    newUserMenuService.send(userIdFromCallBackQuery, "Консультация с новым пользователем");
                }
                if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Записать контактные данные")) {
                    if (!potentialOwnerDetected) {
                        potentialOwnerDetected = potentialOwnerService.add(update.callbackQuery().from().id());
                        sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона");
                    } else {
                        if (!potentialOwnerService.get(userIdFromCallBackQuery).getPhone().isBlank()) {
                            sendMessage(userIdFromCallBackQuery, "Ваши данные уже в базе");
                        } else {
                            sendMessage(userIdFromCallBackQuery, "Введите свое имя и номер телефона");
                        }
                    }
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

}