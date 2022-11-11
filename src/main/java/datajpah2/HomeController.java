package datajpah2;

import datajpah2.repositories.ProjectRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.List;

@Controller
class HomeController {
    private final ProjectRepository projectRepository;

    public HomeController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get
    List<ProjectCode> index() {
        return projectRepository.findAll();
    }

}
