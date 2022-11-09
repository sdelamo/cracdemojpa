package datajpah2;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class TasksPerYear {

    private final Integer tasks;
    private final Integer year;

    public TasksPerYear(Integer tasks, Integer year) {
        this.tasks = tasks;
        this.year = year;
    }

    public Integer getTasks() {
        return tasks;
    }

    public Integer getYear() {
        return year;
    }
}
