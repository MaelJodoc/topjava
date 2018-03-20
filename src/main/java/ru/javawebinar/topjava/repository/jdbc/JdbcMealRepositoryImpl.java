package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private final RowMapper<Meal> ROW_MAPPER;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        ROW_MAPPER = new RowMapper<Meal>() {
            @Nullable
            @Override
            public Meal mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Meal(resultSet.getInt("id"),
                        resultSet.getTimestamp("date_time").toLocalDateTime(),
                        resultSet.getString("description"),
                        resultSet.getInt("calories"));
            }
        };
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            int mealId = simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource).intValue();
            meal.setId(mealId);
        } else {
            String sql = "UPDATE meals SET date_time=:date_time, description=:description, calories=:calories WHERE (user_id=:user_id AND id=:id)";
            int update = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            if (update == 0) return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT* FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT* FROM meals WHERE user_id=? ORDER BY date_time", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Timestamp sd = Timestamp.valueOf(startDate);
        Timestamp ed = Timestamp.valueOf(endDate);
        return jdbcTemplate.query("SELECT* FROM meals WHERE user_id=? AND (date_time BETWEEN ? AND ?)", ROW_MAPPER, userId, sd, ed);
    }
}
