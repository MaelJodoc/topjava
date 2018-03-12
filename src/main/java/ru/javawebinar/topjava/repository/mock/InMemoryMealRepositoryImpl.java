package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(int mealId, int userId) {
        if (mealId != userId) throw new NotFoundException("you try delete meal of another user");
        if (repository.get(mealId) == null) throw new NotFoundException("meal not found");
        repository.remove(mealId);
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        if (meal == null) throw new NotFoundException("meal with this id not exist");
        if (meal.getUserId() != userId)
            throw new NotFoundException("you try to get meal of another user. It is impossible");
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> meals = new ArrayList<>(repository.values());
        meals.sort(Comparator.comparing(Meal::getDateTime));
        return meals;
    }
}

