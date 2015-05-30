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
 * 2014-7-22����4:46:54 ��FileManageBean
 * 
 * @author Mars zhang
 * 
 */
@Table(name = "T_PERSON_PERSON")
public class T_PERSON_PERSON {

    /** ���� */
    @Id(column = "PERSONID")
    private String PERSONID;
    /** ���� */
    private String HOUSEHOLDID;
    /** ��λʱ�� */
    private String AREAID;
    /** ·�� */
    private String IDCARDNO;
    /** �ļ������� */
    private String PERSONNAME;

    /** �����ֶ� */
    private String MALEDICTID;
    /** �ļ����� */
    private String MALEDICTNAME;
    /** �ļ����� */
    private String OTHERNAME;
    /** �ļ����� */
    private String PERSONPHONENO;
    /** �ļ����� */
    private String PERSONTEL;

    /** �����ֶ� */
    private String BIRTH;
    /** �ļ����� */
    private String NATIOND;
    /** �ļ����� */
    private String NATIONDNM;
    /** �ļ����� */
    private String EDUCATIOND;
    /** �ļ����� */
    private String EDUCATIONDNM;

    /** �����ֶ� */
    private String POLITICSD;
    /** �ļ����� */
    private String POLITICSDNM;
    /** �ļ����� */
    private String DUTYD;
    /** �ļ����� */
    private String DUTYDNM;
    /** �ļ����� */
    private String PERSONUNIT;

    /** �����ֶ� */
    private String MARRIAGED;
    /** �ļ����� */
    private String MARRIAGEDNM;
    /** �ļ����� */
    private String PERSONHEIGHT;
    /** �ļ����� */
    private String BLOODTYPEID;
    /** �ļ����� */
    private String BLOODTYPENAME;

    /** �����ֶ� */
    private String FAITHID;
    /** �ļ����� */
    private String FAITHNAME;
    /** �ļ����� */
    private String EMAIL;
    /** �ļ����� */
    private String PERSONREMARK;
    /** �ļ����� */
    private String PHOTOURL;

    /** �����ֶ� */
    private String HOUSEPROV;
    /** �ļ����� */
    private String HOUSECITY;
    /** �ļ����� */
    private String HOUSECOUNTY;
    /** �ļ����� */
    private String HOUSEADDR;
    /** �ļ����� */
    private String HOUSEPLC;

    /** �����ֶ� */
    private String HASHOUSE;
    /** �ļ����� */
    private String NOHOUSEREASON;
    /** �ļ����� */
    private String ADDR;
    /** �ļ����� */
    private String TEMPADDR;
    /** �ļ����� */
    private String ISDEATH;

    /** �����ֶ� */
    private String CREATETIME;
    /** �ļ����� */
    private String UPDATETIME;
    /** �ļ����� */
    private String PERSONTYPE;
    /** �ļ����� */
    private String FMID;
    /** �ļ����� */
    private String CREATEDATE;

    /** �ļ����� */
    private String ISKEYPERSON;

    /** �ļ����� */
    private String KEYTYPE;

    public String getKEYTYPE() {
        return KEYTYPE;
    }

    public void setKEYTYPE(String kEYTYPE) {
        KEYTYPE = kEYTYPE;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String pERSONID) {
        PERSONID = pERSONID;
    }

    public String getHOUSEHOLDID() {
        return HOUSEHOLDID;
    }

    public void setHOUSEHOLDID(String hOUSEHOLDID) {
        HOUSEHOLDID = hOUSEHOLDID;
    }

    public String getAREAID() {
        return AREAID;
    }

    public void setAREAID(String aREAID) {
        AREAID = aREAID;
    }

    public String getIDCARDNO() {
        return IDCARDNO;
    }

    public void setIDCARDNO(String iDCARDNO) {
        IDCARDNO = iDCARDNO;
    }

    public String getPERSONNAME() {
        return PERSONNAME;
    }

    public void setPERSONNAME(String pERSONNAME) {
        PERSONNAME = pERSONNAME;
    }

    public String getMALEDICTID() {
        return MALEDICTID;
    }

    public void setMALEDICTID(String mALEDICTID) {
        MALEDICTID = mALEDICTID;
    }

    public String getMALEDICTNAME() {
        return MALEDICTNAME;
    }

    public void setMALEDICTNAME(String mALEDICTNAME) {
        MALEDICTNAME = mALEDICTNAME;
    }

    public String getOTHERNAME() {
        return OTHERNAME;
    }

    public void setOTHERNAME(String oTHERNAME) {
        OTHERNAME = oTHERNAME;
    }

    public String getPERSONPHONENO() {
        return PERSONPHONENO;
    }

    public void setPERSONPHONENO(String pERSONPHONENO) {
        PERSONPHONENO = pERSONPHONENO;
    }

    public String getPERSONTEL() {
        return PERSONTEL;
    }

    public void setPERSONTEL(String pERSONTEL) {
        PERSONTEL = pERSONTEL;
    }

    public String getBIRTH() {
        return BIRTH;
    }

    public void setBIRTH(String bIRTH) {
        BIRTH = bIRTH;
    }

    public String getNATIOND() {
        return NATIOND;
    }

    public void setNATIOND(String nATIOND) {
        NATIOND = nATIOND;
    }

    public String getNATIONDNM() {
        return NATIONDNM;
    }

    public void setNATIONDNM(String nATIONDNM) {
        NATIONDNM = nATIONDNM;
    }

