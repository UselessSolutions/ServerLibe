package org.useless.serverlibe.api;
@FunctionalInterface
public interface ServerLibeEntrypoint {
	/**
	 * Called by {@link org.useless.serverlibe.ServerLibe ServerLibe} on startup.
	 * All interaction with ServerLibe should be done through this entrypoint so that
	 * ServerLibe can properly manage it's loading process.
	 * <p>
	 *     Entrypoint identifier is {@code serverlibe}.
	 * </p>
	 *
	 * @author Useless
	 * @since beta-1
	 */
	void serverlibeInit();
}
