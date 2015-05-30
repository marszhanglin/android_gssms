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
 * 2014-8-20下午14:41:54 类DailyLogPerson
 * 
 * @author SLQ shen
 * 
 */
@Table(name = "DailyLogPerson")
public class DailyLogPerson {

    /** 系统编码 */
    @Id(column = "SYSID")
    private String SYSID;
    /** 所属网格ID */
    private String AREAID;
    /** 所属网格 */
    private String AREANAME;
    /** 网格员ID */
    private String PERSONID;
    /** 网格员姓名 */
    private String PERSONNAME;
    /** 日志类型 */
    private String LOGTYPE;
    /** 工作日期 */
    private String LOGDATE;
    /** 工作内容 */
    private String LOGWORK;
    /** 备注 */
    private String NOTES;
    /** 备用字段1 */
    private String ATTA1;
    /** 备用字段2 */
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

    public String getAREANAME() {
        return AREANAME;
    }

    public void setAREANAME(String aREANAME) {
        AREANAME = aREANAME;
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

    public String getLOGTYPE() {
        return LOGTYPE;
    }

    public void setLOGTYPE(String lOGTYPE) {
        LOGTYPE = lOGTYPE;
    }

    public String getLOGDATE() {
        return LOGDATE;
    }

    public void setLOGDATE(String lOGDATE) {
        LOGDATE = lOGDATE;
    }

    public String getLOGWORK() {
        return LOGWORK;
    }

    public void setLOGWORK(String lOGWORK) {
        LOGWORK = lOGWORK;
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
