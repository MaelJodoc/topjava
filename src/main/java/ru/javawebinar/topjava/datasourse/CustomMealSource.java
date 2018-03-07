package ru.javawebinar.topjava.datasourse;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Ebozavreg on 06.03.2018.
 */
public class CustomMealSource {
    private Map<Long, Meal> mealMap;
    private AtomicLong id = new AtomicLong(0);
    private static CustomMealSource ourInstance = new CustomMealSource();

    public static CustomMealSource getInstance() {
        return ourInstance;
    }

    private CustomMealSource() {
        mealMap = new ConcurrentHashMap<>();
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public Meal create(Meal meal) {
        long id = nextId();
        meal.setId(id);
        mealMap.put(id, meal);
        return meal;
    }

    public Meal read(long id) {
        return mealMap.get(id);
    }

    public List<Meal> readAll() {
        return Collections.unmodifiableList(new ArrayList<>(mealMap.values()));
    }

    public Meal update(long id, Meal newMeal) {
        newMeal.setId(id);
        return mealMap.replace(id, newMeal);
    }

    public void delete(long id) {
        mealMap.remove(id);
    }

    private long nextId() {
        return id.addAndGet(1);
    }
}
