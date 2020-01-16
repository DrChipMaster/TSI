package com.drchip.ihelp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final  WeakReference<ImageView> imageViewWeakReference;
    public ImageDownloaderTask(ImageView imageView){
        imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadBitmap(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled())
        {
            bitmap = null;
        }
        ImageView imageView = imageViewWeakReference.get();
        if(imageView != null)
        {
            if (bitmap !=null)
            {
                imageView.setImageBitmap(bitmap);
                Bitmap drawable = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Bitmap x = drawable;

                //String root = Environment.getExternalStorageDirectory().toString();
                //File myDir = new File(root);
                //myDir.mkdirs();
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File myDir = new File(root + "/iHelp");
                String fname = "Image-"+ "3" +".jpg";
                File file = new File (myDir, fname);
                if (file.exists ())
                    file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    x.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
            }

    private  Bitmap downloadBitmap(String url)
    {
        HttpURLConnection urlConnection=null;
        try
        {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if(statusCode!= HttpURLConnection.HTTP_OK){
                return null;
            }
            InputStream inputStream =urlConnection.getInputStream();
            if(inputStream!=null)
            {
                return BitmapFactory.decodeStream(inputStream);
            }
        }catch (Exception e) {

        urlConnection.disconnect();
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return null;
    }
}
