package com.example.paeng.busking.Fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.paeng.busking.ListViewContent.ListViewAdapter;
import com.example.paeng.busking.R;
import com.example.paeng.busking.model.Show;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.paeng.busking.Utils.ImageTransformation.rotateBitmap;

public class FragmentList extends Fragment {

    public static final String TITLE = "List";
    ListViewAdapter adapter;
    private ArrayList<Show> showArrayList;

    public static FragmentList newInstance() {

        return new FragmentList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_list, container, false);

        showArrayList = new ArrayList<Show>();
        adapter = new ListViewAdapter();

/*

        for (Dog dogitem : dog){
            if(dogitem != null){

                //http://ec2-13-209-70-175.ap-northeast-2.compute.amazonaws.com:8080/api/v1/images/DLGFDFXE
                String url = Constants.BASE_URL + "images/" + dogitem.getDogId();

                dogArrayList.add(dogitem);
                adapter.addItemDog(url, dogitem.getName(), dogitem.getSpecies(), dogitem.getGender(), getAge(dogitem.getBirth()));
                dogNum += 1;
            }
        }
 */


        // data example
        Show show1 = new Show();
        Show show2 = new Show();

        show1.setId(1);
        show1.setUserId("nuggy875");
        show1.setShowDescription("나는 힙 합 밀당녀");
        show1.setShowGenre("hip hop");
        show1.setShowHeart(5);
        show1.setShowLocation("홍대 걷고싶은 거리 1번");
        show1.setShowName("김선희");
        show1.setShowTime("201807011900");
        show1.setShowTitle("관객을 뒤집을 힙합");

        show2.setId(2);
        show2.setUserId("hihi1234");
        show2.setShowDescription("감성 발라더");
        show2.setShowGenre("ballad");
        show2.setShowHeart(10);
        show2.setShowLocation("신촌");
        show2.setShowName("포스틱맨");
        show2.setShowTime("201807012100");
        show2.setShowTitle("신촌을 못가");



        showArrayList.add(show1);
        showArrayList.add(show2);
        adapter.addItemShow(null, show1.getShowName(), show1.getShowTitle(), show1.getShowHeart(), 1.2, show1.getShowGenre());
        adapter.addItemShow(null, show2.getShowName(), show2.getShowTitle(), show2.getShowHeart(), 0.8, show2.getShowGenre());

        ListView showListView = (ListView) mView.findViewById(R.id.lv_show);
        showListView.setAdapter(adapter);


        return mView;
    }


}