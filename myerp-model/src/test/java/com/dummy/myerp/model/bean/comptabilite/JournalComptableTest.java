package com.dummy.myerp.model.bean.comptabilite;




import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JournalComptableTest {

    private static List<JournalComptable> vList;


    @BeforeEach
    public void init(){
        vList = new ArrayList<>();
        vList.add(new JournalComptable("AC", "Achat"));
        vList.add(new JournalComptable("VE", "Vente"));
    }

    @AfterAll
    public static void setToNull(){
        vList.clear();
    }

    @Test
    public void getByCode(){
        assertEquals(JournalComptable.getByCode(vList, "VE"), vList.get(1));

    }


}
