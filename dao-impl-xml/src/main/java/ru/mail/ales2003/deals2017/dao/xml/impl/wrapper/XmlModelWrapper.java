package ru.mail.ales2003.deals2017.dao.xml.impl.wrapper;

import java.util.ArrayList;
import java.util.List;

public class XmlModelWrapper<ID_TYPE, MODEL> {

    private List<MODEL> rows = new ArrayList<>();

    private ID_TYPE lastId;

    public List<MODEL> getRows() {
        return rows;
    }

    public void setRows(List<MODEL> rows) {
        this.rows = rows;
    }

    public ID_TYPE getLastId() {
        return lastId;
    }

    public void setLastId(ID_TYPE lastId) {
        this.lastId = lastId;
    }
}
