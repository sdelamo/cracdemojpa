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

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
class ProjectSearchControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

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
    void testDynamicWithCriteriaApi() {
        TestUtils.populateSeedData(projectRepository, taskRepository, workerRepository);

        BlockingHttpClient client = httpClient.toBlocking();

        HttpResponse<List<ProjectCode>> response = exchange(client, new Search("P1", null, null));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(1, response.getBody().get().size());

        response = exchange(client, new Search(null, "Project 1", null));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(1, response.getBody().get().size());

        response = exchange(client, new Search("P2", "Project 1", null));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(0, response.getBody().get().size());

        response = exchange(client, new Search("P1", "Project 1", null));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(1, response.getBody().get().size());

        response = exchange(client, new Search(null, null, "Description of Project 1"));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.getBody().isPresent());
        assertEquals(1, response.getBody().get().size());

        TestUtils.cleanup(projectCrudRepository, taskCrudRepository, workerCrudRepository);
    }

    private HttpResponse<List<ProjectCode>> exchange(BlockingHttpClient client,
                                                     Search search) {
        HttpRequest<?> request = req(search);
        return  client.exchange(request, Argument.listOf(ProjectCode.class));
    }

    private HttpRequest<?> req(Search search) {
        URI uri = UriBuilder.of("/project").path("search").build();
        return HttpRequest.POST(uri, search);
    }
















}