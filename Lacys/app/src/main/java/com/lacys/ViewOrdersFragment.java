package com.lacys;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Christina on 3/23/2015.
 */


public class ViewOrdersFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();

        createOrderList();

    }

    private void createOrderList()
    {
        String[] shipToArray = {"Alice Manning", "Alice Manning", "Lisa Roberts"};


        ListAdapter ordersAdapter = new ArrayAdapter<String>(getActivity(), R.layout.view_orders_adapter,
                R.id.ship_to_name, shipToArray);

        ListView ordersListView = (ListView) getView().findViewById(R.id.orders_list_view);

        ordersListView.setAdapter(ordersAdapter);
    }

    public static ViewOrdersFragment newInstance()
    {
        ViewOrdersFragment fragment = new ViewOrdersFragment();
        return fragment;
    }

    public ViewOrdersFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_orders_fragment, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(1);
    }
}
