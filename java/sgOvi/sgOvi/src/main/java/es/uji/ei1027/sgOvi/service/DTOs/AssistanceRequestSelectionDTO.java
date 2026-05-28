package es.uji.ei1027.sgOvi.service.DTOs;

import es.uji.ei1027.sgOvi.model.Assistance_Request;

import java.time.LocalDate;

public class AssistanceRequestSelectionDTO {

    private Assistance_Request assistanceRequest;
    private String idSelection;
    private String selectionState;
    private String oviUserName;
    private String stateDescription;
    private String stateColor;
    private LocalDate selectionDate;


    public Assistance_Request getAssistanceRequest() { return assistanceRequest; }
    public void setAssistanceRequest(Assistance_Request assistanceRequest) { this.assistanceRequest = assistanceRequest; }
    public String getIdSelection() { return idSelection; }
    public void setIdSelection(String idSelection) { this.idSelection = idSelection; }
    public String getSelectionState() { return selectionState; }
    public void setSelectionState(String selectionState) { this.selectionState = selectionState; }
    public String getOviUserName() { return oviUserName; }
    public void setOviUserName(String oviUserName) { this.oviUserName = oviUserName; }
    public String getStateDescription() { return stateDescription; }
    public void setStateDescription(String stateDescription) { this.stateDescription = stateDescription; }
    public String getStateColor() { return stateColor; }
    public void setStateColor(String stateColor) { this.stateColor = stateColor; }
    public LocalDate getSelectionDate() {return selectionDate;}
    public void setSelectionDate(LocalDate selectionDate) {this.selectionDate = selectionDate;}
}