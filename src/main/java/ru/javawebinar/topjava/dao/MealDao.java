package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Ebozavreg on 06.03.2018.
 */
public interface MealDao {
    Meal create(Meal meal);

    Meal read(long id);

    List<Meal> readAll();

    Meal update(long id, Meal newMeal);

    void delete(Meal meal);
}
