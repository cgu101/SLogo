package backend.node.commands;

import backend.node.types.TwoArgumentNode;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 *
 */
public class TOWARDS extends TwoArgumentNode {

	@Override
	public Response run(ManipulateController mc) {
		// get xy
		int targetX = getAndRun(0, mc).getIntegerValue();
		int targetY = getAndRun(1, mc).getIntegerValue();

		ITurtleLambda l = (Turtle t) -> {
			double[] currPos = t.getPosition();
			double theta = Math.atan2(-targetY - currPos[1], targetX - currPos[0]);
			double angle = Math.toDegrees(theta);
			angle = 360 - angle;
          
			if(angle >= 360){
                  angle = angle - 360;
			}
			t.setHeading(angle);
			t.notifyObservers("turtle");
		};
		
		mc.executeOnAllActiveTurtles(l);

		// return the delta
		return new Success(1);
	}
}