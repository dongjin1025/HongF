package com.dongjin.android.hongf.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add, container, false);
        presenter=new AddPresenter();
        presenter.attachView(this);

        btnRegisterStore=(Button)view.findViewById(R.id.btnRegisterStore);
        btnPostReview=(Button)view.findViewById(R.id.btnPostReview);
        btnToDeveloper=(Button)view.findViewById(R.id.btnToDevelop);

        btnRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.navigateToSearch(getContext(),SearchActivity.class);

            }
        });
        btnPostReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.navigateToPostReview(getContext(),PostReviewActivity.class);
            }
        });
        return view;
    }

}
