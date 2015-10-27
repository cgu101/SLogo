package backend.node.commands;
import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.DisplayProperties;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 *
 */
public class CS extends Node {

	@Override
	public Response run(ManipulateController sharedHandle) {
		sharedHandle.executeOnAllTurtles((Turtle t) -> {
			t.setPosition(new double[]{0,0});
			t.setHeading(90);
			return 0;
		});
			
		sharedHandle.executeDisplayProperties((DisplayProperties t) -> {
			t.clear();
			t.notifyObservers("turtle");
			return 0;
		});
		
		return new Success(0);
	}
}