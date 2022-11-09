package datajpah2.entities;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

import java.util.Set;

@MappedEntity
public record Worker(@Nullable @Id @GeneratedValue(GeneratedValue.Type.AUTO) Long id,
                     String email,
                     String firstName,
                     String lastName) {
    public Worker(String email, String firstName, String lastName) {
        this(null, email, firstName, lastName);
    }
}
