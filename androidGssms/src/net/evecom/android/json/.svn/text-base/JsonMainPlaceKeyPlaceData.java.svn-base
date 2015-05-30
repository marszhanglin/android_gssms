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
import net.evecom.android.bean.T_PERSON_INTERVIEW;
import net.evecom.android.bean.T_PERSON_PERSON;
import net.evecom.android.bean.T_PLACE_PLACE;

import com.google.gson.stream.JsonReader;

/**
 * 
 * 2014-8-18…œŒÁ11:25:02 ¿‡JsonMainPersonKeyPersonData
 * 
 * @author Mars zhang
 * 
 */
public class JsonMainPlaceKeyPlaceData {
    /** MemberVariables */
    private static ArrayList<T_PLACE_PLACE> list = null;

    public static ArrayList<T_PLACE_PLACE> getData(final String path, String entity_str) throws Exception {
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
            list = new ArrayList<T_PLACE_PLACE>();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                T_PLACE_PLACE person_view = new T_PLACE_PLACE();
                while (jsonReader.hasNext()) {
                    String nextName = jsonReader.nextName();
                    String nextValue = "";
                    if (nextName.equals("PLACEADDR")) {
                        nextValue = jsonReader.nextString();
                        person_view.setPLACEADDR(nextValue);
                    } else if (nextName.equals("PLACETYPED")) {
                        nextValue = jsonReader.nextString();
                        person_view.setPLACETYPED(nextValue);
                    } else if (nextName.equals("PLACENAME")) {
                        nextValue = jsonReader.nextString();
                        person_view.setPLACENAME(nextValue);
                    } else if (nextName.equals("PLACEID")) {
                        nextValue = jsonReader.nextString();
                        person_view.setPLACEID(nextValue);
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
