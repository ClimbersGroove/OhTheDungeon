package forge_sandbox.com.someguyssoftware.dungeonsengine.model;

import forge_sandbox.com.someguyssoftware.gottschcore.enums.Direction;
import forge_sandbox.com.someguyssoftware.gottschcore.positional.ICoords;

/**
 * 
 * @author Mark Gottschling on Jan 9, 2019
 *
 */
public interface IExit {

	int getID();

	IExit setID(int id);

	ICoords getCoords();

	IExit setCoords(ICoords coords);

	IRoom getRoom();

	IExit setRoom(IRoom room);

	Direction getDirection();

	IExit setDirection(Direction direction);

}