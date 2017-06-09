package com.example.android.newsbroadcast.news;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.newsbroadcast.R;
import com.example.android.newsbroadcast.data.ConstructSources;
import com.example.android.newsbroadcast.data.News;
import com.example.android.newsbroadcast.data.NewsSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private static final String TAG = NewsActivity.class.getName();

    private static String country;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<NewsSource> mNewsSourceList;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        country = "us";

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
//                MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new GeneralFragment(), getResources().getString(R.string.section_general));
        adapter.addFragment(new SportsFragment(), getResources().getString(R.string.section_sports));
        adapter.addFragment(new EntertainmentFragment(), getResources().getString(R.string.section_entertainment));
        adapter.addFragment(new BusinessFragment(), getResources().getString(R.string.section_business));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public List<NewsSource> getmNewsSourceList(String catagory) {
        if(ConstructSources.CATEGORY_GENERAL.equals(catagory)){
            mNewsSourceList = new ArrayList<>();
            mNewsSourceList = ConstructSources.getNewsSource(ConstructSources.COUNTRY_US, ConstructSources.CATEGORY_GENERAL);
        } else if(ConstructSources.CATEGORY_BUSINESS.equals(catagory)){
            mNewsSourceList = new ArrayList<>();
            mNewsSourceList = ConstructSources.getNewsSource(ConstructSources.COUNTRY_US, ConstructSources.CATEGORY_BUSINESS);
        } else if(ConstructSources.CATEGORY_ENTERTAINMENT.equals(catagory)){
            mNewsSourceList = new ArrayList<>();
            mNewsSourceList = ConstructSources.getNewsSource(ConstructSources.COUNTRY_US, ConstructSources.CATEGORY_ENTERTAINMENT);
        } else if(ConstructSources.CATEGORY_SPORTS.equals(catagory)){
            mNewsSourceList = new ArrayList<>();
            mNewsSourceList = ConstructSources.getNewsSource(ConstructSources.COUNTRY_US, ConstructSources.CATEGORY_SPORTS);
        }

        return mNewsSourceList;
    }

//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };

    public void openBrowser(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
