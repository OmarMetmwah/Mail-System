package mailserver.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@CrossOrigin
@RestController
public class Sort {

    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public String sorting(@RequestParam(value = "mails") JSONArray mails, @RequestParam(value = "value")String key)
    {
        //turn the json array to normal list of json objects to sort it
        ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
        for(int i =0; i < mails.length(); i++)
        {
            obj.add((JSONObject) mails.get(i));
        }

        //built-in abstract function to sort lists
        Collections.sort(obj, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                String val1 = new String(); //variable to hold the first value in comparing
                String val2 = new String(); //variable to hold the second value in comparing
                try {
                    val1 = (String) o1.get(key); //get the first value according to the specific key
                    val2 = (String) o2.get(key); //get the second value according to the specific key
                }catch(JSONException e)
                {
                    e.printStackTrace();
                }
                return val1.compareTo(val2); //return int represents the comparison
            }
        });

        JSONArray sorted = new JSONArray();
        for(int i= 0; i < mails.length(); i++)
        {
            sorted.put(obj.get(i));
        }
        return sorted.toString();
    }
}
