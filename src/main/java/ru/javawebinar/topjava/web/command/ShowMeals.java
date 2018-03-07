package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Смена on 07.03.2018.
 */
public class ShowMeals extends AbstractMealCommand {
    public ShowMeals(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        super(request, response, mealDao);
    }

    @Override
    public void execute() throws ServletException, IOException {
        List<Meal> meals = mealDao.readAll();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceededInOnePass2(meals, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", mealWithExceeds);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
