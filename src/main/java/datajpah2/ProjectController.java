package datajpah2;

import datajpah2.entities.Project;
import datajpah2.repositories.ProjectRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/project")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get("/list")
    List<ProjectCode> index() {
        return projectRepository.findAll();
    }
}
