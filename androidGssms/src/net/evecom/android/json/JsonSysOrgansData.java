/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.json;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.evecom.android.bean.MianPerson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * 
 * 2014-7-22下午4:22:15 类XmlSysOrgansData
 * 
 * @author Mars zhang
 * 
 */
public class JsonSysOrgansData {
    /** MemberVariables */
    private static ArrayList<MianPerson> list = null;

    public static ArrayList<MianPerson> getData(final String path, String entity_str) throws Exception {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        byte[] entity = entity_str.getBytes();
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
        conn.getOutputStream().write(entity);
        if (conn.getResponseCode() == 200) {
            InputStream inputstream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[1024];
            for (int i; (i = inputstream.read(b)) != -1;) {
                buffer.append(new String(b, 0, i));
            }
            StringReader reader = new StringReader(buffer.toString());
            JsonReader jsonReader = new JsonReader(reader);
            list = new ArrayList<MianPerson>();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String nextname1 = "";
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                    nextname1 = jsonReader.nextInt() + "";
                } else {
                    nextname1 = jsonReader.nextName();
                }
                if ("rows".equals(nextname1)) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        MianPerson person = new MianPerson();
                        while (jsonReader.hasNext()) {
                            String nextName = "";
                            if (jsonReader.peek() == JsonToken.NULL) {
                                jsonReader.nextNull();
                            } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                nextName = jsonReader.nextInt() + "";
                            } else if (jsonReader.peek() == JsonToken.STRING) {
                                nextName = jsonReader.nextString() + "";
                            } else {
                                nextName = jsonReader.nextName();
                            }
                            String nextValue = "";
                            if (nextName.equals("IDCARDNO")) {// 身份证号码
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setIDCARDNO(nextValue);
                            } else if (nextName.equals("HOUSEHOLDID")) {// 户口号
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setHOUSEHOLDID(nextValue);
                            } else if (nextName.equals("AREAID")) {// 区域id
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setAREAID(nextValue);
                            } else if (nextName.equals("AREANAME")) {// 区域名
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setAREANAME(nextValue);
                            } else if (nextName.equals("PERSONNAME")) {// 人名
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setPERSONNAME(nextValue);
                            } else if (nextName.equals("MALEDICTID")) {// 性别 1男
                                                                       // 2女
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                if (null != nextValue && "1".equals(nextValue)) {
                                    person.setMALEDICTID("男");
                                } else if (null != nextValue && "2".equals(nextValue)) {
                                    person.setMALEDICTID("女");
                                }
                            } else if (nextName.equals("BIRTH")) {// 出身年月
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setBIRTH(nextValue);
                            } else if (nextName.equals("HOUSEADDR")) {// 家庭住址
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setHOUSEADDR(nextValue);
                            } else if (nextName.equals("PERSONID")) {// 人员主键id
                                if (jsonReader.peek() == JsonToken.NULL) {
                                    jsonReader.nextNull();
                                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                                    nextValue = jsonReader.nextInt() + "";
                                } else {
                                    nextValue = jsonReader.nextString();
                                }
                                // nextValue = jsonReader.nextString();
                                person.setPERSONID(nextValue);
                            }
                            System.out.println(nextName + "=" + nextValue);
                        }
                        list.add(person);
                        person = null;
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                }
                //
            }
            jsonReader.endObject();

            // XmlPullParser xml = Xml.newPullParser();
            // xml.setInput(inputstream, "UTF-8");
            // int event = xml.getEventType();
            // while (event != XmlPullParser.END_DOCUMENT) {
            // switch (event) {
            // // 开始解析文档
            // case XmlPullParser.START_DOCUMENT:
            // list = new ArrayList<SysOrgan>();
            // break;
            // case XmlPullParser.START_TAG:
            //
            // String value = xml.getName();
            // if (value.equals("QY")) {
            // organ = new SysOrgan();
            // } else if (value.equals("SO_ID")) {
            // organ.setSoId(xml.nextText());
            // } else if (value.equals("so_Name")) {
            // organ.setSoName(xml.nextText());
            // }
            // break;
            // case XmlPullParser.END_TAG:
            // if (xml.getName().equals("QY")) {
            // list.add(organ);
            // organ = null;
            // }
            // break;
            // default:
            // break;
            // }
            // // 解析下一个对象
            // event = xml.next();
            // }
            return list;
        } else {
            return null;
        }
    }
}
