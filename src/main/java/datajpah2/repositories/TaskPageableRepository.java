package datajpah2.repositories;

import datajpah2.entities.Task;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.Sort;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.H2)
public interface TaskPageableRepository extends PageableRepository<Task, Long> {

    @Override
    List<Task> findAll(Sort sort);

    Slice<Task> list(Pageable pageable);
}
