package mb;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PhaseMBTest {
    
    public PhaseMBTest() {
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
    public void testPhaseSuccessAccepted() {
        PhaseMB prosecution = new PhaseMB();
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("Fase 1");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        prosecution.setIdPhaseStatus(1);
        
        String expResult = "Fase cadastrada com sucesso!";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseSuccessDenied() {
        PhaseMB prosecution = new PhaseMB();
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("Fase 1");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        prosecution.setIdPhaseStatus(2);
        prosecution.setJustification("Lorem ipsum dolor sit ame");
        
        String expResult = "Fase cadastrada com sucesso!";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedProsecutionIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        String expResult = "A fase não possui nenhum processo atrelado à ela. Entre em contato com o Adminstrador.";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedProsecutionIsZero() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)0);
        
        String expResult = "A fase não possui nenhum processo atrelado à ela. Entre em contato com o Adminstrador.";        

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedLawyerIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        
        String expResult = "A fase não possui nenhum advogado atrelado à ela. Entre em contato com o Adminstrador.";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedLawyerIsZero() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)0);
        
        String expResult = "A fase não possui nenhum advogado atrelado à ela. Entre em contato com o Adminstrador.";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedTitleIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        
        String expResult = "Preencha o título";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedTitleIsEmpty() {
        PhaseMB prosecution = new PhaseMB();

        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("");
        
        String expResult = "Preencha o título";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedTitleIsLessThanFiveCaracteres() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcd");
        
        String expResult = "O título deve ter no mínimo 5 caracteres";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedDescriptionIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        
        String expResult = "Preencha a descrição";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedDescriptionIsEmpty() {
        PhaseMB prosecution = new PhaseMB();

        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("");
        
        String expResult = "Preencha a descrição";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedDescriptionIsLessThanTwentyFiveCaracteres() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit am");
        
        String expResult = "A descrição deve ter no mínimo 25 caracteres";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedPhaseTypeIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        
        String expResult = "Defina o tipo da fase";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedPhaseTypeIsZero() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(0);
        
        String expResult = "Defina o tipo da fase";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedPhaseStatusIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        
        String expResult = "Escolha o status da fase";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedPhaseStatusIsZero() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        prosecution.setIdPhaseStatus(0);
        
        String expResult = "Escolha o status da fase";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedJustificationIsNull() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        prosecution.setIdPhaseStatus(2);
        
        String expResult = "Preencha a justificativa";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedJustificationIsEmpty() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        prosecution.setIdPhaseStatus(2);
        prosecution.setJustification("");
        
        String expResult = "Preencha a justificativa";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
    
    @Test
    public void testPhaseFailedJustificationIsLessThanTwentyFiveCaracteres() {
        PhaseMB prosecution = new PhaseMB();
        
        prosecution.setIdProsecution((long)15);
        prosecution.setIdlawyer((long)2);
        prosecution.setTitle("abcde");
        prosecution.setDescription("Lorem ipsum dolor sit ame");
        prosecution.setIdPhaseType(1);
        prosecution.setIdPhaseStatus(2);
        prosecution.setJustification("Lorem ipsum dolor sit am");
        
        String expResult = "A justificativa deve ter no mínimo 25 caracteres";

        String result = prosecution.insertPhase();

        assertEquals(expResult, result);
    }
}
