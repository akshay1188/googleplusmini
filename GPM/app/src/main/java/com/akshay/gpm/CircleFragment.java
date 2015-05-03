package com.akshay.gpm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.akshay.gpm.helper.GetCirclesTask;
import com.google.api.services.plusDomains.model.Circle;

import java.util.ArrayList;
import java.util.List;

import com.akshay.gpm.interfaces.onTaskCompleted;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CircleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CircleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CircleFragment extends Fragment implements AdapterView.OnItemClickListener, onTaskCompleted {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    ArrayList<String> circleNames = new ArrayList<String>();
    List<Circle> circleList;

    // TODO: Rename and change types of parameters
    private int mParam1;

    ListView l;
//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment CircleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CircleFragment newInstance(int position) {
        CircleFragment fragment = new CircleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    public CircleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_circle, container, false);

        switch (mParam1) {
            case 0:
                break;
            case 1: {

//                circleNames.add("Family");
//                circleNames.add("Friends");
//                circleNames.add("Acquaintances");
//                circleNames.add("Other");

                List<Circle> circles = null;
                new GetCirclesTask(this).execute();

//
            }
            break;
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getActivity(), CircleMembers.class);
        Circle c = circleList.get(position);
        intent.putExtra("circle_id",c.getId());
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onTaskCompleted(Object o) {
        circleList = (List<Circle>) o;
        if(circleList == null) {
            Toast.makeText(this.getActivity(), "No circles found!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Circle circle : circleList) {
            System.out.println(circle.getDisplayName());
            Log.i("Circle name", circle.getId());
            circleNames.add(circle.getDisplayName());
        }

        l = (ListView) view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, circleNames);
        l.setAdapter(adapter);
        l.setOnItemClickListener(this);

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
