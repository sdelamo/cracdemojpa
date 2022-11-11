package datajpah2.dev;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.crac.OrderedResource;
import jakarta.inject.Singleton;
import org.crac.Context;
import org.crac.Resource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
public class H2Console implements ApplicationEventListener<StartupEvent>, OrderedResource {
    private static final Logger LOG = LoggerFactory.getLogger(H2Console.class);
    private final Server webServer;
    public H2Console() {
        try {
            webServer = Server.createWebServer();
        } catch (SQLException e) {
            throw new ConfigurationException("unable to create the H2 console");
        }
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        start();
    }

    void start() {
        try {
            LOG.info("starting h2 console in http://localhost:8082");
            webServer.start();
        } catch (SQLException e) {
            LOG.warn("Unable to start H2 Console");
        }
    }

    void stop() {
        LOG.info("stopping h2 console in http://localhost:8082");
        webServer.stop();
    }

    @Override
    public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
        stop();
    }

    @Override
    public void afterRestore(Context<? extends Resource> context) throws Exception {
        start();
    }
}
