package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

public class FD extends Node {

	@Override
	public Response run(ManipulateController mc) {
		int pixels = getAndRun(0, mc).getIntegerValue();
		ITurtleLambda l = (Turtle t) -> {
			double[] currPosition = t.getPosition();
			System.out.println("Current Position..." + currPosition[0] + ":" + currPosition[1]);
			double heading = t.getHeading();

			double xDiff = Math.cos(Math.toRadians(heading)) * pixels; // adjacent
			double yDiff = Math.sin(Math.toRadians(heading)) * pixels; // opposite

			double xBack = (currPosition[0] + xDiff);
			double yBack = (currPosition[1] - yDiff);
			double[] nextPos = new double[] { xBack, yBack };
			t.setPosition(nextPos);
			return pixels;
		};

		mc.executeOnAllActiveTurtles(l);

		// return argument 1 value
		return new Success(pixels);

	}
}
