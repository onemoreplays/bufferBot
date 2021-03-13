package me.inao.discordbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Basic Annotation.
 * Tells system about permission check.
 * If you want to perform permissions check, you HAVE TO have configured them in config.json
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Permission {
}
