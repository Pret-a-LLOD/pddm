package es.upm.oeg.pal.dm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author Pablo
 */
@SpringBootApplication
public class ProjectController extends SpringBootServletInitializer{ 
    

    public static void main(String[] args) {
        SpringApplication.run(ProjectController.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProjectController.class);
    }
}
