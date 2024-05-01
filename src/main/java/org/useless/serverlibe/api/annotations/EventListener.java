package org.useless.serverlibe.api.annotations;

import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.data.Order;
import org.useless.serverlibe.data.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
	@NotNull
	Priority priority() default Priority.NORMAL;
	@NotNull
	Order order() default Order.BEFORE;
	@NotNull
	String event();
}
