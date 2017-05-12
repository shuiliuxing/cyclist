package com.huabing.cyclist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huabing.cyclist.adapter.News;
import com.huabing.cyclist.adapter.NewsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 30781 on 2017/3/26.
 */

public class NewsFragment extends Fragment{

    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvRecycler;
    private List<News> newsList;
    private NewsAdapter adapter;
    private String[] address;
    private int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.frag_news,container,false);
        address=new String[]{"http://www.imbiker.cn/hy/?page=1","http://www.imbiker.cn/news/?page=1",
                "http://www.imbiker.cn/pingce/?page=1","http://www.imbiker.cn/zs/?page=1",
                "http://www.imbiker.cn/race/?page=1","http://www.imbiker.cn/qiji/?page=1"
        };
        page=0;

        srlRefresh=(SwipeRefreshLayout)view.findViewById(R.id.srl_refresh);
        srlRefresh.setColorSchemeResources(R.color.colorGreen);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                if(page<6)
                {
                    MyTask task = new MyTask();
                    task.execute(address[page]);
                }
                srlRefresh.setRefreshing(false);
            }
        });

        newsList=new ArrayList<>();
        MyTask task=new MyTask();
        task.execute(address[page]);

        rvRecycler=(RecyclerView)view.findViewById(R.id.rv_recycler);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rvRecycler.setLayoutManager(manager);
        adapter=new NewsAdapter(newsList);
        rvRecycler.setAdapter(adapter);
        return view;
    }

    //静态工厂方法
    public static NewsFragment newInstance(String title)
    {
        NewsFragment fragment=new NewsFragment();
        return fragment;
    }

    //异步任务
    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            UpdateData(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            adapter.notifyDataSetChanged();
        }
    }

    //根据url加载新闻热点
    private void UpdateData(String url)
    {
        ArrayList<String> nextList=new ArrayList<>();   //下页
        ArrayList<String> titleList=new ArrayList<>();  //标题
        ArrayList<String> imageList=new ArrayList<>();  //图片
        ArrayList<String> timeList=new ArrayList<>();   //时间

        try
        {
            Document doc= Jsoup.connect(url).get();
            //下页、标题
            Elements nextEles=doc.select("p a[href*=news/show]");
            for(Element element:nextEles)
            {
                String path="http://www.imbiker.cn"+element.attr("href");
                nextList.add(path);
                titleList.add(element.attr("title"));
            }
            //图片
            Elements imageEles=doc.select("img[src$=c130x90.jpg]");
            for(Element element:imageEles)
                imageList.add(element.attr("src"));
            //时间
            Elements timeEles=doc.select(".news_hotlu em");
            for(Element element:timeEles)
                timeList.add(element.text());

            //所有赋值给newList
            //newsList.clear();
            int length=nextList.size();
            for(int i=0;i<length;i++)
            {
                News news=new News(nextList.get(i),titleList.get(i),imageList.get(i),timeList.get(i));
                newsList.add(0,news);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
