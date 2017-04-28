import java.util.TreeSet;

public class Simulator {

    //singleton
    private static SimulatorEngine instance = null;

    public static SimulatorEngine getSim() {
        if(instance == null) {
            instance = new SimulatorEngine();
        }
        return instance;
    }

    public static void stopAt(double time) {
        Event stopEvent = new SimulatorEvent(time, getSim(), SimulatorEvent.STOP_EVENT, new Elevator(-1, 0, 0, false), new Floor(-1));
        schedule(stopEvent);
    }
    public static void run() {
        getSim().run();
    }
    public static double getCurrentTime() {
        return getSim().getCurrentTime();
    }
    public static void schedule(Event event) {
        event.setTime(event.getTime() + getSim().getCurrentTime());
        getSim().schedule(event);
    }
}