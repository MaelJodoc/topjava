package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.CustomMealSourceDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Ebozavreg on 04.03.2018.
 */
public class MealServlet extends HttpServlet {
    private static MealDao mealDao = new CustomMealSourceDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = mealDao.readAll();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceededInOnePass2(meals, LocalTime.MIN, LocalTime.MAX, 2000);
        mealWithExceeds.sort((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()));
        req.setAttribute("meals", mealWithExceeds);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
