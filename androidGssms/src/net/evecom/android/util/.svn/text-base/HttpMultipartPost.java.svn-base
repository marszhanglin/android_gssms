/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.util;

import java.io.File;

import net.evecom.android.util.CustomMultipartEntity.ProgressListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
/**
 * 
 * √Ë ˆHttpMultipartPost
 * @author Mars zhang
 * @created 2014-11-5 …œŒÁ10:56:31
 */
public class HttpMultipartPost extends AsyncTask<String, Integer, String> {
    /** MemberVariables */
    private Context context;
    /** MemberVariables */
    private String filePath;
    /** MemberVariables */
    private ProgressDialog pd;
    /** MemberVariables */
    private long totalSize;
    /** MemberVariables */
    private String url;

    public HttpMultipartPost(Context context, String filePath, String url) {
        this.context = context;
        this.filePath = filePath;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("Uploading Picture...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String serverResponse = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);

        try {
            CustomMultipartEntity multipartContent = new CustomMultipartEntity(new ProgressListener() {
                @Override
                public void transferred(long num) {
                    publishProgress((int) ((num / (float) totalSize) * 100));
                }
            });

            // We use FileBody to transfer an image
            multipartContent.addPart("data", new FileBody(new File(filePath)));
            totalSize = multipartContent.getContentLength();

            // Send it
            httpPost.setEntity(multipartContent);
            HttpResponse response = httpClient.execute(httpPost, httpContext);
            serverResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        pd.setProgress((int) (progress[0]));
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("result: " + result);
        pd.dismiss();
    }

    @Override
    protected void onCancelled() {
        System.out.println("cancle");
    }

}
