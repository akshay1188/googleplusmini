package com.akshay.gpm.helper;

import android.os.AsyncTask;

import com.akshay.gpm.CircleMemberFragment;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.api.services.plusDomains.model.Person;

import java.io.IOException;
import java.util.List;

/**
 * Created by akshay on 3/16/15.
 */
public class GetCircleMembersTask extends AsyncTask {
    private onTaskCompleted delegate;
    private String circleId;

    public GetCircleMembersTask(CircleMemberFragment fragment, String cid){
        this.delegate = fragment;
        this.circleId = cid;
    }
    @Override
    protected List<Person> doInBackground(Object[] params) {
        List<Person> people = null;
        try {
            System.out.println("========");
            people = DataManager.getDataManagerInstance().getPeopleInCircle(this.circleId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    protected void onPostExecute(Object o) {
        this.delegate.onTaskCompleted(o);
    }

}
