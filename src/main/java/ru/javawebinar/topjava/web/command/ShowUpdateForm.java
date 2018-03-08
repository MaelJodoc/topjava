package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.MyDateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Смена on 08.03.2018.
 */
public class ShowUpdateForm extends AbstractMealCommand {
    public ShowUpdateForm(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        super(request, response, mealDao);
    }

    @Override
    public void execute() throws ServletException, IOException {
        String idString = request.getParameter("id");
        String dateTimeString = request.getParameter("dateTime");
        String caloriesString = request.getParameter("calories");
        String description = request.getParameter("description");
        if (idString != null && dateTimeString != null && caloriesString != null && description != null) {
            try {
                long id = Long.parseLong(idString);
                LocalDateTime dateTime = MyDateTimeFormatter.toLocalDateTime(dateTimeString);
                int calories = Integer.parseInt(caloriesString);
                request.setAttribute("meal", new Meal(calories, description, dateTime, id));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("editmeal.jsp").forward(request, response);
    }
}
