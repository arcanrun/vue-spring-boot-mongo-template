package io.arcanrun.mongonotes.config.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

  @Around("io.arcanrun.mongonotes.config.logging.aspect.LoggingPointCuts.restControllerMethods()")
  public Object logAroundRestControllerMethods(final ProceedingJoinPoint jp) throws Throwable {
    var signature = jp.getSignature();
    var methodName = signature.getDeclaringTypeName();
    var args = Arrays.toString(jp.getArgs());

    log.info("[EXECUTE]: {}({})", methodName, args);

    try {
      var proceed = jp.proceed();

      log.info("[FINISHED]: {}({}) \n [RESULT]: {}", methodName, args, proceed);

      return proceed;
    } catch (Exception e) {
      var cause = e.getCause() == null ? "_" : e.getCause();

      log.error(
              "[EXCEPTION]: {}({}) | Cause = {} | Exception = {} ",
              methodName,
              args,
              cause,
              e.getMessage(),
              e);

      throw e;
    }
  }
}
