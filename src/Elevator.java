import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Elevator{
	public static final int DEFAULT_ELEVATOR_MAX_CAPACITY = 14;
	public static final int DEFAULT_ELEVATOR_MAX_FLOOR = 10;
	public static final int GOING_UP = 1;
	public static final int GOING_DOWN = 2;
	public static final int HOLDING = 3;
	private final int maxCapacity;
	private final int maxFloor;
	private int elevatorName;
	private int load;
	private Floor location;
	private Floor headingLocation;
	public int direction;
	public PriorityQueue<Request> goingUpRequests;
	public PriorityQueue<Request> goingDownRequests;

	public Elevator(int name, int maxCapacity, int maxFloor, boolean isGoingUp) {
		this.elevatorName = name;
		this.maxCapacity = maxCapacity;
		this.maxFloor = maxFloor;
		this.load = 0;
		this.location = new Floor(1);
		this.direction = HOLDING;
		this.goingUpRequests = new PriorityQueue<Request>(new ComparatorModification());
		this.goingDownRequests = new PriorityQueue<Request>(new ComparatorModification_());

	}
	public void setDirection(int direction){
		this.direction = direction;
	}
		
	public void setHeadingLocation(Floor headingLocation){
		this.headingLocation = headingLocation;
	}
	public void setLocation(Floor location){
		this.location = location;
	}

	public boolean isEmpty() {
		return load == 0;
	}
	public int getName(){
		return this.elevatorName;
	}

	public boolean isFull() {
		return load >= maxCapacity;
	}

	public Floor getLocation() {
		return location;
	}
	public Floor getHeadingLocation() {
		return headingLocation;
	}
	public int getLoad() {return load;}

	public void load(Queue<Request> goingUpRequests2) {
		boolean loadable = true;
		if(!goingUpRequests2.isEmpty()){
			int pollNum = 0;
			int direction = 0;
			for (Request request:goingUpRequests2) {
				if (loadable) {
					int newLoad = request.getRequestLoad();
					if (load + newLoad > maxCapacity) {
						loadable = false;
						//System.out.println("Time: " + String.format("%.02f", Simulator.getCurrentTime()) + "Elevator " + this.getName() + " have " + this.getLoad() + " people ");
						System.out.println("Time: " + String.format("%.02f", Simulator.getCurrentTime()) + " Elevator " + this.getName() + " will not accept more load!");
					} else {
//					System.out.println("Elevator " + this.getName() + " Load: " + newLoad + " in the floor " + this.getLocation().getFloorName() + " at " + Simulator.getCurrentTime());
						if (request.getIsGoingUp()) {
							request.waitingTime = Simulator.getCurrentTime()- request.getRequestTime();
							ElevatorSim.totalWaitingTime += request.waitingTime;
							ElevatorSim.totalPeopleNum += 1;
							goingUpRequests.offer(request);
							pollNum++;
							direction = 0;

						} else {
							request.waitingTime = Simulator.getCurrentTime()- request.getRequestTime();
							ElevatorSim.totalWaitingTime += request.waitingTime;
							ElevatorSim.totalPeopleNum += 1;
							goingDownRequests.offer(request);
							pollNum++;
							direction = 1;

						}

						load += newLoad;

					}
				}
			}
			if(direction == 0){
				for(int i = 0; i < pollNum; i++){
					this.getLocation().goingUpRequests.poll();
				}
			}else{
				for(int i = 0; i < pollNum; i++){
					this.getLocation().goingDownRequests.poll();
				}
			}
			if(pollNum != 0){
				System.out.println("Time: " + String.format("%.02f", Simulator.getCurrentTime()) + " Elevator " + this.getName() + " Load: " + pollNum + " people in the floor " + this.getLocation().getFloorName());

			}
		}	
	}
	public void unload(Floor floor) {
		int pollNum = 0;
		int direction = 0;
		PriorityQueue<Request> requestQueue = null; 
		if(!goingUpRequests.isEmpty()){

			requestQueue = this.goingUpRequests;
			if(!requestQueue.isEmpty()){
				for (Request request:requestQueue){
					if(request.getEndingFloor()==floor.getFloorName()){
						int newUnload = request.getRequestLoad();
						//System.out.println("Time: " + Simulator.getCurrentTime() + " Elevator " + this.getName() + " unLoad " + newUnload + " in the floor " + this.getLocation().getFloorName() + " at " + Simulator.getCurrentTime());
						if(!goingUpRequests.isEmpty()){
							pollNum++;
							direction = 0;
						}
						if(!goingDownRequests.isEmpty()){
							pollNum++;
							direction = 1;
						}
						load-=newUnload;
					}
				}

			}
		}
		else if(!goingDownRequests.isEmpty()){
			requestQueue =  this.goingDownRequests;
			if(!requestQueue.isEmpty()){
				for (Request request:requestQueue){
					if(request.getEndingFloor()==floor.getFloorName()){
						int newUnload = request.getRequestLoad();
						//System.out.println("Elevator " + this.getName() + " unLoad: " + newUnload + " in the floor " + this.getLocation().getFloorName() + " at " + Simulator.getCurrentTime());
						if(!goingUpRequests.isEmpty()){

							pollNum++;
							direction = 0;
						}
						if(!goingDownRequests.isEmpty()){

							pollNum++;
							direction = 1;
						}
						load-=newUnload;
					}
				}
			}
		}
		if(direction == 0){
			for(int i = 0; i < pollNum; i++){
				goingUpRequests.poll();
			}
		}else{
			for(int i = 0; i < pollNum; i++){
				goingDownRequests.poll();
			}
		}

		if (pollNum != 0){
			System.out.println("Time: " + String.format("%.02f", Simulator.getCurrentTime()) + " Elevator " + this.getName() + " unLoad " + pollNum + " people in the floor "+ this.getLocation().getFloorName());
			//System.out.println("Time: " + String.format("%.02f", Simulator.getCurrentTime()) + "Elevator " + this.getName() + " have " + this.getLoad() + " people ");
		}

		
	}
}