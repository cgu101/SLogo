package backend.node.operations;

import backend.node.types.OneArgumentNode;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 *
 */
public class SIN extends OneArgumentNode{

	@Override
	public Response run(ManipulateController mc) {	
		Double x = getAndRun(0, mc).getDoubleValue();
		//convert input to radians, sin, conver to degrees
		return new Success(Math.toDegrees(Math.sin(Math.toRadians(x))));
	}
}