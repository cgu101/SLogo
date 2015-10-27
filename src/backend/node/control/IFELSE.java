package backend.node.control;

import backend.node.types.ControlStructure;
import responses.Response;
import sharedobjects.ManipulateController;

public class IFELSE extends ControlStructure {
	@Override
	public Response run(ManipulateController mc) {
		Response left = getAndRun(0, mc);
		if (left.getDoubleValue() != 0) {
			return getAndRun(1, mc);
		} else {
			return getAndRun(2, mc);
		}
	}
}
