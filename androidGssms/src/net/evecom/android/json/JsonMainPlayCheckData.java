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

import net.evecom.android.bean.T_PERSON_INSPECTION;

import com.google.gson.stream.JsonReader;

/**
 * 
 * 2014-7-22œ¬ŒÁ4:22:15 ¿‡JsonMainPlayCheckData
 * 
 * @author Mars zhang
 * 
 */
public class JsonMainPlayCheckData {
    /** MemberVariables */
    private static ArrayList<T_PERSON_INSPECTION> list = null;

    public static ArrayList<T_PERSON_INSPECTION> getData(final String path, String entity_str) throws Exception {
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
            list = new ArrayList<T_PERSON_INSPECTION>();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                T_PERSON_INSPECTION person_view = new T_PERSON_INSPECTION();
                while (jsonReader.hasNext()) {
                    String nextName = jsonReader.nextName();
                    String nextValue = "";
                    if (nextName.equals("INSDATE")) {
                        nextValue = jsonReader.nextString();
                        person_view.setINSDATE(nextValue);
                    } else if (nextName.equals("RECTSTATE")) {
                        nextValue = jsonReader.nextString();
                        person_view.setRECTSTATE(nextValue);
                    } else if (nextName.equals("INSPLACE")) {
                        nextValue = jsonReader.nextString();
                        person_view.setINSPLACE(nextValue);
                    } else if (nextName.equals("INSRESULT")) {
                        nextValue = jsonReader.nextString();
                        person_view.setINSRESULT(nextValue);
                    } else if (nextName.equals("INSPLACEID")) {
                        nextValue = jsonReader.nextString();
                        person_view.setINSPLACEID(nextValue);
                    } else if (nextName.equals("PERSONID")) {
                        nextValue = jsonReader.nextString();
                        person_view.setPERSONID(nextValue);
                    } else if (nextName.equals("SYSID")) {
                        nextValue = jsonReader.nextString();
                        person_view.setSYSID(nextValue);
                    }
                }
                list.add(person_view);
                person_view = null;
                jsonReader.endObject();
            }
            jsonReader.endArray();
            return list;
        } else {
            return null;
        }
    }
}