    public String getEDUCATIOND() {
        return EDUCATIOND;
    }

    public void setEDUCATIOND(String eDUCATIOND) {
        EDUCATIOND = eDUCATIOND;
    }

    public String getEDUCATIONDNM() {
        return EDUCATIONDNM;
    }

    public void setEDUCATIONDNM(String eDUCATIONDNM) {
        EDUCATIONDNM = eDUCATIONDNM;
    }

    public String getPOLITICSD() {
        return POLITICSD;
    }

    public void setPOLITICSD(String pOLITICSD) {
        POLITICSD = pOLITICSD;
    }

    public String getPOLITICSDNM() {
        return POLITICSDNM;
    }

    public void setPOLITICSDNM(String pOLITICSDNM) {
        POLITICSDNM = pOLITICSDNM;
    }

    public String getDUTYD() {
        return DUTYD;
    }

    public void setDUTYD(String dUTYD) {
        DUTYD = dUTYD;
    }

    public String getDUTYDNM() {
        return DUTYDNM;
    }

    public void setDUTYDNM(String dUTYDNM) {
        DUTYDNM = dUTYDNM;
    }

    public String getPERSONUNIT() {
        return PERSONUNIT;
    }

    public void setPERSONUNIT(String pERSONUNIT) {
        PERSONUNIT = pERSONUNIT;
    }

    public String getMARRIAGED() {
        return MARRIAGED;
    }

    public void setMARRIAGED(String mARRIAGED) {
        MARRIAGED = mARRIAGED;
    }

    public String getMARRIAGEDNM() {
        return MARRIAGEDNM;
    }

    public void setMARRIAGEDNM(String mARRIAGEDNM) {
        MARRIAGEDNM = mARRIAGEDNM;
    }

    public String getPERSONHEIGHT() {
        return PERSONHEIGHT;
    }

    public void setPERSONHEIGHT(String pERSONHEIGHT) {
        PERSONHEIGHT = pERSONHEIGHT;
    }

    public String getBLOODTYPEID() {
        return BLOODTYPEID;
    }

    public void setBLOODTYPEID(String bLOODTYPEID) {
        BLOODTYPEID = bLOODTYPEID;
    }

    public String getBLOODTYPENAME() {
        return BLOODTYPENAME;
    }

    public void setBLOODTYPENAME(String bLOODTYPENAME) {
        BLOODTYPENAME = bLOODTYPENAME;
    }

    public String getFAITHID() {
        return FAITHID;
    }

    public void setFAITHID(String fAITHID) {
        FAITHID = fAITHID;
    }

    public String getFAITHNAME() {
        return FAITHNAME;
    }

    public void setFAITHNAME(String fAITHNAME) {
        FAITHNAME = fAITHNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    public String getPERSONREMARK() {
        return PERSONREMARK;
    }

    public void setPERSONREMARK(String pERSONREMARK) {
        PERSONREMARK = pERSONREMARK;
    }

    public String getPHOTOURL() {
        return PHOTOURL;
    }

    public void setPHOTOURL(String pHOTOURL) {
        PHOTOURL = pHOTOURL;
    }

    public String getHOUSEPROV() {
        return HOUSEPROV;
    }

    public void setHOUSEPROV(String hOUSEPROV) {
        HOUSEPROV = hOUSEPROV;
    }

    public String getHOUSECITY() {
        return HOUSECITY;
    }

    public void setHOUSECITY(String hOUSECITY) {
        HOUSECITY = hOUSECITY;
    }

    public String getHOUSECOUNTY() {
        return HOUSECOUNTY;
    }

    public void setHOUSECOUNTY(String hOUSECOUNTY) {
        HOUSECOUNTY = hOUSECOUNTY;
    }

    public String getHOUSEADDR() {
        return HOUSEADDR;
    }

    public void setHOUSEADDR(String hOUSEADDR) {
        HOUSEADDR = hOUSEADDR;
    }

    public String getHOUSEPLC() {
        return HOUSEPLC;
    }

    public void setHOUSEPLC(String hOUSEPLC) {
        HOUSEPLC = hOUSEPLC;
    }

    public String getHASHOUSE() {
        return HASHOUSE;
    }

    public void setHASHOUSE(String hASHOUSE) {
        HASHOUSE = hASHOUSE;
    }

    public String getNOHOUSEREASON() {
        return NOHOUSEREASON;
    }

    public void setNOHOUSEREASON(String nOHOUSEREASON) {
        NOHOUSEREASON = nOHOUSEREASON;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String aDDR) {
        ADDR = aDDR;
    }

    public String getTEMPADDR() {
        return TEMPADDR;
    }

    public void setTEMPADDR(String tEMPADDR) {
        TEMPADDR = tEMPADDR;
    }

    public String getISDEATH() {
        return ISDEATH;
    }

    public void setISDEATH(String iSDEATH) {
        ISDEATH = iSDEATH;
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

    public String getPERSONTYPE() {
        return PERSONTYPE;
    }

    public void setPERSONTYPE(String pERSONTYPE) {
        PERSONTYPE = pERSONTYPE;
    }

    public String getFMID() {
        return FMID;
    }

    public void setFMID(String fMID) {
        FMID = fMID;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String cREATEDATE) {
        CREATEDATE = cREATEDATE;
    }

    public String getISKEYPERSON() {
        return ISKEYPERSON;
    }

    public void setISKEYPERSON(String iSKEYPERSON) {
        ISKEYPERSON = iSKEYPERSON;
    }

}
