package com.akshay.gpm;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akshay.gpm.helper.DataManager;
import com.akshay.gpm.helper.GetFriendProfileTask;
import com.akshay.gpm.helper.GetFriendUserProfileTask;
import com.akshay.gpm.helper.GetUsernameTask;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.services.plusDomains.model.Person;
import com.loopj.android.image.SmartImageView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FriendProfileActivity extends ActionBarActivity implements onTaskCompleted {

    public Person p;
    String id;
    public TextView pName,pBdate,pGender;
    //View view;
    SmartImageView myImage;
    Button emailButton;

    public static final int  REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        pBdate = (TextView) findViewById(R.id.textView);
        pName = (TextView) findViewById(R.id.nameView);
        pGender = (TextView) findViewById(R.id.textView2);
        myImage = (SmartImageView) findViewById(R.id.imageView);
        emailButton = (Button) findViewById(R.id.email_button);

        Bundle extras = getIntent().getExtras();
        id = extras.getString("Id");
        new GetFriendUserProfileTask(this,MainActivity.mEmail,MainActivity.SCOPE,id).execute();
        //new GetFriendProfileTask(this,id).execute();

        setTitle("Profile");

    emailButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            startActivity(emailIntent);
        }
    });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_profile, menu);
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

    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onTaskCompleted(Object o) {

//            view = inflater.inflate(R.layout.acticity_friend_profile, container, false);
            Person p = (Person)o;
        if (p == null) {
            return;
        }

            pName.setText(p.getDisplayName());
            //String dtStart = "2010-10-15T09:27:37Z";
            //SimpleDateFormat  format = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss'Z'");
            if(p.getBirthday()!=null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                Date date;
                try {

                    date = format.parse(p.getBirthday());
                    System.out.println(date);



                    //pBdate.setText("BirthDay : "+ date.toString());
//                    pBdate.setText("BirthDay : " + p.getBirthday());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            pGender.setText("Gender : "+p.getGender());


            myImage.setImageUrl(p.getImage().getUrl());


    }

    public void handleException(final Exception e) {
        // Because this call comes from the AsyncTask, we must ensure that the following
        // code instead executes on the UI thread.
        runOnUiThread(new Runnable() {


            @Override
            public void run() {
                if (e instanceof GooglePlayServicesAvailabilityException) {
                    // The Google Play services APK is old, disabled, or not present.
                    // Show a dialog created by Google Play services that allows
                    // the user to update the APK
                    int statusCode = ((GooglePlayServicesAvailabilityException)e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                            FriendProfileActivity.this,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException) {
                    // Unable to authenticate, such as when the user has not yet granted
                    // the app access to the account, but the user can fix this.
                    // Forward the user to an activity in Google Play services.
                    Intent intent = ((UserRecoverableAuthException)e).getIntent();
                    startActivityForResult(intent,
                            REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }
            }
        });
    }
}
