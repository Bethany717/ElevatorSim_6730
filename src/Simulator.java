
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Simulator {
    private final int floors;
    private List<Queue<Integer>> requests;
    private List<Elevator> elevators;
    private Scheduler scheduler;

    public Simulator(int floors, int numElevators, Scheduler scheduler, List<Queue<Integer>> intialRequests,
                     int elevatorCapacity) {
        this.floors = floors;
        this.scheduler = scheduler;
        requests = intialRequests;
        elevators = new ArrayList<Elevator>();
        for (int i = 0; i < numElevators; i++) {
            Elevator ele = new Elevator(elevatorCapacity, floors);
            elevators.add(ele);
        }
    }

    private void generateRequest() {



    }

    private void schedule() {
        scheduler.schedule(requests, elevators, floors);
    }

    private void elevatorOp() {
        for (Elevator e : elevators) {
            if (!e.isEmpty()) {
                e.unload();
            }
            if (!e.isFull()) {
                int location = e.getLocation();
                e.load(requests.get(location - 1));
            }
            e.move();
        }
    }

    public void simulate(int steps) throws InterruptedException {
        for (int i = 0; i < steps; i++) {
            generateRequest();
            schedule();
            elevatorOp();
            Thread.sleep(10);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("begins");
        SimpleScheduler scheduler = new SimpleScheduler();
        int floors = 10;
        int numElevators = 3;
        List<Queue<Integer>> initialRequests = new ArrayList<Queue<Integer>>();
        // initialRequests 对每层floor 建一个queue queue的长度是人数， 每一index对应的值是那个人的目的地
        for (int i = 0; i < floors; i++) {
            Queue<Integer> currQueue = new LinkedList<Integer>();
            Random randomPeople = new Random();
            int randPeopoleLoad = randomPeople.nextInt(7); //随机的人数
            for (int j = 0; j <= randPeopoleLoad; j++) {
                Random generator = new Random();
                int req = generator.nextInt(10) + 1;
                currQueue.offer(req);
            }

            initialRequests.add(currQueue);
        }
        Simulator mySimulator = new Simulator(floors, numElevators, scheduler, initialRequests, 14);
        mySimulator.simulate(10);
    }

    private static class SimpleScheduler implements Scheduler {
        @Override
        public void schedule(List<Queue<Integer>> requests, List<Elevator> elevators, int floors) {
            for (Elevator e : elevators) {
                if (e.getLocation() == 0 || e.getLocation() == floors) {
                    e.changeMovingDirection();
                }
            }
        }
    }
}
