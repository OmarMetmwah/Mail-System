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
public class UnStarMail {
    @PostMapping("/unstar")
    public static void unstar(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");
        JSONArray m = new JSONArray(values[0]);
        ArrayList<JSONObject> l = new ArrayList<JSONObject>();
        for (int i = 0; i < m.length(); i++) l.add((JSONObject) m.get(i));
        String[] s = new  String[l.size()];
        for(int i=0;i< l.size();i++) s[i] = l.get(i).getString("subject");
        String json = "",json2="";
        int n =0;
        int j=0;
        while (j!=s.length) {
            String subject=s[j];
            String path="",path2="";
            if(values[1].equals("sent"))
            {
                path = "database\\"+l.get(j).getString("from")+"\\sent\\sent.json";
                path2 = "database\\"+l.get(j).getString("from")+"\\starred\\starred.json";
            } else if(values[1].equals("inbox")){
                path = "database\\"+l.get(j).getString("to")+"\\inbox\\inbox.json";
                path2 = "database\\"+l.get(j).getString("to")+"\\starred\\starred.json";
            }
            else{
                if(l.get(j).getString("from").equals(values[2]))
                    path = "database\\"+values[2]+"\\sent\\sent.json";
                else
                    path = "database\\"+values[2]+"\\inbox\\inbox.json";
                path2 = "database\\"+values[2]+"\\starred\\starred.json";
            }

            String content = Files.readString(Paths.get(path));
            String content2 = Files.readString(Paths.get(path2));

            JSONArray mails = new JSONArray(content); //inbox
            JSONArray mails2 = new JSONArray(content2); // starred
            ArrayList<JSONObject> object = new ArrayList<JSONObject>();
            for (int i = 0; i < mails.length(); i++) {
                object.add((JSONObject) mails.get(i));
            }

            for (int i = 0; i < mails.length(); i++) {
                String type = object.get(i).getString("subject");
                if (type.equals(subject)) {
                    object.get(i).put("starred","false");
                    break;
                }
            }
            ArrayList<JSONObject> object2 = new ArrayList<JSONObject>();
            ArrayList<JSONObject> object3 = new ArrayList<JSONObject>();
            for (int i = 0; i < mails2.length(); i++) object2.add((JSONObject) mails2.get(i));
            int length=0;
            for (int i = 0; i < mails2.length(); i++) {
                String type = object2.get(i).getString("subject");
                if (type.equals(subject)) {
                    length = i;
                    break;
                }
            }
            if(mails2.length()==0) {
                object2.clear();

            }
            else {
                for (int i = 0; i < length; i++) {
                    object3.add((JSONObject) mails2.get(i));
                }
                for (int i = length + 1; i < mails2.length(); i++) object3.add((JSONObject) mails2.get(i));
            }
            // obj.add((JSONObject) mails.get(n));


            JSONArray sorted = new JSONArray();
            for (int i = 0; i < object3.size(); i++)
                sorted.put(object3.get(i));

            JSONArray sorted2 = new JSONArray();
            for (int i = 0; i < object.size(); i++)
                sorted2.put(object.get(i));
            json = sorted.toString();
            json2 = sorted2.toString();
            FileWriter fw,fw2;
            if(values[1].equals("sent")) {
                fw2 = new FileWriter("database\\"+l.get(j).getString("from")+"\\sent\\sent.json");
                fw = new FileWriter("database\\"+l.get(j).getString("from")+"\\starred\\starred.json");

            } else if(values[1].equals("inbox")){
                fw2 = new FileWriter("database\\"+l.get(j).getString("to")+"\\inbox\\inbox.json");
                fw = new FileWriter("database\\"+l.get(j).getString("to")+"\\starred\\starred.json");
            }
            else{
                if(l.get(j).getString("from").equals(values[2]))
                    fw2 = new FileWriter("database\\"+values[2]+"\\sent\\sent.json");
                else
                    fw2 = new FileWriter("database\\"+values[2]+"\\inbox\\inbox.json");
                fw = new FileWriter("database\\"+values[2]+"\\starred\\starred.json");
            }
            fw.write(json);fw2.write(json2);fw.flush();fw.close();fw2.flush();fw2.close();
            j++;
        }

    }
}
