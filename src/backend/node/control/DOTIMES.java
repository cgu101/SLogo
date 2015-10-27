package backend.node.control;

import backend.node.types.Constant;
import backend.node.types.ControlStructure;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;
import sharedobjects.Variables;

public class DOTIMES extends ControlStructure {
	@Override
	public Response run(ManipulateController mc) {
		int count = (int) getAndRun(1, mc).getDoubleValue();
		String variableName = get(0).getName();
		
		mc.executeOnWorkspaceVariables((Variables v) -> {
			v.setVariable(variableName, new Constant(1));
			return new Constant(0);
		});

		// Run - return 0 by default
		Response s = new Success(0);
		for (int i = 0; i < count; i++) {
			s = getAndRun(2, mc);
			// Increment
			mc.executeOnWorkspaceVariables((Variables v) -> {
				v.incrementVariable(variableName);
				return new Constant(0);
			});
		}

		// Return
		return s;
	}
}