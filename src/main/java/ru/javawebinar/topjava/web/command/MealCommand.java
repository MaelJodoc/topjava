package ru.javawebinar.topjava.web.command;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Смена on 07.03.2018.
 */
public interface MealCommand {
    void execute() throws ServletException, IOException;
}
