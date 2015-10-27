package backend.node.operations;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 */
public class MINUS extends Node{

	@Override
	public Response run(ManipulateController mc) {
		Double result = getAndRun(0, mc).getDoubleValue()*-1;
		return new Success(result);
	}
}
