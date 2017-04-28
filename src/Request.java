public class Request {
    private int startingFloor = 0;
    private int endingFloor = 0;
    private double requestTime = 0;
    private int requestLoad = 0;
    private boolean isGoingUp = true;
    public double waitingTime = 0;

    public Request(int startingFloor,int endingFloor,double requestTime) {
        this.startingFloor = startingFloor;
        this.endingFloor = endingFloor;
        this.requestTime = requestTime;
        this.requestLoad = 1;
        this.isGoingUp = endingFloor>startingFloor;
        this.waitingTime = 0;
        
    }

    public int getStartingFloor() {
        return startingFloor;
    }
    public int getEndingFloor() {
        return endingFloor;
    }
    public double getRequestTime(){
    	return requestTime;
    }
    public int getRequestLoad(){
    	return requestLoad;
    }
    public boolean getIsGoingUp(){
    	return isGoingUp;
    }
}