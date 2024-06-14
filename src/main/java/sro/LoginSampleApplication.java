package sro;

import java.util.EnumSet;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

//ServletListenerRegistrationBean

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan({"corp.sensata.com.sample"})
public class LoginSampleApplication extends SpringBootServletInitializer{

 
    public static void main(String[] args) {
        SpringApplication.run(LoginSampleApplication.class, args);
    }

    @Bean
    public FacesServlet facesServlet() {
            return new FacesServlet();
    }
        
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
       
        ServletRegistrationBean registration = new ServletRegistrationBean(facesServlet(), "*.xhtml");
        registration.setName("FacesServlet");
        return registration;
    } 

    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.addUrlPatterns("/*");
        return rwFilter;
    }
}
