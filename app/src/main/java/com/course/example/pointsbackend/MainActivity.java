package com.course.example.pointsbackend;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.course.example.pointsbackend.points.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        text = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String name = text.getText().toString().trim();

                new AsyncName().execute(new Pair<Context, String>(MainActivity.this, name), new Pair<Context, String>(MainActivity.this, "echo"));
                new AsyncName().execute(new Pair<Context, String>(MainActivity.this, name), new Pair<Context, String>(MainActivity.this, "echorev"));
                new AsyncName().execute(new Pair<Context, String>(MainActivity.this, name), new Pair<Context, String>(MainActivity.this, "length"));
                new AsyncName().execute(new Pair<Context, String>(MainActivity.this, name), new Pair<Context, String>(MainActivity.this, "palindrome"));
                new AsyncName().execute(new Pair<Context, String>(MainActivity.this, name), new Pair<Context, String>(MainActivity.this, "piglatin"));

                text.setText("");

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    //AsyncTask to work with backend points.
    //Remember that an AsyncTask instance may only be executed once.
    class AsyncName extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;
        private String operation;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {  // Only do this once

                //using localhost IP address
         /*   MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            */
                //using AppEngine
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://xenon-sentry-130523.appspot.com/_ah/api/");

                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;        //get context
            String name = params[0].second;  //get word
            operation = params[1].second;    //get operation

            
            try {  //execute operation

                switch (operation) {
                    case "echo":
                        return myApiService.echo(name).execute().getData();

                    case "echorev":
                        return myApiService.echoRev(name).execute().getData();

                    case "length":
                        return myApiService.howLong(name).execute().getLength().toString();

                    case "palindrome":
                        return myApiService.isPalindrome(name).execute().getPalindrome().toString();

                    case "piglatin":
                        return myApiService.pigLatin(name).execute().getData();
                }
            } catch (IOException e) {
                return e.getMessage();
            }
            return "never";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            //  Toast.makeText(context, operation, Toast.LENGTH_LONG).show();
        }
    }


}
