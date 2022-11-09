package datajpah2.entities;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

import java.time.LocalDate;
import java.util.UUID;

@MappedEntity
public record Task(@Id @GeneratedValue(GeneratedValue.Type.AUTO) Long id,
                   String uuid,
                   String name,
                   String description,
                   LocalDate dueDate,
                   TaskStatus taskStatus,
                   @Nullable @Relation(value = Relation.Kind.MANY_TO_ONE) Project project,
                   @Nullable @Relation(value = Relation.Kind.MANY_TO_ONE) Worker assignee) {

    public Task(String name, String description, LocalDate dueDate, TaskStatus taskStatus) {
        this(null,  UUID.randomUUID().toString(), name, description, dueDate, taskStatus, null, null);
    }
    public Task(String name, String description, LocalDate dueDate, TaskStatus taskStatus, Worker assignee) {
        this(null,  UUID.randomUUID().toString(), name, description, dueDate, taskStatus, null, assignee);
    }
}
