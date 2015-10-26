package backend.node.commands;


import backend.node.types.TwoArgumentNode;
import datatransferobjects.TurtleTransferObject;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 *
 */
public class GOTO extends TwoArgumentNode {

	@Override
	public Response run(ManipulateController sharedHandle) {
		// get xy
		int x = getAndRun(0, sharedHandle).getIntegerValue();
		int y = getAndRun(1, sharedHandle).getIntegerValue();
				 	
		ITurtleLambda l = (Turtle t) -> {
			TurtleTransferObject dto = new TurtleTransferObject(false, t.getID(), false, t.isPenDown(), t.getPosition(), new double[]{0,0});
			t.setPosition(new double[]{x,y});
			t.notifyObservers(dto);
		};
		sharedHandle.executeOnAllActiveTurtles(l);
		return new Success(0);
	}
}