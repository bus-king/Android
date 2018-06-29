package com.example.paeng.busking.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;

import com.example.paeng.busking.InnerDB.DBHelper;
import com.example.paeng.busking.ListViewContent.ListViewAdapter;
import com.example.paeng.busking.MainActivity;
import com.example.paeng.busking.R;
import com.example.paeng.busking.model.Res;
import com.example.paeng.busking.model.Show;
import com.example.paeng.busking.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.paeng.busking.Utils.ImageTransformation.rotateBitmap;

public class FragmentList extends Fragment {

    public static final String TITLE = "List";
    ListViewAdapter adapter;
    private ArrayList<Show> showArrayList;
    private Button btSortHeart, btSortDistance;
    private CompositeSubscription mSubscriptions;
    private ListView showListView;

    public static FragmentList newInstance() {

        return new FragmentList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_list, container, false);

        btSortDistance = (Button)mView.findViewById(R.id.bt_sort_distance);
        btSortHeart = (Button)mView.findViewById(R.id.bt_sort_heart);
        btSortDistance.setOnClickListener(listener);
        btSortHeart.setOnClickListener(listener);

        showArrayList = new ArrayList<Show>();
        adapter = new ListViewAdapter();
        mSubscriptions = new CompositeSubscription();

        loadShowInformation();

        //inner db

        /*
        final DBHelper dbHelper = new DBHelper(getActivity(), "Busking.db", null, 1);

        String[] showResult = dbHelper.getResultShowList();
        int id = 0;
        for (String showitem : showResult){
            if (showitem!=null) {
                String[] showItems = showitem.split("/");

                Show show1 = new Show();

                show1.setId(id);
                show1.setUserId(showItems[1]);
                show1.setShowName(showItems[2]);
                show1.setShowTitle(showItems[3]);
                show1.setShowLocation(showItems[4]);
                show1.setShowGenre(Integer.valueOf(showItems[5]));
                show1.setShowHeart(Integer.valueOf(showItems[6]));
                show1.setShowTime(showItems[7]);
                show1.setShowDescription(showItems[8]);

                showArrayList.add(show1);
                adapter.addItemShow(null, show1.getShowName(), show1.getShowTitle(), show1.getShowHeart(), show1.getShowHeart(), show1.getShowGenre());

                id++;
            }
        }
        */



        showListView = (ListView) mView.findViewById(R.id.lv_show);
        showListView.setAdapter(adapter);


        return mView;
    }

    private void loadShowInformation(){
        mSubscriptions.add(NetworkUtil.getRetrofit().getAllShow()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Show[] shows){

        for (Show showItem :shows){
            if(showItem != null){

                showArrayList.add(showItem);
                adapter.addItemShow(null, showItem.getShowName(), showItem.getShowTitle(), showItem.getShowHeart(), showItem.getShowHeart(), showItem.getShowGenre());
                showListView.setAdapter(adapter);
            }
        }

    }

    private void handleError(Throwable error) {

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody, com.example.paeng.busking.model.Res.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showSnackBarMessage("Network Error !");
        }
    }
    private void showSnackBarMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    public double getDistanceFromLocation (String location){

        //location 으로부터 distance 구하기
        double distance = 1.2;






        return distance;
    }

    Button.OnClickListener listener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_sort_distance:


                    break;


                case R.id.bt_sort_heart:


                    break;

            }
        }
    };



}