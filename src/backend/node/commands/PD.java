package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.DisplayProperties;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 */
public class PD extends Node {

	@Override
	public Response run(ManipulateController mc) {
		mc.executeDisplayProperties((DisplayProperties t) -> {
			t.setPenDown(true);
			t.notifyObservers("pen");
			return 0;
		});
		// return 1
		return new Success(1);
	}
}