package org.useless.test;

import org.useless.serverlibe.ServerLibe;
import org.useless.serverlibe.api.ServerLibeEntrypoint;

public class TestPlugin implements ServerLibeEntrypoint {
	@Override
	public void serverlibeInit() {
		ServerLibe.registerListener(new TestFeatureListener());
		ServerLibe.registerListener(new DebugInfoListener());
		ServerLibe.registerListener(new GuiTestListener());
	}
}
