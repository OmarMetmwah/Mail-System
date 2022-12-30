package mailserver.Methods;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

@CrossOrigin
@RestController
public class Search {

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "mails") JSONArray mails, @RequestParam(value = "value")String value)
    {

        ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
        JSONArray found = new JSONArray();
        for(int i =0; i<mails.length(); i++)
        {
            obj.add((JSONObject) mails.get(i));
        }

        for(JSONObject i: obj)
        {
            Iterator<String> keys = i.keys();
            while(keys.hasNext())
            {
                String key = keys.next();
                if(((String)i.get(key)).contains(value))
                {
                    found.put(i);
                    break;
                }
            }
        }
        return found.toString();
    }
}
