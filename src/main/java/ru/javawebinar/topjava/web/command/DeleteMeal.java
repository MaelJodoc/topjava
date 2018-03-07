package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Смена on 07.03.2018.
 */
public class DeleteMeal extends AbstractMealCommand {

    public DeleteMeal(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        super(request, response, mealDao);
    }

    @Override
    public void execute() throws ServletException, IOException {
        String param = request.getParameter("id");
        if (param != null) {
            try {
                long id = Long.parseLong(param);
                mealDao.delete(id);
                new ShowMeals(request, response, mealDao).execute();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            new ShowMeals(request, response, mealDao).execute();
        }
    }
}
