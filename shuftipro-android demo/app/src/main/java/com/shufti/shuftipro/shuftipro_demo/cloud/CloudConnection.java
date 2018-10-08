package com.shufti.shuftipro.shuftipro_demo.cloud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.saudali.shuftipro.listeners.NetworkListener;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class CloudConnection {
    private static CloudConnection instance = null;
    private boolean errorOccured = true;
    private String TAG = CloudConnection.class.getSimpleName();
    private static final String SHUFTIPRO_DEMO_CLIENT_URL = "https://shuftipro.com/api/v3/sdk/client_id_and_secret_key";
    private String EMAIL;
    private String PASSWORD;
    private InputStream inputStream = null;

    public CloudConnection(String email, String password) {
        this.EMAIL = email;
        this.PASSWORD = password;
    }

    public static CloudConnection getInstance(String email, String password) {
        if (instance == null) {
            instance = new CloudConnection(email, password);
        }

        return instance;
    }

    @SuppressLint({"StaticFieldLeak"})
    public boolean executeLoginRequest(final NetworkListener networkListener, Context context) {
        if (networkAvailable(context)) {
            (new AsyncTask<Void, Void, String>() {
                protected String doInBackground(Void... voids) {
                    String resultResponse = "";

                    try {
                        URL url = new URL(SHUFTIPRO_DEMO_CLIENT_URL);
                        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        connection.setAllowUserInteraction(false);
                        connection.setRequestProperty("Connection", "Keep-Alive");
                        connection.setConnectTimeout(10000);
                        connection.setReadTimeout(15000);
                        connection.setRequestMethod("POST");
                        connection.setUseCaches(false);
                        connection.connect();

                        HashMap<String, String> params = new HashMap<>();
                        params.put("email",EMAIL);
                        params.put("password",PASSWORD);

                        StringBuilder sbParams = new StringBuilder();
                        int i = 0;
                        for (String key : params.keySet()) {
                            try {
                                if (i != 0){
                                    sbParams.append("&");
                                }
                                sbParams.append(key).append("=")
                                        .append(URLEncoder.encode(params.get(key), "UTF-8"));

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }

                        String paramsString = sbParams.toString();
                        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                        wr.writeBytes(paramsString);
                        wr.flush();
                        wr.close();

                        int responseCode = connection.getResponseCode();
                        Log.d("ResponseCode", resultResponse);
                        if (responseCode == 200) {
                            inputStream = connection.getInputStream();
                            errorOccured = false;
                            resultResponse = inputStreamToString(inputStream);
                        } else {
                            errorOccured = true;
                            resultResponse = "Email or password is invalid. Try again.";
                        }

                        return resultResponse;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return resultResponse;
                    }
                }

                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    if (networkListener != null) {
                        if (!errorOccured) {
                            networkListener.successResponse(result);
                        } else {
                            networkListener.errorResponse(result);
                        }
                    }

                }
            }).execute();
            return true;
        } else {
            return false;
        }
    }

    private static boolean networkAvailable(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        int var6 = netInfo.length;

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected()) {
                haveConnectedWifi = true;
            }

            if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected()) {
                haveConnectedMobile = true;
            }
        }

        if (!haveConnectedWifi) {
            Log.e("NetworkInfo", "No WIFI Available");
        } else {
            Log.e("NetworkInfo", "WIFI Available");
        }

        if (!haveConnectedMobile) {
            Log.e("NetworkInfo", "No Mobile Network Available");
        } else {
            Log.e("NetworkInfo", "Mobile Network Available");
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    private static String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder out = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        for(int var4 = 0; (line = reader.readLine()) != null; var4 += line.length()) {
            out.append(line);
        }

        reader.close();
        return out.toString();
    }
}
