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
public class StarMail {
    @PostMapping("/star")
    public static void starred(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");
        JSONArray m = new JSONArray(values[0]);
        ArrayList<JSONObject> l = new ArrayList<JSONObject>();
        for (int i = 0; i < m.length(); i++) l.add((JSONObject) m.get(i));
        String[] s = new  String[l.size()];
        for(int i=0;i< l.size();i++) s[i] = l.get(i).getString("subject");
        String json = "";
        int n =0;
        int j=0;
        while (j!=s.length) {
            String subject=s[j];
            String path="",path2="";
            if(values[1].equals("sent")) {
                path = "database\\"+l.get(j).getString("from")+"\\sent\\sent.json";
                path2 = "database\\"+l.get(j).getString("from")+"\\starred\\starred.json";
            } else {
                path = "database\\"+l.get(j).getString("to")+"\\inbox\\inbox.json";
                path2 = "database\\"+l.get(j).getString("to")+"\\starred\\starred.json";
            }
            String content = Files.readString(Paths.get(path));
            String content2 = Files.readString(Paths.get(path2));
            if (content2.length() == 0) content2 += "[]";

            JSONArray mails = new JSONArray(content);
            JSONArray mails2 = new JSONArray(content2);
            //  mails.  =mails.get(Integer.valueOf(n));
            ArrayList<JSONObject> obj2 = new ArrayList<JSONObject>();
            for (int i = 0; i < mails.length(); i++) obj2.add((JSONObject) mails.get(i));
            for (int i = 0; i < mails.length(); i++) {
                String type = obj2.get(i).getString("subject");
                if (type.equals(subject)) {
                    n = i;
                    obj2.get(i).put("starred","true");
                    break;
                }
            }
            ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
            for (int i = 0; i < mails2.length(); i++) {
                obj.add((JSONObject) mails2.get(i));
            }

            obj.add((JSONObject) mails.get(n));

            JSONArray sorted = new JSONArray();
            for (int i = 0; i < obj.size(); i++)
                sorted.put(obj.get(i));

            JSONArray sorted2 = new JSONArray();
            for (int i = 0; i < obj2.size(); i++)
                sorted2.put(obj2.get(i));
            json = sorted.toString();
            String json2 = sorted2.toString();
            FileWriter fw,fw2;

            if(values[1].equals("sent")) {
                fw2 = new FileWriter("database\\"+l.get(j).getString("from")+"\\sent\\sent.json");
                fw = new FileWriter("database\\"+l.get(j).getString("from")+"\\starred\\starred.json");

            } else{
                fw2 = new FileWriter("database\\"+l.get(j).getString("to")+"\\inbox\\inbox.json");
                fw = new FileWriter("database\\"+l.get(j).getString("to")+"\\starred\\starred.json");

            }
            fw.write(json);
            fw2.write(json2);
            fw.flush();
            fw.close();
            fw2.flush();
            fw2.close();
            j++;
        }
    }
}
