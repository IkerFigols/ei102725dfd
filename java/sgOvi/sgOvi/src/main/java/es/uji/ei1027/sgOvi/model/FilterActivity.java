package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.ActivityType;
import java.util.List;

public class FilterActivity {

    private String typeSel;
    private List<ActivityType> typeList;
    private String sortSel;

    public String getTypeSel() {
        return typeSel;
    }

    public void setTypeSel(String typeSel) {
        this.typeSel = typeSel;
    }

    public List<ActivityType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<ActivityType> typeList) {
        this.typeList = typeList;
    }

    public String getSortSel() {
        return sortSel;
    }

    public void setSortSel(String sortSel) {
        this.sortSel = sortSel;
    }
}