package com.example.grocerieslist.db.stockdetails;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class StockDetailsClass {
    String id;
    String name;
    String desc;
    String type;
    String status;
    String flag;

    public StockDetailsClass() {
    }

    public StockDetailsClass(String name, String desc, String type, String status, String flag) {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.status = status;
        this.flag = flag;
    }

    public StockDetailsClass(String id, String name, String desc, String type, String status, String flag) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.status = status;
        this.flag = flag;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "StockDetailsClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
