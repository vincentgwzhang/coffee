package tina.coffee.system.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jetty9.InstrumentedConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:SystemConfig.properties")
public class CoffeeServerConfig {

    @Bean
    public JettyEmbeddedServletContainerFactory jettyServletContainer(
            @Value("${Jetty.Thread.max}") Integer jettyThreadMax,
            @Value("${Jetty.Thread.min}") Integer jettyThreadMin,
            @Value("${server.session-timeout}") Integer sessionTimeout,
            @Value("${server.contextPath}") String contextPath,
            @Value("${server.port}") Integer port){
        final JettyEmbeddedServletContainerFactory servletContainerFactory = new JettyEmbeddedServletContainerFactory(port);
        servletContainerFactory.addServerCustomizers(
                server -> {
                    final QueuedThreadPool threadPool = (QueuedThreadPool)server.getThreadPool();
                    threadPool.setMaxThreads(jettyThreadMax);
                    threadPool.setMinThreads(jettyThreadMin);

                    MetricRegistry metricRegistry = new MetricRegistry();
                    final Connector connector = new ServerConnector(server, new InstrumentedConnectionFactory(new HttpConnectionFactory(), metricRegistry.timer("http.connection")));
                    server.addConnector(connector);
                });
        servletContainerFactory.setSessionTimeout(sessionTimeout, TimeUnit.DAYS);
        servletContainerFactory.setRegisterDefaultServlet(false);
        servletContainerFactory.setContextPath(contextPath);
        return servletContainerFactory;
    }
}
