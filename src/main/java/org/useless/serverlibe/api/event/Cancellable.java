package org.useless.serverlibe.api.event;

public interface Cancellable {
	/**
	 * @return true if the Event has been cancelled
	 *
	 * @author Useless
	 * @since beta-1
	 */
	boolean isCancelled();

	/**
	 * Sets the state of the cancel
	 * @param cancel value to set the cancel state
	 *
	 * @author Useless
	 * @since beta-1
	 */
	void setCancelled(boolean cancel);
}
