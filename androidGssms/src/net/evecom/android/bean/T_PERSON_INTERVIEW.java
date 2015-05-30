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
@Table(name = "T_PERSON_INTERVIEW")
public class T_PERSON_INTERVIEW {
    /** 主键 */
    @Id(column = "INTERVIEWID")
    private String INTERVIEWID;
    /** 描述 */
    private String PERSONID;
    /** 定位时间 */
    private String INTERVIEWTIME;
    /** 路径 */
    private String INTERVIEWTYPE;
    /** 文件的类型 */
    private String INTERVIEWEFFECT;

    /** 备用字段 */
    private String LATESTNEWS;
    /** 文件名称 */
    private String INTERVIEWREASON;
    /** 文件名称 */
    private String RECENTSKETCH;
    /** 文件名称 */
    private String TAKESTEP;
    /** 文件名称 */
    private String PAYLOAD;

    /** 文件名称 */
    private String INTERVIEWMARK;
    /** 是否被选择 0否 1是 */
    private String INTERVIEWERID;
    /** 文件名称 */
    private String INTERVIEWERNAME;
    /** 文件名称 */
    private String PERSONNAME;
    /** 文件名称 */
    private String MALEDICTID;
    /** 文件名称 */
    private String BIRTH;

    public String getMALEDICTID() {
        return MALEDICTID;
    }

    public void setMALEDICTID(String mALEDICTID) {
        MALEDICTID = mALEDICTID;
    }

    public String getBIRTH() {
        return BIRTH;
    }

    public void setBIRTH(String bIRTH) {
        BIRTH = bIRTH;
    }

    public String getPERSONNAME() {
        return PERSONNAME;
    }

    public void setPERSONNAME(String pERSONNAME) {
        PERSONNAME = pERSONNAME;
    }

    public String getINTERVIEWID() {
        return INTERVIEWID;
    }

    public void setINTERVIEWID(String iNTERVIEWID) {
        INTERVIEWID = iNTERVIEWID;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String pERSONID) {
        PERSONID = pERSONID;
    }

    public String getINTERVIEWTIME() {
        return INTERVIEWTIME;
    }

    public void setINTERVIEWTIME(String iNTERVIEWTIME) {
        INTERVIEWTIME = iNTERVIEWTIME;
    }

    public String getINTERVIEWTYPE() {
        return INTERVIEWTYPE;
    }

    public void setINTERVIEWTYPE(String iNTERVIEWTYPE) {
        INTERVIEWTYPE = iNTERVIEWTYPE;
    }

    public String getINTERVIEWEFFECT() {
        return INTERVIEWEFFECT;
    }

    public void setINTERVIEWEFFECT(String iNTERVIEWEFFECT) {
        INTERVIEWEFFECT = iNTERVIEWEFFECT;
    }

    public String getLATESTNEWS() {
        return LATESTNEWS;
    }

    public void setLATESTNEWS(String lATESTNEWS) {
        LATESTNEWS = lATESTNEWS;
    }

    public String getINTERVIEWREASON() {
        return INTERVIEWREASON;
    }

    public void setINTERVIEWREASON(String iNTERVIEWREASON) {
        INTERVIEWREASON = iNTERVIEWREASON;
    }

    public String getRECENTSKETCH() {
        return RECENTSKETCH;
    }

    public void setRECENTSKETCH(String rECENTSKETCH) {
        RECENTSKETCH = rECENTSKETCH;
    }

    public String getTAKESTEP() {
        return TAKESTEP;
    }

    public void setTAKESTEP(String tAKESTEP) {
        TAKESTEP = tAKESTEP;
    }

    public String getPAYLOAD() {
        return PAYLOAD;
    }

    public void setPAYLOAD(String pAYLOAD) {
        PAYLOAD = pAYLOAD;
    }

    public String getINTERVIEWMARK() {
        return INTERVIEWMARK;
    }

    public void setINTERVIEWMARK(String iNTERVIEWMARK) {
        INTERVIEWMARK = iNTERVIEWMARK;
    }

    public String getINTERVIEWERID() {
        return INTERVIEWERID;
    }

    public void setINTERVIEWERID(String iNTERVIEWERID) {
        INTERVIEWERID = iNTERVIEWERID;
    }

    public String getINTERVIEWERNAME() {
        return INTERVIEWERNAME;
    }

    public void setINTERVIEWERNAME(String iNTERVIEWERNAME) {
        INTERVIEWERNAME = iNTERVIEWERNAME;
    }

}
