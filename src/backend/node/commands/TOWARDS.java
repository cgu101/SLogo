package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 */
public class TOWARDS extends Node {

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
			Double delta = Math.abs(t.getHeading() - angle);
			delta = Math.min(delta, 360 - delta);
			
			t.setHeading(angle);
			return delta;
		};
		
		Double delta = mc.executeOnAllActiveTurtles(l);

		// return the delta
		return new Success(delta);
	}
}