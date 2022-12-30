package mailserver.Methods.Filter;

import org.json.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class Filter {
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filter(@RequestParam(value = "mails") JSONArray mails,@RequestParam(value = "criteria")String criteria ,@RequestParam(value = "value")String value)
    {
        ArrayList<JSONObject> obj = new ArrayList<JSONObject>(); //turn JSON-Array to array list to be easy to iterate
        JSONArray filtered = new JSONArray(); //new JSON-Array to hold the filtered value
        //iterate through all mails to add each mail to the array list
        for(int i =0; i < mails.length(); i++)
        {
            obj.add((JSONObject) mails.get(i));
        }
        //iterate through all objects to check if the value in the criteria in each mail to filter the emails
        for(JSONObject i:obj)
        {
            if(((String)i.get(criteria)).equals(value))
            {
                filtered.put(i);
            }
        }

        return filtered.toString();
    }
}
