package mailserver.MailFunctions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@CrossOrigin
@RestController
public class DeleteFromDraft {

    @PostMapping("/deleteDraft")
    public static void deleteDraft(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");
        // Turn emails to json array then list of json object
            JSONObject m = new JSONObject(values[0].replaceAll("\\[|\\]",""));
            /*
             * Select file and turn it ri list of json object
             */
            String path = "database\\" + values[1] + "\\draft\\draft.json";
            String content = Files.readString(Paths.get(path));
            JSONArray mails = new JSONArray(content);

            ArrayList<JSONObject> o = new ArrayList<JSONObject>();
            for (int i = 0; i < mails.length(); i++) o.add((JSONObject) mails.get(i));

            JSONArray sorted = new JSONArray();
            for (int i = 0; i < o.size(); i++)
                if(!o.get(i).getString("subject").equals(m.getString("subject")))
                    sorted.put(o.get(i));
            String json = sorted.toString();
            var fw = new FileWriter("database\\"+values[1]+"\\draft\\draft.json");
            fw.write(json);
            fw.flush();
            fw.close();
    }
}
