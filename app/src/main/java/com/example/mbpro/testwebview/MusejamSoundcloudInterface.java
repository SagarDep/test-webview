package com.example.mbpro.testwebview;

import android.webkit.JavascriptInterface;

/**
 * Created by mbpro on 8/13/16.
 */

public class MusejamSoundcloudInterface{
    interface MusejamSoundcloudCallback{
        void enableButton();
    }
    MusejamSoundcloudCallback mContext;
    MusejamSoundcloudInterface(MusejamSoundcloudCallback context){
        mContext = context;
    }
    @JavascriptInterface
    public void onReady(){
        mContext.enableButton();
    }
}
