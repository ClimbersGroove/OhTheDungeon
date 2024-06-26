package otd.nms.v1_20_R4;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.Tag;
import otd.nms.SpawnerLightRule;

public class SpawnerLightRule120R4 implements SpawnerLightRule {
	public void update(Block tileentity, JavaPlugin plugin) {
		org.bukkit.craftbukkit.v1_20_R4.CraftWorld ws = (org.bukkit.craftbukkit.v1_20_R4.CraftWorld) tileentity
				.getWorld();
		net.minecraft.world.level.block.entity.BlockEntity te;
		Location pos = tileentity.getLocation();

		te = ws.getHandle().getBlockEntity(new net.minecraft.core.BlockPos(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ()));

		if (te == null)
			return;

		//net.minecraft.nbt.NBTTagCompound nbt = te.o();
		net.minecraft.nbt.CompoundTag nbt = te.getUpdateTag(null);
		if (nbt.contains("SpawnData")) {
			net.minecraft.nbt.CompoundTag spawnData = (net.minecraft.nbt.CompoundTag) nbt.get("SpawnData");
			net.minecraft.nbt.CompoundTag custom_spawn_rules = new net.minecraft.nbt.CompoundTag();
			net.minecraft.nbt.CompoundTag sky_light_limit = new net.minecraft.nbt.CompoundTag();
			sky_light_limit.putInt("min_inclusive", 0);
			sky_light_limit.putInt("max_exclusive", 15);
			net.minecraft.nbt.CompoundTag block_light_limit = new net.minecraft.nbt.CompoundTag();
			block_light_limit.putInt("min_inclusive", 0);
			block_light_limit.putInt("max_exclusive", 15);
			custom_spawn_rules.put("sky_light_limit", sky_light_limit);
			custom_spawn_rules.put("block_light_limit", block_light_limit);
			spawnData.put("custom_spawn_rules", custom_spawn_rules);
			nbt.put("SpawnData", spawnData);
		}
		if (nbt.contains("SpawnPotentials")) {
			net.minecraft.nbt.NBTTagList spawnPotentials = (net.minecraft.nbt.NBTTagList) nbt.get("SpawnPotentials");
			net.minecraft.nbt.NBTTagList newList = new net.minecraft.nbt.NBTTagList();
			for (net.minecraft.nbt.NBTBase base : spawnPotentials) {
				net.minecraft.nbt.CompoundTag node = (net.minecraft.nbt.CompoundTag) base;
				if (node.contains("data")) {
					net.minecraft.nbt.CompoundTag data = (net.minecraft.nbt.CompoundTag) node.get("data");
					net.minecraft.nbt.CompoundTag custom_spawn_rules = new net.minecraft.nbt.CompoundTag();
					net.minecraft.nbt.CompoundTag sky_light_limit = new net.minecraft.nbt.CompoundTag();
					sky_light_limit.putInt("min_inclusive", 0);
					sky_light_limit.putInt("max_exclusive", 15);
					net.minecraft.nbt.CompoundTag block_light_limit = new net.minecraft.nbt.CompoundTag();
					block_light_limit.putInt("min_inclusive", 0);
					block_light_limit.putInt("max_exclusive", 15);
					custom_spawn_rules.put("sky_light_limit", sky_light_limit);
					custom_spawn_rules.put("block_light_limit", block_light_limit);
					data.put("custom_spawn_rules", custom_spawn_rules);
					node.put("data", data);
				}
				newList.add((NBTBase) node);
			}
			nbt.put("SpawnPotentials", (Tag) newList);
		}
		//te.a(nbt);
		te.loadWithComponents(nbt, ws.getHandle().getLevel().registryAccess());
	}
}
