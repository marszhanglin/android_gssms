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
@Table(name = "EOS_DICT_ENTRY")
public class EOS_DICT_ENTRY {
    /** ���� */
    @Id(column = "ENTRYID")
    private int ENTRYID;
    /** ���� */
    private String DICTTYPEID;
    /** ��λʱ�� */
    private String DICTID;
    /** ·�� */
    private String DICTNAME;
    /** �ļ������� */
    private String STATUS;
    /** �����ֶ� */
    private String SORTNO;
    /** �ļ����� */
    private String RANK;
    /** �ļ����� */
    private String PARENTID;
    /** �ļ����� */
    private String SEQNO;
    /** �ļ����� */
    private String DICSTATEVALUE;
    /** �ļ����� */
    private String DICMATHVALUE;
    /** �Ƿ�ѡ�� 0�� 1�� */
    private String isSelected = "0";

    public EOS_DICT_ENTRY() {
        super();
    }

    /**
     * creator
     * 
     * @param dICTID
     * @param dICTNAME
     */
    public EOS_DICT_ENTRY(String dICTID, String dICTNAME) {
        super();
        DICTID = dICTID;
        DICTNAME = dICTNAME;
    }

    public int getENTRYID() {
        return ENTRYID;
    }

    public void setENTRYID(int eNTRYID) {
        ENTRYID = eNTRYID;
    }

    public String getDICTTYPEID() {
        return DICTTYPEID;
    }

    public void setDICTTYPEID(String dICTTYPEID) {
        DICTTYPEID = dICTTYPEID;
    }

    public String getDICTID() {
        return DICTID;
    }

    public void setDICTID(String dICTID) {
        DICTID = dICTID;
    }

    public String getDICTNAME() {
        return DICTNAME;
    }

    public void setDICTNAME(String dICTNAME) {
        DICTNAME = dICTNAME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    public String getSORTNO() {
        return SORTNO;
    }

    public void setSORTNO(String sORTNO) {
        SORTNO = sORTNO;
    }

    public String getRANK() {
        return RANK;
    }

    public void setRANK(String rANK) {
        RANK = rANK;
    }

    public String getPARENTID() {
        return PARENTID;
    }

    public void setPARENTID(String pARENTID) {
        PARENTID = pARENTID;
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String sEQNO) {
        SEQNO = sEQNO;
    }

    public String getDICSTATEVALUE() {
        return DICSTATEVALUE;
    }

    public void setDICSTATEVALUE(String dICSTATEVALUE) {
        DICSTATEVALUE = dICSTATEVALUE;
    }

    public String getDICMATHVALUE() {
        return DICMATHVALUE;
    }

    public void setDICMATHVALUE(String dICMATHVALUE) {
        DICMATHVALUE = dICMATHVALUE;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

}
