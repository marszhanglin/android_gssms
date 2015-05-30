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
@Table(name = "T_PERSON_INSPECTION")
public class T_PERSON_INSPECTION {

    /** ���� */
    @Id(column = "SYSID")
    private String SYSID;
    /** ���� */
    private String AREAID;
    /** ��λʱ�� */
    private String PERSONID;
    /** ·�� */
    private String PERSONNAME;
    /** �ļ������� */
    private String INSTYPE;

    /** �����ֶ� */
    private String INSDATE;
    /** �ļ����� */
    private String INSPLACEID;
    /** �ļ����� */
    private String INSPLACE;
    /** �ļ����� */
    private String INSPLACEADD;
    /** �ļ����� */
    private String INSRESULT;

    /** �����ֶ� */
    private String ISHIDDEN;
    /** �ļ����� */
    private String HIDDENCOUNT;
    /** �ļ����� */
    private String HIDDENDANGER;
    /** �ļ����� */
    private String RECTTERM;
    /** �ļ����� */
    private String RECTSTATE;

    /** �����ֶ� */
    private String RECTNOTE;
    /** �ļ����� */
    private String NOTES;
    /** �ļ����� */
    private String ATTA1;
    /** �ļ����� */
    private String ATTA2;

    public String getSYSID() {
        return SYSID;
    }

    public void setSYSID(String sYSID) {
        SYSID = sYSID;
    }

    public String getAREAID() {
        return AREAID;
    }

    public void setAREAID(String aREAID) {
        AREAID = aREAID;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String pERSONID) {
        PERSONID = pERSONID;
    }

    public String getPERSONNAME() {
        return PERSONNAME;
    }

    public void setPERSONNAME(String pERSONNAME) {
        PERSONNAME = pERSONNAME;
    }

    public String getINSTYPE() {
        return INSTYPE;
    }

    public void setINSTYPE(String iNSTYPE) {
        INSTYPE = iNSTYPE;
    }

    public String getINSDATE() {
        return INSDATE;
    }

    public void setINSDATE(String iNSDATE) {
        INSDATE = iNSDATE;
    }

    public String getINSPLACEID() {
        return INSPLACEID;
    }

    public void setINSPLACEID(String iNSPLACEID) {
        INSPLACEID = iNSPLACEID;
    }

    public String getINSPLACE() {
        return INSPLACE;
    }

    public void setINSPLACE(String iNSPLACE) {
        INSPLACE = iNSPLACE;
    }

    public String getINSPLACEADD() {
        return INSPLACEADD;
    }

    public void setINSPLACEADD(String iNSPLACEADD) {
        INSPLACEADD = iNSPLACEADD;
    }

    public String getINSRESULT() {
        return INSRESULT;
    }

    public void setINSRESULT(String iNSRESULT) {
        INSRESULT = iNSRESULT;
    }

    public String getISHIDDEN() {
        return ISHIDDEN;
    }

    public void setISHIDDEN(String iSHIDDEN) {
        ISHIDDEN = iSHIDDEN;
    }

    public String getHIDDENCOUNT() {
        return HIDDENCOUNT;
    }

    public void setHIDDENCOUNT(String hIDDENCOUNT) {
        HIDDENCOUNT = hIDDENCOUNT;
    }

    public String getHIDDENDANGER() {
        return HIDDENDANGER;
    }

    public void setHIDDENDANGER(String hIDDENDANGER) {
        HIDDENDANGER = hIDDENDANGER;
    }

    public String getRECTTERM() {
        return RECTTERM;
    }

    public void setRECTTERM(String rECTTERM) {
        RECTTERM = rECTTERM;
    }

    public String getRECTSTATE() {
        return RECTSTATE;
    }

    public void setRECTSTATE(String rECTSTATE) {
        RECTSTATE = rECTSTATE;
    }

    public String getRECTNOTE() {
        return RECTNOTE;
    }

    public void setRECTNOTE(String rECTNOTE) {
        RECTNOTE = rECTNOTE;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String nOTES) {
        NOTES = nOTES;
    }

    public String getATTA1() {
        return ATTA1;
    }

    public void setATTA1(String aTTA1) {
        ATTA1 = aTTA1;
    }

    public String getATTA2() {
        return ATTA2;
    }

    public void setATTA2(String aTTA2) {
        ATTA2 = aTTA2;
    }

}
