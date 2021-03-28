package com.example.grocerieslist.db.product;

/**
 * Created by Tejaswi on 28/03/21.
 */
public class ProductFB {
    public String id;
    public String name;
    public String userId;
    public String packUom;
    public String uom;
    public String packingSize;
    public String mrp;
    public String cost;
    public String special;
    public String retail;
    public String caseQty;
    public String gst;
    public String hsn;
    public String type;
    public String group;
    public String firebaseId;
    public String uploaded;
    public String status;

    public ProductFB(String id, String name, String userId, String packUom, String uom, String packingSize, String mrp, String cost, String special, String retail, String caseQty, String gst, String hsn, String type, String group, String firebaseId, String uploaded, String status) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.packUom = packUom;
        this.uom = uom;
        this.packingSize = packingSize;
        this.mrp = mrp;
        this.cost = cost;
        this.special = special;
        this.retail = retail;
        this.caseQty = caseQty;
        this.gst = gst;
        this.hsn = hsn;
        this.type = type;
        this.group = group;
        this.firebaseId = firebaseId;
        this.uploaded = uploaded;
        this.status = status;
    }

    public ProductFB(String userId, ProductClass pcc) {
        this.id = pcc.getId();
        this.name = pcc.getName();
        this.userId = userId;
        this.packUom = pcc.getPackuom();
        this.uom = pcc.getUom();
        this.packingSize = pcc.getPackingsize();
        this.mrp = pcc.getMrp();
        this.cost = pcc.getCost();
        this.special = pcc.getSpecial();
        this.retail = pcc.getRetail();
        this.caseQty = pcc.getCaseqty();
        this.gst = pcc.getGst();
        this.hsn = pcc.getHsn();
        this.type = pcc.getType();
        this.group = pcc.getGroup();
        this.firebaseId = pcc.getFid();
        //this.uploaded = pcc.getUploaded();
        this.status = pcc.getStatus();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPackUom() {
        return packUom;
    }

    public void setPackUom(String packUom) {
        this.packUom = packUom;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPackingSize() {
        return packingSize;
    }

    public void setPackingSize(String packingSize) {
        this.packingSize = packingSize;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getRetail() {
        return retail;
    }

    public void setRetail(String retail) {
        this.retail = retail;
    }

    public String getCaseQty() {
        return caseQty;
    }

    public void setCaseQty(String caseQty) {
        this.caseQty = caseQty;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Server ProductClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", packUom='" + packUom + '\'' +
                ", uom='" + uom + '\'' +
                ", packingSize='" + packingSize + '\'' +
                ", mrp='" + mrp + '\'' +
                ", cost='" + cost + '\'' +
                ", special='" + special + '\'' +
                ", retail='" + retail + '\'' +
                ", caseQty='" + caseQty + '\'' +
                ", gst='" + gst + '\'' +
                ", hsn='" + hsn + '\'' +
                ", type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", firebaseId='" + firebaseId + '\'' +
                ", uploaded='" + uploaded + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
