package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.State;

import java.util.List;

public class FilterState {

    private String stateSel;
    private List<State> stateList = List.of(State.values());

    public List<State> getStateList() { return stateList; }

    public void setStateSel(String selectedState) { this.stateSel = selectedState; }

    public void setStateList(List<State> stateList) { this.stateList = stateList; }

    public String getStateSel() { return stateSel; }

    private String sortSel;

    private List<String> sortList;

    public String getSortSel() { return sortSel; }

    public List<String> getSortList() { return sortList; }

    public void setSortSel(String sortSel) { this.sortSel = sortSel; }

    public void setSortList(List<String> sortList) { this.sortList = sortList; }
}
