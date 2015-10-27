package backend.node.commands;

import backend.node.Node;
import exceptions.WontImplementException;
import responses.Response;
import sharedobjects.ManipulateController;

public class ASKWITH extends Node  {

	@Override
	public Response run(ManipulateController mc) {
		throw new WontImplementException("ASKWITH");
	}
}
