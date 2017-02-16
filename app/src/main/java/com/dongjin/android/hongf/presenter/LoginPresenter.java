package com.dongjin.android.hongf.presenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.view.Login_View;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import java.security.MessageDigest;

/**
 * Created by kimdongjin on 2017. 2. 16..
 */

public class LoginPresenter implements Presenter<Login_View> {
    Login_View view;
    String kakaoId;
    String kakaoName;
    String kakaoPicture;
    KaKaoInfo kakaoInfo;

    public LoginPresenter(){
        kakaoInfo=KaKaoInfo.getInstance();

    }



    @Override
    public void attachView(Login_View view) {
        this.view=view;
        kakaoInfo.getCacheDir(view.getContext());
    }

    @Override
    public void detachView() {

    }
    public void getAppKeyHash() {
        try {
            PackageInfo info = view.getContext().getPackageManager().getPackageInfo(view.getContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }
    public void kakaorequestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(view.getContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG", "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG", "오류로 카카오로그인 실패 ");

            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                kakaoId = userProfile.getId() + "";
                kakaoInfo.write_id_kakao(kakaoId);
                kakaoName = userProfile.getNickname();
                kakaoInfo.write_name_kakao(kakaoName);
                kakaoPicture=userProfile.getProfileImagePath();
                kakaoInfo.write_picture_kakao(kakaoPicture);



                Log.e("TOKEN", kakaoId);
            }

            @Override
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
            }
        });

    }

}
