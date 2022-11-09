package datajpah2;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(startApplication = false)
class SearchValidationTest {

    @Inject
    Validator validator;

    @Test
    void everyFieldIsOptional() {
        assertTrue(validator.validate(new Search(null, null, null)).isEmpty());
        assertTrue(validator.validate(new Search("", null, null)).isEmpty());
    }
}