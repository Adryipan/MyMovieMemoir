package com.example.mymoviemenoir.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.adapter.MemoirRecyclerViewAdapter;
import com.example.mymoviemenoir.model.MemoirResult;
import com.example.mymoviemenoir.neworkconnection.NetworkConnection;

import java.util.ArrayList;
import java.util.List;

public class MemoirFragment extends Fragment {

    private NetworkConnection networkConnection = null;
    private View view = null;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<MemoirResult> memoirs;
    private MemoirRecyclerViewAdapter adapter;



    public MemoirFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        this.view = inflater.inflate(R.layout.fragment_memoir, container, false);
        networkConnection = new NetworkConnection();
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("USERID", Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString("USERID", null);
        GetAllMemoirTask getAllMemoirTask = new GetAllMemoirTask();
        getAllMemoirTask.execute(uid);
        Toast.makeText(MemoirFragment.this.getContext(), "Loading....", Toast.LENGTH_LONG).show();
        return view;
    }

    private class GetAllMemoirTask extends AsyncTask<String, Void, List<MemoirResult>>{

        @Override
        protected List<MemoirResult> doInBackground(String... strings) {
            return networkConnection.getAllMemoir(strings[0]);
        }


        @Override
        protected void onPostExecute(List<MemoirResult> memoirResults) {
            memoirs = new ArrayList<MemoirResult>();
            memoirs = memoirResults;
            recyclerView = view.findViewById(R.id.myMemoirRV);
            adapter = new MemoirRecyclerViewAdapter(memoirs);
            recyclerView.addItemDecoration(new DividerItemDecoration(MemoirFragment.this.getContext(),
                    LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(MemoirFragment.this.getContext());
            recyclerView.setLayoutManager(layoutManager);

        }
    }
}
