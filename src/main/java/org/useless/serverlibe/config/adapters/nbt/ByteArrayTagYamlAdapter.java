package org.useless.serverlibe.config.adapters.nbt;

import com.mojang.nbt.ByteArrayTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ByteArrayTagYamlAdapter extends NBTTagYamlAdapter<ByteArrayTag>{
	@Override
	public Map<String, Object> serialize(ByteArrayTag object) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", object.getId());
		map.put("name", object.getTagName());
		ArrayList<Byte> bytes = new ArrayList<>();
		byte[] arr = object.getValue();
        for (byte b : arr) {
            bytes.add(b);
        }
		map.put("value", bytes);
		return map;
	}

	@Override
	public void setValue(ByteArrayTag tag, Object input) {
		ArrayList<Integer> bytes = (ArrayList<Integer>)input;
		byte[] arr = new byte[bytes.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = bytes.get(i).byteValue();
		}
		tag.setValue(arr);
    }

}
