package com.dongjin.android.hongf.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.R;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    private NavigationTabBar navigationTabBar;

    public MyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_page, container, false);
        navigationTabBar=(NavigationTabBar)view.findViewById(R.id.ntb_horizontal);
        initUi();
        return view;
    }

    private void initUi() {
        final String[] colors = getResources().getStringArray(R.array.france);


        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("BookMark")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("You've written")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("News")
                        .build()


        );

        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setModels(models);
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
