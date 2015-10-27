package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 *
 */
public class ID extends Node {
	@Override
	public Response run(ManipulateController mc) {
		return new Success(mc.getActiveTurtleIDS().get(mc.getActiveTurtleIDS().size()-1));
	}
}