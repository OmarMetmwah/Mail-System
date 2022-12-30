package mailserver.MailFunctions;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import static mailserver.Login.LoginSignup.read;

@CrossOrigin
@RestController
public class RetrieveMails {

    @PostMapping("/mails")
    public String mails(@RequestBody String mailsInfo){
        int p = mailsInfo.indexOf(" ");
        String userName = mailsInfo.substring(0,p);
        String category = mailsInfo.substring(p+1);
        File file = new File("database/"+userName);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        return read( "database/"+userName+"/"+category+"/"+category+".json")+"$"+ Arrays.toString(directories).replaceAll("\\[|\\]","");
    }
}
