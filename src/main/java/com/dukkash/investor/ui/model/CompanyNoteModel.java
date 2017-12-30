package com.dukkash.investor.ui.model;

public class CompanyNoteModel {
    private String symbol;
    private String note;
    private int importanceLevelId;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getImportanceLevelId() {
        return importanceLevelId;
    }

    public void setImportanceLevelId(int importanceLevelId) {
        this.importanceLevelId = importanceLevelId;
    }
}
