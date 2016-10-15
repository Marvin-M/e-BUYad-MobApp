package com.e_buyad.marvin.e_buyad.background_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.e_buyad.marvin.e_buyad.HomeActivity;
import com.e_buyad.marvin.e_buyad.ProfileActivity;
import com.e_buyad.marvin.e_buyad.R;
import com.e_buyad.marvin.e_buyad.ViewCardActivity;

import java.io.InputStream;
import java.net.URL;

public class ImageLoadAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    private String imageViewText;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(context.getResources().getString(R.string.loading_text));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private Bitmap bmp;
    private ProgressDialog progressDialog;

    public ImageLoadAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String imageUrl = params[0];

        if(context.getClass().getSimpleName().equals("ViewCardActivity")) {
            imageViewText = params[1];
        }


        try {
            InputStream in = new URL(imageUrl).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        progressDialog.dismiss();

        if(bmp != null) {
            switch (context.getClass().getSimpleName()) {
                case "HomeActivity":
                    ((HomeActivity) context).imageView.setImageBitmap(bmp);
                    break;
                case "ProfileActivity":
                    ((ProfileActivity) context).imageView.setImageBitmap(bmp);
                    break;
                case "ViewCardActivity":
                    int resourceID = context.getResources().getIdentifier(
                            imageViewText,
                            "id",
                            context.getPackageName()
                    );

                    if(resourceID != 0) {
                        ImageView imageView = (ImageView)((ViewCardActivity) context)
                                .findViewById(resourceID);

                        if(imageView != null) {
                            imageView.setImageBitmap(bmp);
                        }
                    }

                    break;
            }
        }
    }
}
