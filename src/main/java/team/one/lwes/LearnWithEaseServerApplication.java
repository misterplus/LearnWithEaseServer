package team.one.lwes;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.one.lwes.dao.impl.StudyRoomInfoDaoImpl;
import team.one.lwes.interceptor.AuthInterceptor;
import team.one.lwes.resolver.CurrentUserResolver;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableConfigurationProperties(Config.class)
public class LearnWithEaseServerApplication implements WebMvcConfigurer {

    @Autowired
    private CurrentUserResolver currentUserResolver;
    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private StudyRoomInfoDaoImpl roomDao;

    public static void main(String[] args) {
        SpringApplication.run(LearnWithEaseServerApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //TODO: rework this
        //does this even work lmao
        roomDao.clear();
        resolvers.add(currentUserResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new LWEServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
        tomcat.addConnectorCustomizers(gracefulShutdown());
        return tomcat;
    }

    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown();
    }

    private Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }

    private static class LWEServletWebServerFactory extends TomcatServletWebServerFactory {
        @Override
        protected void postProcessContext(Context context) {
            SecurityConstraint constraint = new SecurityConstraint();
            constraint.setUserConstraint("CONFIDENTIAL");
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            constraint.addCollection(collection);
            context.addConstraint(constraint);
        }
    }

    private static class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
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

}
