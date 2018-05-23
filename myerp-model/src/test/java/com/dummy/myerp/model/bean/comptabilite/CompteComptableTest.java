package com.dummy.myerp.model.bean.comptabilite;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CompteComptableTest {


    private static List<CompteComptable> vList;


    @Before
    public void init(){
        vList = new ArrayList<>();
        vList.add(new CompteComptable(401, "Fournisseurs"));
        vList.add(new CompteComptable(706, "Prestations de services"));
    }

    @AfterClass
    public static void setToNull(){
        vList.clear();
    }

    @Test
    public void getByCode(){
        assertEquals(CompteComptable.getByNumero(vList, 706), vList.get(1));

    }



}
