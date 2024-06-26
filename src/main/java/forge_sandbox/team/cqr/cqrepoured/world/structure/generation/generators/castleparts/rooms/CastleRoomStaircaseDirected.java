package forge_sandbox.team.cqr.cqrepoured.world.structure.generation.generators.castleparts.rooms;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import forge_sandbox.BlockPos;
import forge_sandbox.EnumFacingConstant;
import forge_sandbox.team.cqr.cqrepoured.util.BlockStateGenArray;
import forge_sandbox.team.cqr.cqrepoured.util.DungeonGenUtils;
import forge_sandbox.team.cqr.cqrepoured.world.structure.generation.dungeons.DungeonRandomizedCastle;

public class CastleRoomStaircaseDirected extends CastleRoomBase {
	private static final int PLATFORM_LENGTH = 2;
	private BlockFace doorSide;
	private int numRotations;
	private int upperStairWidth;
	private int upperStairLength;
	private int centerStairWidth;
	private int centerStairLength;

	public CastleRoomStaircaseDirected(int sideLength, int height, BlockFace doorSide, int floor, Random rand) {
		super(sideLength, height, floor, rand);
		this.roomType = EnumRoomType.STAIRCASE_DIRECTED;
		this.doorSide = doorSide;
		this.numRotations = DungeonGenUtils.getCWRotationsBetween(BlockFace.SOUTH, this.doorSide);
		this.defaultCeiling = false;

		this.upperStairWidth = 0;

		// Determine the width of the center stairs and the two upper side stairs. Find
		// the largest possible
		// side width such that the center width is still greater than or equal to the
		// length of each side.
		do {
			this.upperStairWidth++;
			this.centerStairWidth = (sideLength - 1) - this.upperStairWidth * 2;
		} while ((this.centerStairWidth - 2) >= (this.upperStairWidth + 1));

		// Each stair section should cover half the ascent
		this.upperStairLength = height / 2;
		this.centerStairLength = height + 1 - this.upperStairLength; // center section will either be same length or 1
																		// more
	}

	@Override
	public void generateRoom(BlockPos castleOrigin, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		// If stairs are facing to the east or west, need to flip the build lengths
		// since we are essentially
		// generating a room facing south and then rotating it
		int lenX = EnumFacingConstant.getAxis(this.doorSide) == EnumFacingConstant.Axis.Z ? this.roomLengthX
				: this.roomLengthZ;
		int lenZ = EnumFacingConstant.getAxis(this.doorSide) == EnumFacingConstant.Axis.Z ? this.roomLengthZ
				: this.roomLengthX;

		for (int x = 0; x < lenX - 1; x++) {
			for (int z = 0; z < lenZ - 1; z++) {
				this.buildFloorBlock(x, z, genArray, dungeon);

				if (z < 2) {
					this.buildPlatform(x, z, genArray, dungeon);
				} else if (((x < this.upperStairWidth) || (x >= this.centerStairWidth + this.upperStairWidth))
						&& z < this.upperStairLength + PLATFORM_LENGTH) {
					this.buildUpperStair(x, z, genArray, dungeon);
				} else if (((x >= this.upperStairWidth) || (x < this.centerStairWidth + this.upperStairWidth))
						&& z <= this.centerStairLength + PLATFORM_LENGTH) {
					this.buildLowerStair(x, z, genArray, dungeon);
				}
			}
		}
	}

	public void setDoorSide(BlockFace side) {
		this.doorSide = side;
	}

	public int getUpperStairEndZ() {
		return (this.upperStairLength);
	}

	public int getUpperStairWidth() {
		return this.upperStairWidth;
	}

	public int getCenterStairWidth() {
		return this.centerStairWidth;
	}

	public BlockFace getDoorSide() {
		return this.doorSide;
	}

	private void buildFloorBlock(int x, int z, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		BlockData blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
		genArray.addBlockState(this.roomOrigin.add(x, 0, z), blockToBuild, BlockStateGenArray.GenerationPhase.MAIN,
				BlockStateGenArray.EnumPriority.MEDIUM);
	}

	private void buildUpperStair(int x, int z, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int stairHeight = this.centerStairLength + (z - PLATFORM_LENGTH);
		BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.SOUTH, this.numRotations);
		BlockData blockToBuild;
		for (int y = 1; y < this.height; y++) {
			if (y < stairHeight) {
				blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
			} else if (y == stairHeight) {
				Directional dir = (Directional) Bukkit.createBlockData(dungeon.getMainBlockState());
				dir.setFacing(stairFacing);
				blockToBuild = dir;
			} else {
				blockToBuild = Bukkit.createBlockData(Material.AIR);
			}
			genArray.addBlockState(this.getRotatedPlacement(x, y, z, this.doorSide), blockToBuild,
					BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
		}
	}

	private void buildLowerStair(int x, int z, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		int stairHeight = this.centerStairLength - (z - PLATFORM_LENGTH + 1);
		BlockFace stairFacing = DungeonGenUtils.rotateFacingNTimesAboutY(BlockFace.NORTH, this.numRotations);
		BlockData blockToBuild;
		for (int y = 1; y < this.height; y++) {
			if (y < stairHeight) {
				blockToBuild = Bukkit.createBlockData(dungeon.getMainBlockState());
			} else if (y == stairHeight) {
				Directional dir = (Directional) Bukkit.createBlockData(dungeon.getStairBlockState());
				dir.setFacing(stairFacing);
				blockToBuild = dir;
			} else {
				blockToBuild = Bukkit.createBlockData(Material.AIR);
			}
			genArray.addBlockState(this.getRotatedPlacement(x, y, z, this.doorSide), blockToBuild,
					BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
		}
	}

	private void buildPlatform(int x, int z, BlockStateGenArray genArray, DungeonRandomizedCastle dungeon) {
		BlockData blockToBuild;
		int platformHeight = this.centerStairLength; // the stair length is also the platform height

		for (int y = 1; y < this.height; y++) {
			if (y < platformHeight) {
				blockToBuild = Bukkit.createBlockData(dungeon.getFloorBlockState());
			} else {
				blockToBuild = Bukkit.createBlockData(Material.AIR);
			}
			genArray.addBlockState(this.getRotatedPlacement(x, y, z, this.doorSide), blockToBuild,
					BlockStateGenArray.GenerationPhase.MAIN, BlockStateGenArray.EnumPriority.MEDIUM);
		}
	}

	@Override
	public boolean canBuildDoorOnSide(BlockFace side) {
		return (side == this.doorSide);
	}

	@Override
	public boolean reachableFromSide(BlockFace side) {
		return (side == this.doorSide);
	}
}
