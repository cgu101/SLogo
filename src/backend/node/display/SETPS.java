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
public class SETPS extends Node {
	@Override
	public Response run(ManipulateController mc) {
		// Get index
		Double size = getAndRun(0, mc).getDoubleValue();
		
		IDisplayPropertiesLambda l = (DisplayProperties d) -> {
			d.setPenThickness(size);
			return 0;
		};

		mc.executeDisplayProperties(l);
		return new Success(size);
	}
}