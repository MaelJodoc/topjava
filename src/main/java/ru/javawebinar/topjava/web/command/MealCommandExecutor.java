package ru.javawebinar.topjava.web.command;

import ru.javawebinar.topjava.dao.MealDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Смена on 07.03.2018.
 */
public class MealCommandExecutor {
    public static MealCommand getCommand(HttpServletRequest request, HttpServletResponse response, MealDao mealDao) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("action")) {
            switch (parameterMap.get("action")[0]) {
                case "addMeal":
                    return new AddMeal(request, response, mealDao);
                case "showMeals":
                    return new ShowMeals(request, response, mealDao);
                case "deleteMeal":
                    return new DeleteMeal(request, response, mealDao);
                case "updateMeal":
                    return new UpdateMeal(request, response, mealDao);
            }
        } else return new ShowMeals(request, response, mealDao);
        return null;
    }
}
