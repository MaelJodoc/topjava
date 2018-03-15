package ru.javawebinar.topjava.to;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Смена on 15.03.2018.
 */
public class DateFilter {
    private LocalDate startDate;
    private LocalDate stopDate;
    private LocalTime startTime;
    private LocalTime stopTime;

    public DateFilter(LocalDate startDate, LocalDate stopDate, LocalTime startTime, LocalTime stopTime) {
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }
}
