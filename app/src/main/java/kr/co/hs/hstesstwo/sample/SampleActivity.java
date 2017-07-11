package kr.co.hs.hstesstwo.sample;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import kr.co.hs.tesstwo.HsTessTwo;

/**
 * 생성된 시간 2017-07-11, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsTessTwo
 * 패키지명 : kr.co.hs.hstesstwo.sample
 */

public class SampleActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap bitmap = null;
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.MediaColumns.DATE_ADDED+" DESC");
        if(cursor != null && cursor.moveToFirst()){
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            bitmap = BitmapFactory.decodeFile(data);
        }
        String result = HsTessTwo.getInstance().getUTF8Text(this, "kor", bitmap);
        Log.d("a","a");
    }
}
