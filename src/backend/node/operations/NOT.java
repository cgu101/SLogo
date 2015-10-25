package backend.node.operations;

import backend.node.types.OneArgumentNode;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 *
 */
public class NOT extends OneArgumentNode{

	@Override
	public Response run(ManipulateController mc) {	
		Double a = getAndRun(0, mc).getDoubleValue();
		//convert input to radians, cos, conver to degrees
		return new Success(a == 0 ? 1 : 0);
	}
}