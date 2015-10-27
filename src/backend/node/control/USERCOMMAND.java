package backend.node.control;

import backend.node.Node;
import backend.node.types.Constant;
import backend.node.types.ControlStructure;
import responses.Response;
import sharedobjects.Functions;
import sharedobjects.ManipulateController;
import sharedobjects.Variables;

public class USERCOMMAND extends ControlStructure {
	@Override
	public Response run(ManipulateController mc) {
		Node root = mc.executeOnWorkspaceFunctions((Functions f) -> {
			return f.getCommand(getName());
		});
		
		//Set variables to parameters
		for (int i = 0; i < getChildrenNum(); i++) {
			String var = root.get(i).getName();
			//Set variable
			mc.executeOnWorkspaceVariables((Variables v) -> {
				v.setVariable(var, new Constant(get(0).getDoubleValue()));
				return new Constant(0);
			});
		}
		
		//Run the list
		return root.getAndRun(getChildrenNum(), mc);
	}
}