package backend.node.display;

import backend.node.Node;
import exceptions.WontImplementException;
import responses.Response;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 */
public class CLEARSTAMPS extends Node {
	@Override
	public Response run(ManipulateController mc) {
		throw new WontImplementException("CLEARSTAMPS");
	}
}