package config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import system.domain.AddrObject;
import system.domain.House;
import system.domain.Room;
import system.domain.Stead;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private TypeResolver resolver;

    @Autowired
    public void setResolver(TypeResolver resolver) {
        this.resolver = resolver;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(getProduces())
                .consumes(getConsumes())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(
                        new ResponseMessageBuilder().code(400).message("Bad request").build(),
                        new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
                        new ResponseMessageBuilder().code(403).message("Forbidden").build()))
                .additionalModels(
                        resolver.resolve(AddrObject.class),
                        resolver.resolve(House.class),
                        resolver.resolve(Stead.class),
                        resolver.resolve(Room.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FIAS")
                .description("FIAS API")
                .version("1.0")
                .contact(new Contact("Vladislav Ovsyannikov", null, null))
                .build();
    }

    private Set<String> getProduces() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }

    private Set<String> getConsumes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json");
        return consumes;
    }
}