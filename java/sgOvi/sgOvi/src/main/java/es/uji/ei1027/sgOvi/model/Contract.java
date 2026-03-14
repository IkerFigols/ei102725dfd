package es.uji.ei1027.sgOvi.model;

import java.time.LocalDate;
import java.util.Date;

public class Contract {
    private String idContract;
    private LocalDate startDate;
    private LocalDate endDate;
    private String document;
    private double salary;
    private String schedule;
    private String idSelection;

    public String getIdContract() {
        return idContract;
    }

    public void setIdContract(String idContract) {
        this.idContract = idContract;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getIdSelection() {
        return idSelection;
    }

    public void setIdSelection(String idSelection) {
        this.idSelection = idSelection;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "idContract='" + idContract + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", document='" + document + '\'' +
                ", salary=" + salary +
                ", schedule='" + schedule + '\'' +
                ", idSelection='" + idSelection + '\'' +
                '}';
    }
}
