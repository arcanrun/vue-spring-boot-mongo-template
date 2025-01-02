package io.arcanrun.mongonotes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@PropertySource("classpath:git.properties")
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String GIT_INFO_MESSAGE_TMPL =
            """
                  <h3>------------- GIT INFO -------------</h3>
                  <p><b>Git branch:</b> %s</p>
                  <p><b>Commit SHA:</b> %s</p>
                  <p><b>Commit Date:</b> %s</p>
                  <h3>------------ BUILD INFO -----------</h3>
                  <p><b>App Version:</b> %s</p>
                  <p><b>Build Date:</b> %s</p>""";
    public static final String SECURITY_REFERENCE = "bearer";
    public static final String BEARER_FORMAT = "JWT";
    public static final String VERSION = "1.0";

    private BuildProperties buildProperties;

    @Value("${git.commit.time}")
    private String commitDate;

    @Value("${git.branch}")
    private String gitBranch;

    @Value("${git.commit.id.abbrev}")
    private String commitSha;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme(SECURITY_REFERENCE)
                                                .bearerFormat(BEARER_FORMAT)))
                .info(getApiInfo());
    }

    private Info getApiInfo() {
        return new Info()
                .description(getDescription())
                .title(buildProperties.getName())
                .version(VERSION);
    }

    private String getDescription() {
        var time = buildProperties.getTime();
        var version = buildProperties.getVersion();

        return String.format(
                GIT_INFO_MESSAGE_TMPL, gitBranch, commitSha, getIsoDateStr(commitDate), version, time);
    }

    private String getIsoDateStr(String date) {
        var zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());

        return zonedDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Autowired
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }
}
