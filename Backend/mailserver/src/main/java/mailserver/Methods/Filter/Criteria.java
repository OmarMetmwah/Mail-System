package mailserver.Methods.Filter;

import org.json.JSONArray;

public interface Criteria {
    public String filter(JSONArray mails, String criteria, String value);
}
