package datajpah2;

import datajpah2.repositories.ProjectCrudRepository;
import datajpah2.repositories.ProjectRepository;
import datajpah2.repositories.TaskCrudRepository;
import datajpah2.repositories.TaskRepository;
import datajpah2.repositories.WorkerCrudRepository;
import datajpah2.repositories.WorkerRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
class ProjectControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

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
    void getProjectList() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);
        BlockingHttpClient client = httpClient.toBlocking();
        URI uri = UriBuilder.of("/project").path("list").build();
        HttpRequest<?> request = HttpRequest.GET(uri);
        HttpResponse<List<ProjectCode>> response = client.exchange(request, Argument.listOf(ProjectCode.class));
        assertEquals(HttpStatus.OK, response.getStatus());
        Optional<List<ProjectCode>> projectsOptional = response.getBody();
        assertTrue(projectsOptional.isPresent());
        assertEquals(3, projectsOptional.get().size());
        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }
}