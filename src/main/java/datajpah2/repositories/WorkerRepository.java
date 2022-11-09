package datajpah2.repositories;

import datajpah2.entities.Task;
import datajpah2.entities.Worker;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JdbcRepository(dialect = Dialect.H2)
public interface WorkerRepository extends GenericRepository<Worker, Long> {
    Worker save(@Valid @NotNull @NonNull Worker entity);

    void delete(@NonNull @NotNull Task entity);
}
