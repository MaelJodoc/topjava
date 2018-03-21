package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealServiceTestData.*;

/**
 * Created by Смена on 20.03.2018.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
}
)
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    private static Logger logger = LoggerFactory.getLogger(MealServiceImplTest.class);


    @Before
    @Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
    public void init() {
        logger.info("populate db");
    }

    @AfterClass
    @Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
    public static void exit() {
        logger.info("populate db");
    }


    @Test
    public void create() {
        NEW_MEAL.setId(null);
        Meal created = mealService.create(NEW_MEAL, USER_ID);
        NEW_MEAL.setId(created.getId());
        List<Meal> meals = mealService.getAll(USER_ID);
        assertTrue(created == NEW_MEAL && created.getId() != null && meals.contains(NEW_MEAL));
    }

    @Test
    public void get() throws Exception {
        assertTrue(mealService.get(MEAL_ID_OF_USER, USER_ID).equals(MEAL_OF_USER));
    }

    @Test(expected = NotFoundException.class)
    public void unavailableGet() {
        mealService.get(WRONG_MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherPeopleMeal() {
        mealService.get(MEAL_ID_OF_USER, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        Meal meal = mealService.create(MEAL_OF_USER, USER_ID);
        mealService.delete(meal.getId(), USER_ID);
        List<Meal> meals = mealService.getAll(USER_ID);
        assertFalse(meals.contains(meal));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = mealService.getBetweenDateTimes(
                LocalDateTime.of(2018, 3, 15, 7, 0),
                LocalDateTime.of(2018, 3, 15, 18, 59),
                USER_ID);
        assertTrue(
                meals.size() == 2 &&
                        meals.contains(new Meal(
                                100_002,
                                LocalDateTime.of(2018, 3, 15, 7, 0),
                                "завтрак",
                                700)) &&
                        meals.contains(new Meal(
                                100_003,
                                LocalDateTime.of(2018, 3, 15, 14, 0),
                                "обед",
                                1200))
        );
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = mealService.getAll(ADMIN_ID);
        assertTrue(meals.size() == 3 &&
                meals.contains(new Meal(
                        100_011,
                        LocalDateTime.of(2018, 3, 15, 7, 0),
                        "завтрак админа",
                        700)) &&
                meals.contains(new Meal(
                        100_012,
                        LocalDateTime.of(2018, 3, 15, 14, 0),
                        "обед админа",
                        1200)) &&
                meals.contains(new Meal(
                        100_013,
                        LocalDateTime.of(2018, 3, 15, 20, 0),
                        "ужин админа",
                        500))
        );
    }

    @Test(expected = NotFoundException.class)
    public void getAllForUnavailable() {
        mealService.getAll(WRONG_USER_ID);
    }

    @Test
    public void update() throws Exception {
        Meal newMeal = new Meal(100_002,
                LocalDateTime.of(1111, 1, 1, 1, 1),
                "updated",
                1488);
        Meal updated = mealService.update(newMeal, USER_ID);
        assertTrue(newMeal.equals(updated));
    }

    @Test(expected = NotFoundException.class)
    public void updateNoMeal() {
        new Meal(WRONG_MEAL_ID,
                LocalDateTime.of(1111, 1, 1, 1, 1),
                "updated",
                1488);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() {
        Meal newMeal = mealService.create(NEW_MEAL, USER_ID);
        Meal updated = new Meal(newMeal.getId(),
                LocalDateTime.of(1111, 1, 1, 1, 1),
                "updated",
                1488);
        mealService.update(updated, ADMIN_ID);
    }
}