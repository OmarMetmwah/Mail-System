package mailserver.DAO;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailDao {
    public String sortEmails(@RequestParam(value = "mails") JSONArray mails, @RequestParam(value = "value")String key);
    public String filterEmails(JSONArray emails, String criteria, String value);
    public String searchInEmails(JSONArray emails, String value);
}
