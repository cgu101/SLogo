package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 *
 */
public class TELL extends Node {
	@Override
	public Response run(ManipulateController mc) {
		// Call setTurtleIDS(array of ints) -> this should create turtles as well
		// If only one argument then I'll create a range 0->x
		int numChildren = getChildren().get(0).getChildrenNum();
		int[] ids = new int[numChildren];
		for(int i =0; i < numChildren; i++){
			ids[i] = getChildren().get(0).getAndRun(i, mc).getIntegerValue();
		}
//		getAndRun(0,mc);
		int id = mc.tellTurtles(ids);
		return new Success(id);
	}
}