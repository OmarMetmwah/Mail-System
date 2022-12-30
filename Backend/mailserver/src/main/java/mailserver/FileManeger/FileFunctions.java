package mailserver.FileManeger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@CrossOrigin
@RestController
public class FileFunctions {
    @PostMapping("/addFile")
    public static void AddFile(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");

        new File("database/"+values[1]+"/"+values[0]).mkdirs();new File("database/"+values[1]).mkdirs();
        new FileWriter("database/"+values[1]+"/"+values[0]+"/"+values[0]+".json").close();
    }
    @PostMapping("/deleteFile")
    public static void DeleteFile(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");

        File file = new File("database/"+values[1]+"/"+values[0]+"/"+values[0]+".json");
        file.delete();
        Path path = Path.of("database/" + values[1] + "/" + values[0] );
        Files.delete(path);

    }

    @PostMapping("/renameFile")
    public static void RenameFile(@RequestBody String mail) throws IOException {
        String[] values = mail.split("\\$");
        File file = new File("database/"+values[2]+"/"+values[0]);
        File file2 = new File("database/"+values[2]+"/"+values[1]);
        file.renameTo(file2);
        File file3 = new File("database/"+values[2]+"/"+values[1]+"/"+values[0]+".json");
        File file4 = new File("database/"+values[2]+"/"+values[1]+"/"+values[1]+".json");
        file3.renameTo(file4);


    }
}
