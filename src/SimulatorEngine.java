import java.util.TreeSet;

public class SimulatorEngine implements EventHandler {

    private double m_currentTime;
    private TreeSet<Event> m_eventList;
    private boolean m_running;
    private int number = 0;

    SimulatorEngine() {
        m_running = false;
        m_currentTime = 0.0;
        m_eventList = new TreeSet<Event>();
    }

    void run() {
        m_running = true;

        while(m_running) {
//            if (this.getCurrentTime() == (int) this.getCurrentTime() ) {
//                ElevatorSim.generateRequest();
//            }
            number ++;
            if(number % 2 == 0) {
                ElevatorSim.generateRequest();
            }
            if(!m_eventList.isEmpty()){
                Event ev = m_eventList.pollFirst();
                m_currentTime = ev.getTime();
                ev.getHandler().handle(ev);
            }
        }

    }

    public void handle(Event event) {
        SimulatorEvent ev = (SimulatorEvent)event;

        switch(ev.getEventType()) {
            case SimulatorEvent.STOP_EVENT:
                m_running = false;
                System.out.println("Simulator stopping at time: " + ev.getTime());
                break;
            default:
                System.out.println("Invalid event type");
        }
    }

    public void schedule(Event event) {
        if (this.getCurrentTime() == (int) this.getCurrentTime() && (int) this.getCurrentTime() % 5 == 0) {
            ElevatorSim.generateRequest();
        }
        m_eventList.add(event);
    }

    public void stop() {
        m_running = false;
    }

    public double getCurrentTime() {
        return m_currentTime;
    }
}