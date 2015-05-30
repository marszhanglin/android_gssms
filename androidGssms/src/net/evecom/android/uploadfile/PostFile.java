/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.uploadfile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * 2014-7-22下午4:33:07 类PostFile
 * 
 * @author Mars zhang
 * 
 */
public class PostFile {
    /** 上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件，这根据需要页定 */
    public static String post(String actionUrl, Map<String, String> params, Map<String, File> files,
            ArrayList<String> filenames) throws IOException {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        System.out.println("PostFile");
        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setConnectTimeout(8 * 1000);
        // 设置从服务器读取数据超时时间
        conn.setReadTimeout(30 * 1000);
        // conn.setReadTimeout(5 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }

        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null) {
            int i = 0;
            for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                // sb1.append("Content-Disposition: form-data;
                // name=\"file"+(i++)+"\"; filename=\""+file.getKey()+"\""+LINEND);
                sb1.append("Content-Disposition: form-data; name=\"file" + (i++) + "\"; filename=\"" + filenames.get(i)
                        + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINEND.getBytes());
            }
        }

        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();

        // 得到响应码
        // int res = conn.getResponseCode();
        // InputStream in = null;
        // StringBuilder sb2 = new StringBuilder();
        // if (res == 200) {
        // in = conn.getInputStream();
        // int ch;
        // while ((ch = in.read()) != -1) {
        // sb2.append((char) ch);
        // }
        // }
        // System.out.println(in == null ? null : sb2.toString()+"     上传结束");
        // return in == null ? null : sb2.toString();

        int res = conn.getResponseCode();
        InputStream in = null;
        BufferedReader br = null;
        String s = "";
        String xmlStr = "";
        if (res == 200) {
            in = conn.getInputStream();
            br = new java.io.BufferedReader(new InputStreamReader(in));
            while ((s = br.readLine()) != null) {
                xmlStr += s;
            }
            String newString = new String(xmlStr.getBytes(), "UTF-8");
            return in == null ? null : newString;
        }
        return null;

    }

    // InputStream in = conn.getInputStream();
    //
    // BufferedReader br = new java.io.BufferedReader( new
    // InputStreamReader(in));
    //
    // while ((s = br.readLine()) != null ) {
    //
    // xmlStr += s;
    //
    // }
    // String newString = new String(xmlStr.getBytes(), "UTF-8" );

    public static String uploadFile(String fileName) {
        // String urlStr ="";// HttpUtil.BASE_URL+"servlet/UploadPicServlet";
        // String urlStr="http://10.0.2.2:8020/cs/upload1.htm";
        String urlStr = "http://do.jhost.cn/fzsmt/servlet/UploadFileServlet";
        // String
        // urlStr="http://10.0.2.2:8088/Chapter15_Mobile_Police_Server/servlet/UploadFileServlet";

        String newName = "image.gif";
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            /* 设置传送的method=POST */
            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            /* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);

            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(fileName);
            /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            /* close streams */
            fStream.close();
            ds.flush();

            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }

            ds.close();

            return b.toString().trim();
        } catch (Exception e) {
            // showAlert(""+e);
            return "" + e;
        }

        // return null;
    }

}
