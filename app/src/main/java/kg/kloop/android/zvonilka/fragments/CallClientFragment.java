package kg.kloop.android.zvonilka.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import kg.kloop.android.zvonilka.R;
import kg.kloop.android.zvonilka.adapters.ClientsRecyclerViewAdapter;
import kg.kloop.android.zvonilka.objects.Client;


public class CallClientFragment extends Fragment {

    private static final int REQUEST_CODE_ADD_CLIENT = 101;
    private static final String TAG = CallClientFragment.class.getSimpleName();
    private RecyclerView clientsToCallRecyclerView;
    private ArrayList<Client> clientArrayList;
    private ClientsRecyclerViewAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private String currentCampaignId;
    private Query callQuery;

    public CallClientFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_client, container, false);

        clientsToCallRecyclerView = view.findViewById(R.id.clients_to_call_recycler_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentCampaignId = getActivity().getIntent().getStringExtra("currentCampaignId");
        callQuery = firebaseDatabase.getReference()
                .child("Companies")
                .child("TestCompany")
                .child("Campaigns")
                .child(currentCampaignId)
                .child("Clients")
                .orderByChild("category")
                .equalTo(3); // 3 == no category yet
        clientArrayList = new ArrayList<>();
        getDataFromFirebase();

        adapter = new ClientsRecyclerViewAdapter(getContext(), clientArrayList);
        clientsToCallRecyclerView.setAdapter(adapter);
        clientsToCallRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clientsToCallRecyclerView.setHasFixedSize(true);

        return view;
    }


    private void getDataFromFirebase() {
        callQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Client client = dataSnapshot.getValue(Client.class);
                Log.i(TAG, "onChildAdded: " + client.toString());
                if (!clientArrayList.contains(client)) {
                    Log.i(TAG, "onChildAdded: " + client.getName());
                    clientArrayList.add(0, client);
                    adapter.notifyItemInserted(0);
                    clientsToCallRecyclerView.scrollToPosition(0);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Client client = dataSnapshot.getValue(Client.class);
                clientArrayList.remove(client);
                int index = clientArrayList.indexOf(client);
                Log.i(TAG, "changed client index: " + index);
                Log.i(TAG, "changed item: " + clientArrayList.get(index).getName());
                adapter.notifyItemChanged(index);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
