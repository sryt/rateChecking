package com.example.grocerieslist.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.grocerieslist.R;
import com.example.grocerieslist.adapter.listadapter.CompanyAdapter;
import com.example.grocerieslist.db.company.CompanyAccess;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.utilities.AppGlobal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 24/07/21.
 */
public class CompanyFragment extends Fragment {
    String TAG = CompanyFragment.class.getSimpleName();

    AppGlobal appGlobal;
    ListView lv;
    SearchView search;

    CompanyAccess ca;
    List<CompanyClass> ccs;

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        appGlobal = new AppGlobal(getActivity());
        ca = new CompanyAccess(getActivity());
        lv = rootView.findViewById(R.id.listview);
        search = rootView.findViewById(R.id.search);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ca.open();
        ccs = ca.getCompaniesDetails();
        ca.close();

        Log.i(TAG,"Customer details size "+ccs.size());
        if(ccs.size() > 0){
            adapter = new CompanyAdapter(getActivity(),R.layout.custlayout,ccs);
            adapter.sort(new Comparator<CompanyClass>() {
                @Override
                public int compare(CompanyClass s, CompanyClass t1) {
                    return s.getCompanyName().compareTo(t1.getCompanyName());
                }
            });
            lv.setAdapter(adapter);
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchFlow(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchFlow(s);
                return false;
            }
        });
    }

    boolean filterFlag = false;
    List<String> proNameFilter;
    List<CompanyClass> pcFilter;
    CompanyAdapter adapter;

    public void searchFlow(String s){
        filterFlag = true;
        pcFilter = new ArrayList<>();
        proNameFilter = new ArrayList<>();
        for(CompanyClass pc : ccs){
            if(pc.getCompanyName().toLowerCase().contains(s.toLowerCase())){
                pcFilter.add(pc);
                proNameFilter.add(pc.getCompanyName());
            }
        }

        adapter = new CompanyAdapter(getActivity(),R.layout.custlayout,pcFilter);
        adapter.sort(new Comparator<CompanyClass>() {
            @Override
            public int compare(CompanyClass lhs, CompanyClass rhs) {
                return lhs.getCompanyName().compareTo(rhs.getCompanyName());
            }
        });
        lv.setAdapter(adapter);
    }

}
