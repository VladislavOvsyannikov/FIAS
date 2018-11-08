package javaconfig;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponses;
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
import system.model.House;
import system.model.Room;
import system.model.Stead;

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
                .globalResponseMessage(RequestMethod.POST, newArrayList(
                        new ResponseMessageBuilder().code(400).message("Bad request").build(),
                        new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
                        new ResponseMessageBuilder().code(403).message("Forbidden").build()))
                .globalResponseMessage(RequestMethod.GET, newArrayList(
                        new ResponseMessageBuilder().code(401).message("Unauthorized").build(),
                        new ResponseMessageBuilder().code(403).message("Forbidden").build()))
                .additionalModels(resolver.resolve(Parameters.class),
                        resolver.resolve(House.class),
                        resolver.resolve(Stead.class),
                        resolver.resolve(Room.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FIAS")
                .description("Description of FIAS API")
                .version("1.0")
                .license("Home page")
                .licenseUrl("http://localhost:8080/user")
                .contact(new Contact("Vladislav Ovsyannikov", null, null))
                .build();
    }

    private Set<String> getProduces(){
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }

    private Set<String> getConsumes(){
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json");
        return consumes;
    }

    private static class Parameters  {
        @ApiModelProperty(example = "true")
        public String onlyActual;

        @ApiModelProperty(example = "addrObject house stead room")
        public String searchType;

        @ApiModelProperty(example = "d8327a56-80de-4df2-815c-4f6ab1224c50")
        public String guid;

        @ApiModelProperty(example = "385000")
        public String postalcode;
    }
}