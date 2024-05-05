package org.useless.serverlibe.config.adapters.nbt;

import com.mojang.nbt.ByteTag;

public class ByteTagYamlAdapter extends NBTTagYamlAdapter<ByteTag> {

	@Override
	public void setValue(ByteTag tag, Object input) {
		tag.setValue(((Integer)input).byteValue());
	}
}
