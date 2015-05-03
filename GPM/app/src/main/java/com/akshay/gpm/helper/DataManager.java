package com.akshay.gpm.helper;

import android.util.Log;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.api.services.plusDomains.model.CircleFeed;
import com.google.api.services.plusDomains.model.PeopleFeed;
import com.google.api.services.plusDomains.model.Person;
import com.google.api.services.plusDomains.model.Circle;

import java.io.IOException;
import java.util.List;

/**
 * Created by akshay on 3/13/15.
 */
public class DataManager {

    private static String token;

    private static DataManager theDataManagerInstance;

    private static GoogleCredential credential;
    private static PlusDomains plusDomains;

    private DataManager(){

    }

    public static void setToken(String token){
        if(theDataManagerInstance == null){
            theDataManagerInstance = new DataManager();
        }
        theDataManagerInstance.token = token;
        credential = new GoogleCredential().setAccessToken(token);
        plusDomains = new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("gpm").build();
    }

    public static DataManager getDataManagerInstance(){
        if(theDataManagerInstance == null){
            theDataManagerInstance = new DataManager();
        }
        System.out.print("KKKKKKK"+theDataManagerInstance.toString());
        return theDataManagerInstance;
    }

    public Person getProfile(String profileId) throws IOException {
        return plusDomains.people().get(profileId).execute();
    }

    public List<Circle> getCircles() throws IOException {
        PlusDomains.Circles.List listCircles = plusDomains.circles().list("me");
        listCircles.setMaxResults(5L);
        CircleFeed circleFeed = listCircles.execute();
        List<Circle> circles = circleFeed.getItems();
        return circles;
    }

    public List<Person> getPeopleInCircle(String circleId) throws IOException {
        PlusDomains.People.ListByCircle listPeople = plusDomains.people().listByCircle(circleId);
        listPeople.setMaxResults(100L);
        PeopleFeed peopleFeed = listPeople.execute();
        return peopleFeed.getItems();
    }
}
