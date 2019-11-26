/*Team Name: Humber Elites*/
package smartden.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Log_in extends AppCompatActivity {

    EditText email;
    EditText password;

    String showUrl="http://boswellkyle.com/ceng319_smartden/check2.php";

    RequestQueue rq;

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        rq = Volley.newRequestQueue(Log_in.this);

        TextView tv_reg = (TextView)findViewById(R.id.lnkRegister);
        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Log_in.this, Registration.class);
                startActivity(intent);
            }
        });

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email= (EditText)findViewById(R.id.editText);
                password = (EditText)findViewById(R.id.editText2);
                final String password_input = password.getText().toString();
                final String email_input = email.getText().toString();

                if (isValid(email_input) == false) {
                    email.requestFocus();
                    email.setError("Invalid Username");
                }
                else if(password_input.length() <=8)
                {
                    password.requestFocus();
                    password.setError("Invalid password");

                }
                else {




                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,showUrl,null,new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            try {
                                JSONArray sd_users = response.getJSONArray("SDusers");
                                for (int i=0;i<sd_users.length();i++)
                                {
                                    JSONObject user = sd_users.getJSONObject(i);
                                    String email = user.getString("email");
                                }



                            } catch (JSONException e) {e.printStackTrace(); Toast.makeText(Log_in.this,e.getMessage(),Toast.LENGTH_SHORT).show();}

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Log_in.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    rq.add(req);


                    Intent intent = new Intent(Log_in.this, MainMenu.class);
                    startActivity(intent);
                } // end of else



         //// This code below is for testing, when you are done testing, delete the code below and replace it with code above ( the one I commented out)
//                Intent intent = new Intent(Log_in.this, MainMenu.class);
//                startActivity(intent);
        //// This is for testing only



            }
        });



    }
}
