// This entire file is part of my masterpiece.
// Inan Tainwala

package observers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import GUI.SlogoView;
import GUI.turtlepane.CanvasObserver;
import GUI.turtlepane.TurtleGroupObserver;
import GUI.viewbox.TurtleStateBoxObserver;

/**
 * @author Inan (Edited by Logan)
 *
 */
public class ObserverFactory {

    private List<Observer> myObservers;

    private TurtleGroupObserver guiTurtleGroup;
    private CanvasObserver guiCanvas;

    public ObserverFactory () {
        this.myObservers = new ArrayList<Observer>();
        createObservers();
    }

    private void createObservers(){
        myObservers.add(guiTurtleGroup = new TurtleGroupObserver());
        myObservers.add(guiCanvas = new CanvasObserver());
    }

    public void createObserversWithGUIDependancies(SlogoView slogoView){
        myObservers.add(new FunctionVariableObserver(slogoView.getFunctionDisplayBox(), slogoView.getVariableDisplayBox()));
        TurtleStateBoxObserver t = new TurtleStateBoxObserver(slogoView.getTurtlePaneGroup().getTurtleImageMap());
        slogoView.addElementToRightWindow(t);
        myObservers.add(t);
        //No need for wrapper observer class here- MessageDisplayBox is only used in GUI once.
        myObservers.add(slogoView.getMessageBox());
        myObservers.add(slogoView.getRect());
    }

    public List<Observer> getObservers(){
        return myObservers;
    }

    public TurtleGroupObserver getGuiTurtleGroup () {
        return guiTurtleGroup;
    }

    public CanvasObserver getGuiCanvas () {
        return guiCanvas;
    }
}
