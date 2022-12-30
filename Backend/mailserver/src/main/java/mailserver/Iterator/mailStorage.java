package mailserver.Iterator;

import java.util.LinkedList;
import java.util.Queue;

public class mailStorage {
    private Queue<mail> shapes= new LinkedList<>();
    private int index;

    public void addShape(String name){
        int i = index++;
        shapes.add( new mail(i,name));
    }

    public Queue<mail> getShapes(){
        return shapes;
    }
}
