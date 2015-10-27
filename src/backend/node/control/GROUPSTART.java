package backend.node.control;

import backend.node.types.ControlStructure;
import exceptions.WontImplementException;
import responses.Response;
import sharedobjects.ManipulateController;

public class GROUPSTART extends ControlStructure {

	@Override
	public Response run(ManipulateController mc) {
		throw new WontImplementException("groups");
	}

}
