package org.useless.test;

import com.mojang.nbt.tags.ByteArrayTag;
import com.mojang.nbt.tags.DoubleArrayTag;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import org.useless.serverlibe.ServerLibe;
import org.useless.serverlibe.api.ServerLibeEntrypoint;
import org.useless.serverlibe.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TestPlugin implements ServerLibeEntrypoint {
	@Override
	public void serverlibeInit() throws IOException {
		ServerLibe.registerListener(new TestFeatureListener());
		ServerLibe.registerListener(new DebugInfoListener());
		ServerLibe.registerListener(new GuiTestListener());

		File config = new File(FabricLoader.getInstance().getConfigDir() + "/test.yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(config);
		configuration.addDefault("test1", "val1");
		configuration.addDefault("test2", 1);
		configuration.addDefault("test3", 1.2);
		configuration.addDefault("test4", new ByteArrayTag(new byte[]{1, 2, 3, 4}));
		configuration.addDefault("test5", new DoubleArrayTag(new double[]{1.1, 2.2, 3.3, 4.4}));
		ItemStack testStack = Blocks.BLOCK_DIAMOND.getDefaultStack();
		testStack.setCustomName("testName");
		configuration.addDefault("testItemStack", testStack);
		configuration.save(config);

		configuration = YamlConfiguration.loadConfiguration(config);
		System.out.printf("Test1 : %s\n", configuration.getAsString("test1").equals("val1"));
		System.out.printf("Test2 : %s\n", configuration.getAsInt("test2") == 1);
		System.out.printf("Test3 : %s\n", configuration.getAsDouble("test3") == 1.2);
		System.out.printf("Test4 : %s\n", configuration.getAsClass("test4", ByteArrayTag.class).getValue()[3] == 4);
		System.out.printf("Test5 : %s\n", configuration.getAsClass("test5", DoubleArrayTag.class).getValue()[3] == 4.4);
		System.out.printf("TestStack : %s\n", configuration.getAsClass("testItemStack", ItemStack.class).isStackEqual(testStack));
		System.out.printf("TestStack Name: %s\n", configuration.getAsClass("testItemStack", ItemStack.class).getDisplayName());

//		throw new RuntimeException();
    }
}
