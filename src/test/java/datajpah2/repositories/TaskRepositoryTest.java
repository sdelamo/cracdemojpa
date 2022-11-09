package datajpah2.repositories;

import datajpah2.TasksPerYear;
import datajpah2.TestUtils;
import datajpah2.entities.Project;
import datajpah2.entities.Task;
import datajpah2.entities.TaskStatus;
import datajpah2.entities.Worker;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
class TaskRepositoryTest {

    @Inject
    TaskRepository taskRepository;

    @Inject
    WorkerRepository workerRepository;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    ProjectCrudRepository projectCrudRepository;

    @Inject
    TaskCrudRepository taskCrudRepository;

    @Inject
    WorkerCrudRepository workerCrudRepository;

    @Test
    void findById() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);

        Optional<Project> projectOptional = projectRepository.findByCode("P1");
        assertTrue(projectOptional.isPresent());
        Optional<Task> optionalTask = projectOptional.get().tasks().stream().findFirst();
        assertTrue(optionalTask.isPresent());
        Optional<Task> taskOptional = taskRepository.findById(optionalTask.get().id());
        assertTrue(taskOptional.isPresent());

        //cleanup:
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);

    }

    @Test
    void existsById() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);

        Optional<Project> projectOptional = projectRepository.findByCode("P1");
        assertTrue(projectOptional.isPresent());
        Optional<Task> optionalTask = projectOptional.get().tasks().stream().findFirst();
        assertTrue(optionalTask.isPresent());
        assertTrue(taskRepository.existsById(optionalTask.get().id()));

        //cleanup:
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByDueDateBefore() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(2, taskRepository.findByDueDateBefore(LocalDate.of(2025, 2, 25)).size());
        //cleanup:
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByDueDateLessThanEquals() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(2, taskRepository.findByDueDateLessThanEquals(LocalDate.of(2025, 2, 10)).size());
        //cleanup:
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByAssigneeFirstName() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(1, taskRepository.findByAssigneeFirstName("John").size());
        assertEquals(0, taskRepository.findByAssigneeFirstName("Sergio").size());

        //cleanup:
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void deleteCompleteTasks() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        assertEquals(4, taskRepository.count());
        taskRepository.deleteCompleteTasks();
        assertEquals(3, taskRepository.count());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void countDueByYear() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        // 2025 3
        // 2026 1
        List<TasksPerYear> tasksPerYears = taskRepository.countDueByYear();
        assertEquals(2, tasksPerYears.size());
        assertTrue(tasksPerYears.stream().anyMatch(t -> t.getYear().equals(2026) && t.getTasks().equals(1)));
        assertTrue(tasksPerYears.stream().anyMatch(t -> t.getYear().equals(2025) && t.getTasks().equals(3)));
        //cleanup:
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findByNameLike() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);

        Pageable pageable = Pageable.from(0, 2, Sort.of(Sort.Order.desc("dueDate")));
        List<Task> tasks = taskRepository.findByNameLike("%ask%", pageable);
        assertEquals(2, tasks.size());
        tasks = taskRepository.findByNameLike("%foo%", pageable);
        assertEquals(0, tasks.size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);

    }

    @Test
    void findAllOrderByDueDateDesc() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        List<Task> tasks = taskRepository.findAllOrderByDueDateDesc();
        assertEquals(4, tasks.size());
        assertEquals("Task 4", tasks.get(0).name());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void allTasksSortedByDueDateDesc() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        List<Task> tasks = taskRepository.allTasksSortedByDueDateDesc();
        assertEquals(4, tasks.size());
        assertEquals("Task 4", tasks.get(0).name());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void allTasksByName() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Pageable pageable = Pageable.from(0, 2);
        Page<Task> page = taskRepository.allTasksByName("%ask%", pageable);
        assertEquals(4, page.getTotalSize());
        assertEquals(2, page.getNumberOfElements());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findAllOrderByDueDateDescAndAssigneeLastNameAsc() {
        Worker john = workerRepository.save(new Worker("john@test.com", "John", "John"));
        Worker aegon = workerRepository.save(new Worker("aegon@test.com", "Aegon", "Aegon"));

        Task t1 = new Task("Task 1", "Task 1 Description", LocalDate.of(2025, 1, 12), TaskStatus.TO_DO);
        Task t2 = new Task("Task 2", "Task 2 Description", LocalDate.of(2025, 2, 10), TaskStatus.DONE);
        Task t3 = new Task("Task 3", "Task 3 Description", LocalDate.of(2025, 3, 16), TaskStatus.TO_DO);
        Task t4 = new Task("Task 4", "Task 4 Description", LocalDate.of(2026, 6, 25), TaskStatus.IN_PROGRESS, aegon);
        Task t5 = new Task("Task 5", "Task 4 Description", LocalDate.of(2026, 6, 25), TaskStatus.IN_PROGRESS, john);
        Set<Task> tasks = new HashSet<>();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        tasks.add(t4);
        tasks.add(t5);

        projectRepository.save(new Project("P1", "Project 1", "Description of Project 1", tasks));
        projectRepository.save(new Project( "P2", "Project 2", "Description of Project 3"));
        projectRepository.save(new Project("P3", "Project 3", "Description of Project 3"));

        List<Task> result = taskRepository.findAllOrderByDueDateDescAndAssigneeLastNameAsc();

        assertEquals(2, result.size());
        assertEquals("Task 4", result.get(0).name());

        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }
}