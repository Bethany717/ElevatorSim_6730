import java.util.Comparator;

public class ComparatorModification implements  Comparator<Request>{
	public int compare(Request r1, Request r2){
		return r1.getEndingFloor()-r2.getEndingFloor();
	}
}
