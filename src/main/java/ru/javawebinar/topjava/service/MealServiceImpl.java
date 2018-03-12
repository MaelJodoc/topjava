package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        repository.delete(mealId, userId);
    }

    @Override
    public Meal get(int mealId, int userId) throws NotFoundException {
        return repository.get(mealId, userId);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        Meal fromRepo = repository.get(meal.getId(), userId);
        if (fromRepo == null) throw new NotFoundException("can not update meal because it not exist");
        if (fromRepo.getUserId() != userId) throw new NotFoundException("can not update meal of another user");
        repository.save(meal);
    }

    @Override
    public List<Meal> getAll() {
        return repository.getAll();
    }


    @Override
    public List<Meal> getAllForUser(int userId) {
        return getAll().stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }
}