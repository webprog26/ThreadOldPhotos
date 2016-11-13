package com.example.webprog26.threadsoldphotos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by webprog26 on 13.11.2016.
 */

public class BitmapUtils {

    private static final String TAG = "BitmapUtils_TAG";

    /**
     * Decodes {@link Bitmap} from given {@link String} filePath with given width & height
     * @param filePath {@link String}
     * @param reqWidth int
     * @param reqHeight int
     * @return Bitmap
     */
    public static Bitmap getBitmap(String filePath, int reqWidth, int reqHeight){

        if(PhotosFinder.isVideo(filePath)){
            return ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MICRO_KIND);
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);

    }

    /**
     *
     * @param options {@link android.graphics.BitmapFactory.Options}
     * @param reqWidth int
     * @param reqHeight int
     * @return int
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
