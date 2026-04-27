package es.uji.ei1027.sgOvi.model;

public class probandocosas {
    public static void main(String args[]){
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
    }
}

//yiu
