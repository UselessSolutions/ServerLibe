package org.useless.serverlibe.config.adapters.nbt;

import com.mojang.nbt.Tag;
import org.useless.serverlibe.config.adapters.YamlAdapter;

import java.util.HashMap;
import java.util.Map;

public abstract class NBTTagYamlAdapter<T extends Tag<?>> implements YamlAdapter<T> {
	@Override
	public Map<String, Object> serialize(T object) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", object.getId());
		map.put("name", object.getTagName());
		map.put("value", object.getValue());
		return map;
	}
	@Override
	public T deserialize(Map<String, Object> data) {
		T tag = (T) Tag.newTag(((Integer)data.get("id")).byteValue());
		tag.setName((String) data.get("name"));
		setValue(tag,data.get("value"));
		return tag;
	}
	public abstract void setValue(T tag, Object input);
}
