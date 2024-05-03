package org.useless.serverlibe.api.event;

public interface ICancellable {
	boolean isCancelled();
	void setCancelled(boolean cancel);
}
