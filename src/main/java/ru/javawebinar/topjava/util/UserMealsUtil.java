package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        mealList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            mealList.add(new UserMeal(LocalDateTime.of(i, Month.APRIL, 30, 10, 0), "test", i));
        }
        for (int i = 0; i <20 ; i++) {
            printTimeOf(UserMealsUtil::getFilteredWithExceededV3, mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        }
        System.out.println();

        for (int i = 0; i <20 ; i++) {
            printTimeOf(UserMealsUtil::getFilteredWithExceeded, mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        }



        //getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
        //.forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();

        //System.out.println(getFilteredWithExceededV3(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));


    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        class UserMealPerDay {
            int caloriesPerDay;
            List<UserMeal> list = new ArrayList<>();
        }
        Map<LocalDate, UserMealPerDay> map = new HashMap<>();
        for (UserMeal um : mealList) {
            LocalDate key = um.getDateTime().toLocalDate();
            UserMealPerDay userMealPerDay = map.get(key);
            if (userMealPerDay == null) userMealPerDay = new UserMealPerDay();
            userMealPerDay.caloriesPerDay += um.getCalories();
            if (TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealPerDay.list.add(um);
            }
            map.put(key, userMealPerDay);
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMealPerDay umpd : map.values()) {
            boolean exceed = umpd.caloriesPerDay > caloriesPerDay;
            for (UserMeal um : umpd.list) {
                result.add(new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), exceed));
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededV2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        long start = new Date().getTime();
        Stream<List<UserMeal>> userMealsPerDayStream = mealList.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream();
        System.out.println("To list=" + (new Date().getTime() - start));
        start = new Date().getTime();

        Map<Boolean, Optional<List<UserMeal>>> map = userMealsPerDayStream
                .collect(Collectors.partitioningBy(list -> list.stream()
                                        .mapToInt(UserMeal::getCalories)
                                        .sum() > caloriesPerDay
                                , Collectors.reducing((accList, concatList) -> {
                                    concatList.addAll(accList);
                                    return concatList;
                                })
                        )
                );
        System.out.println("To map=" + (new Date().getTime() - start));
        start = new Date().getTime();

        Optional<List<UserMealWithExceed>> result = map.entrySet().stream()
                .map(entry -> {
                    boolean key = entry.getKey();
                    List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
                    if (entry.getValue().isPresent()) {
                        userMealWithExceedList = entry.getValue().get().stream()
                                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(), key))
                                .collect(Collectors.toList());
                    }
                    return userMealWithExceedList;
                })
                .reduce((u1, u2) -> {
                    u1.addAll(u2);
                    return u1;
                });
        System.out.println("To result=" + (new Date().getTime() - start));
        if (result.isPresent()) return result.get();
        return new ArrayList<>();
    }

    public static List<UserMealWithExceed> getFilteredWithExceededV3(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Stream<List<UserMeal>> userMealsPerDayStream = mealList.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream();

        return userMealsPerDayStream
                .collect(
                        Collector.of(
                                ArrayList<UserMealWithExceed>::new,
                                (ArrayList<UserMealWithExceed> result, List<UserMeal> userMealList) -> {
                                    int totalCaloriesPerDay = userMealList.stream()
                                            .mapToInt(UserMeal::getCalories)
                                            .sum();
                                    boolean exceed = totalCaloriesPerDay > caloriesPerDay;
                                    List<UserMealWithExceed> userMealWithExceedList = userMealList.stream()
                                            .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                                            .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed))
                                            .collect(Collectors.toList());
                                    result.addAll(userMealWithExceedList);
                                },
                                (ArrayList<UserMealWithExceed> result1, ArrayList<UserMealWithExceed> result2) -> {
                                    result1.addAll(result2);
                                    return result1;
                                }
                        )
                );
    }

    @FunctionalInterface
    private interface TestFunction {
        List<UserMealWithExceed> getList(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay);
    }

    private static void printTimeOf(TestFunction tf, List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        long start = new Date().getTime();
        tf.getList(mealList, startTime, endTime, caloriesPerDay);
        System.out.println("Time complete=" + (new Date().getTime() - start));
    }
}
