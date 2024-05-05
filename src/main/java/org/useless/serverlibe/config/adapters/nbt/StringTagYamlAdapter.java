package org.useless.serverlibe.config.adapters.nbt;

import com.mojang.nbt.StringTag;

public class StringTagYamlAdapter extends NBTTagYamlAdapter<StringTag> {


	@Override
	public void setValue(StringTag tag, Object input) {
		tag.setValue((String) input);
	}
}
