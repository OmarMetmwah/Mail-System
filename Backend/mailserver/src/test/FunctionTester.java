package com.example.mailserver;

import mailserver.EmailDao;
import mailserver.EmailDaoImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class FunctionTester {

    JSONArray input;
    JSONObject mail1;
    JSONObject mail2;
    JSONObject mail3;
    JSONObject mail4;
    JSONArray FilterOut;
    JSONArray SearchOut;
    JSONArray SortOut;
    String SearchValue;
    String filterValue;
    String criteria;
    String sOut;
    String fOut;
    String sortValue;
    String sortout;
    @InjectMocks
    EmailDao dao;

    @Mock
    EmailDaoImpl func;

    @BeforeEach
    public void setUp() throws JSONException{
        dao = mock(EmailDao.class);
        func = new EmailDaoImpl();

        mail1 = new JSONObject();
        //Inserting key-value pairs into the json object
        mail1.put("From", "Ahmed");
        mail1.put("To", "Omar");
        mail1.put("subject", "welcome");
        mail1.put("body", "welcome omar to this society, I hope you will do great");

        mail2= new JSONObject();
        //Inserting key-value pairs into the json object
        mail2.put("From", "youssef");
        mail2.put("To", "Omar");
        mail2.put("subject", "congratulation");
        mail2.put("body", "congrats bro for passing this exam");

        mail3 = new JSONObject();
        //Inserting key-value pairs into the json object
        mail3.put("From", "ah123");
        mail3.put("To", "Mr.Hamdi");
        mail3.put("subject", "promotion");
        mail3.put("body", "hello, sir I am Ahmed the new employee in the office, I just wanted to ask about the promotion when will it be, regards");

        mail4 = new JSONObject();
        //Inserting key-value pairs into the json object
        mail4.put("From", "omar");
        mail4.put("To", "youssef");
        mail4.put("subject", "thanks");
        mail4.put("body", "thanks bro, It was a good exam I hope you pass yours, too");


        input= new JSONArray();
        input.put(mail1);
        input.put(mail2);
        input.put(mail3);
        input.put(mail4);

        criteria = "To";
        filterValue = "Omar";

        SearchValue = "Ahmed";

        sortValue = "From";

        FilterOut = new JSONArray();
        FilterOut.put(mail1);
        FilterOut.put(mail2);
        fOut = FilterOut.toString();

        SearchOut = new JSONArray();
        SearchOut.put(mail1);
        SearchOut.put(mail3);
        sOut = SearchOut.toString();

        SortOut = new JSONArray();
        SortOut.put(mail1);
        SortOut.put(mail3);
        SortOut.put(mail4);
        SortOut.put(mail2);
        sortout = SortOut.toString();


    }

    @Test
    public void testFunctions() throws JSONException  {
        this.setUp();

        when(dao.searchInEmails(input, SearchValue)).thenReturn(sOut);

        when(dao.filterEmails(input, criteria, filterValue)).thenReturn(fOut);

        when(dao.sortEmails(input, sortValue)).thenReturn(sortout);

        Assert.assertEquals("sorry, there is an error in search", sOut, func.searchInEmails(input, SearchValue));
        Assert.assertEquals("sorry there is an error in filter",fOut ,func.filterEmails(input, criteria, filterValue));
        Assert.assertEquals("sorry there is an error in sorting",sortout ,func.sortEmails(input, sortValue));
    }



}

