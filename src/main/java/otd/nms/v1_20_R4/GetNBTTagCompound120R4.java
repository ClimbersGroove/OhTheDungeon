package otd.nms.v1_20_R4;

import forge_sandbox.greymerk.roguelike.worldgen.spawners.SpawnPotential;
import otd.nms.GetNBTTagCompound;

public class GetNBTTagCompound120R4 implements GetNBTTagCompound {
	public Object get(int level, String name, Object inbt, SpawnPotential sp) {
		Object nbt;
		if (inbt == null) {
			nbt = new net.minecraft.nbt.NBTTagCompound();
		} else {
			nbt = ((net.minecraft.nbt.NBTTagCompound) inbt).g();
		}
		return sp.getPotential(sp.getRoguelike(level, name, nbt));
	}
}
