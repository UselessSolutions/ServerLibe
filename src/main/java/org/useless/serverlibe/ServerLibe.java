package org.useless.serverlibe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.useless.serverlibe.api.ServerLibeEntrypoint;
import org.useless.serverlibe.internal.InternalStorageClass;

import java.util.Objects;


public class ServerLibe implements ModInitializer {
    public static final String MOD_ID = "serverlibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
		FabricLoader.getInstance().getEntrypoints("serverlibe", ServerLibeEntrypoint.class).forEach(ServerLibeEntrypoint::serverlibeEntry);
        LOGGER.info("ServerLibe initialized.");

    }
	public static void registerEventPackage(@NotNull Class<?> eventHandler){
		InternalStorageClass.eventHandlerClassesSet.add(Objects.requireNonNull(eventHandler));
	}
}
