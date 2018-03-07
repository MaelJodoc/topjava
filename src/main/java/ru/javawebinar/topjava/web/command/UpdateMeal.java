package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Смена on 07.03.2018.
 */
public class UpdateMeal extends AbstractMealCommand {
    public UpdateMeal(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        super(request, response, mealDao);
    }

    @Override
    public void execute() {

    }
}
