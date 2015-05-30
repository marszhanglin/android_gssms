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
 * 2014-7-22下午4:46:54 类FileManageBean
 * 
 * @author Mars zhang
 * 
 */
@Table(name = "T_PERSON_INSPECTION")
public class T_PERSON_INSPECTION {

    /** 主键 */
    @Id(column = "SYSID")
    private String SYSID;
    /** 描述 */
    private String AREAID;
    /** 定位时间 */
    private String PERSONID;
    /** 路径 */
    private String PERSONNAME;
    /** 文件的类型 */
    private String INSTYPE;

    /** 备用字段 */
    private String INSDATE;
    /** 文件名称 */
    private String INSPLACEID;
    /** 文件名称 */
    private String INSPLACE;
    /** 文件名称 */
    private String INSPLACEADD;
    /** 文件名称 */
    private String INSRESULT;

    /** 备用字段 */
    private String ISHIDDEN;
    /** 文件名称 */
    private String HIDDENCOUNT;
    /** 文件名称 */
    private String HIDDENDANGER;
    /** 文件名称 */
    private String RECTTERM;
    /** 文件名称 */
    private String RECTSTATE;

    /** 备用字段 */
    private String RECTNOTE;
    /** 文件名称 */
    private String NOTES;
    /** 文件名称 */
    private String ATTA1;
    /** 文件名称 */
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
