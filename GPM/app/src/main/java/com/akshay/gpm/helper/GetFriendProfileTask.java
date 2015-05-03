package com.akshay.gpm.helper;

import android.app.Activity;
import android.os.AsyncTask;

import com.akshay.gpm.FriendProfileActivity;
import com.akshay.gpm.helper.DataManager;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;

/**
 * Created by Krishna on 3/17/15.
 */
public class GetFriendProfileTask extends AsyncTask{

    private String id;
    Person p;
    FriendProfileActivity delegate;

    public GetFriendProfileTask(FriendProfileActivity friendProfileActivity, String id) {
        this.id = id;
        delegate = friendProfileActivity;
    }

//    public void GetFriendProfileTask(Activity activity,String id){
//        this.id = id;
//        delegate = (FriendProfileActivity) activity;
//    }
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            p = DataManager.getDataManagerInstance().getProfile(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

   @Override
    protected void onPostExecute(Object o)
   {
       this.delegate.onTaskCompleted(p);
   }
}
