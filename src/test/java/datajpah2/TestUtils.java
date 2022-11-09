package datajpah2;

import datajpah2.entities.Project;
import datajpah2.entities.Task;
import datajpah2.entities.TaskStatus;
import datajpah2.entities.Worker;
import datajpah2.repositories.ProjectCrudRepository;
import datajpah2.repositories.ProjectRepository;
import datajpah2.repositories.TaskCrudRepository;
import datajpah2.repositories.TaskRepository;
import datajpah2.repositories.WorkerCrudRepository;
import datajpah2.repositories.WorkerRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static void populateSeedData(ProjectRepository projectRepository,
                                        TaskRepository taskRepository,
                                        WorkerRepository workerRepository) {
        Project p2 = new Project("P2", "Project 2", "Description of Project 2");
        Project p3 = new Project("P3", "Project 3", "Description of Project 3");
        Worker john = workerRepository.save(new Worker("john@test.com", "John", "Snow"));
        Task t1 = new Task("Task 1", "Task 1 Description", LocalDate.of(2025, 1, 12), TaskStatus.TO_DO);
        Task t2 = new Task("Task 2", "Task 2 Description", LocalDate.of(2025, 2, 10), TaskStatus.DONE);
        Task t3 = new Task("Task 3", "Task 3 Description", LocalDate.of(2025, 3, 16), TaskStatus.TO_DO);
        Task t4 = new Task("Task 4", "Task 4 Description", LocalDate.of(2026, 6, 25), TaskStatus.IN_PROGRESS, john);
        Set<Task> tasks = new HashSet<>();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        tasks.add(t4);
        Project p1 = new Project("P1", "Project 1", "Description of Project 1", tasks);



        projectRepository.save(p1);
        projectRepository.save(p2);
        projectRepository.save(p3);
    }

    public static void cleanup(ProjectCrudRepository projectRepository,
                               TaskCrudRepository taskRepository,
                               WorkerCrudRepository workerRepository) {
        taskRepository.deleteAll();
        workerRepository.deleteAll();
        projectRepository.deleteAll();
    }
}
