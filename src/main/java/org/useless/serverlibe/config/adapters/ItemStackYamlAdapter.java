package org.useless.serverlibe.config.adapters;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.item.ItemStack;
/*import org.useless.serverlibe.config.YamlConfiguration;*/

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/*public class ItemStackYamlAdapter implements YamlAdapter<ItemStack>{
	@Override
	public Map<String, Object> serialize(ItemStack object) {
		Map<String, Object> map = new LinkedHashMap<>();
		if (object != null){
			map.put("id", object.itemID);
			map.put("damage", object.getMetadata());
			map.put("amount", object.stackSize);
			CompoundTag data = object.getData();
			if (!data.getValues().isEmpty()){
				map.put("data", data.getValue());
			}
		} else {
			map.put("id", -1);
		}

		return map;
	}

	@Override
	public ItemStack deserialize(Map<String, Object> data) throws IOException {
		int id = (int) data.get("id");
		if (id < 0) return null;
		int meta = (int) data.get("damage");
		int stacksize = (int) data.get("amount");
		CompoundTag tag;
		if (data.get("data") != null){
			tag = YamlConfiguration.getMapAsObject((Map<String, Object>) data.get("data"), CompoundTag.class);
		} else {
			tag = null;
		}
		return new ItemStack(id, stacksize, meta, tag);
	}
}*/
