package gundam.utils;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {
    private static int URL_CONNECTION_CONNECT_TIMEOUT = 10000;
    private static int URL_CONNECTION_READ_TIMEOUT = 30000;

    private static String DELIMITER = "--";
    private static String BOUNDARY = "------WebKitFormBoundaryI8koBgFPKU5GoZ2O--";

    public static String doPostData(String uploadUrl, MultipartFile file){
        HttpURLConnection conn = null;
        InputStream input = null;
        DataOutputStream dos = null;
        try {
            URL url = new URL(uploadUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();

            String fileName = file.getOriginalFilename();
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes((DELIMITER + BOUNDARY + "\r\n"));
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"\r\n");
            dos.writeBytes("Content-Type: ");
            dos.writeBytes(file.getContentType());
            dos.writeBytes("\r\n");// Content-Type: text/plain
            dos.writeBytes("Content-Transfer-Encoding: binary\r\n\r\n");

            // create a buffer of maximum size
            byte[] data = file.getBytes();
            dos.write(data, 0, data.length);

            dos.writeBytes("\r\n");
            dos.writeBytes(DELIMITER + BOUNDARY + DELIMITER + "\r\n");
            dos.flush();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new Exception(String.format("Received the response code %d from the URL %s", responseCode, url));
            }

            input = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(bytes)) != -1) {
                baos.write(bytes, 0, bytesRead);
            }
            byte[] bytesReceived = baos.toByteArray();
            return new String(bytesReceived, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(null != dos){
                    dos.close();
                    dos = null;
                }
                if(null != input){
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doGet(String url, String charset){
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(0);
        client.getHttpConnectionManager().getParams().setSoTimeout(0);
        GetMethod method = new GetMethod(url);
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset="+charset);
        method.addRequestHeader("Connection", "keep-alive");
        DefaultHttpMethodRetryHandler retry = new DefaultHttpMethodRetryHandler(0, false);
        String responseText = null;

        try{
            method.getParams().setParameter("http.method.retry-handler", retry);
            client.executeMethod(method);
            responseText =  method.getResponseBodyAsString();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            method.releaseConnection();
        }
        return responseText;
    }

    public static String doPost(String url, String data, String charset){
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(0);
        client.getHttpConnectionManager().getParams().setSoTimeout(0);
        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset="+charset);
        method.addRequestHeader("Connection", "keep-alive");
        DefaultHttpMethodRetryHandler retry = new DefaultHttpMethodRetryHandler(0, false);
        String responseText = null;

        try{
            RequestEntity objRequestEntity = new ByteArrayRequestEntity(data.getBytes(charset));
            method.setRequestEntity(objRequestEntity);
            method.getParams().setParameter("http.method.retry-handler", retry);
            client.executeMethod(method);
            responseText =  method.getResponseBodyAsString();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            method.releaseConnection();
        }
        return responseText;
    }

    public static String doJsonPost(String url, String json, String charset){
        HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) );
        client.getHttpConnectionManager().getParams().setSoTimeout(3000);
        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Content-Type", "application/json; charset="+charset);
        method.addRequestHeader("Connection", "keep-alive");
        DefaultHttpMethodRetryHandler retry = new DefaultHttpMethodRetryHandler(0, false);
        String responseText = null;

        try{
            RequestEntity objRequestEntity = new ByteArrayRequestEntity(json.getBytes(charset));
            method.setRequestEntity(objRequestEntity);
            //method.getParams().setParameter("http.method.retry-handler", retry);
            client.executeMethod(method);
            responseText =  method.getResponseBodyAsString();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            method.abort();
            method.releaseConnection();
        }
        return responseText;
    }

    public static String doUploadFile(String reqUrl, Map<String, String> parameters, String filename, byte[] data) throws Exception {
        HttpURLConnection urlConn = null;
        try {
            urlConn = sendFormdata(reqUrl, parameters, filename, data);
            String responseContent = new String(getBytes(urlConn));
            return responseContent.trim();
        } catch (Exception e) {
            throw e;
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    public static byte[] getBytes(String reqUrl, Map<String, String> parameters) {
        HttpURLConnection conn = sendPost(reqUrl, parameters);
        return getBytes(conn);
    }

    private static byte[] getBytes(HttpURLConnection urlConn) {
        InputStream input = null;
        try {
            input = urlConn.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = input.read(buf)) > 0;)
                os.write(buf, 0, i);

            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally{
            try {
                if(null != input){
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static HttpURLConnection sendFormdata(String reqUrl, Map<String, String> parameters, String filename, byte[] data) throws Exception{
        HttpURLConnection urlConn = null;
        URL url = new URL(reqUrl);
        urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestMethod("POST");
        urlConn.setConnectTimeout(URL_CONNECTION_CONNECT_TIMEOUT);// （单位：毫秒）jdk
        urlConn.setReadTimeout(URL_CONNECTION_READ_TIMEOUT);// （单位：毫秒）jdk 1.5换成这个,读操作超时
        urlConn.setDoOutput(true);

        urlConn.setRequestProperty("connection", "keep-alive");

        String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
        urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        boundary = "--" + boundary;
        StringBuffer params = new StringBuffer();
        if (parameters != null) {
            for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String value = parameters.get(name);
                params.append(boundary + "\r\n");
                params.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
                // params.append(URLEncoder.encode(value, "UTF-8"));
                params.append(value);
                params.append("\r\n");
            }
        }

        StringBuilder sb = new StringBuilder();
        // sb.append("\r\n");
        sb.append(boundary);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                + filename + "\"\r\n");
        sb.append("Content-Type: application/octet-stream \r\n\r\n");
        byte[] fileDiv = sb.toString().getBytes();
        byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
        byte[] ps = params.toString().getBytes();

        OutputStream outputStream = null;
        try {
            outputStream = urlConn.getOutputStream();
            outputStream.write(ps);
            outputStream.write(fileDiv);
            outputStream.write(data);
            outputStream.write(endData);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(null != outputStream){
                outputStream.close();
                outputStream = null;
            }
        }
        return urlConn;
    }

    private static HttpURLConnection sendPost(String reqUrl, Map<String, String> parameters) {
        HttpURLConnection urlConn = null;
        OutputStream outputStream = null;
        try {
            String params = generatorParamString(parameters);
            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3");
            urlConn.setConnectTimeout(URL_CONNECTION_CONNECT_TIMEOUT);// （单位：毫秒）jdk
            urlConn.setReadTimeout(URL_CONNECTION_READ_TIMEOUT);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);
            byte[] b = params.getBytes();

            outputStream = urlConn.getOutputStream();
            outputStream.write(b, 0, b.length);
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally{
            try {
                if(null != outputStream){
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urlConn;
    }

//    *
//     * 将parameters中数据转换成用"&"链接的http请求参数形式
//     * @param parameters
//     * @return
//
    public static String generatorParamString(Map<String, String> parameters) {
        StringBuffer params = new StringBuffer();
        if (parameters != null) {
            for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String value = parameters.get(name);
                params.append(name + "=");
                try {
                    params.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (Exception e) {
                    String message = String.format("'%s'='%s'", name, value);
                    throw new RuntimeException(message, e);
                }
                if (iter.hasNext()) params.append("&");
            }
        }
        return params.toString();
    }
}
