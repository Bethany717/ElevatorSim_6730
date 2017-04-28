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
		FloorEvent newFloorEvent1 = new FloorEvent(0, floorList.get(0), 3, elevatorList.get(0), floorList.get(0));
		Simulator.schedule(newFloorEvent1);
		FloorEvent newFloorEvent2 = new FloorEvent(0, floorList.get(0), 3, elevatorList.get(1), floorList.get(0));
		Simulator.schedule(newFloorEvent2);
		FloorEvent newFloorEvent3 = new FloorEvent(0, floorList.get(0), 3, elevatorList.get(2), floorList.get(0));
		Simulator.schedule(newFloorEvent3);
		ElevatorSim.generateRequest();
//		double time = Simulator.getCurrentTime();
//		int startingFloor = 7;
//		int endingFloor = 2;
//		Request newRequest = new Request(startingFloor,endingFloor,time);
//		System.out.println("start at " + startingFloor + " end at " + endingFloor + " at time " + time);
//		if(newRequest.getIsGoingUp()){
//			floorList.get(startingFloor-1).goingUpRequests.offer(newRequest);
//		}
//		else{
//			floorList.get(startingFloor-1).goingDownRequests.offer(newRequest);
//		}
//
//		double time2 = Simulator.getCurrentTime();
//		int startingFloor2 = 7;
//		int endingFloor2 = 2;
//		Request newRequest2 = new Request(startingFloor2,endingFloor2,time2);
//		System.out.println("start at " + startingFloor2 + " end at " + endingFloor2 + " at time " + time2);
//		if(newRequest2.getIsGoingUp()){
//			floorList.get(startingFloor2-1).goingUpRequests.offer(newRequest2);
//		}
//		else{
//			floorList.get(startingFloor2-1).goingDownRequests.offer(newRequest2);
//		}
//
//		double time3 = Simulator.getCurrentTime();
//		int startingFloor3 = 5;
//		int endingFloor3 = 6;
//		Request newRequest3 = new Request(startingFloor3,endingFloor3,time3);
//		System.out.println("start at " + startingFloor3 + " end at " + endingFloor3 + " at time " + time2);
//		if(newRequest3.getIsGoingUp()){
//			floorList.get(startingFloor3-1).goingUpRequests.offer(newRequest3);
//		}
//		else{
//			floorList.get(startingFloor3-1).goingDownRequests.offer(newRequest3);
//		}
        Simulator.stopAt(500);
        Simulator.run();
		System.out.println("The total number of people loaded: " + totalPeopleNum);
		System.out.println("The total number of requests: " + totalRequestNum);
		System.out.println("The total waiting time of people: " + totalWaitingTime);
		System.out.println("The avarage waiting time per person: " + totalWaitingTime/totalPeopleNum);
	}

}
