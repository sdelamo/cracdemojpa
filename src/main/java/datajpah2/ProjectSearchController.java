package datajpah2;

import datajpah2.entities.Project;
import datajpah2.repositories.ProjectRepository;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.core.util.StringUtils;
import io.micronaut.data.repository.jpa.criteria.PredicateSpecification;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("/project")
class ProjectSearchController {

    private final ProjectRepository projectRepository;

    ProjectSearchController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Post("/search")
    List<ProjectCode> search(@Body @Valid Search search) {
        PredicateSpecification<Project> predicate = null;

        final BeanWrapper<Search> wrapper = BeanWrapper.getWrapper(search);
        for (String propertyName : wrapper.getPropertyNames()) {
            Optional<String> valueOptional = wrapper.getProperty(propertyName, String.class);
            if (valueOptional.isPresent()) {
                Optional<PredicateSpecification<Project>> predicateOptional = createPredicate(propertyName, valueOptional.get());
                if (predicateOptional.isPresent()) {
                    if (predicate == null) {
                        predicate = predicateOptional.get();
                    } else {
                        predicate = predicate.and(predicateOptional.get());
                    }
                }
            }
        }

        return predicate == null ? Collections.emptyList() : projectRepository.findAll(predicate)
                .stream()
                .map(p -> new ProjectCode(p.name(), p.code()))
                .collect(Collectors.toList());
    }

    private Optional<PredicateSpecification<Project>> createPredicate(String propertyName, String value) {
        return StringUtils.isEmpty(value) ? Optional.empty() :
                Optional.of((root, criteriaBuilder) -> criteriaBuilder.equal(root.get(propertyName), value));
    }

}
