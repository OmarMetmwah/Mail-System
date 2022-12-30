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
public class MoveMail {
    @PostMapping("/move")
    public static void delete(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");
        JSONArray m = new JSONArray(values[0]);
        ArrayList<JSONObject> l = new ArrayList<JSONObject>();
        for (int i = 0; i < m.length(); i++) l.add((JSONObject) m.get(i));
        String[] s = new  String[l.size()];
        for(int i=0;i< l.size();i++) s[i] = l.get(i).getString("subject");
        String json = "",json2="",json3="";
        int n =0;
        int j=0;
        while (j!=s.length) {
            String path="",path2="",path3="";

            path = "database\\"+values[3]+"\\"+values[1]+"\\"+values[1]+".json";
            path2 = "database\\"+values[3]+"\\"+values[2]+"\\"+values[2]+".json";

            String content = Files.readString(Paths.get(path));
            String content2 = Files.readString(Paths.get(path2));
            if (content2.length() == 0) content2 += "[]";
            JSONArray mails = new JSONArray(content);
            JSONArray mails2 = new JSONArray(content2);
            //turn the json array to normal list of json objects to sort it
            ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
            ArrayList<JSONObject> o = new ArrayList<JSONObject>();
            for (int i = 0; i < mails.length(); i++) o.add((JSONObject) mails.get(i));
            String subject = s[j];
            boolean temp = false;
            for (int i = 0; i < mails.length(); i++) {
                String type = o.get(i).getString("subject");
                if (type.equals(subject)) {
                    if(o.get(i).getString("starred").equals("true"))
                        temp = true;
                    n = i;
                    break;
                }
            }
            int length =0;
            if(temp){
                if(values[1].equals("sent"))  path3 = "database\\"+l.get(j).getString("from")+"\\starred\\starred.json";
                else path3 = "database\\"+l.get(j).getString("to")+"\\starred\\starred.json";
                String content3 = Files.readString(Paths.get(path3));
                JSONArray mails3 = new JSONArray(content3);
                ArrayList<JSONObject> object = new ArrayList<>();
                ArrayList<JSONObject> object2 = new ArrayList<>();
                for (int i=0;i< mails3.length();i++)   object.add((JSONObject) mails3.get(i));
                for(int i=0;i< mails3.length();i++){
                    if(object.get(i).getString("subject").equals(subject)){
                        length = i; break;
                    }
                }
                if(mails3.length()==1) object2.clear();
                else {
                    for (int i = 0; i < length; i++) {
                        object2.add((JSONObject) mails3.get(i));
                    }
                    for (int i = length + 1; i < mails3.length(); i++) object2.add((JSONObject) mails3.get(i));
                }
                JSONArray sorted3 = new JSONArray();
                for (int i = 0; i < object2.size(); i++) {
                    sorted3.put(object2.get(i));
                }
                json3 = sorted3.toString();
                FileWriter fw3;

                if(values[1].equals("sent"))
                    fw3 = new FileWriter("database\\"+l.get(j).getString("from")+"\\starred\\starred.json");
                else
                    fw3 = new FileWriter("database\\"+l.get(j).getString("to")+"\\starred\\starred.json");
                fw3.write(json3); fw3.flush();fw3.close();

            }
            if(mail.length()==1) o.clear();
            else {
                for (int i = 0; i < n; i++) {
                    obj.add((JSONObject) mails.get(i));
                }
                for (int i = n + 1; i < mails.length(); i++) obj.add((JSONObject) mails.get(i));
            }
            ArrayList<JSONObject> obj2 = new ArrayList<JSONObject>();
            for (int i = 0; i < mails2.length(); i++) {
                obj2.add((JSONObject) mails2.get(i));
            }
            obj2.add((JSONObject) mails.get(n));

            JSONArray sorted = new JSONArray();
            JSONArray sorted2 = new JSONArray();
            for (int i = 0; i < obj.size(); i++) {
                sorted.put(obj.get(i));

            }
            for (int i = 0; i < obj2.size(); i++) sorted2.put(obj2.get(i));

            json = sorted.toString();
            json2 = sorted2.toString();


            FileWriter fw,fw2;

                fw = new FileWriter("database\\"+values[3]+"\\"+values[1]+"\\"+values[1]+".json");
                fw2 = new FileWriter("database\\"+values[3]+"\\"+values[2]+"\\"+values[2]+".json");
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
