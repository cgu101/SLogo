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
public class ST extends Node {

	@Override
	public Response run(ManipulateController mc) {
		ITurtleLambda l = (Turtle t) -> {
			t.show();
			return 1;
		};
		mc.executeOnAllActiveTurtles(l);
		// return 1
		return new Success(1);
	}
}