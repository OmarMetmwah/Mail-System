package mailserver.nullObject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class CustomerFactory {
    public static AbstractCustomer getCustomer(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj;
        JSONObject o = new JSONObject();

        obj = parser.parse(new FileReader("emails.json"));
        o   = (JSONObject) obj;
        if(o.get(name)==null)
        return new NullCustomer();
        return new RealCustomer();
    }
}
