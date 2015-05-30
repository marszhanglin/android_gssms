/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import net.evecom.android.R;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * MessagePostWebActivity
 * 
 * @author Mars zhang
 * 
 */
public class Web3Activity extends Activity {
    /** MemberVariables */
    private WebView webView;
    /** MemberVariables */
    ProgressDialog dialog = null;
    /** MemberVariables */
    protected Context mContext;
    /** 分页 */
    private String temp = "15";
    /** 进度提示框 */
    private AlertDialog dialogPress;
    /** 图片 */
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        temp = HttpUtil.getPageSize(this);
        setContentView(R.layout.message_post_web);
        imageView = (ImageView) findViewById(R.id.image_view_at_web);
        webView = (WebView) this.findViewById(R.id.wv_oauth_message);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
        /**
         * 设置WebView的属性，此时可以去执行JavaScript脚本
         */
        webView.getSettings().setJavaScriptEnabled(true);

        /**
         * 调用loadUrl()方法进行加载内容
         */
        webView.setWebViewClient(new HelloWebViewClient());
        dialog = ProgressDialog.show(Web3Activity.this, null, "正在获取，请稍后..");
        dialog.setCancelable(true);
        // http://harlan-pc/fzaj/emergency/mobileWebApp/publicInfo/login.do?userName=zf1&userPwd=1
        // http://localhost/gssms/mobile/base/loginController/login?loginname=sysadmin&pwd=888888
        // String url = HttpUtil.BASE_PC_URL
        // + "/base/loginController/login";
        // http://localhost/gssms/mobile/loginController/baseLogin?loginname=sysadmin&pwd=888888基础数据
        String url = HttpUtil.BASE_PC_URL + "mobile/loginController/baseLogin";
        // post访问需要提交的参数
        // String postDate = "loginname=sysadmin&pwd=888888";
        String postDate = "loginname=" + ShareUtil.getString(getApplicationContext(), "SESSION", "USERNAME", "")
                + "&pwd=" + ShareUtil.getString(getApplicationContext(), "SESSION", "PASSWORD", "") + "&pageSize="
                + temp;
        // 由于webView.postUrl(url, postData)中 postData类型为byte[] ，
        // 通过EncodingUtils.getBytes(data, charset)方法进行转换
        webView.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));
        // 调用自定义的下载监听
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);// support zoom
        // webSettings.setPluginsEnabled(true);//support flash
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // webSettings.setPluginsEnabled(true); //为了视频播放(flash好像没有用了)

        /** 百度地图 */
        // //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        // 启用地理定位
        webSettings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        // 最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        // DebugLog.d(TAG, "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(ZoomDensity.CLOSE);
            // }else if(mDensity == DisplayMetrics..DENSITY_XHIGH){
            // webSettings.setDefaultZoom(ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_HIGH) {
            webSettings.setDefaultZoom(ZoomDensity.FAR);
        }
        webView.setWebChromeClient(m_chromeClient);// 为了视频播放(flash好像没有用了)

        dialogPress = new AlertDialog.Builder(this).setTitle("正在下载文件").setMessage("下载进度:0/0")
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    /** MemberVariables */
    private WebChromeClient m_chromeClient = new WebChromeClient() {
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
            return super.onCreateWindow(view, dialog, userGesture, resultMsg);
        }

        /**
         * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
         */
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);

            // 不需要绑定按键事件
            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });
            // 禁止响应按back键的事件
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
            return true;
            // return super.onJsAlert(view, url, message, result);
        }

        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        /**
         * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
         */
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("提示").setMessage(message).setPositiveButton("确定", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNeutralButton("取消", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });
            builder.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                    return true;
                }
            });
            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
         * window.prompt('请输入您的域名地址', '618119.com');
         */
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("提示").setMessage(message);
            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et).setPositiveButton("确定", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm(et.getText().toString());
                }
            }).setNeutralButton("取消", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });

            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsPrompt(view, url, message, defaultValue,
            // result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onRequestFocus(WebView view) {
            super.onRequestFocus(view);
        }

        /**
         * 百度地图配置权限（同样在WebChromeClient中实现）
         */
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

    };

    /**
     * HelloWebViewClient
     * 
     * @author Mars zhang
     * 
     */
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.setVisibility(View.INVISIBLE);
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.setVisibility(View.VISIBLE);
            if (dialog != null)
                dialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // toast(failingUrl);
            if (dialog != null)
                dialog.dismiss();
            view.clearView();
            view.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            // view.setVisibility(View.GONE);
            System.out.println(errorCode + "                errorCode ");
            if (errorCode == -6) {
                try {
                    view.stopLoading();
                } catch (Exception e) {
                }
                try {
                    view.clearView();
                } catch (Exception e) {
                }
                if (view.canGoBack()) {
                    view.goBack();
                }
            } else if (errorCode == -2) {
                System.out.println("404------------");
                try {
                    view.stopLoading();
                    view.destroy();
                } catch (Exception e) {
                }
                try {
                    view.clearView();
                } catch (Exception e) {
                }
                if (view.canGoBack()) {
                    view.goBack();
                }
            }
            // Toast.makeText(And_meetActivity.this,errorCode+
            // "网络延迟，请重新尝试！"+description, Toast.LENGTH_SHORT).show();
            Toast.makeText(Web3Activity.this, "网络延迟，请重新尝试！", Toast.LENGTH_SHORT).show();
            // Toast.makeText(And_meetActivity.this, "网络延迟，请重新尝试！",
            // Toast.LENGTH_LONG);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            // toast(url);
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            System.out.println(handler + "" + error);
            super.onReceivedSslError(view, handler, error);
        }

    }

    // @Override
    // protected Dialog onCreateDialog(int id) {
    // if (id == PROGRESS_ID) {
    // return ProgressDialog.show(mContext, "正在请求数据", "正在获取服务器数据，请稍后..");
    // }
    // return super.onCreateDialog(id);
    // }

    // 下面代码没有添加，在我的手机里也隐藏地址栏了，但是有的设备可能还要加这些
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            // webView.goBack();
            if (dialog != null)
                dialog.dismiss();
            // finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回首页
     * 
     * @param v
     */
    public void message_post_web_tomain(View v) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Web3Activity.this);
        builder1.setTitle("提示信息");
        builder1.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder1.setMessage("是否确定要返回首页？");
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder1.show();
    }

    // 内部类
    /**
     * 
     * 2014-7-22下午5:18:50 类MyWebViewDownLoadListener
     * 
     * @author Mars zhang
     * 
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                long contentLength) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast t = Toast.makeText(mContext, "需要SD卡。", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }
            // downLoadApk(url);

            // FinalHttp fh = new FinalHttp();
            // //调用download方法开始下载
            // HttpHandler handler =
            // fh.download("http://www.xxx.com/下载路径/xxx.apk", //这里是下载的路径
            // "/mnt/sdcard/testapk.apk", //这是保存到本地的路径
            // new AjaxCallBack() {
            // @Override
            // public void onLoading(long count, long current) {
            // System.out.println("下载进度："+current+"/"+count);
            // }
            //
            // public void onSuccess(File t) {
            // textView.setText(t==null?"null":t.getAbsoluteFile().toString());
            // }
            // });
            // //调用stop()方法停止下载
            // handler.stop();

            System.out.println(url);
            DownloaderTask task = new DownloaderTask();
            task.execute(url);
        }

    }

    /** 判断并下载文件 */
    private void downLoadApk(String url) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
            FinalHttp fh = new FinalHttp();
            // HttpClient client=fh.getHttpClient();
            // client.getParams().setIntParameter("http.socket.timeout",10000);
            // 创建一个临时文件
            File clear_temp = new File(Environment.getExternalStorageDirectory(), "temp.pdf");
            deleteFile(clear_temp);
            File temp = new File(Environment.getExternalStorageDirectory(), "temp.pdf");
            dialogPress.show();
            // 调用download方法开始下载
            HttpHandler handler1 = fh.download(url, // 这里是下载的路径
                    // temp.getAbsolutePath(), // 这是保存到本地的路径
                    Environment.getExternalStorageDirectory().getAbsolutePath(), false, // true:断点续传
                                                                                        // false:不断点续传（全新下载）
                    new AjaxCallBack<File>() {
                        @Override
                        public void onLoading(long count, long current) {// 每秒回调一次
                            System.out.println(current + "/" + count);
                            dialogPress.setMessage("下载进度:" + current / 1024 + "k/" + count / 1024 + "k");
                            super.onLoading(count, current);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            dialogPress.dismiss();
                            super.onFailure(t, errorNo, strMsg);
                        }

                        @Override
                        public void onSuccess(File t) {
                            System.out.println(t == null ? "null" : t.getAbsoluteFile().toString() + "下载成功");
                            dialogPress.dismiss();
                        }
                    });
        } else {
            toast("没有SD卡更新失败");
        }
    }

    /** 土司 */
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

    /** 删除文件 */
    public void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
                System.out.println("file.delete();");
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
            System.out.println("file.delete();");
        } else {
            System.out.println("文件不存在。。");
        }
    }

    // 内部类
    /**
     * 
     * 2014-7-22下午5:18:40 类DownloaderTask
     * 
     * @author Mars zhang
     * 
     */
    private class DownloaderTask extends AsyncTask<String, Void, String> {

        public DownloaderTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = params[0];
            // Log.i("tag", "url="+url);
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            fileName = URLDecoder.decode(fileName);
            Log.i("tag", "fileName=" + fileName);

            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, fileName);
            if (file.exists()) {
                Log.i("tag", "The file has already exists.");
                return fileName;
            }
            try {
                HttpClient client = new DefaultHttpClient();
                client.getParams().setIntParameter("http.socket.timeout", 10000);// 设置超时
                HttpGet get = new HttpGet(url);
                HttpResponse response = client.execute(get);
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    HttpEntity entity = response.getEntity();
                    InputStream input = entity.getContent();

                    writeToSDCard(fileName, input);

                    input.close();
                    // entity.consumeContent();
                    return fileName;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            closeProgressDialog();
            if (result == null) {
                Toast t = Toast.makeText(mContext, "连接错误！请稍后再试！", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }

            Toast t = Toast.makeText(mContext, "已保存到SD卡。", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, result);
            Log.i("tag", "Path=" + file.getAbsolutePath());

            Intent intent = getFileIntent(file);

            startActivity(intent);

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

    }

    /** MemberVariables */
    private ProgressDialog mDialog;

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(mContext);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
            mDialog.setMessage("正在加载 ，请等待...");
            mDialog.setIndeterminate(false);// 设置进度条是否为不明确
            mDialog.setCancelable(true);// 设置进度条是否可以按退回键取消
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog = null;
                }
            });
            mDialog.show();

        }
    }

    private void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public Intent getFileIntent(File file) {
        // Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Log.i("tag", "type=" + type);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public void writeToSDCard(String fileName, InputStream input) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, fileName);
            // if(file.exists()){
            // Log.i("tag", "The file has already exists.");
            // return;
            // }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[2048];
                int j = 0;
                while ((j = input.read(b)) != -1) {
                    fos.write(b, 0, j);
                }
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.i("tag", "NO SDCard.");
        }
    }

    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

        /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";//
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf")
                || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
                || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        }
        // else if(end.equals("pptx")||end.equals("ppt")){
        // type = "application/vnd.ms-powerpoint";
        // }else if(end.equals("docx")||end.equals("doc")){
        // type = "application/vnd.ms-word";
        // }else if(end.equals("xlsx")||end.equals("xls")){
        // type = "application/vnd.ms-excel";
        // }
        else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }

}
