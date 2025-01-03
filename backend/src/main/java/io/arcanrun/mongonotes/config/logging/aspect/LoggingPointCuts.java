package io.arcanrun.mongonotes.config.logging.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingPointCuts {
  @Pointcut("within(io.arcanrun..*) && within(org.springframework.web.filter..*)")
  public void allMethods() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
  public void restControllerMethods() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }

  @Pointcut("allMethods() && !restControllerMethods()")
  public void allMethodButWithoutRestControllerMethods() {
    // Method is empty as this is just a Pointcut, the implementations are in the advices.
  }
}
