package com.example.webprog26.threadsoldphotos;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/**
 * Created by webprog26 on 13.11.2016.
 */

class UiHandler extends Handler {

    private static final String TAG = "UiHandler_TAG";

    static final int DECODED_BITMAP = 101;
    static final String HAVE_A_BITMAP = "have_a_bitmap";
    private BitmapAddictor mBitmapAddictor;

    UiHandler(BitmapAddictor mBitmapAddictor) {
        this.mBitmapAddictor = mBitmapAddictor;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case DECODED_BITMAP:
                Bitmap bitmap = msg.getData().getParcelable(HAVE_A_BITMAP);
                mBitmapAddictor.addBitmapToList(bitmap);
                break;
        }
    }

    /**
     * Clears {@link android.os.MessageQueue} to avoid memory leaks
     */
    void clearMessagesQueue(){
        clearMessagesQueue();
    }
}
