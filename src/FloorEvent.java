//YOUR NAME HERE

public class FloorEvent extends Event {
    public static final int GOING_UP = 0;
    public static final int GOING_DOWN = 1;
    public static final int LOADING = 2;
    public static final int HOLDING = 3;
    
    

    FloorEvent(double delay, EventHandler handler, int eventType, Elevator elevator, Floor headingFloor) {
        super(delay, handler, eventType, elevator, headingFloor);
    }
}