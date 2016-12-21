package rjt.example.com.tmrealestate.adapters;

import java.io.Serializable;

/**
 * Created by shutenmei on 2016/12/7.
 */
public class Property implements Serializable {

    private String property_id;
    private String p_address;
    private String[] p_image;
    private String p_type;
    private String p_num_bedroom;
    private String p_num_bath;
    private String p_num_car_allow;
    private String p_on_sale;
    private String p_on_rent;
    private String p_price;
    private String p_morgage;
    private String prov_name;
    private String prov_mobile;
    private String prov_image;

    public String getStatus(){
        if(getP_on_rent().equals("1")){
            return "ON RENT";
        }else return "ON_SALE";
    }

    public String getProperty_id(){
        return property_id;
    }
    public void setProperty_id(String property_id){
        this.property_id=property_id;
    }
    public String getP_address(){
        return p_address;
    }
    public void setP_address(String p_address){
        this.p_address=p_address;
    }
    public String getP_type(){
        return p_type;
    }
    public void setP_type(String p_type){
        this.p_type=p_type;
    }
    public String getP_num_bedroom(){
        return p_num_bedroom;
    }
    public void setP_num_bedroom(String p_num_bedroom){
        this.p_num_bedroom=p_num_bedroom;
    }
    public String getP_num_bath(){
        return p_num_bath;
    }
    public void setP_num_bath(String p_num_bath){
        this.p_num_bath=p_num_bath;
    }
    public String getP_num_car_allow(){
        return p_num_car_allow;
    }
    public void setP_num_car_allow(String p_num_car_allow){
        this.p_num_car_allow=p_num_car_allow;
    }
    public String getP_on_sale(){
        return this.p_on_sale;
    }
    public void setP_on_sale(String p_on_sale){
        this.p_on_sale=p_on_sale;
    }
    public String getP_on_rent(){
        return p_on_rent;
    }
    public void setP_on_rent(String p_on_rent){
        this.p_on_rent=p_on_rent;
    }
    public String getP_price(){
        return p_price;
    }
    public void setP_price(String p_price){
        this.p_price=p_price;
    }
    public String getP_morgage(){
        return p_morgage;
    }
    public void setP_morgage(String p_morgage){
        this.p_morgage=p_morgage;
    }
    public String getProv_name(){
        return prov_name;
    }
    public void setProv_name(String prov_name){
        this.prov_name=prov_name;
    }
    public String getProv_mobile(){
        return prov_mobile;
    }
    public void setProv_mobile(String prov_name){
        this.prov_name=prov_name;
    }
    public String getProv_image(){
        return prov_image;
    }
    public void setProv_image(String prov_image){
        this.prov_image=prov_image;
    }
    public String[] getP_image(){
        return this.p_image;
    }
    public void setP_image(String p_image){
        this.p_image=p_image.split(",");
    }

}
