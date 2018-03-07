package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.CustomMealSourceDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.web.command.MealCommand;
import ru.javawebinar.topjava.web.command.MealCommandExecutor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ebozavreg on 04.03.2018.
 */
public class MealServlet extends HttpServlet {
    private static final MealDao mealDao = new CustomMealSourceDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MealCommand command = MealCommandExecutor.getCommand(req, resp, mealDao);
        if (command != null) command.execute();
    }
}
