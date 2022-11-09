package datajpah2.dev;

import datajpah2.ProjectCode;
import datajpah2.entities.Project;
import datajpah2.entities.Task;
import datajpah2.entities.TaskStatus;
import datajpah2.entities.Worker;
import datajpah2.repositories.ProjectRepository;
import datajpah2.repositories.TaskRepository;
import datajpah2.repositories.WorkerRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
class Data implements ApplicationEventListener<StartupEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(Data.class);

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;

    private final WorkerRepository workerRepository;

    Data(ProjectRepository projectRepository, TaskRepository taskRepository, WorkerRepository workerRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.workerRepository = workerRepository;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        populate();
        LOG.info("Projects:");
        Iterable<ProjectCode> projectIterable = projectRepository.findAll();
        Iterator<ProjectCode> iterator = projectIterable.iterator();
        while (iterator.hasNext()) {
            ProjectCode project = iterator.next();
            LOG.info("{}", project);
        }
    }

    private void populate() {
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
        projectRepository.save(new Project("P2", "Project 2", "Description of Project 3"));
        projectRepository.save(new Project( "P3", "Project 3", "Description of Project 3"));
    }
}
