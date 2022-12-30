package mailserver.nullObject;

public class RealCustomer extends AbstractCustomer {

    @Override
    public boolean isNil() {
        return false;
    }
}
