import java.util.Queue;

public class Elevator {
    public static final int DEFAULT_ELEVATOR_MAX_CAPACITY = 14;
    public static final int DEFAULT_ELEVATOR_MAX_FLOOR = 10;
    public static final int DEFAULT_INITIAL_LOCATION = 1;
    private final int maxCapacity;
    private final int maxFloor;
    private int load;
    private int location;
    private boolean isGoingUp;
    private int[] requests; // how many people in every destination floor

    public Elevator(int maxCapacity, int maxFloor) {
        this.maxCapacity = maxCapacity;
        this.maxFloor = maxFloor;
        load = 0; // change it to 0
        location = DEFAULT_INITIAL_LOCATION;
        isGoingUp = true;
        requests = new int[maxFloor];

    }

    public Elevator() {
        this(DEFAULT_ELEVATOR_MAX_CAPACITY, DEFAULT_ELEVATOR_MAX_FLOOR);
    }

    public boolean isEmpty() {
        return load == 0;
    }

    public boolean isFull() {
        return load >= maxCapacity;
    }

    public int getLocation() {
        return location;
    }

    public int move() {
        if (isGoingUp) {
            return (location + 1 <= maxFloor) ? ++location : location;
        } else {
            return (location - 1 >= 0) ? --location : location;
        }
    }

    public boolean changeMovingDirection() {
        isGoingUp = !isGoingUp;
        return isGoingUp;
    }

    public int load(Queue<Integer> currQueue) {
        int numNewLoad = currQueue.size();
        if (load + numNewLoad > maxCapacity) {
            System.out.println("exceed capacity, not load new passengers");
            return 0;
        }
        for (int requestedFloor : currQueue) {
            requests[requestedFloor - 1] += 1;
        }
        load += numNewLoad;
        System.out.println("load more " + numNewLoad + " current load " +  load);
        currQueue.clear();

        return numNewLoad;
    }

    public int unload() {
        int numRequests = requests[location - 1];
        if (numRequests > 0) {
            load -= numRequests;
            requests[location - 1] = 0;
            System.out.println("unload " + numRequests + " on the floor "+ location);
            return numRequests;
        }
        return 0;
    }
}
