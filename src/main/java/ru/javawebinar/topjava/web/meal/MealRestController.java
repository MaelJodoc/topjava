package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAllForUser() {
        List<Meal> meals = service.getAllForUser(AuthorizedUser.id());
        return MealsUtil.getFilteredWithExceeded(meals, AuthorizedUser.getCaloriesPerDay(), LocalTime.MIN, LocalTime.MAX);
    }

    public Meal getMeal(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public void addMeal(Meal meal) {
        service.create(meal);
    }

    public void deleteMeal(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public void updateMeal(Meal meal) {
        service.update(meal, AuthorizedUser.id());
    }

    public void save(Meal meal) {
        meal.setUserId(AuthorizedUser.id());
        service.create(meal);
    }

}