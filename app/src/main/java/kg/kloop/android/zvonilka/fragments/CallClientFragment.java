package kg.kloop.android.zvonilka.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.kloop.android.zvonilka.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallClientFragment extends Fragment {


    public CallClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call_client, container, false);
    }

}
