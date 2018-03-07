package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Смена on 07.03.2018.
 */
public abstract class AbstractMealCommand implements MealCommand {
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final MealDao mealDao;

    protected AbstractMealCommand(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        this.request = request;
        this.response = response;
        this.mealDao = mealDao;
    }

    @Override
    public abstract void execute() throws ServletException, IOException;
}
