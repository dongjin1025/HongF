package com.dongjin.android.hongf.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.present.MapPresenter;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity{


    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private StoryFragment storyFragment;
    private StoreListFragment listFragment;
    private MyPageFragment myPageFragment;
    private ToDongFragment toDongFragment;
    private MapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();
        storyFragment = new StoryFragment();
        listFragment = new StoreListFragment();
        myPageFragment= new MyPageFragment();
        toDongFragment = new ToDongFragment();
        Button button =(Button)findViewById(R.id.button);



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
                        fragment = storyFragment;
                        break;
                    case 3:
                        fragment =myPageFragment;
                        break;
                    case 4:
                        fragment = toDongFragment;
                }


                return fragment;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.france);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Map")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("List")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Story")
                        .build()


        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Story")
                        .build()

        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Mon Page")
                        .build()

        );

        viewPager.addOnPageChangeListener(navigationTabBar);


        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setBackgroundColor(getResources().getColor(R.color.white));
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

}


