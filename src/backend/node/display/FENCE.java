package backend.node.display;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;
import sharedobjects.MapType;

public class FENCE extends Node {

	@Override
	public Response run(ManipulateController mc) {
		mc.changeMapType(MapType.FENCE);
		return new Success("3");	
	}

}

