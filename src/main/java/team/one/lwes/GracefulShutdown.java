package team.one.lwes;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    private final int waitTime = 10;
    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        try {
            if (executor instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
