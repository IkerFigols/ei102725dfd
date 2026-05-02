package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.Assistance_Request;

public class AssistanceRequestSelectionDTO {
    private Assistance_Request assistanceRequest;
    private String idSelection;
    private String selectionState;

    // Getters y Setters
    public Assistance_Request getAssistanceRequest() { return assistanceRequest; }
    public void setAssistanceRequest(Assistance_Request assistanceRequest) { this.assistanceRequest = assistanceRequest; }
    public String getIdSelection() { return idSelection; }
    public void setIdSelection(String idSelection) { this.idSelection = idSelection; }
    public String getSelectionState() { return selectionState; }
    public void setSelectionState(String selectionState) { this.selectionState = selectionState; }
}