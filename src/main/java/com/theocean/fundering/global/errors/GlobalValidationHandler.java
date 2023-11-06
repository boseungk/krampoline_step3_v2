package com.theocean.fundering.global.errors;

import com.theocean.fundering.global.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Before("postMapping()")
    public void validationAdvice(final JoinPoint jp) {
        final Object[] args = jp.getArgs();
        for (final Object arg : args) {
            if (arg instanceof final Errors errors) {

                if (errors.hasErrors()) {
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getDefaultMessage() + ":" + errors.getFieldErrors().get(0).getField()
                    );
                }
            }
        }
    }
}
