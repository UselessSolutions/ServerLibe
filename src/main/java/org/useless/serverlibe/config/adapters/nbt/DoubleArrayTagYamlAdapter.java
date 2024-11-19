package org.useless.serverlibe.config.adapters.nbt;

import com.mojang.nbt.tags.DoubleArrayTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoubleArrayTagYamlAdapter extends NBTTagYamlAdapter<DoubleArrayTag>{
	@Override
	public Map<String, Object> serialize(DoubleArrayTag object) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", object.getId());
		map.put("name", object.getTagName());
		ArrayList<Double> bytes = new ArrayList<>();
		double[] arr = object.getValue();
		for (double b : arr) {
			bytes.add(b);
		}
		map.put("value", bytes);
		return map;
	}

	@Override
	public void setValue(DoubleArrayTag tag, Object input) {
		ArrayList<Double> bytes = (ArrayList<Double>)input;
		double[] arr = new double[bytes.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = bytes.get(i).byteValue();
		}
		tag.setValue(arr);
	}
}
