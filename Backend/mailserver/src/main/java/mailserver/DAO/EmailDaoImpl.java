package mailserver.DAO;

import mailserver.Methods.Filter.Filter;
import mailserver.Methods.Search;
import mailserver.Methods.Sort;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestParam;

public class EmailDaoImpl implements  EmailDao{

    public String sortEmails(@RequestParam(value = "mails") JSONArray mails, @RequestParam(value = "value")String key)
    {
        Sort s = new Sort();
        return s.sorting(mails,key);

    }
    public String filterEmails(JSONArray emails, String criteria, String value)
    {
        Filter f = new Filter();
        return f.filter(emails, criteria, value);
    }
    public String searchInEmails(JSONArray emails, String value)
    {
        Search se = new Search();
        return se.search(emails, value);
    }

}
