package com.example.aman.loginsignup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
  Button reg_bn;
  EditText Name,Email,UserName,Password,ConPassword;
  String name,email,username,password,conpass;
  AlertDialog.Builder builder;
  String reg_url = "http://192.168.2.43:7070/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        reg_bn = (Button)findViewById(R.id.bn_reg);
        Name = (EditText)findViewById(R.id.reg_name);
        Email = (EditText)findViewById(R.id.reg_email);
        UserName = (EditText)findViewById(R.id.reg_user_name);
        Password = (EditText)findViewById(R.id.reg_password);
        ConPassword = (EditText)findViewById(R.id.reg_con_password);
        builder = new AlertDialog.Builder(Signup.this);
        reg_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=Name.getText().toString();
                email=Email.getText().toString();
                username = UserName.getText().toString();
                password = Password.getText().toString();
                conpass = ConPassword.getText().toString();
                if(name.equals("")|| email.equals(username.equals("")|| password.equals("")|| conpass.equals(""))) {

                    builder.setTitle("Something went wrong...");
                    builder.setMessage("please fill all the fields");
                    displayAlert("input_error");
                }

                else
                {
                    if(!(password.equals(conpass))){
                        builder.setTitle("Something went wrong...");
                        builder.setMessage("Your passwords are not matching");
                        displayAlert("input_error");
                    }
                    else{
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    String message = jsonObject.getString("message");
                                    builder.setTitle("Server Response...");
                                    builder.setMessage(message);
                                    displayAlert(code);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(Signup.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
                                    error.printStackTrace();


                                }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                               Map<String,String> params = new HashMap<String, String>();
                               params.put("name", name);
                               params.put("email", email);
                               params.put("user_name", username);
                               params.put("password", password);
                                return params;

                            }
                        };

                        MySingleton.getInstance(Signup.this).addToRequestqueue(stringRequest);
                    }
                }
            }
        });

    }

    public void displayAlert (final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(code.equals("input_error")){
                    Password.setText("");
                    ConPassword.setText("");
                }
                else if (code.equals("reg_success")){
                    finish();
                }
                else if (code.equals("reg_failed")){
                    Name.setText("");
                    Email.setText("");
                    UserName.setText("");
                    Password.setText("");
                    ConPassword.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
