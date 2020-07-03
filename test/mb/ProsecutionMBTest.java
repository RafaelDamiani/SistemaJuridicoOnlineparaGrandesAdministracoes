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
        prosecution.setIdPromovente((long)16);
        prosecution.setIdPromoventeLawyer((long)2);
        prosecution.setIdPromovido((long)17);
        prosecution.setIdPromovidoLawyer((long)14);
        
        String expResult = "Processo cadastrado com sucesso!";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromoventeIsNull() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        String expResult = "Preencha o promovente";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromoventeIsZero() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        prosecution.setIdPromovente((long)0);
        
        String expResult = "Preencha o promovente";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromoventeLawyerIsNull() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        prosecution.setIdPromovente((long)16);
        
        String expResult = "Preencha o advogado do promovente";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromoventeLawyerIsZero() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        prosecution.setIdPromovente((long)16);
        prosecution.setIdPromoventeLawyer((long)0);
        
        String expResult = "Preencha o advogado do promovente";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromovidoIsNull() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        prosecution.setIdPromovente((long)16);
        prosecution.setIdPromoventeLawyer((long)2);
        
        String expResult = "Preencha o promovido";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromovidoIsZero() {
        ProsecutionMB prosecution = new ProsecutionMB();

        prosecution.setIdPromovente((long)16);
        prosecution.setIdPromoventeLawyer((long)2);
        prosecution.setIdPromovido((long)0);
        
        String expResult = "Preencha o promovido";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromovidoLawyerIsNull() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        prosecution.setIdPromovente((long)16);
        prosecution.setIdPromoventeLawyer((long)2);
        prosecution.setIdPromovido((long)17);
        
        String expResult = "Preencha o advogado do promovido";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testProsecutionFailedPromovidoLawyerIsZero() {
        ProsecutionMB prosecution = new ProsecutionMB();
        
        prosecution.setIdPromovente((long)16);
        prosecution.setIdPromoventeLawyer((long)2);
        prosecution.setIdPromovido((long)17);
        prosecution.setIdPromovidoLawyer((long)0);
        
        String expResult = "Preencha o advogado do promovido";

        String result = prosecution.insertProsecution();

        assertEquals(expResult, result);
    }
}
