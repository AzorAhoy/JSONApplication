package com.example.jsonapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<itemModel> items;
    Button btnSlowWork;
    Button btnQuickWork;
    TextView TextView;
    EditText txtMsg;
    Long startingMillis;
    StringBuilder response = new StringBuilder();
    private static Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = (EditText) findViewById(R.id.txtMsg);
        TextView = (TextView) findViewById(R.id.list);
// slow work...for example: delete databases: “dummy1” and “dummy2”
        btnSlowWork = (Button) findViewById(R.id.btnSlow);
        this.btnSlowWork.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                new VerySlowTask().execute("dummy1", "dummy2");
            }
        });
        btnQuickWork = (Button) findViewById(R.id.btnQuick);
        this.btnQuickWork.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                txtMsg.setText((new Date()).toString()); // quickly show today’s date
            }
        });
        TextView.setText("");
        //items.add(new itemModel("Edurila.com","$19 Only(First 10 spots)..","Are you looking to Learn..", R.drawable.ic_launcher_background,"12:34 PM"));

//        ListView listView = findViewById(R.id.list);
//        CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(items, MainActivity.context);
//        listView.setAdapter(adapter);
//        MainActivity.context = getApplicationContext();
    }// onCreate
    private class VerySlowTask extends AsyncTask<String, Long, Void> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        String waitMsg = "Wait\nSome SLOW job is being done... ";
        protected void onPreExecute() {

            startingMillis = System.currentTimeMillis();
            txtMsg.setText("Start Time: " + startingMillis);
            TextView.setText("");
            this.dialog.setMessage(waitMsg);
            this.dialog.setCancelable(false); //outside touch doesn't dismiss you
            this.dialog.show();
        }
        protected Void doInBackground(final String... args) {
            try{
                URL url = new URL("https://jsonplaceholder.typicode.com/users");
                //URL url = new URL("http://httpbin.org/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
// optional default is GET
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                Log.v("TAG", "Sending 'GET' request to URL : " + url.toString());
                Log.v("TAG", "Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(con.getInputStream()));
                String inputLine;
                //StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
//print result
                Log.v("TAG", response.toString());
                //txtMsg.setText("TAG"+ response.toString());

            } catch (Exception e) {

            }
// show on Log.e the supplied dummy arguments
            Log.e("doInBackground>>", "Total args: " + args.length );
            Log.e("doInBackground>>", "args[0] = " + args[0] );
            try {
                for (Long i = 0L; i < 5L; i++) {
                    Thread.sleep(10000); // simulate the slow job here . . .
                    publishProgress((Long) i);
                }
            } catch (InterruptedException e) {
                Log.e("slow-job interrupted", e.getMessage());
            }
            return null;
        }
        // periodic updates - it is OK to change UI
        @Override
        protected void onProgressUpdate(Long... value) {
            super.onProgressUpdate(value);
            dialog.setMessage(waitMsg + value[0]);
            txtMsg.append("\nworking..." + value[0]);
        }
        // can use UI thread here
        protected void onPostExecute(final Void unused) {
            //items = new ArrayList<>();
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            String result = "";
//            Toast.makeText(getApplicationContext(),
//                    "Json parsing error: " + e.getMessage(),
//                    Toast.LENGTH_LONG)
//                    .show();
            try {
                //JSONObject data = new JSONObject(response.toString());
                JSONArray dataList = new JSONArray(response.toString());
                for (int i = 0; i < dataList.length(); i++) {
                    try {
                        JSONObject item = dataList.getJSONObject(i);
                        try {
                            String username = item.getString("username");
                            String name = item.getString("name");
                            String email = item.getString("email");
                            JSONObject address = item.getJSONObject("address");
                            String addressStr = address.getString("suite")+", "+ address.getString("street")+ ", " +address.getString("city");
                            TextView.append("Username: "+username+"\n");
                            TextView.append("Name: "+name+"\n");
                            TextView.append("Email: "+email+"\n");
                            TextView.append("Adress: "+addressStr+"\n");
                            //TextView.append("Data: "+item.toString()+"\n");
                            TextView.append("\n");
//                        String name = item.getString("name");
//                        String email = item.getString("email");
//                        String address = item.getString("suite")+" "+ item.getString("street")+ " " +item.getString("city");
//                        String tmp = "\n"+username+"-" +name+"\n"+email+"\n"+address;
                        } catch (final Exception e) {
                            Log.e("TAG", "Json parsing error1: " + e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });
                        }

//                        result+= tmp;

                    }  catch (final Exception e) {
                        e.printStackTrace();

                        Log.e("TAG", "Json parsing error1: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }


                    //items.add(new itemModel(username, name, email, R.drawable.ic_launcher_background, address));
                }
                //TextView.setText(response);
                //txtMsg.append("Data: "+dataList.toString()+"\n");
            } catch (final Exception e) {
                e.printStackTrace();

                   Log.e("TAG", "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }



// cleaning-up, all done
            txtMsg.append("\nEnd Time:"
                    + (System.currentTimeMillis() - startingMillis) / 1000);
            //txtMsg.append("\nTAG"+ response.toString());
            txtMsg.append("\ndone!");
            //txtMsg.append(result);
            //TextView.setText(response.toString());
//            ListView listView = findViewById(R.id.list);
//            CustomIconLabelAdapter adapter = new CustomIconLabelAdapter(items, MainActivity.context);
//            listView.setAdapter(adapter);
        }
    }// AsyncTask
}// MainActivity