package datajpah2.repositories;

import datajpah2.ProjectCode;
import datajpah2.entities.Project;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.GenericRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2)
public interface ProjectRepository extends GenericRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Optional<Project> findById(@NotNull @NonNull Long id);
    long count();

    Project save(@Valid @NotNull @NonNull Project entity);
    Iterable<Project> saveAll(@Valid @NotNull @NonNull Iterable<Project> entities);
    void delete(@NonNull @NotNull Project entity);


    @Query("select * from Project p where p.description like CONCAT(:prefix, '%', :suffix)")
    List<Project> findWithDescriptionWithPrefixAndSuffix(String prefix, String suffix);

    @Query(value = "select * from project p where LENGTH(p.description) < :length", nativeQuery = true)
    List<Project> findWithDescriptionIsShorterThan(@Parameter("length") int len);

    @Query("select * from project p where p.description like :keyword")
    List<Project> findWithDescriptionLike(String keyword);

    @Query("select * from project p where p.code in(:codes)")
    List<Project> findWithCodeIn(Collection<String> codes);

    // Micronaut DATA JPA
    //@Query(nativeQuery = true, value = SQL)
    //@Query(value = "JPQL query")

    // Micronaut DATA JPA
    //@Query(value = "SQL query")

    @Query("select * from project p where p.name=:name and p.description=:description")
    List<Project> findByNameAndDescriptionParameterNamedBind(String name, String description);

    @Query("select * from project p where p.name=:name and p.description=:description")
    List<Project> findByNameAndDescriptionParameterNamedBindWithAtParameter(@Parameter("name") String na,
                                                                            @Parameter("description") String des);

    @Query("select * from project as p where p.code='P1'")
    Optional<Project> findP1();

    @Query("select p.name from project as p where p.code='P1'")
    Optional<String> findNameByCode();

    @Query("select * from project limit 1")
    Project findSingleProject();

    List<ProjectCode> findAll();

    List<Project> list();

    List<Project> findAllByNameLike(String name);

    @Join("tasks")
    Optional<Project> findByCode(String code);

    List<Project> findAllByCodeNotEqual(String code);

    List<Project> findAllByNameStartingWith(String name);

    Optional<Project> findByCodeAndDescription(String code, String description);

    Optional<Project> findByCodeOrDescription(String code, String description);

    Long countByCode(String code);

    Optional<Project> findByCodeEqual(String code);

    @Query("select p.id, p.code, p.name, p.description, p.created, p.updated from project p inner join task t on t.project_id = p.id where t.name like CONCAT('%', :taskName, '%') group by p.id")
    List<Project> findDistinctByTasksNameContaining(String taskName);

    Long deleteByCode(String code);
}
