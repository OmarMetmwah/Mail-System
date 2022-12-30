package mailserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailServerApplication {

    public static void main(String[] args) {
      //  SpringApplication.run(MailServerApplication.class, args);
        int count = 0;
    while(51>count) count++;
        System.out.println(count);
    }
}