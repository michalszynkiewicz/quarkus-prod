package com.redhat.quarkus.prodapp;

public class ExtensionUpdate {
    private String id;
    private String notes;
    private SupportStatus supportStatus;
    private SupportType supportType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SupportStatus getSupportStatus() {
        return supportStatus;
    }

    public void setSupportStatus(SupportStatus supportStatus) {
        this.supportStatus = supportStatus;
    }

    public SupportType getSupportType() {
        return supportType;
    }

    public void setSupportType(SupportType supportType) {
        this.supportType = supportType;
    }

    @Override
    public String toString() {
        return "ExtensionUpdate{" +
                "id='" + id + '\'' +
                ", notes='" + notes + '\'' +
                ", SupportStatus=" + supportStatus +
                ", supportType=" + supportType +
                '}';
    }

}
