package forge_sandbox.greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import forge_sandbox.greymerk.roguelike.treasure.ITreasureChest;
import forge_sandbox.greymerk.roguelike.treasure.Treasure;
import forge_sandbox.greymerk.roguelike.treasure.TreasureManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.loot.LootTable;

public class LootTableRule {

	private LootTable table;
	private List<Treasure> type;
	List<Integer> level;

	public LootTableRule(JsonObject json) throws Exception {
		if (!json.has("table"))
			throw new Exception("Loot table requires a table field");
		this.table = Bukkit.getServer().getLootTable(NamespacedKey.minecraft(json.get("table").getAsString()));
		if (json.has("type")) {
			type = new ArrayList<>();
			JsonElement typeElement = json.get("type");
			if (typeElement.isJsonArray()) {
				JsonArray arr = typeElement.getAsJsonArray();
				for (JsonElement e : arr) {
					this.type.add(Treasure.valueOf(e.getAsString()));
				}
			} else {
				this.type.add(Treasure.valueOf(typeElement.getAsString()));
			}
		}

		if (json.has("level")) {
			this.level = parseLevels(json.get("level"));
		}
	}

	public void process(TreasureManager treasure) {
		List<ITreasureChest> chests = getMatching(treasure);
		for (ITreasureChest chest : chests) {
			if (chest.getType() != Treasure.EMPTY)
				chest.setLootTable(table);
		}
	}

	private List<ITreasureChest> getMatching(TreasureManager treasure) {
		if (this.type == null && this.level == null)
			return treasure.getChests();

		List<ITreasureChest> chests = new ArrayList<>();
		if (this.type == null) {
			for (int level : this.level) {
				chests.addAll(treasure.getChests(level));
			}
		}

		if (this.level == null) {
			for (Treasure type : this.type) {
				chests.addAll(treasure.getChests(type));
			}
		}

		return chests;
	}

	private List<Integer> parseLevels(JsonElement e) {

		List<Integer> levels = new ArrayList<>();

		if (e.isJsonArray()) {
			JsonArray arr = e.getAsJsonArray();
			for (JsonElement i : arr) {
				levels.add(i.getAsInt());
			}
			return levels;
		}

		levels.add(e.getAsInt());
		return levels;
	}

}
