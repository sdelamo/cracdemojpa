package datajpah2;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Status;

@Controller
class HomeController {

    @Get
    @Status(HttpStatus.OK)
    void index() {
    }
}
