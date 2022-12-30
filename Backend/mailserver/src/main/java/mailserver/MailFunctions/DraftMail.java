package mailserver.MailFunctions;

import mailserver.Login.LoginSignup;
import mailserver.nullObject.AbstractCustomer;
import mailserver.nullObject.CustomerFactory;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

@CrossOrigin
@RestController
public class DraftMail {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    LocalDateTime now;

    @PostMapping("/draft")
    public void draft(MultipartFile[] file) throws IOException, ParseException {

    Path path = Paths.get("database/" + file[file.length - 1].getOriginalFilename());
    file[file.length - 1].transferTo(path);
    File x = path.toFile();
    String mail = new String((Files.readAllBytes(Paths.get(x.getAbsolutePath()))));
    //sender=o  reciver=q subject body file
    /*
     * o ==> inbox (welcom)  && sent (message)
     * q ==> inbox (welcome w message)
     * */
    String result = " ";
    now = LocalDateTime.now();
    String[] values = mail.split("\\$");
    String[] v = values[1].split(" ");
    int temp = 0;
    Queue<String> q = new LinkedList<>();
    for (int i = 0; i < v.length; i++) q.add(v[i]);
    while (q.size() > 0) {
        String value = q.poll();
        AbstractCustomer customer = CustomerFactory.getCustomer(value);

            String comma ;
                String Smails = LoginSignup.read("database/" + values[0] + "/draft/draft.json");
                comma = Smails.length() == 2 ? "" : ",";
                if (Smails.length() == 0)
                    Smails = "[{\"subject\":\"" + values[2] + "\",\"time\":\"" + dtf.format(now) + "\",\"from\":\"" + values[0] + "\",\"to\":\"" + value + "\",\"body\":\"" + values[3] + "\",\"file\":\"" + (values.length == 5 ? values[4] : "") + "\",\"starred\":\"false\"}]";
                else {
                    Smails = Smails.substring(0, Smails.length() - 1) + comma +
                            "{\"subject\":\"" + values[2] + "\",\"time\":\"" + dtf.format(now) + "\",\"from\":\"" + values[0] + "\",\"to\":\"" + value + "\",\"body\":\"" + values[3] + "\",\"file\":\"" + (values.length == 5 ? values[4] : "") + "\",\"starred\":\"false\"}]";
                }
                var f = new FileWriter("database/" + values[0] +
                        "/draft/draft.json");
                if (file.length > 1) {
                    for (int i = 0; i < file.length - 1; i++) {
                        Path path2 = Paths.get("database/" + values[0] + "/draft/" + file[i].getOriginalFilename());
                        file[i].transferTo(path2);
                    }

                }
                f.write(Smails);
                f.flush();
                f.close();
                temp++;


    }
}

}
