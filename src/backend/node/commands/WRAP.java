package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 *
 */
public class WRAP extends Node {

	@Override
	public Response run(ManipulateController sharedHandle) {
		ITurtleLambda l = (Turtle t) -> {
			t.setWindowWrap();
			return 1;
		};
		sharedHandle.executeOnAllActiveTurtles(l);
		
		// return argument 1 value
		return new Success(1);
	}
}