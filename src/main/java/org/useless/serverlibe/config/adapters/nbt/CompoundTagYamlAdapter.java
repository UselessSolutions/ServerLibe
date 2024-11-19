package org.useless.serverlibe.config.adapters.nbt;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.Tag;
import org.useless.serverlibe.config.YamlConfiguration;

import java.io.IOException;
import java.util.Map;
public class CompoundTagYamlAdapter extends NBTTagYamlAdapter<CompoundTag> {
	@Override
	public CompoundTag deserialize(Map<String, Object> data) throws IOException {
		CompoundTag compoundTag = new CompoundTag();
		System.out.println(data);
		for (Map.Entry<String, Object> e : data.entrySet()){
			Map<String, Object> tagData = (Map<String, Object>) e.getValue();
			Tag tag = Tag.newTag(((Integer) tagData.get("id")).byteValue());
			tag = YamlConfiguration.getMapAsObject(tagData, tag.getClass());
			compoundTag.put(e.getKey(), tag);
		}
		return compoundTag;
	}

	@Override
	public void setValue(CompoundTag tag, Object input) {
		return;
	}
}
