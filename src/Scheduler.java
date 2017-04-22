import java.util.Queue;

public interface Scheduler {
    void schedule(java.util.List<Queue<Integer>> requests, java.util.List<Elevator> elevators, int floors);
}
