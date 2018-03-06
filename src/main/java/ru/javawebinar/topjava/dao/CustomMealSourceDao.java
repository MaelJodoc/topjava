package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.datasourse.CustomMealSource;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Ebozavreg on 06.03.2018.
 */
public class CustomMealSourceDao implements MealDao {
    private CustomMealSource mealSource = CustomMealSource.getInstance();

    @Override
    public Meal create(Meal meal) {
        return mealSource.create(meal);
    }

    @Override
    public Meal read(long id) {
        return mealSource.read(id);
    }

    @Override
    public List<Meal> readAll() {
        return mealSource.readAll();
    }

    @Override
    public Meal update(long id, Meal newMeal) {
        return mealSource.update(id, newMeal);
    }

    @Override
    public void delete(Meal meal) {
        mealSource.delete(meal);
    }
}
