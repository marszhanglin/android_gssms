/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.bean;

import java.util.Date;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 
 * 2014-7-22����4:46:54 ��FileManageBean
 * 
 * @author Mars zhang
 * 
 */
@Table(name = "File_ManageBean")
public class FileManageBean {
    /** ���� */
    @Id(column = "File_ID")
    private int File_ID;
    /** ���� */
    private String File_DESCRIP;
    /** ��λʱ�� */
    private Date File_Time;
    /** ·�� */
    private String File_URL;
    /** �ļ������� ͼƬ�ļ�1 ��Ƶ�ļ�2 ��Ƶ�ļ�3 */
    private String File_Flag;
    /** �����ֶ� */
    private String File_by1;
    /** �ļ����� */
    private String File_Name;

    public int getFile_ID() {
        return File_ID;
    }

    public void setFile_ID(int file_ID) {
        File_ID = file_ID;
    }

    public String getFile_DESCRIP() {
        return File_DESCRIP;
    }

    public void setFile_DESCRIP(String file_DESCRIP) {
        File_DESCRIP = file_DESCRIP;
    }

    public Date getFile_Time() {
        return File_Time;
    }

    public void setFile_Time(Date file_Time) {
        File_Time = file_Time;
    }

    public String getFile_URL() {
        return File_URL;
    }

    public void setFile_URL(String file_URL) {
        File_URL = file_URL;
    }

    public String getFile_Flag() {
        return File_Flag;
    }

    public void setFile_Flag(String file_Flag) {
        File_Flag = file_Flag;
    }

    public String getFile_by1() {
        return File_by1;
    }

    public void setFile_by1(String file_by1) {
        File_by1 = file_by1;
    }

    public String getFile_Name() {
        return File_Name;
    }

    public void setFile_Name(String file_Name) {
        File_Name = file_Name;
    }

}
