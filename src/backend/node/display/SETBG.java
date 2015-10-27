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
public class SETBG extends Node {
	@Override
	public Response run(ManipulateController mc) {
		// Get index
		int index = getAndRun(0, mc).getIntegerValue();
		IDisplayPropertiesLambda l = (DisplayProperties p) -> {
			p.setBgColor(index);
			return 0;
		};

		mc.executeDisplayProperties(l);
		return new Success(index);
	}
}