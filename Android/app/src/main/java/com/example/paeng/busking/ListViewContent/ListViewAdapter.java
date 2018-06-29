package com.example.paeng.busking.ListViewContent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paeng.busking.R;

import java.util.ArrayList;


/**
 * Created by paeng on 2018. 4. 8..
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_item, parent, false);

        RelativeLayout layoutGenre = (RelativeLayout) convertView.findViewById(R.id.item_genre);
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.item_img);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.item_name);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.item_title);
        TextView itemHeart = (TextView) convertView.findViewById(R.id.item_heart);
        TextView itemDistance = (TextView) convertView.findViewById(R.id.item_distance);

        ListViewItem listViewItem = listViewItemList.get(position);

        String iconUrl = listViewItem.getIconUrl();
        int genre = listViewItem.getGenre();


        // Coloring for Genre
        if (genre == 1){
            layoutGenre.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
        }else if (genre == 2){
            layoutGenre.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
        }


        // image example
        if (iconUrl == null && genre==1){
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_hiphop);
            showImageTest(bm, iconImageView);
        }
        else if (iconUrl == null && genre==2){
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_hs);
            showImageTest(bm, iconImageView);
        }


        nameTextView.setText(listViewItem.getName());
        titleTextView.setText(listViewItem.getTitle());
        itemHeart.setText(String.valueOf(listViewItem.getHeart()));
        itemDistance.setText(String.valueOf(listViewItem.getDistance()));

        return convertView;
    }
    public void addItemShow(String iconUrl, String name, String title, int heart, double distance, int genre) {
        ListViewItem item = new ListViewItem();

        item.setIconUrl(iconUrl);
        item.setName(name);
        item.setTitle(title);
        item.setHeart(heart);
        item.setDistance(distance);
        item.setGenre(genre);
        listViewItemList.add(item);
    }


    private void showImageTest(Bitmap orgImage, ImageView imgView){
        Bitmap resize = Bitmap.createScaledBitmap(orgImage, 300, 300, true);
        // image rotation

        imgView.setImageBitmap(resize);
        imgView.setBackground(null);

    }
}
