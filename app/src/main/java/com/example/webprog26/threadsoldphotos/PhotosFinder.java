package com.example.webprog26.threadsoldphotos;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by webprog26 on 13.11.2016.
 */

class PhotosFinder {

    private static final String DECTINATION_FOLDER = "DCIM/Camera";
    private static final String PHOTOS = "jpg";
    private static final String VIDEOS = "3gp";


    /**
     * Gets {@link ArrayList<File>} that contains all the photo/ video files in the current folder
     * @return {@link ArrayList<File>}
     */
    static ArrayList<File> getPhotosAndVideosFilesFromSDCard(){
        ArrayList<File> filesList = new ArrayList<>();

        File fileDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), DECTINATION_FOLDER);

        Collections.addAll(filesList, fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return isPhoto(pathname.getAbsolutePath())
                        || isVideo(pathname.getAbsolutePath());
            }
        }));

        return filesList;
    }

    /**
     * Checks is file photo
     * @param filePath {@link String}
     * @return boolean
     */
    private static boolean isPhoto(String filePath){
        return filePath.substring(filePath.length() - 3).equals(PHOTOS);
    }

    /**
     * Checks is file video
     * @param filePath {@link String}
     * @return boolean
     */
    static boolean isVideo(String filePath){
        return filePath.substring(filePath.length() - 3).equals(VIDEOS);
    }
}
