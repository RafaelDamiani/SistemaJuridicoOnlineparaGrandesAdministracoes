package mb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProsecutionMBTest {
    
    public ProsecutionMBTest() {
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
    public void testProsecutionSuccess() {
        ProsecutionMB prosecution = new ProsecutionMB();
        prosecution.setIdJudge((long)2);

        String expResult = "Processo cadastrado com sucesso!";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedIdJudIsZero() {
        ProsecutionMB prosecution = new ProsecutionMB();
        prosecution.setIdJudge((long)0);

        String expResult = "Preencha o juiz";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedIdJudIsNull() {
        ProsecutionMB prosecution = new ProsecutionMB();

        String expResult = "Preencha o juiz";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
}
