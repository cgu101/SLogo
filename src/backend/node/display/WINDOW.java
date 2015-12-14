package backend.node.display;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;
import sharedobjects.MapType;

public class WINDOW extends Node {

	@Override
	public Response run(ManipulateController mc) {
		mc.changeMapType(MapType.WINDOW);
		return new Success("2");	
	}

}
