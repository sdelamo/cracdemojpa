package datajpah2.entities;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@MappedEntity
public record Project(@Id @GeneratedValue(GeneratedValue.Type.AUTO) @Nullable Long id,
                      String code,
                      String name,
                      String description,
                      @DateCreated @Nullable LocalDate created,
                      @DateUpdated @Nullable LocalDateTime updated,
                      @Relation(mappedBy = "project", value = Relation.Kind.ONE_TO_MANY, cascade = Relation.Cascade.ALL) Set<Task> tasks) {

    public Project(String code, String name, String description, Set<Task> tasks) {
        this(null, code, name, description, null, null, tasks);
    }
    public Project(String code, String name, String description) {
        this(null, code, name, description, null, null, new HashSet<>());
    }
}
