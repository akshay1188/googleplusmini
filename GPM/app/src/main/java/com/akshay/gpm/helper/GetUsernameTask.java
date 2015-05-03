package com.akshay.gpm.helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;
import java.util.List;

import com.akshay.gpm.MainActivity;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.PeopleFeed;
import com.google.api.services.plusDomains.model.Person;
import com.google.api.services.plusDomains.model.CircleFeed;


/**
 * Created by akshay on 3/11/15.
 */
public class GetUsernameTask extends AsyncTask{
    MainActivity mActivity;
    String mScope;
    String mEmail;

    private onTaskCompleted delegate;

    public GetUsernameTask(Activity activity, String name, String scope) {
        this.mActivity = (MainActivity)activity;
        this.delegate = this.mActivity;
        this.mScope = scope;
        this.mEmail = name;
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (UserRecoverableAuthException userRecoverableException) {
            // GooglePlayServices.apk is either old, disabled, or not present
            // so we need to show the user some UI in the activity to recover.
            mActivity.handleException(userRecoverableException);
            Log.i("fetchToken","userRecoverableException "+userRecoverableException);
        } catch (GoogleAuthException fatalException) {
            // Some other type of unrecoverable exception has occurred.
            // Report and log the error as appropriate for your app.
            Log.i("fetchToken","fatalException "+fatalException);
        }
        return null;
    }

    /**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            String token = fetchToken();

            if (token != null) {
                DataManager.setToken(token);
                this.delegate.onTaskCompleted();
                /*
                // Insert the good stuff here.
                // Use the token to access the user's Google data.
                Log.i("doInBackground","token "+token);
                GoogleCredential credential = new GoogleCredential().setAccessToken(token);

                PlusDomains plusDomains = new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("gpm").build();
                Log.i("doInBackground","here");
                Person person = plusDomains.people().get("me").execute();
                Log.i("doInBackground","person "+person.getDisplayName());

                PlusDomains.Circles.List listCircles = plusDomains.circles().list("me");
                listCircles.setMaxResults(5L);
                CircleFeed circleFeed = listCircles.execute();
                List<Circle> circles = circleFeed.getItems();

                // Loop until no additional pages of results are available.
                    for (Circle circle : circles) {
                        System.out.println(circle.getDisplayName());
                        Log.i("Circle name",circle.getId());

                        PlusDomains.People.ListByCircle listPeople = plusDomains.people().listByCircle(circle.getId());
                        listPeople.setMaxResults(100L);

                        PeopleFeed peopleFeed = listPeople.execute();

                        if(peopleFeed.getItems() != null && peopleFeed.getItems().size() > 0 ) {
                            for(Person personInCircle : peopleFeed.getItems()) {
                                System.out.println("\t" + personInCircle.getDisplayName());
                                Person otherPerson = plusDomains.people().get(personInCircle.getId()).execute();
                                Log.i("doInBackground","other person"+otherPerson.getDisplayName());
                            }
                        }
                    }*/
            }
        } catch (IOException e) {
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.
            Log.i("doInBackground","exception "+e);
        }
        return null;
    }
}
