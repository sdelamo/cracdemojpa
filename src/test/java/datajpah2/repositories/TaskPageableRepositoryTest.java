package datajpah2.repositories;

import datajpah2.TestUtils;
import datajpah2.entities.Project;
import datajpah2.entities.Task;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.Sort;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(startApplication = false)
class TaskPageableRepositoryTest {


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

    @Inject
    TaskPageableRepository taskPageableRepository;

    @Test
    void findAllSort() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Sort dueDateDesc = Sort.of(Sort.Order.desc("dueDate"));
        List<Task> tasks = taskPageableRepository.findAll(dueDateDesc);
        assertEquals(4, tasks.size());
        assertEquals("Task 4", tasks.get(0).name());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void findAllPageable() {
        Sort dueDateAsc = Sort.of(Sort.Order.asc("dueDate"));

        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Pageable pageable = Pageable.from(0, 3, dueDateAsc);
        Page<Task> tasksPage = taskPageableRepository.findAll(pageable);
        assertNotNull(tasksPage);
        assertEquals(3, tasksPage.getNumberOfElements());
        assertEquals(4, tasksPage.getTotalSize());
        assertEquals(3, tasksPage.getContent().size());
        assertEquals("Task 1", tasksPage.getContent().get(0).name());

        // Second Page
        pageable = Pageable.from(1, 3, dueDateAsc);
        tasksPage = taskPageableRepository.findAll(pageable);
        assertNotNull(tasksPage);
        assertEquals(1, tasksPage.getNumberOfElements());
        assertEquals(4, tasksPage.getTotalSize());
        assertEquals("Task 4", tasksPage.getContent().get(0).name());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    @Test
    void listSlice() {
        Sort dueDateAsc = Sort.of(Sort.Order.asc("dueDate"));

        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        Pageable pageable = Pageable.from(0, 3, dueDateAsc);
        Slice<Task> tasksPage = taskPageableRepository.list(pageable);
        assertNotNull(tasksPage);
        assertEquals(3, tasksPage.getNumberOfElements());
        assertEquals(3, tasksPage.getContent().size());
        assertEquals("Task 1", tasksPage.getContent().get(0).name());

        // Second Page
        pageable = Pageable.from(1, 3, dueDateAsc);
        tasksPage = taskPageableRepository.findAll(pageable);
        assertNotNull(tasksPage);
        assertEquals(1, tasksPage.getNumberOfElements());
        assertEquals("Task 4", tasksPage.getContent().get(0).name());

        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }
}