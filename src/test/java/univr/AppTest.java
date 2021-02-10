package univr;

import static org.junit.Assert.*;
import net.sourceforge.jwebunit.junit.WebTester;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AppTest {

    private WebTester tester;

    @Before
    public void prepare(){
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8080/");
    }

    @Test
    public void visualizzaListaPazientiTest(){
        tester.beginAt("/");
        tester.assertTextPresent("Benvenuto");
        tester.clickLinkWithText("Vai alla lista dei pazienti");
        tester.assertTablePresent("");
        tester.assertTextInTable("", "\u00c8 pericoloso\u003f");
    }

    @Test
    public void visualizzaProssimiAppuntamenti(){
        tester.beginAt("/");
        tester.clickLinkWithText("Vai all'agenda");
        tester.assertTablePresent("");
        tester.assertTextInTable("", "Data e ora visita");
    }

    @Test
    public void modificaCartellaPazienteTest(){
        tester.beginAt("/");
        tester.clickLinkWithText("Vai alla lista dei pazienti");
        tester.assertTablePresent("");
        tester.clickLinkWithText("Modifica cartella di Luigi Verdi");
        tester.setTextField("diagnosi", "Prova inserimento diagnosi");
        tester.checkCheckbox("pericolosita");
        tester.uncheckCheckbox("autosufficiente");
        tester.submit();
        tester.assertTextPresent("Dati paziente");
        tester.assertTextPresent("Diagnosi: Prova inserimento diagnosi");
        tester.assertTextPresent("Il paziente \u00E8 pericoloso");
        tester.assertTextPresent("Il paziente non \u00E8 autosufficiente");
    }

    @Test
    public void nuovaPrescrizioneTest(){
        tester.beginAt("/");
        tester.clickLinkWithText("Vai alla lista dei pazienti");
        tester.assertTablePresent("");
        tester.clickLinkWithText("Visualizza cartella di Luigi Verdi");
        tester.assertTextPresent("Dati paziente");
        tester.clickLinkWithText("Nuova prescrizione");
        tester.assertTextPresent("Nuova prescrizione per Luigi Verdi");
        tester.selectOption("farmaco", "Zyprexa (olanzapina)");
        tester.setTextField("dosaggio", "50 mg");
        tester.submit();
        tester.assertTextPresent("Dati paziente");
        tester.assertTextInTable("prescrizioni", "Zyprexa");
        tester.assertTextInTable("prescrizioni", "50 mg");
    }

    @Test
    public void aggiungiOsservazioniVisitaTest(){
        tester.beginAt("/");
        tester.clickLinkWithText("Vai alla lista dei pazienti");
        tester.assertTablePresent("");
        tester.clickLinkWithText("Visualizza cartella di Bianchi Neri");
        tester.assertTextPresent("Dati paziente");
        tester.clickLinkWithText("Vai", 0);
        tester.assertTextPresent("Informazioni visita");
        tester.setTextField("osservazioni", "Questa \u00E8 una prova di un'osservazione.");
        tester.submit();
        tester.assertTextPresent("Dati paziente");
        tester.clickLinkWithText("Vai", 0);
        tester.assertTextFieldEquals("osservazioni", "Questa \u00E8 una prova di un'osservazione.");
    }

    @Test
    public void scaricaReportPazienteTest(){
        tester.beginAt("/");
        tester.clickLinkWithText("Vai alla lista dei pazienti");
        tester.assertTablePresent("");
        tester.clickLinkWithText("Visualizza cartella di Bianchi Neri");
        tester.assertTextPresent("Dati paziente");
        tester.clickLinkWithText("Scarica report");
        assertEquals(tester.getHeader("Content-Disposition"), "form-data; name=\"attachment\"; filename=\"Report Bianchi Neri.docx\"");
    }

}
