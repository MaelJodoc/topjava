package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MyDateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Смена on 07.03.2018.
 */
public class AddMeal extends AbstractMealCommand {
    public AddMeal(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        super(request, response, mealDao);
    }

    @Override
    public void execute() throws ServletException, IOException {
        String description = request.getParameter("description");
        String caloriesString = request.getParameter("calories");
        String dateTimeString = request.getParameter("dateTime");

        if (description != null && caloriesString != null && dateTimeString != null) {
            try {
                int calories = Integer.parseInt(caloriesString);
                LocalDateTime dateTime = MyDateTimeFormatter.toLocalDateTime(dateTimeString);
                Meal meal = new Meal(dateTime, description, calories);
                mealDao.create(meal);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            new ShowMeals(request, response, mealDao).execute();
        }
    }
}
