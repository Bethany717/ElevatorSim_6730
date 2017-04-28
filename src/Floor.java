import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

//YOUR NAME HERE

public class Floor implements EventHandler {
	private static double MOVING_TIME = 0.1;
	private static double LOADING_TIME = 1.0;
	private static double HOLDING_TIME = 0.2;

	private int floorName;
	public Queue<Request> goingUpRequests = new LinkedList<>();
	public Queue<Request> goingDownRequests = new LinkedList<>();

	public Floor(int name) {
		this.floorName = name;
	}

	public int getFloorName() {
		return floorName;
	}

	public void handle(Event event) {
		FloorEvent floorEvent = (FloorEvent)event;
		Elevator elevator = floorEvent.getElevator();
		switch(floorEvent.getEventType()) {
			case FloorEvent.GOING_UP:
				elevator.setDirection(Elevator.GOING_UP);
				elevator.setLocation(floorEvent.getHeadingFloor());
				if(elevator.goingUpRequests.isEmpty()){
					if(this.goingUpRequests.isEmpty()&&this.goingDownRequests.isEmpty()){
						FloorEvent newFloorEvent = new FloorEvent(Floor.HOLDING_TIME, this, FloorEvent.HOLDING, elevator, this);
						Simulator.schedule(newFloorEvent);

					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.LOADING_TIME, this, FloorEvent.LOADING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
				}
				else{
					if(!this.goingUpRequests.isEmpty()||elevator.goingUpRequests.peek().getEndingFloor()==this.getFloorName()){
						FloorEvent newFloorEvent = new FloorEvent(Floor.LOADING_TIME, this, FloorEvent.LOADING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME, ElevatorSim.floorList.get(this.floorName), FloorEvent.GOING_UP, elevator, ElevatorSim.floorList.get(this.floorName));
						Simulator.schedule(newFloorEvent);
					}
				}
				break;


			case FloorEvent.GOING_DOWN:
				elevator.setDirection(Elevator.GOING_DOWN);
				elevator.setLocation(floorEvent.getHeadingFloor());
				//this means current floor or not?
				if(elevator.goingDownRequests.isEmpty()){
					if(this.goingUpRequests.isEmpty()&&this.goingDownRequests.isEmpty()){
						FloorEvent newFloorEvent = new FloorEvent(Floor.HOLDING_TIME, this, FloorEvent.HOLDING, elevator, this);
						Simulator.schedule(newFloorEvent);

					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.LOADING_TIME, this, FloorEvent.LOADING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
				}
				else{
					if(!this.goingDownRequests.isEmpty()||elevator.goingDownRequests.peek().getEndingFloor()==this.getFloorName()){
						FloorEvent newFloorEvent = new FloorEvent(Floor.LOADING_TIME, this, FloorEvent.LOADING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME, ElevatorSim.floorList.get(this.floorName-2), FloorEvent.GOING_DOWN, elevator, ElevatorSim.floorList.get(this.floorName-2));
						Simulator.schedule(newFloorEvent);
					}
				}
				break;

			case FloorEvent.LOADING:
				elevator.setDirection(Elevator.HOLDING);
				elevator.setLocation(floorEvent.getHeadingFloor());
				if(!elevator.goingUpRequests.isEmpty()&&elevator.goingDownRequests.isEmpty()){
					//�����������ϵ���
					elevator.unload(this);
					//unload��Щ��
					if(!this.goingUpRequests.isEmpty()){
						//������û��Ҫ�ӵ����ϵ�
						elevator.load(this.goingUpRequests);
					}
					if(!elevator.goingUpRequests.isEmpty()){
						//�������----����
						FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME, ElevatorSim.floorList.get(this.floorName), FloorEvent.GOING_UP, elevator, ElevatorSim.floorList.get(this.floorName));
						Simulator.schedule(newFloorEvent);
					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.HOLDING_TIME, this, FloorEvent.HOLDING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
				}
				else if(elevator.goingUpRequests.isEmpty()&&!elevator.goingDownRequests.isEmpty()){
					//�����������µ���
					elevator.unload(this);
					//unload��Щ��
					if(!this.goingDownRequests.isEmpty()){
						//������û��Ҫ�ӵ����µ�
						elevator.load(this.goingDownRequests);
					}
					if(!elevator.goingDownRequests.isEmpty()){
						//�������----����
						FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME, ElevatorSim.floorList.get(this.floorName-2), FloorEvent.GOING_DOWN, elevator, ElevatorSim.floorList.get(this.floorName-2));
						Simulator.schedule(newFloorEvent);
					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.HOLDING_TIME, this, FloorEvent.HOLDING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
				}
				else if(elevator.goingDownRequests.isEmpty() && elevator.goingUpRequests.isEmpty() && !this.goingUpRequests.isEmpty()){
					//������û���ˣ�¥���������ϵ���
					elevator.load(this.goingUpRequests);
					FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME, ElevatorSim.floorList.get(this.floorName), FloorEvent.GOING_UP, elevator, ElevatorSim.floorList.get(this.floorName));
					Simulator.schedule(newFloorEvent);

				}
				else if(elevator.goingUpRequests.isEmpty() && elevator.goingDownRequests.isEmpty() && !this.goingDownRequests.isEmpty()){
					//������û���ˣ�¥���������µ���
					elevator.load(this.goingDownRequests);
					FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME, ElevatorSim.floorList.get(this.floorName-2), FloorEvent.GOING_DOWN, elevator, ElevatorSim.floorList.get(this.floorName-2));
					Simulator.schedule(newFloorEvent);
				}
				else{
					FloorEvent newFloorEvent = new FloorEvent(Floor.HOLDING_TIME, this, FloorEvent.HOLDING, elevator, this);
					Simulator.schedule(newFloorEvent);
				}
				break;



			case FloorEvent.HOLDING:
				elevator.setDirection(Elevator.HOLDING);
				elevator.setLocation(floorEvent.getHeadingFloor());
				Request minRequest = null;
				//System.out.println("Elevator "+ elevator.getName() + " holding: in the floor " + elevator.getLocation().floorName + " at " + Simulator.getCurrentTime() );

				double minRequestTime = Simulator.getCurrentTime();
				for (Floor holdFloor : ElevatorSim.floorList){
					if (!holdFloor.goingUpRequests.isEmpty() && minRequestTime >= holdFloor.goingUpRequests.peek().getRequestTime()){
						minRequest = holdFloor.goingUpRequests.peek();
						minRequestTime = minRequest.getRequestTime();
					}
					if (!holdFloor.goingDownRequests.isEmpty() && minRequestTime >= holdFloor.goingDownRequests.peek().getRequestTime()){
						minRequest = holdFloor.goingDownRequests.peek();
						minRequestTime = minRequest.getRequestTime();
					}
				}

				if (minRequest != null){
					int floorDiff = minRequest.getStartingFloor() - this.getFloorName();
					if (floorDiff>0) {
						FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME*Math.abs(floorDiff), ElevatorSim.floorList.get(minRequest.getStartingFloor()-1), FloorEvent.GOING_UP, elevator, ElevatorSim.floorList.get(minRequest.getStartingFloor()-1));
						Simulator.schedule(newFloorEvent);
					}
					else if(floorDiff<0){
						FloorEvent newFloorEvent = new FloorEvent(Floor.MOVING_TIME*Math.abs(floorDiff), ElevatorSim.floorList.get(minRequest.getStartingFloor()-1), FloorEvent.GOING_DOWN, elevator, ElevatorSim.floorList.get(minRequest.getStartingFloor()-1));
						Simulator.schedule(newFloorEvent);
					}
					else{
						FloorEvent newFloorEvent = new FloorEvent(Floor.LOADING_TIME, this, FloorEvent.LOADING, elevator, this);
						Simulator.schedule(newFloorEvent);
					}
				}
				else {
					FloorEvent newFloorEvent = new FloorEvent(Floor.HOLDING_TIME, this, FloorEvent.HOLDING, elevator, this);
					Simulator.schedule(newFloorEvent);
				}
				break;

			case FloorEvent.REQUEST:
				ElevatorSim.generateRequest();
				break;

		}
	}
}