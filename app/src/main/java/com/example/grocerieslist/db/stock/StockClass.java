package com.example.grocerieslist.db.stock;

/**
 * Created by Tejaswi on 22/07/21.
 */
public class StockClass {
    String id;
    String proName;
    String custId;
    String locationId;
    String personId;
    String ts;
    String type;
    String qty;
    String remark;

    public StockClass() {
    }

    public StockClass(String proName, String custId, String locationId, String personId, String ts, String type, String qty, String remark) {
        this.proName = proName;
        this.custId = custId;
        this.locationId = locationId;
        this.personId = personId;
        this.ts = ts;
        this.type = type;
        this.qty = qty;
        this.remark = remark;
    }

    public StockClass(String id, String proName, String custId, String locationId, String personId, String ts, String type, String qty, String remark) {
        this.id = id;
        this.proName = proName;
        this.custId = custId;
        this.locationId = locationId;
        this.personId = personId;
        this.ts = ts;
        this.type = type;
        this.qty = qty;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StockClass{" +
                "id='" + id + '\'' +
                ", proName='" + proName + '\'' +
                ", custId='" + custId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", personId='" + personId + '\'' +
                ", ts='" + ts + '\'' +
                ", type='" + type + '\'' +
                ", qty='" + qty + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
