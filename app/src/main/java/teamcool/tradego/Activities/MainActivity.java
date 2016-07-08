package teamcool.tradego.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import teamcool.tradego.R;

public class MainActivity extends AppCompatActivity {

    public static final String YOUR_APPLICATION_ID = "tradego";
    //public static final String YOUR_CLIENT_KEY = "1997";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY); */


        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(YOUR_APPLICATION_ID)
                .server("http://tradego.herokuapp.com/parse/").build());

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();


        //Toast.makeText(this, testObject.get("foo"), Toast.LENGTH_SHORT).show(); */

    }

}