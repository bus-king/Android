package com.example.paeng.busking.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.paeng.busking.Logics.PositionLogic;
import com.example.paeng.busking.MainActivity;
import com.example.paeng.busking.MapApiConst;
import com.example.paeng.busking.R;
import com.example.paeng.busking.Services.GpsInfo;
import com.example.paeng.busking.model.Res;
import com.example.paeng.busking.model.Show;
import com.example.paeng.busking.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class FragmentMap extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener{
    private static final int MENU_MAP_TYPE = Menu.FIRST + 1;
    private static final int MENU_MAP_MOVE = Menu.FIRST + 2;

    private boolean wait_for_server = false;
    private Context mContext;
    GpsInfo gpsInfo;
    private List<Show> show_list = new ArrayList<>();
    protected LocationManager locationManager;

    private static final String LOG_TAG = "MapViewDemoActivity";
    public static final String TITLE = "Map";
    private CompositeSubscription mSubscriptions;


    private MapView mMapView;
    MapPOIItem marker;

    public static FragmentMap newInstance() {
        return new FragmentMap();
    }
    private void requestNecessaryPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        double lat = 0;
        double lon = 0;

        View mView = inflater.inflate(R.layout.fragment_map, container, false);
        String[] PERMISSIONS = {"android.permission.INTERNET",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCEESS_FINE_LOCATION"
        };
        mSubscriptions = new CompositeSubscription();
        loadShowInformation();


        requestNecessaryPermissions(PERMISSIONS);
        gpsInfo = new GpsInfo(getContext());


        MapLayout mapLayout = new MapLayout(getActivity());
        mMapView = mapLayout.getMapView();

        mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setOpenAPIKeyAuthenticationResultListener(this);
        mMapView.setMapViewEventListener(this);
        mMapView.setPOIItemEventListener(this);
        mMapView.setMapType(MapView.MapType.Standard);

        ViewGroup mapViewContainer = (ViewGroup) mView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapLayout);
        marker = new MapPOIItem();
//        for(Show show: show_list) {
//            PositionLogic position_logic = new PositionLogic(show.getShowLocation());
//            position_logic.load_data(lat, lon);
//            Log.e("lat, lon", lat + " " + lon + " ");
//            marker.setItemName("title: " + show.getShowTitle() + '\n' + "name: " + show.getShowName() + '\n');
//            marker.setTag(0);
//            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
//            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
//            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//            Log.e("marker", marker.toString());
//            mMapView.addPOIItem(marker);
//        }

        return mView;
    }

    private void loadShowInformation(){
        mSubscriptions.add(NetworkUtil.getRetrofit().getAllShow()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Show[] shows){

        double lat = 37;
        double lon = 127;
        String genre = new String();

        for (Show show:shows){


            if(show != null){
                Log.e("test(location)", show.getShowLocation());
                if(show.getShowLocation().equals("홍대")) {
                    Random oRandom = new Random();
                    lat = oRandom.nextDouble() + 37.0;
                    lon = oRandom.nextDouble() + 127.0;
                }
                //PositionLogic position_logic = new PositionLogic(show.getShowLocation());
                Log.e("test", "one");
                //position_logic.load_data(lat, lon);
                Log.e("lat, lon", lat + " " + lon + " ");
               switch(show.getShowGenre()) {
                    case 1:
                        genre = "노래";
                        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        break;
                    case 2:
                        genre = "춤";
                        marker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        break;
                    case 3:
                        genre = "퍼포먼스";
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        break;
                    case 4:
                        genre = "기타";
                        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        break;
                     default:
                         Log.e("error", "genre should be 1 ~ 4");

                }
                marker.setItemName("title: " + show.getShowTitle() + '\n' + "name: " + show.getShowName() + '\n' + "genre: " + genre + "time: " + show.getShowTime());
                marker.setTag(0);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                Log.e("marker", marker.toString());
                mMapView.addPOIItem(marker);
            }
        }



        Log.e("before", "show list");
//        for(Show show: show_list) {
//            PositionLogic position_logic = new PositionLogic(show.getShowLocation());
//            position_logic.load_data(lat, lon);
//            Log.e("lat, lon", lat + " " + lon + " ");
//            marker.setItemName("title: " + show.getShowTitle() + '\n' + "name: " + show.getShowName() + '\n');
//            marker.setTag(0);
//            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
//            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.mapViewContainer.addView(mapLayout);
//            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//            Log.e("marker", marker.toString());
//            mMapView.addPOIItem(marker);
//        }
        Log.e("marker", marker.toString());
        Log.e("show size", show_list.size()+"");

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
    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int resultCode, String resultMessage) {
        Log.i(LOG_TAG,	String.format("Open API Key Authentication Result : code=%d, message=%s", resultCode, resultMessage));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // net.daum.mf.map.api.MapView.MapViewEventListener

    public void onMapViewInitialized(MapView mapView) {
        //Log.i(LOG_TAG, "MapView had loaded. Now, MapView APIs could be called safely");
        //mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
         mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(gpsInfo.getLatitude(),gpsInfo.getLongitude()), 2, true);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapCenterPoint.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
            /*
            MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("DaumMapLibrarySample");
            alertDialog.setMessage(String.format("Double-Tap on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
            */
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
            /*
            MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("DaumMapLibrarySample");
            alertDialog.setMessage(String.format("Long-Press on (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
            */
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        //Log.i(LOG_TAG, String.format("MapView onMapViewSingleTapped (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        //Log.i(LOG_TAG, String.format("MapView onMapViewDragStarted (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        //Log.i(LOG_TAG, String.format("MapView onMapViewDragEnded (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        //Log.i(LOG_TAG, String.format("MapView onMapViewMoveFinished (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {
        //Log.i(LOG_TAG, String.format("MapView onMapViewZoomLevelChanged (%d)", zoomLevel));
    }

    //    @Override
    //    public void onCurrentLocationUpdate(MapView mapView, final MapPoint mapPoint, float v) {
    //
    //        Toast.makeText(getContext(), "내가 있는 곳이 바로 내 구역이지!!", Toast.LENGTH_SHORT).show();
    //        Log.e("position", "update");
    //
    //        MapPOIItem marker = new MapPOIItem();
    //        marker.setItemName("First");
    //        marker.setTag(0);
    //        marker.setMapPoint(mapPoint);
    //
    //        //marker.setCustomImageResourceId(R.drawable.marker_cus);
    //        marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
    //        marker.setCustomImageAnchor(0.5f, 0.5f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
    //        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
    //        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
    //
    ////                mMapView.setShowCurrentLocationMarker(true);


    //
    //
    //   }
    //    @Override
    //    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
    //
    //    }
    //
    //    @Override
    //    public void onCurrentLocationUpdateFailed(MapView mapView) {
    //        Log.e("update", "fail");
    //    }
    //
    //    @Override
    //    public void onCurrentLocationUpdateCancelled(MapView mapView) {
    //
    //    }


    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

}