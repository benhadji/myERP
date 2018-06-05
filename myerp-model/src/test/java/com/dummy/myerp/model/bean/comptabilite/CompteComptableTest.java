package com.dummy.myerp.model.bean.comptabilite;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CompteComptableTest {


    private static List<CompteComptable> vList;


    @BeforeEach
    public void init(){
        vList = new ArrayList<>();
        vList.add(new CompteComptable(401, "Fournisseurs"));
        vList.add(new CompteComptable(706, "Prestations de services"));
    }

    @AfterAll
    public static void setToNull(){
        vList.clear();
    }

    @Test
    public void getByCode(){
        assertEquals(CompteComptable.getByNumero(vList, 706), vList.get(1));

    }



}
