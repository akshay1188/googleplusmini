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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.gpm.helper.GetCirclesTask;
import com.akshay.gpm.interfaces.onTaskCompleted;
import com.google.api.services.plusDomains.model.Circle;
import com.google.api.services.plusDomains.model.Person;
import com.loopj.android.image.SmartImageView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements AdapterView.OnItemClickListener, onTaskCompleted {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView img;
    TextView pName;

    View view;
    //ArrayList<String> circleNames = new ArrayList<String>();
    //List<Circle> circleList;

    // TODO: Rename and change types of parameters
    private int mParam1;

    //ListView l;
//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment CircleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(int position) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        System.out.println("reached here safely");
        Person p = MainActivity.p;
        System.out.println(p.toString());
        //String[] circles = {"Family", "Friends", "Acquaintances", "Other"};
        pName = (TextView) view.findViewById(R.id.nameView);
        pName.setText(p.getDisplayName());
        //String dtStart = "2010-10-15T09:27:37Z";
        //SimpleDateFormat  format = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss'Z'");
        if(p.getBirthday()!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            Date date;
            try {

                date = format.parse(p.getBirthday());
                System.out.println(date);


                TextView pBdate = (TextView) view.findViewById(R.id.textView);
                //pBdate.setText("BirthDay : "+ date.toString());
                pBdate.setText("BirthDay : " + p.getBirthday());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        TextView pGender = (TextView) view.findViewById(R.id.textView2);
        pGender.setText("Gender : "+p.getGender());

        SmartImageView myImage = (SmartImageView) view.findViewById(R.id.imageView);
        myImage.setImageUrl(p.getImage().getUrl());
//        switch (mParam1) {
//            case 0:
//                break;
//            case 1: {
//
////                circleNames.add("Family");
////                circleNames.add("Friends");
////                circleNames.add("Acquaintances");
////                circleNames.add("Other");
//
//                List<Circle> circles = null;
//                new GetCirclesTask(this).execute();
//
////
//            }
//            break;
//        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(this.getActivity(), CircleMembers.class);
//        Circle c = circleList.get(position);
//        intent.putExtra("circle_id",c.getId());
//        startActivity(intent);
    }

    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onTaskCompleted(Object o) {
//        circleList = (List<Circle>) o;
//        if(circleList == null) {
//            Toast.makeText(this.getActivity(), "No circles found!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        for (Circle circle : circleList) {
//            System.out.println(circle.getDisplayName());
//            Log.i("Circle name", circle.getId());
//            circleNames.add(circle.getDisplayName());
//        }
//
//        l = (ListView) view.findViewById(R.id.listView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, circleNames);
//        l.setAdapter(adapter);
//        l.setOnItemClickListener(this);

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
