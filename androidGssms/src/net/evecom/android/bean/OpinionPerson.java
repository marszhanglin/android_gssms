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
 * 2014-8-16����17:30:54 ��OpiinionPerson
 * 
 * @author SLQ shen
 * 
 */
@Table(name = "OpiinionPerson")
public class OpinionPerson {
    /** ���� */
    @Id(column = "ID")
    private String ID;
    /** �������� */
    private String EVENTNM;
    /** ��Դʱ�� */
    private String OCCURTIME;
    /** �����ص� */
    private String OCCURADDR;
    /** ��Դ���� */
    private String SOURCEWAY;
    /** ��Դ�� */
    private String SOURCEPEOPLE;
    /** ��ϵ�绰 */
    private String PHONE;
    /** ����״̬ */
    private String EVENTSTATUS;
    /** ��Ϣ���� */
    private String NEWS;
    /** ����ID */
    private String AREAID;
    /** ���� */
    private String LONGITUDE;
    /** γ�� */
    private String LATITUDE;
    /** ������ */
    private String OPERATORID;
    /** ����ʱ�� */
    private String CREATETIME;
    /** �޸�ʱ�� */
    private String UPDATETIME;
    /** ���� */
    private String PHOTOURL;

    public String getID() {
        return ID;
    }

    public void setID(String Id) {
        ID = Id;
    }

    public String getEVENTNM() {
        return EVENTNM;
    }

    public void setEVENTNM(String eVENTNM) {
        EVENTNM = eVENTNM;
    }

    public String getOCCURTIME() {
        return OCCURTIME;
    }

    public void setOCCURTIME(String oCCURTIME) {
        OCCURTIME = oCCURTIME;
    }

    public String getOCCURADDR() {
        return OCCURADDR;
    }

    public void setOCCURADDR(String oCCURADDR) {
        OCCURADDR = oCCURADDR;
    }

    public String getSOURCEWAY() {
        return SOURCEWAY;
    }

    public void setSOURCEWAY(String sOURCEWAY) {
        SOURCEWAY = sOURCEWAY;
    }

    public String getSOURCEPEOPLE() {
        return SOURCEPEOPLE;
    }

    public void setSOURCEPEOPLE(String sOURCEPEOPLE) {
        SOURCEPEOPLE = sOURCEPEOPLE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String pHONE) {
        PHONE = pHONE;
    }

    public String getEVENTSTATUS() {
        return EVENTSTATUS;
    }

    public void setEVENTSTATUS(String eVENTSTATUS) {
        EVENTSTATUS = eVENTSTATUS;
    }

    public String getNEWS() {
        return NEWS;
    }

    public void setNEWS(String nEWS) {
        NEWS = nEWS;
    }

    public String getAREAID() {
        return AREAID;
    }

    public void setAREAID(String aREAID) {
        AREAID = aREAID;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String lONGITUDE) {
        LONGITUDE = lONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String lATITUDE) {
        LATITUDE = lATITUDE;
    }

    public String getOPERATORID() {
        return OPERATORID;
    }

    public void setOPERATORID(String oPERATORID) {
        OPERATORID = oPERATORID;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String cREATETIME) {
        CREATETIME = cREATETIME;
    }

    public String getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(String uPDATETIME) {
        UPDATETIME = uPDATETIME;
    }

    public String getPHOTOURL() {
        return PHOTOURL;
    }

    public void setPHOTOURL(String pHOTOURL) {
        PHOTOURL = pHOTOURL;
    }
}
