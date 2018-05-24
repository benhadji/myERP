package com.dummy.myerp.consumer.impl.dao;


import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {


    private static Date currentDate;
    private static String refYear;
    private static EcritureComptable vEC;

    @BeforeClass
    public static void initialization(){
        currentDate = new Date();
    }

    @Before
    public void init(){
        vEC = new EcritureComptable();
    }


    // ==================== CompteComptable ==================== //

    @Test
    public void getListCompteComptable(){
        List<CompteComptable> list = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        assertEquals(7, list.size());
    }


    // ==================== JournalComptable ==================== //

    @Test
    public void getListJournalComptable(){
        List<JournalComptable> list = getDaoProxy().getComptabiliteDao().getListJournalComptable();
        assertEquals(4, list.size());
    }


    // ==================== SequenceEcritureComptable ==================== //
    @Test
    public void getLastSeqOfTheYear(){

        SequenceEcritureComptable vSeq = getDaoProxy().getComptabiliteDao().
                                                getLastSeqOfTheYear(2016, "OD");
        assertNotNull(vSeq);
        assertEquals(2016, (int)vSeq.getAnnee());
        assertEquals(88, (int)vSeq.getDerniereValeur());

    }


    @Test
    public void insertSequenceEcritureComptable(){
        SequenceEcritureComptable vSeq = new SequenceEcritureComptable();
        vSeq.setAnnee(2018);
        vSeq.setDerniereValeur(77);
        getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(vSeq, "OD");
    }

    @Test
    public void updateSequnceEcritureComptable(){
        //On recupere une seq existante
        SequenceEcritureComptable vSeq = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(2016, "OD");
        //On set d'autre valeur aux champs de ce vSeq
        vSeq.setAnnee(vSeq.getAnnee()+2);
        vSeq.setDerniereValeur(vSeq.getDerniereValeur()+2);
        //on appelle la methode update, et on lui passe en param l'objet SequenceEcritureComptable etnle code journal
        getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(vSeq, "OD");

    }

    // ==================== ListLigneEcriture - LOAD ====================
    @Test
    public void loadListLignEcriture(){
        vEC.setId(-1);
        getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vEC);
        assertEquals(3, vEC.getListLigneEcriture().size());
    }


    // ==================== EcritureComptable - GET ==================== //

    @Test
    public void getListEcritureComptable(){
        List<EcritureComptable> list = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        assertEquals(4, list.size());
    }

    @Test
    public void getEcritureComptable(){
        try{
            vEC = getDaoProxy().getComptabiliteDao().getEcritureComptable(-2);
            assertNotNull(vEC);
            assertEquals("VE", vEC.getJournal().getCode());
            assertEquals("VE-2016/00002", vEC.getReference());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ecritureDate = sdf.parse("2016-12-30");
            assertEquals(ecritureDate, vEC.getDate());
            assertEquals("TMA Appli Xxx", vEC.getLibelle());

        }catch(ParseException | NotFoundException nfe){
            nfe.printStackTrace();
        }

    }

    @Test
    public void getEcritureComptableByRef(){
        try {
            vEC = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("VE-2016/00004");
            assertEquals(-4, (int) vEC.getId());

        }catch (NotFoundException nfe){
            nfe.printStackTrace();

        }
    }

    @Test
    public void insertEcritureComptable(){
        vEC.setJournal(new JournalComptable("VE","Vente"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        vEC.setDate(new Date());
        refYear = sdf.format(vEC.getDate());

        vEC.setReference("VE-" + refYear + "/12345");

        vEC.setDate(currentDate);
        vEC.setLibelle("Allez l'OM !!");
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "First ligne for new insert Ecriture Comptable", new BigDecimal(12),
                null));
        vEC.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                "Second ligne for new insert in Ecriture Comptable", null,
                new BigDecimal(10)));

        getDaoProxy().getComptabiliteDao().insertEcritureComptable(vEC);

    }

    @Test
    public void updateEcritureComptable(){
        try {
            vEC = getDaoProxy().getComptabiliteDao().getEcritureComptable(-5);
            vEC.setLibelle("Paiement facture C Discount");
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(vEC);
        }catch (NotFoundException nfe){
            nfe.printStackTrace();
        }

    }

    @Test
    public void deleteEcritureComptable(){
        getDaoProxy().getComptabiliteDao().deleteEcritureComptable(-5);

    }







}
