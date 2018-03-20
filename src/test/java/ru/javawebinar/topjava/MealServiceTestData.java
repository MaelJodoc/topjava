package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

/**
 * Created by Смена on 20.03.2018.
 */
public class MealServiceTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int WRONG_MEAL_ID = START_SEQ - 100;
    public static final int MEAL_ID_OF_USER = START_SEQ + 2;
    public static final Meal MEAL_OF_USER = new Meal(MEAL_ID_OF_USER, LocalDateTime.of(2018, 3, 15, 7, 0), "завтрак", 700);
    public static final LocalDateTime TEST_TIME = LocalDateTime.of(1488, 1, 2, 10, 15);
    public static final Meal NEW_MEAL = new Meal(TEST_TIME, "test", 999);

}
