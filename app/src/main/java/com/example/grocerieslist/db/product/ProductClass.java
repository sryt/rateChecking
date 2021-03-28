package com.example.grocerieslist.db.product;

/**
 * Created by Tejaswi on 08/12/20.
 */
public class ProductClass {
    public String id;
    public String name;
    public String desc;
    public String ts;
    public String fid;
    public String packuom;
    public String uom;
    public String packingsize;
    public String mrp;
    public String purRate;
    public String cost;
    public String special;
    public String retail;
    public String caseqty;
    public String sheetNo;
    public String gst;
    public String hsn;
    public String type;
    public String group;
    public String status;

    public ProductClass() {
    }

    public ProductClass(ProductFB pfb){
        this.id = pfb.getId();
        this.name = pfb.getName();
        this.packuom = pfb.getPackUom();
        this.uom = pfb.getUom();
        this.packingsize = pfb.getPackingSize();
        this.mrp = pfb.getMrp();
        this.cost = pfb.getCost();
        this.special = pfb.getSpecial();
        this.retail = pfb.getRetail();
        this.caseqty = pfb.getCaseQty();
        this.gst = pfb.getGst();
        this.hsn = pfb.getHsn();
        this.type = pfb.getType();
        this.group = pfb.getGroup();
        this.fid = pfb.getFirebaseId();
        this.status = pfb.getStatus();
    }

    public ProductClass(String name,String desc,String ts, String fid,String packuom, String uom, String packingsize,
                        String mrp, String purRate, String cost, String special, String retail,
                        String caseqty, String sheetNo, String gst, String hsn, String type,
                        String group, String status) {
        this.name = name;
        this.desc = desc;
        this.ts = ts;
        this.fid = fid;
        this.packuom = packuom;
        this.uom = uom;
        this.packingsize = packingsize;
        this.mrp = mrp;
        this.purRate = purRate;
        this.cost = cost;
        this.special = special;
        this.retail = retail;
        this.caseqty = caseqty;
        this.sheetNo = sheetNo;
        this.gst = gst;
        this.hsn = hsn;
        this.type = type;
        this.group = group;
        this.status = status;
    }


    public ProductClass(String id, String name,String desc,String ts,String fid,String packuom, String uom,
                        String packingsize, String mrp, String purRate, String cost,
                        String special, String retail, String caseqty, String sheetNo,
                        String gst, String hsn, String type, String group, String status) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.ts = ts;
        this.fid = fid;
        this.packuom = packuom;
        this.uom = uom;
        this.packingsize = packingsize;
        this.mrp = mrp;
        this.purRate = purRate;
        this.cost = cost;
        this.special = special;
        this.retail = retail;
        this.caseqty = caseqty;
        this.sheetNo = sheetNo;
        this.gst = gst;
        this.hsn = hsn;
        this.type = type;
        this.group = group;
        this.status = status;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getPackuom() {
        return packuom;
    }

    public void setPackuom(String packuom) {
        this.packuom = packuom;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getPackingsize() {
        return packingsize;
    }

    public void setPackingsize(String packingsize) {
        this.packingsize = packingsize;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPurRate() {
        return purRate;
    }

    public void setPurRate(String purRate) {
        this.purRate = purRate;
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

    public String getCaseqty() {
        return caseqty;
    }

    public void setCaseqty(String caseqty) {
        this.caseqty = caseqty;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", ts='" + ts + '\'' +
                ", fid='" + fid + '\'' +
                ", packuom='" + packuom + '\'' +
                ", uom='" + uom + '\'' +
                ", packingsize='" + packingsize + '\'' +
                ", mrp='" + mrp + '\'' +
                ", purRate='" + purRate + '\'' +
                ", cost='" + cost + '\'' +
                ", special='" + special + '\'' +
                ", retail='" + retail + '\'' +
                ", caseqty='" + caseqty + '\'' +
                ", sheetNo='" + sheetNo + '\'' +
                ", gst='" + gst + '\'' +
                ", hsn='" + hsn + '\'' +
                ", type='" + type + '\'' +
                ", group='" + group + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
