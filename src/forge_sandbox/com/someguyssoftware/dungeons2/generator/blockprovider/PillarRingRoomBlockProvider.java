/**
 * 
 */
package forge_sandbox.com.someguyssoftware.dungeons2.generator.blockprovider;

import forge_sandbox.com.someguyssoftware.dungeons2.model.Room;
import forge_sandbox.com.someguyssoftware.dungeons2.style.Layout;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;

/**
 * Creates a room where the pillars are generated in a single ring around the inner edge of the room.
 * @author Mark Gottschling on Sep 22, 2016
 *
 */
public class PillarRingRoomBlockProvider implements IDungeonsBlockProvider {

    //  pillar
    @Override
    public boolean isPillarElement(ICoords coords, Room room, Layout layout) {
        int x = coords.getX();
        int y = coords.getY();
        int z = coords.getZ();
        
        if (!room.hasPillar() || Math.min(room.getWidth(), room.getDepth()) < 7 || y == room.getMaxY()) return false;

        // get the x,z indexes
        int xIndex = x - room.getCoords().getX();
        int zIndex = z - room.getCoords().getZ();
        int offset = 1;
        int m = 0;
        
        // if the room also has pilasters, then the offset is increased so there is still space between pillar and pilaster
        if (room.hasPilaster()) {
            offset = 2;
            m = 1;
        }
        // check if at an inner ring either 1 space away from wall or 2 spaces from wall if there are pilasters
        
        return ((xIndex > offset && xIndex < room.getWidth() - offset) && Math.abs(xIndex % 2) == m 
                && (zIndex == offset+1 || zIndex == room.getDepth() - (offset+1)) && Math.abs(zIndex % 2) == m) ||
                ((xIndex == offset+1 || xIndex < room.getWidth() - (offset+1)) && Math.abs(xIndex % 2) == m
                && (zIndex > offset && zIndex < room.getDepth() - offset) && Math.abs(zIndex % 2) == m);
    }
}
