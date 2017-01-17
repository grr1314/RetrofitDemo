package com.fragment.admin.retrofitdemo.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fragment.admin.retrofitdemo.iview.iMovieView;
import com.fragment.admin.retrofitdemo.model.HttpResult;
import com.fragment.admin.retrofitdemo.model.MovieEntity;
import com.fragment.admin.retrofitdemo.R;
import com.fragment.admin.retrofitdemo.http.HttpMethods;
import com.fragment.admin.retrofitdemo.model.subscribers.ProgressSubscriber;
import com.fragment.admin.retrofitdemo.model.subscribers.SubscriberOnNextListener;
import com.fragment.admin.retrofitdemo.presenter.MoviePresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by admin on 2016/12/14.
 */
public class fragment extends Fragment implements iMovieView.View {
    private static final String TAG = "fragment";
    private static final String TAG_RESP = "response";
    private Button btn_search;
    private TextView tv;

    private RecyclerView movie_ls;
    private MyAdapter adapter;
    private List<MovieEntity> list = new ArrayList<>();
    private SubscriberOnNextListener getTopMoveOnNext;


    private iMovieView.Presenter presenter;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initListener();
        initViews(view);
        return view;
    }

    private void initListener() {
        getTopMoveOnNext = new SubscriberOnNextListener<List<MovieEntity>>() {

            @Override
            public void onNext(List<MovieEntity> movieEntities) {
                for (int i = 0; i < movieEntities.size(); i++) {
                    list.add(movieEntities.get(i));
                }
                adapter = new MyAdapter(list, getActivity());
                movie_ls.setAdapter(adapter);
            }

            @Override
            public void onStart() {
                showDialog();
            }

            @Override
            public void onCompleted() {
                dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();

            }
        };
    }

    private void initViews(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        movie_ls = (RecyclerView) view.findViewById(R.id.movie_ls);
        movie_ls.setLayoutManager(linearLayoutManager);
        //添加divider
        movie_ls.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        //初始化Loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        getMovie();
    }

    /**
     * 进行网络请求
     */
    private void getMovie() {
        new MoviePresenter(this, getActivity(), getTopMoveOnNext);
        presenter.start(0,10);

//        //豆瓣电影的url
//        String url = "https://api.douban.com/v2/movie/";
//        //配置Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        demoInterface demoInterface = retrofit.create(demoInterface.class);

//        Call<MovieEntity> repos = demoInterface.getTopMovie(0, 2);
//
//        repos.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                Log.e(TAG, "返回结果：" + response.body());
//                tv.setText(response.body().getSubjects().get(0)+"");
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                tv.setText("NetWork Error！Error Code：" + t.getMessage());
//            }
//        });


//        demoInterface.getTopMovie(0, 10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MovieEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        Toast.makeText(getActivity(), "已经完成网络操作", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(MovieEntity movieEntity) {
//                        list = movieEntity.getSubjects();
//                        adapter = new MyAdapter(list, getActivity());
//                        adapter.setRecyclerItemClickListener(new MyAdapter.RecyclerItemClickListener() {
//                            @Override
//                            public void OnItemClick(View v, int pos) {
//                                Toast.makeText(getActivity(), "pos:" + pos, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        movie_ls.setAdapter(adapter);
////                        adapter.notifyItemInserted(0);
//                        //                        MovieEntity.Subject subject = null;
////                        subject = movieEntity.getSubjects().get(1);
////                        StringBuffer cast = new StringBuffer();
////                        for (int i = 0; i < subject.getCasts().size(); i++) {
////                            cast.append(subject.getCasts().get(i).getName());
////                        }
////                        tv.setText("电影名称:" + subject.getTitle() + "(原名称：" + subject.getOriginal_title() + ")" + "\n" + "年份：" + subject.getYear() + "\n" + "演员表：" + cast.toString() + "\n" +
////                                "图片地址：" + subject.getImages().getMedium());
//                    }
//                });
//        method3();
//        method4();
    }


    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showDataInfo(Object t) {

    }

    @Override
    public void setPresenter(iMovieView.Presenter presenter) {
        this.presenter = presenter;
    }

//    private void method4() {
//        HttpMethods.getInstance().getTopMovie(new ProgressSubscriber(getTopMoveOnNext, getActivity()), 0, 10);
//    }

//    /**
//     * 步骤3 封装HttpMethods
//     */
//    private void method3() {
//        HttpMethods.getInstance().getTopMovie(new Subscriber<HttpResult<List<MovieEntity>>>() {
//            @Override
//            public void onCompleted() {
//                Toast.makeText(getActivity(), "已经完成网络操作", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNext(HttpResult<List<MovieEntity>> listHttpResult) {
//                Log.e(TAG_RESP, listHttpResult.getSubjects().size()+"");
//                for (int i=0;i<listHttpResult.getSubjects().size();i++)
//                {
//                    list.add(listHttpResult.getSubjects().get(i));
//                }
//                adapter =new MyAdapter(list,getActivity());
//                movie_ls.setAdapter(adapter);
//            }
//        }, 0, 10);
//    }

}
