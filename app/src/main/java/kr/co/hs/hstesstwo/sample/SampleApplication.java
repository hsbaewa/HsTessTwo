package kr.co.hs.hstesstwo.sample;

import android.app.Application;

import kr.co.hs.tesstwo.HsTessTwo;

/**
 * 생성된 시간 2017-07-11, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsTessTwo
 * 패키지명 : kr.co.hs.hstesstwo.sample
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HsTessTwo.getInstance().initMetaData(getApplicationContext());
    }
}
