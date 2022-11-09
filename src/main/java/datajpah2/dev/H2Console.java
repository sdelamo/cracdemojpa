package datajpah2.dev;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Singleton;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
public class H2Console implements ApplicationEventListener<StartupEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(H2Console.class);
    @Override
    public void onApplicationEvent(StartupEvent event) {
        try {
            LOG.info("starting h2 console in http://localhost:8082");
            Server.createWebServer().start();
        } catch (SQLException e) {
            LOG.warn("Unable to start H2 Console");
        }
    }
}
