package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
            if (update.message() != null && update.message().text().equalsIgnoreCase("/start") ) {
                mainMenuService.send(update.message().chat().id(), "Этап0");
            }
            if (mainMenuService.buttonTap(update.callbackQuery()).equals("Кнопка1")) {
                newUserMenuService.send(update.callbackQuery().message().chat().id(), "Этап1");
            }
            if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Кнопонька1")) {
                sendMessage(update.callbackQuery().message().chat().id(), "Кнопонька1 нажалась!");

            }
            if (newUserMenuService.buttonTap(update.callbackQuery()).equals("Кнопонька2")) {
                    sendMessage(update.callbackQuery().message().chat().id(), "Введите свое имя и номер телефона");
                    potentialOwnerService.add(update.callbackQuery().from().id());
                    potentialOwnerDetected = true;
            }
            if (update.message() != null && potentialOwnerDetected && !update.message().text().isBlank() && potentialOwnerService.find(update.message().chat().id())) {
                String msg = update.message().text();
                potentialOwnerService.add(update.message().chat().id(), msg);
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