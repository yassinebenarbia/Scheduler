package com.example.scheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scheduler.databinding.FragmentListOfRowsBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass that
 * populates the RecyclerView.
 * Use the {@link ListOfRows#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfRows extends Fragment {
    // TODO: Rename parameter arguments, choose names that match \
    //  the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "UUID";

    // TODO : Rename and change types of parameters

    // TODO : populate the ListRow parameter
    private String mParam1;
    private String mParam2;
    private FragmentListOfRowsBinding binding;
    private ListRowAdapter adapter;
    private RecyclerView rv;
    public ListOfRows() {
        // Required empty public constructor
    }

    //populating the RecyclerView with cards
    private List<ListRow> popluate(){
        Date date1 = new Date();
        Date date2 = new Date();
        List<ListRow> listRows = new ArrayList<>();
        listRows.add(ListRow.newInstance(date1,5,"",""));
        listRows.add(ListRow.newInstance(date1,5,"",""));
        listRows.add(ListRow.newInstance(date1,5,"",""));
        listRows.add(ListRow.newInstance(date1,5,"",""));
        listRows.add(ListRow.newInstance(date1,5,"",""));
        listRows.add(ListRow.newInstance(date1,5,"",""));
        listRows.add(ListRow.newInstance(date2,5,"",""));
        return listRows;
    }

    /**
     * instantiating the list of rows/cart widgets
     *
     */
    // TODO : Add changing theme feature
    // ToDo : is it possible to pass object with bundle ?
    public static ListOfRows newInstance(String fragmentName, String fragmentUuid) {
        ListOfRows fragment = new ListOfRows();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fragmentName);
        // ToDo: add reference the the Cycle Object created
        Cycle cycle = new Cycle();
        args.putString(ARG_PARAM2, fragmentUuid);
//        args.putSerializable(cycle.getUuid().toString(), (Serializable) cycle);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        inflater.inflate(R.layout.fragment_list_of_rows, container, false);
//        ListOfRows.inflate()
//        inflater.inflate(inflater, container, false);

        binding = FragmentListOfRowsBinding.inflate(inflater, container, false);

        List<ListRow> listRows = popluate();

        adapter = new ListRowAdapter(getContext(), listRows);

        if(adapter.getItemCount()==0){
            Log.d("this is a BIG problem", "item count is zero !");
        }

        rv =  binding.scheduelsRecycler;

        rv.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        return  binding.getRoot();
    }
}