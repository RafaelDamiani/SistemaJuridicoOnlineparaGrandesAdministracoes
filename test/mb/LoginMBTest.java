package mb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    
    public LoginTest() {
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

    /**
     * Test of getEmail method, of class Login.
     */
    @Test
    public void testLogin() {        
        Login login = new Login();
        login.setEmail("rafael.damiani@ufpr.br");
        login.setPassword("123456");
        String expResult = "";
        String result = login.logar();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetEmail() {
        
        
        System.out.println("setEmail");
        
        String email = "";
        Login instance = new Login();
        instance.setEmail(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
