package com.dongjin.android.hongf.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongjin.android.hongf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDongFragment extends Fragment {

    private TextView tvNotice;

    public ToDongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_to_dong, container, false);

        tvNotice=(TextView) view.findViewById(R.id.tvNotice);
        tvNotice.setText(
                "이곳은 익명 게시판 입니다.개발자에게 하고 싶은 말을 전달해주세요.버그,건의사항,*폐점 신고* 등 어떤 말이라도 괜찮습니다.\n" +
                "혹은 불문과 학우들에게 하고 싶은 말을 보내주시면 전달해드리도록 하겠습니다.피드백,전달 내용은 이곳에 올려질 예정입니다.\n");


        return view;
    }

}
