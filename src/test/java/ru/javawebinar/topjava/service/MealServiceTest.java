package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.assertMatch;

/**
 * Created by Смена on 19.03.2018.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
}
)
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void create() {
        final int userId = 100_000;
        List<Meal> oldMeals = mealService.getAll(userId);
        Meal meal = new Meal(LocalDateTime.now(), "test", 1488);
        mealService.create(meal, userId);
        List<Meal> newMeals = mealService.getAll(userId);
        oldMeals.add(meal);

    }
}