package forge_sandbox.greymerk.roguelike.treasure.loot.provider;

import java.util.Random;

import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.treasure.loot.PotionMixture;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.item.ItemStack;

public class ItemMixture extends ItemBase {

	PotionMixture type;

	public ItemMixture(JsonObject data, int weight) throws Exception {
		super(weight, 0);

		if (!data.has("name"))
			throw new Exception("Mixture requires a name");

		String name = data.get("name").getAsString();

		try {
			this.type = PotionMixture.valueOf(name.toUpperCase());
		} catch (Exception e) {
			throw new Exception("No such Mixture: " + name);
		}
	}

	@Override
	public ItemStack getLootItem(Random rand, int level) {
		return PotionMixture.getPotion(rand, type);
	}
}
