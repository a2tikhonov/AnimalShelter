package ru.skypro.jd6.team3.animalshelter.model;

import java.util.HashMap;
import java.util.Map;

final class BotInfoMenuConstants {

    static final String button1 = "Узнать информацию о приюте";

    static final String button2 = "Как взять собаку из приюта";

    static final String button3 = "Прислать отчет о питомце";

    static final String button4 = "Позвать волонтера";

    static final String SHELTER_INFO = "Приют для собак. Часы работы пн-сб с 9:00 до 20:00.";

    static final String HOT_TO_ADOPT = "Обсудите сумму с куратором заранее. Подготовьтесь к приезду питомца домой: купите для него лежанку для сна," +
            " игрушки и корм, объясните детям, как нужно вести себя с собакой." +
            " Лучше приехать за ней на своей машине или нанять такси";

    static final String SUBMIT_PET_INFORMATION = "В ежедневный отчет входит следующая информация: Фото животного," + "\r\n" +
            "* Рацион животного, " + "\r\n" +
            "* Общее самочувствие и привыкание к новому месту," + "\r\n" +
            "* Изменение в поведении: отказ от старых привычек, приобретение новых." + "\r\n" +
            "Отчет нужно присылать каждый день, ограничений в сутках по времени сдачи отчета нет";

    static final String ASK_FOR_VOLUNTEER = "Волонтер скоро Вам ответит";

    static final HashMap<String, String> buttons = new HashMap<>(Map.of(button1,SHELTER_INFO,
            button2, HOT_TO_ADOPT,
            button3, SUBMIT_PET_INFORMATION,
            button4,ASK_FOR_VOLUNTEER ));


    private BotInfoMenuConstants() {
    }

}
