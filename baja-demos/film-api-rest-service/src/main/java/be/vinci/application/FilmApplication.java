package be.vinci.application;



import be.vinci.utils.ApplicationBinder;
import be.vinci.utils.Config;
import be.vinci.utils.WebExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class FilmApplication extends ResourceConfig {

    public FilmApplication(){
        Config.load("dev.properties");

        System.out.println("FILMS INIT...");
        packages("be.vinci.api");
        register(JacksonFeature.class);
        register(ApplicationBinder.class);
        register(WebExceptionMapper.class);
        //register(ApiListingResource.class);
        //register(SwaggerSerializers.class);
        /*var film = new DomainFactoryImpl().getFilm();
        var myDep = new FilmDataServiceImpl();
        film.setId(-1);
        film.setTitle("Call to ResourceConfig");
        film.setLink("Call");
        myDep.createOne(film);*/
    }
}

/*
@ApplicationPath("/")
public class FilmApplication extends Application {
    static {
        Config.load("dev.properties");
        System.out.println("FILMS INIT...");
    }
}*/