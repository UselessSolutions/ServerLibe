package org.useless.test;

import org.useless.serverlibe.ServerLibe;
import org.useless.serverlibe.api.ServerLibeEntrypoint;

public class TestMain implements ServerLibeEntrypoint {
	@Override
	public void serverlibeEntry() {
		ServerLibe.registerListener(new TestPlugin());
	}
}
