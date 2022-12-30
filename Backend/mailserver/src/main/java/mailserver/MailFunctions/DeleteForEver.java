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
public class DeleteForEver {
    @PostMapping("/deletetrash")
    public static void deleteTrash(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");
        JSONArray m = new JSONArray(values[0]);
        ArrayList<JSONObject> l = new ArrayList<JSONObject>();
        for (int i = 0; i < m.length(); i++) l.add((JSONObject) m.get(i));
        String[] s = new  String[l.size()];
        for(int i=0;i< l.size();i++) s[i] = l.get(i).getString("subject");
        String json = "";
        int n =0;
        int j=0;
        while (j != s.length) {
            String path = "database\\" + values[1] + "\\trash\\trash.json";
            String content = Files.readString(Paths.get(path));
            JSONArray mails = new JSONArray(content);
            ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
            ArrayList<JSONObject> o = new ArrayList<JSONObject>();
            for (int i = 0; i < mails.length(); i++) o.add((JSONObject) mails.get(i));
            String subject = s[j];
            for (int i = 0; i < mails.length(); i++) {
                String type = o.get(i).getString("subject");
                if (type.equals(subject)) n = i;
            }
            if(mails.length()==1) obj.clear();
            else {
                for (int i = 0; i < n; i++) {
                    obj.add((JSONObject) mails.get(i));
                }
                for (int i = n + 1; i < mails.length(); i++) obj.add((JSONObject) mails.get(i));
            }
            JSONArray sorted = new JSONArray();
            for (int i = 0; i < obj.size(); i++) sorted.put(obj.get(i));
            json = sorted.toString();
            var fw = new FileWriter("database\\"+values[1]+"\\trash\\trash.json");
            fw.write(json);
            fw.flush();
            fw.close();
        }
    }
}
