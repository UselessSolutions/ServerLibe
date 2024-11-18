package org.useless.serverlibe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.useless.serverlibe.api.Listener;
import org.useless.serverlibe.api.ServerLibeEntrypoint;
import org.useless.serverlibe.api.annotations.EventListener;
import org.useless.serverlibe.internal.EventContainer;
import org.useless.test.DebugInfoListener;
import org.useless.test.GuiTestListener;
import org.useless.test.TestFeatureListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


public class ServerLibe implements ModInitializer {
    public static final String MOD_ID = "serverlibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
//		if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) return;
		for (ServerLibeEntrypoint serverLibeEntrypoint : FabricLoader.getInstance().getEntrypoints("serverlibe", ServerLibeEntrypoint.class)) {
			try {
				serverLibeEntrypoint.serverlibeInit();
			} catch (IOException e) {
				LOGGER.error("Failed to get EntryPoint");
				continue;
			}
		}
		LOGGER.info("ServerLibe initialized.");
		//registerListener(new DebugInfoListener());
		registerListener(new GuiTestListener());
		registerListener(new TestFeatureListener());
    }

	/**
	 * Registers all the {@link EventListener} annotated methods in the provided {@link Listener} into {@link ServerLibe}.
	 *
	 * @param listener Listener class to register
	 *
	 * @author Useless
	 * @since beta.1
	 */
	public static void registerListener(@NotNull Listener listener){
		Method[] methods = listener.getClass().getMethods();
		for (Method m : methods){
			if (m.isAnnotationPresent(EventListener.class)){
				EventListener anno = m.getAnnotation(EventListener.class);
				Type[] types = m.getGenericParameterTypes();
				if (types.length != 1) throw new RuntimeException(String.format("Method '%s' in class '%s' has '%d' parameters, all event methods must have exactly 1 parameter!", m, listener.getClass().getName(), types.length));
				Class<?> event = (Class<?>) types[0];
                try {
					EventContainer eventContainer = (EventContainer) event.getMethod("getEventContainer", (Class<?>[]) null).invoke(null);
					eventContainer.addEvent(listener, m, anno);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(String.format("Type: %s, Error: %s", event, e));
                }
            }
		}
	}
}
