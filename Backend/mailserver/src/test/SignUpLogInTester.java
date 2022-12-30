package com.example.mailserver;

import mailserver.Login.LoginSignup;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignUpLogInTester {

    String signupInfo;
    String signupInfoWrong;
    String loginInfo;
    String loginInfoWrong;


    @Mock
    LoginSignup logSign;

    @BeforeEach
    public void setUp() throws JSONException, IOException, ParseException {
        logSign =mock(LoginSignup.class);

        signupInfo = "ah123 1234";
        signupInfoWrong = "ahmed 123";
        loginInfo = "ah123 1234";
        loginInfoWrong = "mohamed 1111";

    }

    @Test
    public void testFunctions() throws JSONException, IOException, ParseException {
        this.setUp();

        when(logSign.signup(signupInfo)).thenReturn("done");

        when(logSign.signup(signupInfoWrong)).thenReturn("wrong");

        when(logSign.login(loginInfo)).thenReturn("done");

        when(logSign.login(loginInfoWrong)).thenReturn("wrong");

        Assert.assertEquals("sorry, there is an error in sign up", "done", logSign.signup(signupInfo));
        Assert.assertEquals("sorry there is an error in sign up", "wrong", logSign.signup(signupInfoWrong));
        Assert.assertEquals("sorry there is an error in log in", "done", logSign.signup(loginInfo));
        Assert.assertEquals("sorry there is an error in sign up", "wrong", logSign.login(loginInfoWrong));
    }
}
