package sharedobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import backend.node.Node;
import responses.Response;

public class ManipulateController implements IWorkSpaceController {
	private Workspace currWorkspace;
	private List<Workspace> workspaceList = new LinkedList<Workspace>();

	public ManipulateController(Workspace w) {
		currWorkspace = w;
		workspaceList.add(currWorkspace);
	}

	public void setTempTurtles(int[] ids) {
		currWorkspace.getTurtleContainer().setTempTurtles(ids);
	}

	public List<Integer> getActiveTurtleIDS() {
		List<Integer> ids = new ArrayList<Integer>();
		currWorkspace.getTurtleContainer().getActiveTurtles().forEach((a) -> ids.add(a.getID()));
		return ids;
	}

	public List<Integer> getTurtleIDS() {
		List<Integer> ids = new ArrayList<Integer>();
		(currWorkspace.getTurtleContainer().getAllTurtles().values()).forEach((a) -> ids.add(a.getID()));
		return ids;
	}

	public void clearTempTurtles() {
		currWorkspace.setTempTurtles(Collections.<Turtle> emptyList());
	}

	public int tellTurtles(int[] ids) {
		return currWorkspace.getTurtleContainer().tellTurtles(ids);
	}

	public double executeDisplayProperties(IDisplayPropertiesLambda l) {
		return l.run(currWorkspace.getDisplayProp());
	}

	public Node executeOnWorkspaceFunctions(IWorkspaceFunctionsLambda l) {
		return l.run(currWorkspace.getFuncs());
	}

	public Node executeOnWorkspaceVariables(IWorkspaceVariablesLambda l) {
		return l.run(currWorkspace.getVars());
	}

	public double executeOnTurtleContainer(ITurtleLambda lambda) {
		return currWorkspace.executeOnAllActiveTurtles(lambda);
	}

	public double executeOnAllActiveTurtles(ITurtleLambda lambda) {
		return currWorkspace.executeOnAllActiveTurtles(lambda);
	}

	public double executeOnAllTurtles(ITurtleLambda lambda) {
		return currWorkspace.executeOnAllTurtles(lambda);
	}

	public void setReponse(Response s) {
		currWorkspace.setCurrentResponse(s);
	}

	public double executeOnCurrentTurtle(ITurtleLambda lambda) {
		return currWorkspace.executeOnCurrentTurtle(lambda);
	}

	public void tellDuvall2Dance() {
		currWorkspace.tellDuvall2Dance();
	}
	
	public void changeMapType(MapType m){
		currWorkspace.changeMapType(m);
	}
}