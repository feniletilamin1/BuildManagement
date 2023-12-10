package com.example.buildmanagement.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageWorker {
    public static void saveFile(Uri sourceuri, Context context, String loadPath) throws IOException {
        String destinationFilename = context.getFilesDir() + File.separator + loadPath;

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), sourceuri);
//
        try (FileOutputStream out = new FileOutputStream(destinationFilename)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out); // bmp is your Bitmap instance
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateUniqeName() {
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return fileName;
    }

    public static void filedDelete(String path) {
        File fdelete = new File(path);
        fdelete.delete();
    }
}
