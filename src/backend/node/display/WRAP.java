package backend.node.display;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;
import sharedobjects.MapType;

public class WRAP extends Node {

	@Override
	public Response run(ManipulateController mc) {
		mc.changeMapType(MapType.WRAP);
		return new Success("1");	
	}

}
