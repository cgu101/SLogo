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
public class SETPALETTE extends Node {
	@Override
	public Response run(ManipulateController mc) {
		// Get index
		int index = getAndRun(0, mc).getIntegerValue();
		int r = getAndRun(1, mc).getIntegerValue();
		int g = getAndRun(2, mc).getIntegerValue();
		int b = getAndRun(3, mc).getIntegerValue();
		
		IDisplayPropertiesLambda l = (DisplayProperties d) -> {
			d.newPaletteColor(index, r, g, b);
			return 0;
		};
		
		mc.executeDisplayProperties(l);
		return new Success(index);
	}
}