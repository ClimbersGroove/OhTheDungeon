package forge_sandbox.team.cqr.cqrepoured.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import forge_sandbox.BlockPos;
import forge_sandbox.Vec3i;

public class GenerationTemplate {

	private class GenerationRule {

		private Predicate<Vec3i> condition;
		private BlockData block;

		public GenerationRule(Predicate<Vec3i> condition, BlockData blockToBuild) {
			this.condition = condition;
			this.block = blockToBuild;
		}

		public Predicate<Vec3i> getCondition() {
			return this.condition;
		}

		public BlockData getBlock() {
			return this.block;
		}

	}

	private List<GenerationRule> generationRules;
	private int lengthX;
	private int lengthY;
	private int lengthZ;

	private boolean fillUnusedBlockWithAir = true;

	public GenerationTemplate(int lengthX, int lengthY, int lengthZ) {
		this.generationRules = new ArrayList<>();
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.lengthZ = lengthZ;
	}

	public void setFillUnusedBlockWithAir(boolean shouldFill) {
		this.fillUnusedBlockWithAir = shouldFill;
	}

	public GenerationTemplate(Vec3i dimensions) {
		this.generationRules = new ArrayList<>();
		this.lengthX = dimensions.getX();
		this.lengthY = dimensions.getY();
		this.lengthZ = dimensions.getZ();
	}

	public void addRule(Predicate<Vec3i> condition, BlockData blockToBuild) {
		this.generationRules.add(new GenerationRule(condition, blockToBuild));
	}

	public void addToGenArray(BlockPos origin, BlockStateGenArray genArray, BlockStateGenArray.GenerationPhase phase) {
		this.addToGenArray(origin, genArray, phase, null);
	}

	public void addToGenArray(BlockPos origin, BlockStateGenArray genArray, BlockStateGenArray.GenerationPhase phase,
			@Nullable Set<BlockPos> positionsFilled) {
		Map<BlockPos, BlockData> genMap = this.getGenerationMap(origin, this.fillUnusedBlockWithAir);
		genArray.addBlockStateMap(genMap, phase, BlockStateGenArray.EnumPriority.MEDIUM);
		if (positionsFilled != null) {
			for (Map.Entry<BlockPos, BlockData> entry : genMap.entrySet()) {
				if (entry.getValue().getMaterial() != Material.AIR) {
					positionsFilled.add(entry.getKey());
				}
			}
		}
	}

	public Map<BlockPos, BlockData> getGenerationMap(BlockPos origin, boolean fillUnusedWithAir) {
		Map<BlockPos, BlockData> result = new HashMap<>();

		for (int x = 0; x < this.lengthX; x++) {
			for (int z = 0; z < this.lengthZ; z++) {
				for (int y = 0; y < this.lengthY; y++) {
					boolean foundRule = false;

					Vec3i offset = new Vec3i(x, y, z);
					for (GenerationRule rule : this.generationRules) {
						if (rule.getCondition().test(offset)) {
							result.put(origin.add(offset), rule.getBlock());
							foundRule = true;
							break; // No need to test other rules
						}
					}

					if (!foundRule && fillUnusedWithAir) {
						result.put(origin.add(offset), Bukkit.createBlockData(Material.AIR));
					}
				}
			}
		}

		return result;
	}

	public List<Map.Entry<BlockPos, BlockData>> getGenerationList(BlockPos origin, boolean fillUnusedWithAir) {
		return new ArrayList<>(this.getGenerationMap(origin, fillUnusedWithAir).entrySet());
	}
}