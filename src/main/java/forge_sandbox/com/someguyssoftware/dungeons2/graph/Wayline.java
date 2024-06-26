/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.graph;

import forge_sandbox.com.someguyssoftware.dungeons2.Dungeons2;
import forge_sandbox.com.someguyssoftware.gottschcore.enums.Alignment;

/**
 * @author Mark Gottschling on Jul 22, 2016
 * @since 1.0.0
 * @version 2.0
 */
public class Wayline {
	public static final int START_POINT_INDEX = 0;
	public static final int END_POINT_INDEX = 1;

	private Waypoint point1;
	private Waypoint point2;
	private Alignment alignment;
	/**
	 * the other wayline in an L-Shaped wayline path.
	 * 
	 * @since 2.0
	 */
	private Wayline wayline;

	/**
	 * 
	 */
	public Wayline() {
	}

	/**
	 * @param point1
	 * @param point2
	 */
	public Wayline(Waypoint point1, Waypoint point2) {
		this();
		this.point1 = point1;
		this.point2 = point2;

		// take best guess at alignment
		if (point1.getZ() == point2.getZ())
			setAlignment(Alignment.HORIZONTAL);
		else
			setAlignment(Alignment.VERTICAL);
	}

	/**
	 * 
	 * @param point1
	 * @param point2
	 * @param alignment
	 */
	public Wayline(Waypoint point1, Waypoint point2, Alignment alignment) {
		this(point1, point2);
		setAlignment(alignment);
	}

	/**
	 * 
	 * @param point1
	 * @param point2
	 * @param alignment
	 * @param line
	 */
	public Wayline(Waypoint point1, Waypoint point2, Alignment alignment, Wayline line) {
		this(point1, point2, alignment);
		setWayline(line);
	}

	/**
	 * @return the point1
	 */
	public Waypoint getPoint1() {
		return point1;
	}

	/**
	 * @param point1 the point1 to set
	 */
	public void setPoint1(Waypoint point1) {
		this.point1 = point1;
	}

	/**
	 * @return the point2
	 */
	public Waypoint getPoint2() {
		return point2;
	}

	/**
	 * @param point2 the point2 to set
	 */
	public void setPoint2(Waypoint point2) {
		this.point2 = point2;
	}

	@Override
	public String toString() {
		return "Wayline [point1=" + point1 + ", point2=" + point2 + ", alignment=" + alignment + "]";
	}

	/**
	 * @return the alignment
	 */
	public Alignment getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public final void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	/**
	 * 
	 * @return
	 */
	public Wayline getWayline() {
		return wayline;
	}

	/**
	 * 
	 * @param wayline
	 */
	public final void setWayline(Wayline wayline) {
		this.wayline = wayline;
	}

	/**
	 * 
	 * @return
	 */
	public Waypoint[] getAlignedPoints() {
		Waypoint[] points = new Waypoint[2];
		Dungeons2.log.debug("wayline -> {}", new Object[] { this });
		if (this.getAlignment() == Alignment.HORIZONTAL) {
			// determine which point is the "start point" - having the smallest coords
			if (this.getPoint1().getX() < this.getPoint2().getX()) {
				points[START_POINT_INDEX] = this.getPoint1();
				points[END_POINT_INDEX] = this.getPoint2();
			} else {
				points[START_POINT_INDEX] = this.getPoint2();
				points[END_POINT_INDEX] = this.getPoint1();
			}
		} else {
			if (this.getPoint1().getZ() < this.getPoint2().getZ()) {
				points[START_POINT_INDEX] = this.getPoint1();
				points[END_POINT_INDEX] = this.getPoint2();
			} else {
				points[START_POINT_INDEX] = this.getPoint2();
				points[END_POINT_INDEX] = this.getPoint1();
			}
		}
		return points;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSegment() {
		// determine if this is a "elbow joint" wayline

		return !getPoint1().isTerminated() || !getPoint2().isTerminated();
	}
}
