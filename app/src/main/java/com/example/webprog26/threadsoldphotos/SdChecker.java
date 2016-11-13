package com.example.webprog26.threadsoldphotos;

import android.os.Environment;

/**
 * Created by webprog26 on 13.11.2016.
 */

class SdChecker {

    /**
     * Checks is SD card mounted and readable
     * @return
     */
    static boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
