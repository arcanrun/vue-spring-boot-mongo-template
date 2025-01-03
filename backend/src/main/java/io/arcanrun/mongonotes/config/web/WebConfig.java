package io.arcanrun.mongonotes.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.arcanrun.mongonotes.config.logging.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebConfigProperties.class)
public class WebConfig implements WebMvcConfigurer {

  private final WebConfigProperties webConfigProperties;
  private static final List<String> ALL = List.of(CorsConfiguration.ALL);

  private final LoggingInterceptor loggingInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggingInterceptor);
  }

  /**
   * Configures CORS settings for the application.
   *
   * @return A WebMvcConfigurer with CORS configuration.
   */
  @Bean
  public WebMvcConfigurer corsMappingConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(webConfigProperties.getAllowedOrigins())
                .allowedMethods(webConfigProperties.getAllowedMethods())
                .allowedHeaders(ALL.toArray(String[]::new))
                .maxAge(webConfigProperties.getMaxAge())
                .exposedHeaders(webConfigProperties.getExposedHeaders());
      }
    };
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    var converter = new MappingJackson2HttpMessageConverter();
    var objectMapper = new ObjectMapper();

    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    converter.setObjectMapper(objectMapper);
    converters.add(byteArrayHttpMessageConverter());
    converters.add(stringHttpMessageConverter());
    converters.add(converter);
  }

  private StringHttpMessageConverter stringHttpMessageConverter() {
    var messageConverter = new StringHttpMessageConverter();
    messageConverter.setSupportedMediaTypes(
            List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.ALL));
    return messageConverter;
  }

  private ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
    return new ByteArrayHttpMessageConverter();
  }
}
