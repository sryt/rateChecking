package com.example.grocerieslist.db.sales;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class SalesClass {
    String billNo;
    String custName;
    String ts;
    String proName;
    String qty;
    String rate;
    String mrp;
    String amt;
    String flag;

    public SalesClass() {
    }

    public SalesClass(String billNo, String custName, String ts, String proName, String qty, String rate,String mrp,String amt, String flag) {
        this.billNo = billNo;
        this.custName = custName;
        this.ts = ts;
        this.proName = proName;
        this.qty = qty;
        this.rate = rate;
        this.mrp = mrp;
        this.amt = amt;
        this.flag = flag;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "SalesClass{" +
                "billNo='" + billNo + '\'' +
                ", custName='" + custName + '\'' +
                ", ts='" + ts + '\'' +
                ", proName='" + proName + '\'' +
                ", qty='" + qty + '\'' +
                ", rate='" + rate + '\'' +
                ", mrp='" + mrp + '\'' +
                ", amt='" + amt + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
