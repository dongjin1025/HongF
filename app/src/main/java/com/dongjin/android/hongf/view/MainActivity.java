package com.dongjin.android.hongf.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.dongjin.android.hongf.R;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity{


    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private StoryFragment storyFragment;
    private StoreListFragment listFragment;
    private MyPageFragment myPageFragment;
    private AddFragment addFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();
        storyFragment = new StoryFragment();
        listFragment = new StoreListFragment();
        myPageFragment= new MyPageFragment();
        addFragment = new AddFragment();



        initUi();



    }


    private void initUi() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);



        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = mapFragment;

                        break;
                    case 1:
                        fragment = listFragment;

                        break;
                    case 2:
                        fragment=addFragment;
                        break;
                    case 3:
                        fragment =storyFragment;

                        break;
                    case 4:
                        fragment = myPageFragment;

                }
                return fragment;
            }
        });
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablay);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.map_normal));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.list_normal));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.add_normal));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.review_normal));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.mypage_normal));

        final String[] colors = getResources().getStringArray(R.array.france);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.bottomicon1),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.bottomicon1))
                        .title("Map")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.botlist),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.botlist))
                        .title("List")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.bottomicon3),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.bottomicon3))
                        .title("Add")
                        .build()


        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.bottomicon6),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.bottomicon6))
                        .title("Story")
                        .build()

        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.bottomicon7),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.bottomicon7))
                        .title("Mon Page")
                        .build()

        );

        viewPager.addOnPageChangeListener(navigationTabBar);


        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setBackgroundColor(getResources().getColor(R.color.splash_backgroind));
        navigationTabBar.setIsBadged(false);



        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("종료");
        dialog.setMessage("정말 앱을 종료 하시겠습니까?");
        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finishAndRemoveTask();

            }
        });
        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        dialog.show();
    }
}


