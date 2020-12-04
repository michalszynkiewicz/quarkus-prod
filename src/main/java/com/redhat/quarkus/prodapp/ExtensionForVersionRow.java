package com.redhat.quarkus.prodapp;

public class ExtensionForVersionRow {

    private String id; // extensionId
    private String name;
    private String productizedSince;
    private SupportStatus supportStatus;
    private SupportType supportType;
    private String notes;

    public ExtensionForVersionRow() {
    }

    public ExtensionForVersionRow(String id, String name, String productizedSince, SupportStatus supportStatus, SupportType supportType, String notes) {
        this.id = id;
        this.name = name;
        this.productizedSince = productizedSince;
        this.supportType = supportType;
        this.supportStatus = supportStatus;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductizedSince() {
        return productizedSince;
    }

    public void setProductizedSince(String productizedSince) {
        this.productizedSince = productizedSince;
    }

    public SupportStatus getSupportStatus() {
        return supportStatus;
    }

    public ExtensionForVersionRow setSupportStatus(SupportStatus supportStatus) {
        this.supportStatus = supportStatus;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public ExtensionForVersionRow setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public ExtensionForVersionRow setSupportType(SupportType supportType) {
        this.supportType = supportType;
        return this;
    }

    public SupportType getSupportType() {
        return supportType;
    }

    @Override
    public ExtensionForVersionRow clone() {
        return new ExtensionForVersionRow(id, name, productizedSince, supportStatus, supportType, notes);
    }
}
