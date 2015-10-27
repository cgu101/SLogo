package backend.node.commands;

import backend.node.types.Command;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 */
public class HOME extends Command {

	public Response run(ManipulateController mc) {
		// get xy		
		ITurtleLambda l = (Turtle t) -> {
			double[] curr = t.getPosition();
			Double dist = Math.sqrt(Math.pow(curr[0] - 0, 2) + Math.pow(curr[1] - 0, 2));
			t.setPosition(new double[]{0,0});
			return dist;
		};
		double dist = mc.executeOnAllActiveTurtles(l);
		return new Success(dist);
	}
}