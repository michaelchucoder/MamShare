package com.babyspace.mamshare.app.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.adapter.SearchFlowAdapter;
import com.babyspace.mamshare.basement.BaseFragment;
import com.babyspace.mamshare.bean.FlowSearchItem;
import com.babyspace.mamshare.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecommendLabelFragment extends BaseFragment {

    //TODO 的有多中类型的item 为listview 或gridview的情况

    ListView mListView;


    public RecommendLabelFragment() {
        // Required empty public constructor
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recommend_label);
    }

    @Override
    public void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recommend_label, container, false);

        mListView = (ListView) rootView.findViewById(R.id.my_listview);
        // 设置Adapter
        mListView.setAdapter(new SearchFlowAdapter(getActivity(), mockData()));
        // 点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "listview position = " + position,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        return rootView;
    } // end of

    /**
     * 模拟一些数据
     *
     * @return
     */
    private List<FlowSearchItem> mockData() {
        List<FlowSearchItem> items = new ArrayList<FlowSearchItem>();
        FlowSearchItem item = new FlowSearchItem();
        item.mText = "111";
        item.data.add(new TestBean("呵呵1"));
        item.data.add(new TestBean("呵呵2"));
        item.data.add(new TestBean("呵呵2"));
        item.data.add(new TestBean("呵呵1"));
        item.data.add(new TestBean("呵呵3"));
        item.data.add(new TestBean("呵呵1"));
        item.data.add(new TestBean("呵呵1"));
        item.data.add(new TestBean("呵呵2"));
        item.data.add(new TestBean("呵呵2"));
        item.data.add(new TestBean("呵呵1"));
        item.data.add(new TestBean("呵呵3"));
        item.data.add(new TestBean("呵呵1"));
        items.add(item);

        item = new FlowSearchItem();
        item.mText = "222";
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));
        item.data.add(new TestBean("哈哈1"));
        item.data.add(new TestBean("哈哈2"));

        items.add(item);

        return items;
    }

}
