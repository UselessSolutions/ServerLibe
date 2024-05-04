package org.useless.serverlibe.api.annotations;

import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.api.enums.Priority;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with {@link EventListener} in a {@link org.useless.serverlibe.api.Listener Listener} will be called by the event system. <br><br/>
 * To properly set up an {@link EventListener} the attached method must return void and its only parameter must be a class which extends {@link org.useless.serverlibe.api.event.Event Event}
 *
 * @author Useless
 * @since beta.1
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
	/**
	 * Defines when the method should be called, with methods marked {@code HIGHEST} run first and methods marked {@code LOWEST} run last.
	 *
	 * @author Useless
	 * @since beta.1
	 */
	@NotNull
	Priority priority() default Priority.NORMAL;

	/**
	 * Specifies if the method should be called if the event has already been cancelled
	 * <p>
	 * if true then the method won't be called if cancelled <p>
	 * if false then the method will always be called
	 *
	 * @author Useless
	 * @since beta.1
	 */
	boolean ignoreCancelled() default false;
}
