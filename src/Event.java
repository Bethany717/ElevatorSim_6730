//YOUR NAME HERE

public class Event implements Comparable<Event>{
    private EventHandler eventHandler;
    private double time;
    private int eventType;
    private Elevator elevator;
    private Floor headingFloor;
    private int m_eventId;
    static private int m_nextId = 0;

    Event(double delay, EventHandler handler, int eventType, Elevator elevator, Floor headingFloor) {
        this.time = delay;
        this.eventHandler = handler;
        this.eventType = eventType;
        this.elevator = elevator;
        this.headingFloor = headingFloor;
        m_eventId = m_nextId++;
    }
    public int getId() {
        return m_eventId;
    }
    public Elevator getElevator() {
        return elevator;
    }
    public double getTime() {
        return time;
    }
    public void setTime(double time) {
    	this.time = time;
    }

    public EventHandler getHandler() {
        return eventHandler;
    }

    public int getEventType() { 
    	return eventType; 
    }

    public void setHandler(EventHandler handler) {
        eventHandler = handler;
    }
    public int getElevatorName(Elevator elevator) {
        return elevator.getName();
    }
    public Floor getHeadingFloor() { 
    	return headingFloor; 
    }
    @Override
    public int compareTo(final Event ev) {
        int timeCmp = Double.compare(time, ev.getTime());
        if(timeCmp != 0) {
            return timeCmp;
        }
        else
            return Integer.compare(m_eventId, ev.getId());
    }
}