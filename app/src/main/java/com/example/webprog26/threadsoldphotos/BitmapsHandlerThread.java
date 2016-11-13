package com.example.webprog26.threadsoldphotos;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by webprog26 on 13.11.2016.
 */

class BitmapsHandlerThread extends HandlerThread {

    private static final int DECODE_BITMAPS = 100;

    private static final String TAG = "BitmapsHandlerThread";
    private static final String FILES_PATHS_LIST = "files_path_list";

    private static final int DECODING_BITMAP_WIDTH = 96;
    private static final int DECODING_BITMAP_HEIGHT = 96;


    private Handler mWorkerHandler;
    private Handler mUiHandler;

    BitmapsHandlerThread(Handler mUiHandler) {
        super(TAG);
        this.mUiHandler = mUiHandler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mWorkerHandler = new Handler(this.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case DECODE_BITMAPS:
                        ArrayList<String> filesPathList = msg.getData()
                                .getStringArrayList(FILES_PATHS_LIST);
                        for(String filePath: filesPathList){
                            Bitmap bitmap = BitmapUtils.getBitmap(filePath,
                                    DECODING_BITMAP_WIDTH, DECODING_BITMAP_HEIGHT);
                            Message bitmapMessage = mUiHandler
                                    .obtainMessage(UiHandler.DECODED_BITMAP);
                            Bundle bitmapBundle = new Bundle();
                            bitmapBundle.putParcelable(UiHandler.HAVE_A_BITMAP, bitmap);
                            bitmapMessage.setData(bitmapBundle);
                            bitmapMessage.sendToTarget();
                        }
                        break;
                }
            }
        };
    }

    /**
     * Sends message and data to mWorkerHandler
     * @param filesPathList {@link ArrayList<String>}
     */
    void decodeBitmaps(ArrayList<String> filesPathList){
        Message filesListMessage = mWorkerHandler.obtainMessage(DECODE_BITMAPS);

        Bundle filesPathBundle = new Bundle();
        filesPathBundle.putStringArrayList(FILES_PATHS_LIST, filesPathList);
        filesListMessage.setData(filesPathBundle);

        filesListMessage.sendToTarget();
    }

    /**
     * Prepares mWorkerHandler to avoid NPE
     */
    void prepareHandler(){
        if(mWorkerHandler == null){
            mWorkerHandler = new Handler();
        }
    }

}
