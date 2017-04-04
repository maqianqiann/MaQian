package com.share4.test.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.amiao.managlibary.dao.MangDao;
import com.share4.test.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/3/31.
 */

public class Fragment2 extends Fragment {

    private View view;
    private ArrayList<String> list=new ArrayList<>();
    private ArrayList<String> list1=new ArrayList<>();
    private MangDao dao;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f2_layout,null);
        dao = new MangDao(getActivity());
        for (int i = 0; i <10 ; i++) {

         //   dao.addMy("标题"+i);
        }
        for (int i = 0; i <10 ; i++) {

         //   dao.addMore("更多"+i);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final GridView grid_wo= (GridView) view.findViewById(R.id.grid_wo_f2);
        GridView grid_more= (GridView) view.findViewById(R.id.grid_more_f2);
        list=dao.query_My();
        list1=dao.query_More();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        grid_wo.setAdapter(adapter);
        adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list1);
        grid_more.setAdapter(adapter1);
        grid_wo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               grid_wo.setBackgroundColor(Color.GREEN);
                grid_wo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dao.getMoreTable(list,list1,list.get(position));
                        adapter.notifyDataSetChanged();
                        adapter1.notifyDataSetChanged();
                    }
                });

                return false;
            }
        });

        grid_more.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dao.getMyTable(list,list1,list1.get(position));
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
            }
        });

    }
}
