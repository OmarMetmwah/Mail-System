package mailserver.nullObject;

public class NullCustomer extends AbstractCustomer {

    @Override
    public boolean isNil() {
        return true;
    }
}
