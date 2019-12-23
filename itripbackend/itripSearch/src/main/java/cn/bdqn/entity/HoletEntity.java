package cn.bdqn.entity;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class HoletEntity implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Field
    private  String id;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Field
    private String hotelName;
}
