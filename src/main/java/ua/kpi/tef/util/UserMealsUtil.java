package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        
        
        Map<LocalDate, Integer> limitCal = mealList.stream().collect(
                Collectors.groupingBy(
                        (meal) -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));
        System.out.println(limitCal + "\n");


        List<UserMealWithExceed> meals = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                meals.add(new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        limitCal.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        for (int i = 0; i < meals.size(); i++) {
            System.out.println(meals.get(i).toString());
        }

//        List<UserMeal> mealsExc = mealList.stream().filter((meal) -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());
//        for (int i = 0; i < meals.size(); i++) {
//            System.out.println(mealsExc.get(i).toString());
//        }

        return meals;
    }
}
