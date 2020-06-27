package mb;

import java.security.NoSuchAlgorithmException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginMBTest {
    
    public LoginMBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLoginSuccess() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setEmail("rafael.damiani@ufpr.br");
        login.setPassword("123456");
        String expResult = "Logando";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginFailedPWD() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setEmail("rafael.damiani@ufpr.br");
        login.setPassword("1234567");
        String expResult = "E-mail ou senha incorretos";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginFailedEmail() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setEmail("rafael.damiani@ufpr.com.br");
        login.setPassword("123456");
        String expResult = "E-mail ou senha incorretos";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginFailedEmailNull() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setPassword("123456");
        String expResult = "Preencha o e-mail";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginFailedEmailEmpty() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setEmail("");
        login.setPassword("123456");
        String expResult = "Preencha o e-mail";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginFailedPWDNull() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setEmail("rafael.damiani@ufpr.com.br");
        String expResult = "Preencha a senha";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLoginFailedPWDEmpty() throws NoSuchAlgorithmException {        
        LoginMB login = new LoginMB();
        login.setEmail("rafael.damiani@ufpr.com.br");
        login.setPassword("");
        String expResult = "Preencha a senha";
        String result = login.logar();
        assertEquals(expResult, result);
    }
}
