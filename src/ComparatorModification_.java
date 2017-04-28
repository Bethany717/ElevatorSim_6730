import java.util.Comparator;

public class ComparatorModification_ implements  Comparator<Request>{
	public int compare(Request r1, Request r2){
		return r2.getEndingFloor()-r1.getEndingFloor();
	}
}