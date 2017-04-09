package com.dongjin.android.hongf.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.presenter.AddPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements Add_View {

    Button btnRegisterStore;
    Button btnPostReview;
    Button btnToDeveloper;
    AddPresenter presenter;
    LinearLayout linearLayout;
    private Button button2;
    private Button speeach;
    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        linearLayout.animate().translationY(-1000);
        button2.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add, container, false);
        presenter=new AddPresenter();
        presenter.attachView(this);

        linearLayout=(LinearLayout) view.findViewById(R.id.moving_lay);
        btnRegisterStore=(Button)view.findViewById(R.id.btnRegisterStore);
        btnPostReview=(Button)view.findViewById(R.id.btnPostReview);
        btnToDeveloper=(Button)view.findViewById(R.id.btnToDevelop);
        button2=(Button)view.findViewById(R.id.button2);
        speeach=(Button)view.findViewById(R.id.speech);

        linearLayout.animate().translationY(-1000);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.animate().translationY(0);
                button2.setVisibility(View.INVISIBLE);
            }
        });

        btnRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.navigateToSearch(getContext(),SearchActivity.class);

            }
        });
        btnPostReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.navigateToPostReview(getContext(),SearchForReviewActivity.class);
            }
        });
        btnToDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ToDeveloper.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
