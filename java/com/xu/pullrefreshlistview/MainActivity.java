package com.xu.pullrefreshlistview;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xu.pullrefreshlistview.ui.RefreshListView;


public class MainActivity extends Activity {

    private RefreshListView listview;
    private ArrayList<String> listDatas;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        //在这里就调用构造方法，这样就能拿到其他类的引用了
        setContentView(R.layout.activity_main);

        listview = (RefreshListView) findViewById(R.id.listview);

        //设置监听的接口
        listview.setRefreshListener(new RefreshListView.OnRefreshListener() {

            //刷新的方法
            @Override
            public void onRefresh() {
                // 模拟加载数据
                new Thread(){
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        listDatas.add(0,"我是下拉刷新出来的数据!");

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                listview.onRefreshComplete();
                            }
                        });
                    };

                }.start();
            }

            //加载更多的方法
            @Override
            public void onLoadMore() {
                new Thread(){
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        listDatas.add("我是加载更多出来的数据!1");
                        listDatas.add("我是加载更多出来的数据!2");
                        listDatas.add("我是加载更多出来的数据!3");

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                listview.onRefreshComplete();
                            }
                        });
                    };

                }.start();
            }

        });

        //模式了30条数据
        listDatas = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            listDatas.add("这是一条ListView数据: " + i);
        }

        // 设置数据适配器
        adapter = new MyAdapter();
        listview.setAdapter(adapter);


    }

    //数据适配器
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listDatas.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(18f);
            textView.setText(listDatas.get(position));

            return textView;
        }

        @Override
        public Object getItem(int position) {
            return listDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}
