package mailserver.Iterator;

import java.util.Iterator;
import java.util.Queue;

public class mailIterator implements Iterator<mail> {

    private Queue<mail> shapes;
    int pos;

    public mailIterator(Queue<mail> shapes){
        this.shapes = shapes;
    }
    @Override
    public boolean hasNext() {
        if(shapes.size()==1)
            return false;
        return true;
    }

    @Override
    public mail next() {
        mail sh = shapes.peek();
        shapes.poll();
        return sh;
    }
}
