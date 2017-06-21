package com.example.harsh.a5;

/**
 * Created by harsh on 3/26/17.
 */

import java.io.Serializable;

public class Location implements Serializable
{
    private String city1;
    private String state1;
    private String zip1;
    private String name;
    private String positiontype;
    private String positionname;
    private String partytype;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String address6;

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    private String locationname;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    private String phone;
    private String website;
    private String email;
    private String GooglePlus;
    private String Facebook;
    private String Twitter;
    private String Youtube;

    public String getGooglePlus() {
        return GooglePlus;
    }

    public void setGooglePlus(String googlePlus) {
        GooglePlus = googlePlus;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getTwitter() {
        return Twitter;
    }

    public void setTwitter(String twitter) {
        Twitter = twitter;
    }

    public String getYoutube() {
        return Youtube;
    }

    public void setYoutube(String youtube) {
        Youtube = youtube;
    }

    private String photo;

    public Location(String positiontype, String positionname, String address, String phone, String email, String website)
    {
        this.positiontype = positiontype;
        this.positionname = positionname;
        this.address1 = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
    }

    public Location(String objJson, String jb1, String jb2, String photo, String address1,String address2,String address3,String address4,String address5, String address6, String phone, String email, String website, String GooglePlus, String Facebook, String Twitter, String Youtube, String locationname)
    {
        this.positiontype = objJson;
        this.positionname = jb1;
        this.partytype = jb2;
        this.photo = photo;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.address6 = address6;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.GooglePlus=GooglePlus;
        this.Facebook=Facebook;
        this.Twitter=Twitter;
        this.Youtube=Youtube;
        this.locationname=locationname;
    }

    public Location(String objJson, String jb1, String jb2, String photo, String address1,String address2,String address3,String address4,String address5, String phone, String email, String website, String GooglePlus, String locationname)
    {
        this.positiontype = objJson;
        this.positionname = jb1;
        this.partytype = jb2;
        this.photo = photo;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.GooglePlus=GooglePlus;
        this.locationname=locationname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Location(String positiontype, String positionname)
    {
        this.positiontype = positiontype;
        this.positionname = positionname;
        //this.partytype = partytype;
    }



    public String getPartytype() {
        return partytype;
    }

    public void setPartytype(String partytype) {
        this.partytype = partytype;
    }

    public Location(String positionType)
    {
        positiontype = positionType;
    }

    public String getPositiontype() {
        return positiontype;
    }

    public void setPositiontype(String positiontype) {
        this.positiontype = positiontype;
    }

    public String getPositionname() {
        return positionname;
    }

    public void setPositionname(String positionname) {
        this.positionname = positionname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public String getZip1() {
        return zip1;
    }

    public void setZip1(String zip1) {
        this.zip1 = zip1;
    }

    public Location(String city, String state, String zip)
    {
        city1 = city;
        state1 = state;
        zip1 = zip;
    }
}
