/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 
 * 2014-7-22下午4:46:54 类T_PLACE_PLACE
 * 
 * @author Mars zhang
 * 
 */
@Table(name = "T_PLACE_PLACE")
public class T_PLACE_PLACE {

    /** 主键 */
    @Id(column = "PLACEID")
    private String PLACEID;
    /** 描述 */
    private String AREAID;
    /** 定位时间 */
    private String PLACENAME;
    /** 路径 */
    private String PLACEADDR;
    /** 文件的类型 */
    private String ISIMP;

    /** 备用字段 */
    private String PLACEPERSON;
    /** 文件名称 */
    private String PLACETYPED;
    /** 文件名称 */
    private String FOCUSD;
    /** 文件名称 */
    private String FOCUSDNM;
    /** 文件名称 */
    private String FILEURL;

    /** 备用字段 */
    private String PERSONTEL;
    /** 文件名称 */
    private String PERSONPHONE;
    /** 文件名称 */
    private String REMARK;
    /** 文件名称 */
    private String CREATETIME;
    /** 文件名称 */
    private String CREATEDATE;

    /** 备用字段 */
    private String UPDATETIME;
    /** 文件名称 */
    private String RISKSITU;
    /** 文件名称 */
    private String HIDRECT;
    /** 文件名称 */
    private String SURRCIRC;
    /** 文件名称 */
    private String FIREKEYSITES;

    /** 备用字段 */
    private String SINGRICK;

    public String getPLACEID() {
        return PLACEID;
    }

    public void setPLACEID(String pLACEID) {
        PLACEID = pLACEID;
    }

    public String getAREAID() {
        return AREAID;
    }

    public void setAREAID(String aREAID) {
        AREAID = aREAID;
    }

    public String getPLACENAME() {
        return PLACENAME;
    }

    public void setPLACENAME(String pLACENAME) {
        PLACENAME = pLACENAME;
    }

    public String getPLACEADDR() {
        return PLACEADDR;
    }

    public void setPLACEADDR(String pLACEADDR) {
        PLACEADDR = pLACEADDR;
    }

    public String getISIMP() {
        return ISIMP;
    }

    public void setISIMP(String iSIMP) {
        ISIMP = iSIMP;
    }

    public String getPLACEPERSON() {
        return PLACEPERSON;
    }

    public void setPLACEPERSON(String pLACEPERSON) {
        PLACEPERSON = pLACEPERSON;
    }

    public String getPLACETYPED() {
        return PLACETYPED;
    }

    public void setPLACETYPED(String pLACETYPED) {
        PLACETYPED = pLACETYPED;
    }

    public String getFOCUSD() {
        return FOCUSD;
    }

    public void setFOCUSD(String fOCUSD) {
        FOCUSD = fOCUSD;
    }

    public String getFOCUSDNM() {
        return FOCUSDNM;
    }

    public void setFOCUSDNM(String fOCUSDNM) {
        FOCUSDNM = fOCUSDNM;
    }

    public String getFILEURL() {
        return FILEURL;
    }

    public void setFILEURL(String fILEURL) {
        FILEURL = fILEURL;
    }

    public String getPERSONTEL() {
        return PERSONTEL;
    }

    public void setPERSONTEL(String pERSONTEL) {
        PERSONTEL = pERSONTEL;
    }

    public String getPERSONPHONE() {
        return PERSONPHONE;
    }

    public void setPERSONPHONE(String pERSONPHONE) {
        PERSONPHONE = pERSONPHONE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String cREATETIME) {
        CREATETIME = cREATETIME;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String cREATEDATE) {
        CREATEDATE = cREATEDATE;
    }

    public String getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(String uPDATETIME) {
        UPDATETIME = uPDATETIME;
    }

    public String getRISKSITU() {
        return RISKSITU;
    }

    public void setRISKSITU(String rISKSITU) {
        RISKSITU = rISKSITU;
    }

    public String getHIDRECT() {
        return HIDRECT;
    }

    public void setHIDRECT(String hIDRECT) {
        HIDRECT = hIDRECT;
    }

    public String getSURRCIRC() {
        return SURRCIRC;
    }

    public void setSURRCIRC(String sURRCIRC) {
        SURRCIRC = sURRCIRC;
    }

    public String getFIREKEYSITES() {
        return FIREKEYSITES;
    }

    public void setFIREKEYSITES(String fIREKEYSITES) {
        FIREKEYSITES = fIREKEYSITES;
    }

    public String getSINGRICK() {
        return SINGRICK;
    }

    public void setSINGRICK(String sINGRICK) {
        SINGRICK = sINGRICK;
    }

}
