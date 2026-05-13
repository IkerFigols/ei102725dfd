package es.uji.ei1027.sgOvi.model;

import java.util.Comparator;

public class APComparator implements Comparator<Assistance_Request> {


    @Override
    public int compare(Assistance_Request o1, Assistance_Request o2) {
        return o1.getDate().compareTo(o2.getDate()) ;
    }
}
