/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package forge_sandbox;

public class AxisAlignedBB {
	public final double minX;
	public final double minY;
	public final double minZ;
	public final double maxX;
	public final double maxY;
	public final double maxZ;

	public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.minX = Math.min(x1, x2);
		this.minY = Math.min(y1, y2);
		this.minZ = Math.min(z1, z2);
		this.maxX = Math.max(x1, x2);
		this.maxY = Math.max(y1, y2);
		this.maxZ = Math.max(z1, z2);
	}

	public AxisAlignedBB(BlockPos pos) {
		this((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double) (pos.getX() + 1),
				(double) (pos.getY() + 1), (double) (pos.getZ() + 1));
	}

	public AxisAlignedBB(BlockPos pos1, BlockPos pos2) {
		this((double) pos1.getX(), (double) pos1.getY(), (double) pos1.getZ(), (double) pos2.getX(),
				(double) pos2.getY(), (double) pos2.getZ());
	}

	public AxisAlignedBB(Vec3d min, Vec3d max) {
		this(min.x, min.y, min.z, max.x, max.y, max.z);
	}

	public AxisAlignedBB setMaxY(double y2) {
		return new AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, y2, this.maxZ);
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof AxisAlignedBB)) {
			return false;
		} else {
			AxisAlignedBB axisalignedbb = (AxisAlignedBB) p_equals_1_;

			if (Double.compare(axisalignedbb.minX, this.minX) != 0) {
				return false;
			} else if (Double.compare(axisalignedbb.minY, this.minY) != 0) {
				return false;
			} else if (Double.compare(axisalignedbb.minZ, this.minZ) != 0) {
				return false;
			} else if (Double.compare(axisalignedbb.maxX, this.maxX) != 0) {
				return false;
			} else if (Double.compare(axisalignedbb.maxY, this.maxY) != 0) {
				return false;
			} else {
				return Double.compare(axisalignedbb.maxZ, this.maxZ) == 0;
			}
		}
	}

	@Override
	public int hashCode() {
		long i = Double.doubleToLongBits(this.minX);
		int j = (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.minY);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.minZ);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.maxX);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.maxY);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.maxZ);
		j = 31 * j + (int) (i ^ i >>> 32);
		return j;
	}

	public AxisAlignedBB contract(double x, double y, double z) {
		double d0 = this.minX;
		double d1 = this.minY;
		double d2 = this.minZ;
		double d3 = this.maxX;
		double d4 = this.maxY;
		double d5 = this.maxZ;

		if (x < 0.0D) {
			d0 -= x;
		} else if (x > 0.0D) {
			d3 -= x;
		}

		if (y < 0.0D) {
			d1 -= y;
		} else if (y > 0.0D) {
			d4 -= y;
		}

		if (z < 0.0D) {
			d2 -= z;
		} else if (z > 0.0D) {
			d5 -= z;
		}

		return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
	}

	public AxisAlignedBB expand(double x, double y, double z) {
		double d0 = this.minX;
		double d1 = this.minY;
		double d2 = this.minZ;
		double d3 = this.maxX;
		double d4 = this.maxY;
		double d5 = this.maxZ;

		if (x < 0.0D) {
			d0 += x;
		} else if (x > 0.0D) {
			d3 += x;
		}

		if (y < 0.0D) {
			d1 += y;
		} else if (y > 0.0D) {
			d4 += y;
		}

		if (z < 0.0D) {
			d2 += z;
		} else if (z > 0.0D) {
			d5 += z;
		}

		return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
	}

	public AxisAlignedBB grow(double x, double y, double z) {
		double d0 = this.minX - x;
		double d1 = this.minY - y;
		double d2 = this.minZ - z;
		double d3 = this.maxX + x;
		double d4 = this.maxY + y;
		double d5 = this.maxZ + z;
		return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
	}

	public AxisAlignedBB grow(double value) {
		return this.grow(value, value, value);
	}

	public AxisAlignedBB intersect(AxisAlignedBB other) {
		double d0 = Math.max(this.minX, other.minX);
		double d1 = Math.max(this.minY, other.minY);
		double d2 = Math.max(this.minZ, other.minZ);
		double d3 = Math.min(this.maxX, other.maxX);
		double d4 = Math.min(this.maxY, other.maxY);
		double d5 = Math.min(this.maxZ, other.maxZ);
		return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
	}

	public AxisAlignedBB union(AxisAlignedBB other) {
		double d0 = Math.min(this.minX, other.minX);
		double d1 = Math.min(this.minY, other.minY);
		double d2 = Math.min(this.minZ, other.minZ);
		double d3 = Math.max(this.maxX, other.maxX);
		double d4 = Math.max(this.maxY, other.maxY);
		double d5 = Math.max(this.maxZ, other.maxZ);
		return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
	}

	public AxisAlignedBB offset(double x, double y, double z) {
		return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y,
				this.maxZ + z);
	}

	public AxisAlignedBB offset(BlockPos pos) {
		return new AxisAlignedBB(this.minX + (double) pos.getX(), this.minY + (double) pos.getY(),
				this.minZ + (double) pos.getZ(), this.maxX + (double) pos.getX(), this.maxY + (double) pos.getY(),
				this.maxZ + (double) pos.getZ());
	}

	public AxisAlignedBB offset(Vec3d vec) {
		return this.offset(vec.x, vec.y, vec.z);
	}

	public double calculateXOffset(AxisAlignedBB other, double offsetX) {
		if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
			if (offsetX > 0.0D && other.maxX <= this.minX) {
				double d1 = this.minX - other.maxX;

				if (d1 < offsetX) {
					offsetX = d1;
				}
			} else if (offsetX < 0.0D && other.minX >= this.maxX) {
				double d0 = this.maxX - other.minX;

				if (d0 > offsetX) {
					offsetX = d0;
				}
			}

			return offsetX;
		} else {
			return offsetX;
		}
	}

	public double calculateYOffset(AxisAlignedBB other, double offsetY) {
		if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
			if (offsetY > 0.0D && other.maxY <= this.minY) {
				double d1 = this.minY - other.maxY;

				if (d1 < offsetY) {
					offsetY = d1;
				}
			} else if (offsetY < 0.0D && other.minY >= this.maxY) {
				double d0 = this.maxY - other.minY;

				if (d0 > offsetY) {
					offsetY = d0;
				}
			}

			return offsetY;
		} else {
			return offsetY;
		}
	}

	public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
		if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
			if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
				double d1 = this.minZ - other.maxZ;

				if (d1 < offsetZ) {
					offsetZ = d1;
				}
			} else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
				double d0 = this.maxZ - other.minZ;

				if (d0 > offsetZ) {
					offsetZ = d0;
				}
			}

			return offsetZ;
		} else {
			return offsetZ;
		}
	}

	public boolean intersects(AxisAlignedBB other) {
		return this.intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
	}

	public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
		return this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1;
	}

	public boolean intersects(Vec3d min, Vec3d max) {
		return this.intersects(Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z),
				Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
	}

	public boolean contains(Vec3d vec) {
		if (vec.x > this.minX && vec.x < this.maxX) {
			if (vec.y > this.minY && vec.y < this.maxY) {
				return vec.z > this.minZ && vec.z < this.maxZ;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public double getAverageEdgeLength() {
		double d0 = this.maxX - this.minX;
		double d1 = this.maxY - this.minY;
		double d2 = this.maxZ - this.minZ;
		return (d0 + d1 + d2) / 3.0D;
	}

	public AxisAlignedBB shrink(double value) {
		return this.grow(-value);
	}

	@Override
	public String toString() {
		return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", "
				+ this.maxZ + "]";
	}

	public boolean hasNaN() {
		return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX)
				|| Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
	}

	public Vec3d getCenter() {
		return new Vec3d(this.minX + (this.maxX - this.minX) * 0.5D, this.minY + (this.maxY - this.minY) * 0.5D,
				this.minZ + (this.maxZ - this.minZ) * 0.5D);
	}
}
