package com.akshay.gpm;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.akshay.gpm.adapters.CircleMemberAdapter;
import com.akshay.gpm.dummy.DummyContent;
import com.akshay.gpm.helper.GetCircleMembersTask;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CircleMemberFragment extends android.support.v4.app.Fragment implements AbsListView.OnItemClickListener, onTaskCompleted {

    private OnFragmentInteractionListener mListener;

    View view;
    List<Person> people;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private CircleMemberAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static CircleMemberFragment newInstance(String param1, String param2) {
        CircleMemberFragment fragment = new CircleMemberFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circlemember, container, false);
        String cid = this.getActivity().getIntent().getExtras().getString("circle_id");
        new GetCircleMembersTask(this,cid).execute();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Option Clicked",""+id+" "+position);
        Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
        intent.putExtra("Id",people.get(position).getId());
        //intent.putExtra("Birthday",people.get(position).getBirthday());
        //intent.putExtra("Gender",people.get(position).getGender());
        //intent.putExtra("Url",people.get(position).getUrl());
        startActivity(intent);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onTaskCompleted(Object o) {
         people = (List<Person>)o;
        ArrayList<String> peopleNames = new ArrayList<String>();

        if(people.size() == 0) {
            Toast.makeText(this.getActivity(), "No members found!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Person p : people) {
            System.out.println(p.toString());
            Log.i("Circle name", p.getId());
            peopleNames.add(p.getDisplayName());
        }

        mAdapter = new CircleMemberAdapter(this.getActivity(), R.layout.adapter_member_circle, (ArrayList<Person>) people);


        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
