package com.dummy.myerp.business.impl.manager;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;



public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private static ComptabiliteManagerImpl manager;
    private static ComptabiliteManager comptabiliteManager;
    private static EcritureComptable vEcritureComptable;
    private static Date currentDate;
    private static String refYear;

    @BeforeAll
    public static void initialization(){
        manager = new ComptabiliteManagerImpl();
        comptabiliteManager = getBusinessProxy().getComptabiliteManager();
        currentDate = new Date();
    }

    @AfterAll
    public static void assignToNull(){
        vEcritureComptable = null;
    }

    @BeforeEach
    public void init() {
        vEcritureComptable = new EcritureComptable();
    }


    @Test
    public void addReference() throws Exception {
        vEcritureComptable.setId(-3);
        vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        vEcritureComptable.setDate(sdf.parse("2016-12-29 00:00:00"));
        vEcritureComptable.setLibelle("Paiement Facture F110001");

        //RG # 3 : il doit y avoir au minimum au moins deux lignes d'écriture :
        // une au débit et une au crédit.

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(52), null));

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512),
                null, null, new BigDecimal(52)));

        manager.addReference(vEcritureComptable);
        assertEquals("BQ-2016/00052", vEcritureComptable.getReference());

    }

    @Test
    public void checkEcritureComptable() throws Exception {
        vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
        vEcritureComptable.setDate(currentDate);
        vEcritureComptable.setLibelle("Libelle");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        refYear = sdf.format(vEcritureComptable.getDate());

        vEcritureComptable.setReference("VE-" + refYear + "/00004");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptable(vEcritureComptable);
    }

    // Comentaire

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(currentDate);
        vEcritureComptable.setLibelle("Libelle");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        refYear = sdf.format(vEcritureComptable.getDate());

        vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-" + refYear + "/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void checkEcritureComptableContext() throws FunctionalException {
        vEcritureComptable.setReference("VE-2016/00001");
        manager.checkEcritureComptableContext(vEcritureComptable);
    }

    @Test
    public void insertEcritureComptable() throws FunctionalException {
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(currentDate);
        vEcritureComptable.setLibelle("Libelle");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        refYear = sdf.format(vEcritureComptable.getDate());

        vEcritureComptable.setReference(vEcritureComptable.getJournal().getCode()+"-" + refYear + "/00003");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(123)));

        manager.insertEcritureComptable(vEcritureComptable);
        assertNotNull(vEcritureComptable.getId());

    }

    @Test
    public void updateEcritureComptable() throws FunctionalException {

        List<EcritureComptable> list =
                getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
        for (EcritureComptable vEC : list){
            if (vEC.getId() == -4){
                vEC.setLibelle("test");
                getBusinessProxy().getComptabiliteManager().updateEcritureComptable(vEC);
                assertEquals("test", vEC.getLibelle());
            }
        }

    }

    @Test
    public void deleteEcritureComptable(){
        try{
            getBusinessProxy().getComptabiliteManager().deleteEcritureComptable(6);
        }catch (Exception e){
            fail("La suppression de l'ecriture comptable 6 a echoué");
        }
    }


    @Test
    public void checkEcritureComptableUnitViolation() {

        assertThrows(FunctionalException.class, ()->{
            manager.checkEcritureComptableUnit(vEcritureComptable);
        } );

    }


    //RG_Compta_2	Pour qu'une écriture comptable soit valide, elle doit être équilibrée :
    // la somme des montants au crédit des lignes d'écriture doit être égale à la somme des montants au débit.

    @Test
    public void checkEcritureComptableUnitRG2() {
        assertThrows(FunctionalException.class,()->{
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                    null, null,
                    new BigDecimal(1234)));
            manager.checkEcritureComptableUnit(vEcritureComptable);
                });
    }

    //RG_Compta_3	Une écriture comptable doit contenir au moins deux lignes d'écriture :
    // une au débit et une au crédit.

    @Test
    public void checkEcritureComptableUnitRG3() {
        assertThrows(FunctionalException.class, ()->{
            vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setLibelle("Libelle");
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                    null, new BigDecimal(123),
                    null));
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });

    }



    // RG_Compta_6	La référence d'une écriture comptable doit être unique,
    // il n'est pas possible de créer plusieurs écritures ayant la même référence.

    @Test
    void checkEcritureComptableContextRG6() {
        assertThrows(FunctionalException.class, () -> {
            vEcritureComptable.setReference("VE-2016/00002");
            manager.checkEcritureComptableContext(vEcritureComptable);
        });
        assertThrows(FunctionalException.class, () -> {
            vEcritureComptable.setId(0);
            vEcritureComptable.setReference("VE-2016/00002");
            manager.checkEcritureComptableContext(vEcritureComptable);
        });
    }



}
