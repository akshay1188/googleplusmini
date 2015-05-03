package com.akshay.gpm.helper;

import android.os.AsyncTask;

import com.akshay.gpm.CircleFragment;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.api.services.plusDomains.model.Circle;

import java.io.IOException;
import java.util.List;

/**
 * Created by akshay on 3/15/15.
 */
public class GetCirclesTask extends AsyncTask {

    private onTaskCompleted delegate;

    public GetCirclesTask(CircleFragment fragment){
        this.delegate = fragment;
    }
    @Override
    protected List<Circle> doInBackground(Object[] params) {
        List<Circle> circles = null;
        try {
            System.out.println("========");
            circles = DataManager.getDataManagerInstance().getCircles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return circles;
    }

    @Override
    protected void onPostExecute(Object o) {
        this.delegate.onTaskCompleted(o);
    }

}
