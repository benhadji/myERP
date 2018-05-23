package com.dummy.myerp.model.bean.comptabilite;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    private static List<JournalComptable> vList;


    @Before
    public void init(){
        vList = new ArrayList<>();
        vList.add(new JournalComptable("AC", "Achat"));
        vList.add(new JournalComptable("VE", "Vente"));
    }

    @AfterClass
    public static void setToNull(){
        vList.clear();
    }

    @Test
    public void getByCode(){
        assertEquals(JournalComptable.getByCode(vList, "VE"), vList.get(1));

    }


}
