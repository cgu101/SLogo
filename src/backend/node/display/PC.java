package backend.node.display;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.DisplayProperties;
import sharedobjects.IDisplayPropertiesLambda;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 */
public class PC extends Node {
	@Override
	public Response run(ManipulateController mc) {
		IDisplayPropertiesLambda l = (DisplayProperties p) -> {
			return p.getPenColorID();
		};

		double result = mc.executeDisplayProperties(l);
		return new Success(result);
	}
}