package datajpah2.entities;

import datajpah2.TestUtils;
import datajpah2.repositories.ProjectCrudRepository;
import datajpah2.repositories.ProjectRepository;
import datajpah2.repositories.TaskCrudRepository;
import datajpah2.repositories.TaskRepository;
import datajpah2.repositories.WorkerCrudRepository;
import datajpah2.repositories.WorkerRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//ApplicationContext applicationContext = ApplicationContext.run(); => @MicronautTest(startApplication = false)
//EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer); => @MicronautTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@MicronautTest(startApplication = false)
class ProjectRepositoryTest {

    @Inject
    ProjectRepository projectRepository;

    @Inject
    TaskRepository taskRepository;

    @Inject
    WorkerRepository workerRepository;

    @Inject
    ProjectCrudRepository projectCrudRepository;

    @Inject
    TaskCrudRepository taskCrudRepository;

    @Inject
    WorkerCrudRepository workerCrudRepository;


    @Test
    void findByCode() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Optional<Project> projectOptional = projectRepository.findByCode("P1");
        assertTrue(projectOptional.isPresent());
        assertEquals("Project 1", projectOptional.get().name());
        assertFalse(projectRepository.findByCode("FOO").isPresent());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByCodeEqual() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Optional<Project> projectOptional = projectRepository.findByCodeEqual("P1");
        assertTrue(projectOptional.isPresent());
        assertEquals("Project 1", projectOptional.get().name());
        assertFalse(projectRepository.findByCodeEqual("FOO").isPresent());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void countByCode() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(1, projectRepository.countByCode("P1"));
        assertEquals(0, projectRepository.countByCode("FOO"));
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByCodeAndDescription() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertTrue(projectRepository.findByCodeAndDescription("P1", "Description of Project 1").isPresent());
        assertFalse(projectRepository.findByCodeAndDescription("P1", "Description of Project 2").isPresent());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByCodeOrDescription() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertTrue(projectRepository.findByCodeOrDescription("P1", "Description of Project 1").isPresent());
        assertTrue(projectRepository.findByCodeOrDescription("P1", "Description of Project 2").isPresent());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findWithDescriptionLike() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(3, projectRepository.findWithDescriptionLike("%tion%").size());
        assertEquals(1, projectRepository.findWithDescriptionLike("%roject 3%").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findWithDescriptionWithPrefixAndSuffix() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(1, projectRepository.findWithDescriptionWithPrefixAndSuffix("Descrip", "2").size());
        assertEquals(0, projectRepository.findWithDescriptionWithPrefixAndSuffix("Descrip", "4").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findWithDescriptionIsShorterThan() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(0, projectRepository.findWithDescriptionIsShorterThan(10).size());
        assertEquals(3, projectRepository.findWithDescriptionIsShorterThan(30).size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findAllByCodeNotEqual() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(2, projectRepository.findAllByCodeNotEqual("P1").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void deleteByCode() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(3, projectRepository.findAll().size());
        assertEquals(1, projectRepository.deleteByCode("P3"));
        assertEquals(2, projectRepository.findAll().size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findWithCodeIn() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(2, projectRepository.findWithCodeIn(Arrays.asList("P5", "P2", "P3")).size());
        assertEquals(0, projectRepository.findWithCodeIn(Collections.singletonList("P5")).size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByNameAndDescriptionParameterNamedBind() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(1, projectRepository.findByNameAndDescriptionParameterNamedBind("Project 1", "Description of Project 1").size());
        assertEquals(0, projectRepository.findByNameAndDescriptionParameterNamedBind("Project Foo", "Description of Project 1").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByNameAndDescriptionParameterNamedBindWithAtParameter() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(1, projectRepository.findByNameAndDescriptionParameterNamedBindWithAtParameter("Project 1", "Description of Project 1").size());
        assertEquals(0, projectRepository.findByNameAndDescriptionParameterNamedBindWithAtParameter("Project Foo", "Description of Project 1").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findNameByCode() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Optional<String> nameOptional = projectRepository.findNameByCode();
        assertTrue(nameOptional.isPresent());
        assertEquals("Project 1", nameOptional.get());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }
    @Test
    void findSingleProject() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Project project = projectRepository.findSingleProject();
        assertNotNull(project);
        assertEquals("Project 1", project.name());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findP1() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Optional<Project> projectOptional = projectRepository.findP1();
        assertTrue(projectOptional.isPresent());
        assertEquals("Project 1", projectOptional.get().name());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findAllByNameLike() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(0, projectRepository.findAllByNameLike("ject").size());
        assertEquals(3, projectRepository.findAllByNameLike("%ject%").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findAllByNameStartingWith() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(3, projectRepository.findAllByNameStartingWith("Proj").size());
        assertEquals(0, projectRepository.findAllByNameStartingWith("Foo").size());
        // Watch out with sanitization
        assertEquals(3, projectRepository.findAllByNameStartingWith("").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findAllByTasksNameContaining() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(0, projectRepository.findDistinctByTasksNameContaining("Foo").size());
        assertEquals(1, projectRepository.findDistinctByTasksNameContaining("ask").size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);

    }

    @Test
    void save() {
        assertEquals(0, projectRepository.findAll().size());
        Project p1 = new Project( "P1", "Project 1", "Description of Project 1");
        Project p2 = new Project( "P2", "Project 2", "Description of Project 3");
        Project p3 = new Project( "P3", "Project 3", "Description of Project 3");
        projectRepository.saveAll(Arrays.asList(p1, p2, p3));

        assertEquals(3, projectRepository.count());

        projectRepository.delete(p3);
        projectRepository.delete(p2);
        projectRepository.delete(p1);
    }
}