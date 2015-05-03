package com.akshay.gpm;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akshay.gpm.helper.DataManager;
import com.akshay.gpm.helper.GetUsernameTask;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, onTaskCompleted {

    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    public static String mEmail;
    Button signInButton;
    public static Person p;
    private final static String PROFILE_ME = "https://www.googleapis.com/auth/plus.me";
    private final static String CIRCLE_SCOPE = "https://www.googleapis.com/auth/plus.circles.read";
    private final static String PROFILE_OTHER = "https://www.googleapis.com/auth/plus.profiles.read";

    static final String SCOPE =
            "oauth2:" + PROFILE_ME + " " + CIRCLE_SCOPE + " " + PROFILE_OTHER;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;


    /**
     * Attempts to retrieve the username.
     * If the account is not yet known, invoke the picker. Once the account is known,
     * start an instance of the AsyncTask to get the auth token and do work with it.
     */
    private void getUsername() {
        if (mEmail == null) {
            pickUserAccount();
        } else {
//            if (isDeviceOnline()) {
                new GetUsernameTask(MainActivity.this, mEmail, SCOPE).execute();
//            } else {
//                Toast.makeText(this, R.string.not_online, Toast.LENGTH_LONG).show();
//            }
        }
    }

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult","resultCode "+resultCode+" requestCode "+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth tokens
                Log.i("result OK","email "+mEmail);
                getUsername();
            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, "Please pick an account", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate","in oncreate");
        setContentView(R.layout.activity_main);
        signInButton = (Button)findViewById(R.id.sign_in);
        signInButton.setOnClickListener(this);
        setTitle("Login");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                pickUserAccount();
//                Intent intent = new Intent(this, TabActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * This method is a hook for background threads and async tasks that need to
     * provide the user a response UI when an exception occurs.
     */
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
                            MainActivity.this,
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

    @Override
    public void onTaskCompleted() {
        System.out.println("Task Completed");
        try {
            this.p = DataManager.getDataManagerInstance().getProfile("me");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);


//        try {
//            this.p = DataManager.getDataManagerInstance().getProfile("me");
//            System.out.println(""+p.getDisplayName());
//
//            List<Circle> circles = DataManager.getDataManagerInstance().getCircles();
//            for (Circle circle : circles) {
//                System.out.println(circle.getDisplayName());
//                Log.i("Circle name", circle.getId());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onTaskCompleted(Object o) {

    }
}
