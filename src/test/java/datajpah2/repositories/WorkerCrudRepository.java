package datajpah2.repositories;

import datajpah2.entities.Worker;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.H2)
public interface WorkerCrudRepository extends CrudRepository<Worker, Long> {
}
