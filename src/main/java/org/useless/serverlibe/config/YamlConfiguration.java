package org.useless.serverlibe.config;

import com.mojang.nbt.tags.ByteArrayTag;
import com.mojang.nbt.tags.ByteTag;
import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.DoubleArrayTag;
import com.mojang.nbt.tags.StringTag;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.useless.serverlibe.ServerLibe;
/*import org.useless.serverlibe.config.adapters.ItemStackYamlAdapter;
import org.useless.serverlibe.config.adapters.YamlAdapter;*/
/*import org.useless.serverlibe.config.adapters.nbt.ByteArrayTagYamlAdapter;
import org.useless.serverlibe.config.adapters.nbt.ByteTagYamlAdapter;
import org.useless.serverlibe.config.adapters.nbt.CompoundTagYamlAdapter;
import org.useless.serverlibe.config.adapters.nbt.DoubleArrayTagYamlAdapter;
import org.useless.serverlibe.config.adapters.nbt.StringTagYamlAdapter;*/
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*public class YamlConfiguration {
	@NotNull
	private static final Map<Class<?>, YamlAdapter<?>> registeredAdapters = new HashMap<>();
	static {
		registerYamlAdapter(ItemStack.class, new ItemStackYamlAdapter());

		registerYamlAdapter(ByteTag.class, new ByteTagYamlAdapter());
		registerYamlAdapter(ByteArrayTag.class, new ByteArrayTagYamlAdapter());
		registerYamlAdapter(CompoundTag.class, new CompoundTagYamlAdapter());
		registerYamlAdapter(DoubleArrayTag.class, new DoubleArrayTagYamlAdapter());
		registerYamlAdapter(StringTag.class, new StringTagYamlAdapter());
	}
	@NotNull
	protected final Map<String, Object> data = new LinkedHashMap<>();
	public void addDefault(String key, Object valueDefault){
		if (data.containsKey(key)) return;
		this.data.put(key, getAsYamlDataObject(valueDefault));
	}
	public byte getAsByte(String key){
		return (byte) data.get(key);
	}
	public short getAsShort(String key){
		return (short) data.get(key);
	}
	public int getAsInt(String key){
		return (int) data.get(key);
	}
	public long getAsLong(String key){
		return (long) data.get(key);
	}
	public float getAsFloat(String key){
		return (float) data.get(key);
	}
	public double getAsDouble(String key){
		return (double) data.get(key);
	}
	public String getAsString(String key){
		return (String) data.get(key);
	}
	public <T> T getAsClass(String key, Class<T> classOfT) throws IOException {
		if (registeredAdapters.containsKey(classOfT)){
			return (T) registeredAdapters.get(classOfT).deserialize((Map<String, Object>) data.get(key));
		}
		return (T) data.get(key);
	}
	public void load(File file){
		data.clear();
		Yaml yaml = new Yaml();
		try {
			Map<String, Object> _map = yaml.load(new FileInputStream(file));
			if (_map != null){
				data.putAll(_map);
			}
		} catch (FileNotFoundException e) {
			ServerLibe.LOGGER.warn("Could not find file '" + file + "'!", e);
		}
	}
	public void save(File file){
		DumperOptions options = new DumperOptions();
		options.setIndent(2);
		options.setPrettyFlow(true);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(options);
		try (FileWriter writer = new FileWriter(file)) {
			yaml.dump(getAsYamlDataObject(data), writer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static <T> T getMapAsObject(Map<String, Object> map, Class<T> classOfT) throws IOException {
		return (T) registeredAdapters.get(classOfT).deserialize(map);
	}
	private static Object getAsYamlDataObject(Object o){
		if (o instanceof Number) return o;
		if (o instanceof String) return o;
		if (o.getClass().isArray()) return o;
		if (o instanceof List){
			List<Object> newList = new ArrayList<>();
			for (Object listO : (List)o){
				newList.add(getAsYamlDataObject(listO));
			}
			return newList;
		}
		if (o instanceof Map){
			Map<String, Object> newMap = new LinkedHashMap<>();
			for (Map.Entry<?, ?> entryO : ((Map<?,?>)o).entrySet()){
				newMap.put(entryO.getKey().toString(), getAsYamlDataObject(entryO.getValue()));
			}
			return newMap;
		}
		if (registeredAdapters.containsKey(o.getClass())){
			return ((YamlAdapter<Object>)registeredAdapters.get(o.getClass())).serialize(o);
		}
		throw new RuntimeException("Failed to parse object '" + o + "' with class '" + o.getClass() + "' is missing a Yaml Adapter!");
	}
	public static YamlConfiguration loadConfiguration(File file){
		YamlConfiguration configuration = new YamlConfiguration();
		configuration.load(file);
   		return configuration;
	}
	public static <T> void registerYamlAdapter(Class<T> classOfT, YamlAdapter<T> adapter){
		if (registeredAdapters.containsKey(classOfT)) ServerLibe.LOGGER.warn("Class '" + classOfT + "' already has a registered adapter, overriding previous!");
		registeredAdapters.put(classOfT, adapter);
	}
	public static void registerYamlAdapterUnsafe(Class<?> classOf, YamlAdapter<?> adapter){
		if (registeredAdapters.containsKey(classOf)) ServerLibe.LOGGER.warn("Class '" + classOf + "' already has a registered adapter, overriding previous!");
		registeredAdapters.put(classOf, adapter);
	}
}*/
