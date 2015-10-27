package backend.node.commands;

import backend.node.types.Command;
import responses.Response;
import responses.Success;
import sharedobjects.DisplayProperties;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 */
public class PENDOWNP extends Command{

	@Override
	public Response run(ManipulateController mc) {
		double d = mc.executeDisplayProperties((DisplayProperties t) -> {
			if (t.getPenDown())
				return 1;
			return 0;
		});
		return new Success(d);
	}

}
