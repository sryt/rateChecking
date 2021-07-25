package com.example.grocerieslist.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.grocerieslist.R;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

/**
 * Created by Tejaswi on 23/07/21.
 */
public class SettingsFragment extends Fragment {

    AppGlobal appGlobal;
    TextInputLayout importData,exportData;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settingslayout, container, false);
        appGlobal = new AppGlobal(getActivity());

        importData = rootView.findViewById(R.id.sl_import_data);
        exportData = rootView.findViewById(R.id.sl_export_data);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        importData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertWindow(Constant.IMPORT);
            }
        });

        exportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertWindow(Constant.EXPORT);
            }
        });
    }

    /**
     *
     * @param cmd
     */
    public void alertWindow(String cmd){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.mipmap.logo);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
        String proPath = "";
        String custPath = "";
        String stockPath = "";

        if(cmd.equals(Constant.IMPORT)){
            builderSingle.setTitle(getActivity().getResources().getString(R.string.imposrtDataD));
            proPath = String.valueOf(appGlobal.getPreference(appGlobal.App_Product_Path));
            custPath = String.valueOf(appGlobal.getPreference(appGlobal.App_Customer_Path));
            stockPath = String.valueOf(appGlobal.getPreference(appGlobal.App_Stock_Path));
        }else if(cmd.equals(Constant.EXPORT)){
            builderSingle.setTitle(getActivity().getResources().getString(R.string.exportDataD));
            proPath = String.valueOf(appGlobal.getPreference(appGlobal.App_Product_Path_Lastest));
            custPath = String.valueOf(appGlobal.getPreference(appGlobal.App_Customer_Path_Lastest));
            stockPath = String.valueOf(appGlobal.getPreference(appGlobal.App_Stock_Path_Lastest));
        }

        if(!proPath.isEmpty() || !proPath.equals("null"))
            arrayAdapter.add(proPath);
        if(!custPath.isEmpty() || !custPath.equals("null"))
            arrayAdapter.add(custPath);
        if(!stockPath.isEmpty() || !stockPath.equals("null"))
            arrayAdapter.add(stockPath);
        builderSingle.setAdapter(arrayAdapter,null);
        builderSingle.show();
    }

}
