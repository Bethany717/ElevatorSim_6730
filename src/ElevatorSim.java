import java.util.ArrayList;
import java.util.Random;

public class ElevatorSim {
	public static ArrayList<Floor> floorList;
	public static ArrayList<Elevator> elevatorList;
	public static final int GENERATION_TIME = 5;
	public static final int FLOORNUM = 10;
	public static final int ELEVATORNUM = 3;
	public static double totalWaitingTime = 0;
	public static int totalPeopleNum = 0;
	public static int totalRequestNum = 0;
	public static int runTime = 50;
	
	public static void generateRequest(){
		for (int i = 0;i<= 4;i++){
			int startingFloor = (int)(Math.random()*FLOORNUM+1);
			int endingFloor = startingFloor;
			while(endingFloor==startingFloor){
				endingFloor = (int)(Math.random()*FLOORNUM+1);
			}
			double time = Simulator.getCurrentTime() ;
			Request newRequest = new Request(startingFloor,endingFloor,time);
			//System.out.println("start at " + startingFloor + " end at " + endingFloor + " at time " + time);
			if(newRequest.getIsGoingUp()){
				floorList.get(startingFloor-1).goingUpRequests.offer(newRequest);
			}
			else{
				floorList.get(startingFloor-1).goingDownRequests.offer(newRequest);
			}
			totalRequestNum += 1;
		}		
	}

	public static void generateRequestMain() {
	    int i = 0;
	    while(i*5 < runTime) {
            FloorEvent request = new FloorEvent(i*5, floorList.get(0), FloorEvent.REQUEST, elevatorList.get(0), floorList.get(0));
            Simulator.schedule(request);
            i++;
        }

	}

	public static void main(String[] args) {
		floorList = new ArrayList<>();
		elevatorList = new ArrayList<>();
		// TODO Auto-generated method stub
		for (int i = 0; i < FLOORNUM; i++){
			Floor newFloor = new Floor(i+1);
			floorList.add(newFloor);
		}
		
		for (int i = 0; i < ELEVATORNUM; i++){
			Elevator newElevator = new Elevator(i + 1, 20, floorList.size(), false);
			elevatorList.add(newElevator);
		}
		FloorEvent newFloorEvent1 = new FloorEvent(0, floorList.get(0), FloorEvent.HOLDING, elevatorList.get(0), floorList.get(0));
		Simulator.schedule(newFloorEvent1);
		FloorEvent newFloorEvent2 = new FloorEvent(0, floorList.get(0), FloorEvent.HOLDING, elevatorList.get(1), floorList.get(0));
		Simulator.schedule(newFloorEvent2);
		FloorEvent newFloorEvent3 = new FloorEvent(0, floorList.get(0), FloorEvent.HOLDING, elevatorList.get(2), floorList.get(0));
		Simulator.schedule(newFloorEvent3);
		ElevatorSim.generateRequestMain();

        Simulator.stopAt(runTime);
        Simulator.run();
		System.out.println("The total number of people loaded: " + totalPeopleNum);
		System.out.println("The total number of requests: " + totalRequestNum);
		System.out.println("The total waiting time of people: " + totalWaitingTime);
		System.out.println("The avarage waiting time per person: " + totalWaitingTime/totalPeopleNum);
	}

}
