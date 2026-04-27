package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.service.ListByNameImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class probandocosas {
    public static void main(String args[]){
        ListByNameImp lbn = new ListByNameImp();
        Map<String, List<String>> mapa = new HashMap<>();
        ArrayList<String> lista = new ArrayList<>();
        lista.add("ante");
        lista.add("despue");
        mapa.put("12345678W", lista);
        for(List<String> dni : mapa.values()){
            System.out.println(mapa.values().toString());
            System.out.println(dni.toString());
        }
    }
}

//yiu
/*
       String prueba = "ARS999998";
        Integer n = Integer.parseInt(prueba.trim().substring(3)) + 1;
        int a = n.toString().length();
        String base = "ARS";
        while(base.length()<=9){
            if(base.length() + a == 9) {
                base = base + n;
                break;
            }
            base = base + "0";
            System.out.println(base);
        }
        System.out.println(base);
        System.out.println(a);
        System.out.println(n);
 */