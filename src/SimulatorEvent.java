public class SimulatorEvent extends Event {
    public static final int STOP_EVENT = 0;

    SimulatorEvent(double delay, EventHandler handler, int eventType, Elevator elevator, Floor headingFloor) {
        super(delay, handler, eventType, elevator, headingFloor);
    }
}