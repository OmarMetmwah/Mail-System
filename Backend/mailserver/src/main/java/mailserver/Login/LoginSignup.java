package mailserver.Login;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
public class LoginSignup {
    JSONParser parser = new JSONParser();
    Object obj;
    JSONObject o;
    FileWriter fileWriter;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    LocalDateTime now;

    public LoginSignup() throws IOException, ParseException {
        obj = parser.parse(new FileReader("emails.json"));
        o   = (JSONObject) obj;
    }

    @PostMapping("/login")
    public String login(@RequestBody String loginInfo){
        int p = loginInfo.indexOf(" ");
        String userName = loginInfo.substring(0,p);
        String password = loginInfo.substring(p+1);
        if ((o.get(userName)+"").equals(password))
            return read( "database/"+userName+"/inbox/inbox.json");
        else
            return "wrong";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody String signupInfo) throws IOException {
        int p = signupInfo.indexOf(" ");
        String userName = signupInfo.substring(0,p);
        String password = signupInfo.substring(p+1);
        if (o.get(userName)==null){
            fileWriter = new FileWriter("emails.json");
            o.put(userName,password);
            fileWriter.write(o+"");
            fileWriter.flush();
            fileWriter.close();
            now = LocalDateTime.now();
            new File("database/"+userName).mkdirs();new File("database/"+userName).mkdirs();//create directory for the new user in database folder
            new File("database/"+userName+"/sent").mkdirs();new File("database/"+userName).mkdirs();//create directory for the new user in database folder
            new File("database/"+userName+"/inbox").mkdirs();new File("database/"+userName).mkdirs();//create directory for the new user in database folder
            new File("database/"+userName+"/draft").mkdirs(); new File("database/"+userName).mkdirs();
            new FileWriter("database/"+userName+"/draft/draft.json");
            new File("database/"+userName+"/starred").mkdirs(); new File("database/"+userName).mkdirs();
            new FileWriter("database/"+userName+"/starred/starred.json");
            new File("database/"+userName+"/trash").mkdirs(); new File("database/"+userName).mkdirs();
            new FileWriter("database/"+userName+"/trash/trash.json");
            new FileWriter("database/"+userName+"/sent/sent.json");//create json file inside the folder for the emails of that user
            var x = new FileWriter("database/"+userName+"/inbox/inbox.json");//create json file inside the folder for the emails of that user
            x.write("[{\"subject\":\"welcome\",\"time\":\""+dtf.format(now)+"\",\"from\":\"Developers\",\"to\":\""+userName+"\",\"body\":\"welcome to our mail system\",\"file\":\"\",\"starred\":\"false\"}]");
            x.flush();
            x.close();
            return "done";
        }
        else
            return "This user name already used";
    }
    public static String read(String filePath)
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content;
    }
}
