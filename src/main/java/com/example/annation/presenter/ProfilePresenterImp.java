package com.example.annation.presenter;

import com.example.annation.http.BaseNetWork;
import com.example.annation.http.HttpResponse;
import com.example.annation.status.UserEntity;
import com.example.annation.uri.Contants;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.PreferenceUtils;
import com.example.annation.view.ProFileView;
import com.google.gson.Gson;
import com.sina.weibo.sdk.net.WeiboParameters;


public class ProfilePresenterImp implements ProfilePresenter {
    private ProFileView mProfileView;
    private PreferenceUtils mSPUtils;
    private WeiboParameters mParameters;

    public ProfilePresenterImp(ProFileView profileView) {
        mProfileView = profileView;
        mSPUtils = PreferenceUtils.getInstance(profileView.getActivity());
        mParameters = new WeiboParameters(Contants.APP_KEY);
    }

    public void loadUserInfo() {
        new BaseNetWork(mProfileView, Contants.API.USER_INFO) {
            public WeiboParameters onPrepares() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN,mSPUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.GAME_PARAMS_UID,mSPUtils.getToken().getUid());
                return mParameters;
            }

            public void onFinish(HttpResponse response, boolean success) {
                if(success){
                    UserEntity entity = new Gson().fromJson(response.response,UserEntity.class);
                    mProfileView.onLoadUserInfo(entity);
                }
            }
        }.get();
    }
}
