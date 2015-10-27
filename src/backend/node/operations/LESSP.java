package backend.node.operations;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 */
public class LESSP extends Node{

	@Override
	public Response run(ManipulateController mc) {	
		Double a = getAndRun(0, mc).getDoubleValue();
		Double b = getAndRun(1, mc).getDoubleValue();
		//convert input to radians, cos, conver to degrees
		return new Success(a < b ? 1 : 0);
	}
}