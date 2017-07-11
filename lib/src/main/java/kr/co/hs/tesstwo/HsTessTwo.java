package kr.co.hs.tesstwo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Bae on 2016-11-16.
 */
public class HsTessTwo {
    private static HsTessTwo instance = null;
    public static HsTessTwo getInstance(){
        if(instance == null)
            instance = new HsTessTwo();
        return instance;
    }

    private String[] arrLang = {"eng", "kor", "train"};


    public String getUTF8Text(Context context, String trainedDataFileName, File file){
        TessBaseAPI baseAPI = new TessBaseAPI();
        baseAPI.init(context.getFilesDir().toString()+"/SimpleAndroidOCR", trainedDataFileName);
        baseAPI.setImage(file);
        String result = baseAPI.getUTF8Text();
        return result;
    }

    public String getUTF8Text(Context context, String trainedDataFileName, Bitmap bitmap){
        TessBaseAPI baseAPI = new TessBaseAPI();
        baseAPI.init(context.getFilesDir().toString()+"/SimpleAndroidOCR", trainedDataFileName);
        baseAPI.setImage(bitmap);
        String result = baseAPI.getUTF8Text();
        return result;
    }

    public String getUTF8KorText(Context context, Bitmap bitmap){
        TessBaseAPI baseAPI = new TessBaseAPI();
        baseAPI.init(context.getFilesDir().getAbsolutePath(), "kor");
        baseAPI.setImage(bitmap);
        String result = baseAPI.getUTF8Text();
        return result;
    }

    public boolean initMetaData(Context context){
        File dataPath = new File(context.getFilesDir().toString() + "/SimpleAndroidOCR");
        File tessDataPath = new File(dataPath.getAbsolutePath()+"/tessdata");

        if(!dataPath.exists()){
            if(!dataPath.mkdirs()){
                return false;
            }
        }
        if(!tessDataPath.exists()){
            if(!tessDataPath.mkdirs()){
                return false;
            }
        }

        for(String lang : arrLang){
            File dataFile = new File(tessDataPath.getAbsolutePath()+"/"+lang+".traineddata");
            if(!dataFile.exists()){
                AssetManager assetManager = context.getAssets();
                BufferedInputStream bis;
                BufferedOutputStream bos;
                try{
                    bis = new BufferedInputStream(assetManager.open("tessdata/"+lang+".traineddata"));
                }catch (Exception e){
                    String errorLog = "정의된 %s.traineddata 파일을 여는데 실패하였습니다.(message : %s)";
                    errorLog = String.format(errorLog, lang, e.getMessage());
                    return false;
                }
                try{
                    FileOutputStream fos = new FileOutputStream(tessDataPath.getAbsolutePath()+"/"+lang+".traineddata");
                    bos = new BufferedOutputStream(fos);
                }catch (Exception e){
                    String errorLog = "%s.traineddata 파일을 생성하는데 실패하였습니다.(message : %s)";
                    errorLog = String.format(errorLog, lang, e.getMessage());
                    return false;
                }


                try{
                    byte[] buf = new byte[8196];
                    int readCnt = 0;
                    while((readCnt = bis.read(buf, 0, buf.length))>0){
                        bos.write(buf, 0, readCnt);
                    }
                    bos.flush();
                }catch (Exception e){
                    String errorLog = "%s.traineddata 파일을 쓰는 도중 실패하였습니다.(message : %s)";
                    errorLog = String.format(errorLog, lang, e.getMessage());
                    return false;
                }

                try{

                    bos.close();
                    bis.close();
                }catch (Exception e){
                    String errorLog = "스트림을 닫는데 실패하였습니다.(message : %s)";
                    errorLog = String.format(errorLog, e.getMessage());
                }
            }
        }
        return true;
    }
}
