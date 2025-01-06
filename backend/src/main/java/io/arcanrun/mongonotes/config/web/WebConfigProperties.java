package io.arcanrun.mongonotes.config.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(
    prefix = "vue-spring-boot-mongo-template.web.cors",
    ignoreUnknownFields = false)
public class WebConfigProperties {

  @NotEmpty private String[] allowedOrigins;

  @NotEmpty private String[] allowedMethods;

  @NotEmpty private String[] allowedHeaders;

  private String[] exposedHeaders;

  private long maxAge;
}
