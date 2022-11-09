package datajpah2.repositories;

import datajpah2.TasksPerYear;
import datajpah2.entities.Project;
import datajpah2.entities.Task;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.repository.GenericRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2)
public interface TaskRepository extends GenericRepository<Task, Long> {
    Task save(@Valid @NotNull @NonNull Task entity);
    void delete(@NonNull @NotNull Task entity);

    Optional<Task> findById(@NotNull @NonNull Long id);
    boolean existsById(@NotNull @NonNull Long id);
    long count();

    @Query("delete from task t where t.task_status = 'DONE'")
    int deleteCompleteTasks();

    @Query("select count(*) as tasks, year(t.due_date) as year from task t group by year(t.due_date)")
    List<TasksPerYear> countDueByYear();

    List<Task> findByDueDateLessThan(LocalDate dueDate);

    List<Task> findByDueDateLessThanEquals(LocalDate dueDate);

    List<Task> findByDueDateBefore(LocalDate dueDate);

    List<Task> findByAssigneeFirstName(String firstName);

    List<Task> findByNameLike(String name, Pageable pageable);

    List<Task> findAllOrderByDueDateDesc();

    List<Task> findAllOrderByDueDateDescAndAssigneeLastNameAsc();

    @Query(value = "select * from task t where t.name like :name",
            countQuery = "select count(*) from Task t where t.name like :name")
    Page<Task> allTasksByName(String name, Pageable pageable);

    @Query(value = "select * from task t order by t.due_date desc", nativeQuery = true)
    List<Task> allTasksSortedByDueDateDesc();
}
