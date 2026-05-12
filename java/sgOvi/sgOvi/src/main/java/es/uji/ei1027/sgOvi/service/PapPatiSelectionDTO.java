package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Selection;

public class PapPatiSelectionDTO extends PersonPapPatiDTO {
    private Selection selection;

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

}
