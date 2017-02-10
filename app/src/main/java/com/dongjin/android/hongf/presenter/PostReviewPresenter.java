package com.dongjin.android.hongf.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.view.PostReviewActivity;
import com.dongjin.android.hongf.view.PostReview_View;

import java.io.IOException;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public class PostReviewPresenter implements Presenter<PostReview_View> {

    PostReview_View postReview_view;

    @Override
    public void attachView(PostReview_View view) {
        this.postReview_view=view;
    }

    @Override
    public void detachView() {

    }
    public void addPhotos(PostReviewActivity postReviewActivity){
        Intent intent = new Intent(postReviewActivity, AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
        postReviewActivity.startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    public Bitmap getThumbnailImage(String imgPath) {
        Bitmap image = null;
        try {
            // 이미지 축소
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3; // 1/3로 축소

            image = BitmapFactory.decodeFile(imgPath,options);

            Log.i("image","instance="+image);
            int exifDegree = exifOrientationToDegrees(imgPath);
            Log.i("image","exifDegree="+exifDegree);
            image = rotateImage(image, exifDegree);

        }catch (Exception e){
            Log.e("Thumbnail Error",e.toString());
            e.printStackTrace();
        }
        return image;
    }
    public int exifOrientationToDegrees(String imgPath){
        int degree = 0;
        ExifInterface exif = null;

        try{
            exif = new ExifInterface(imgPath);
        }catch (IOException e){
            Log.e("exifOrientation", "cannot read exif");
            e.printStackTrace();
        }

        if (exif != null){
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1){
                // We only recognize a subset of orientation tag values.
                switch(orientation){
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    public ImageView setSelectedIg(ImageView ig, ArrayList<ImageView> images, ImageView selected,
                                   TextView ratingText, ArrayList<TextView> textViews,Context context){
        ImageView selectedig =selected;
        String colorDivider="#b5b5b5";
        String colorBlack="#000000";
        if(selectedig!=null){

            for(int i=0;i<images.size();i++){
                images.get(i).setColorFilter(ContextCompat.getColor(context, R.color.divider_color));
                textViews.get(i).setTextColor(Color.parseColor(colorDivider));
            }
            if(ig==selectedig){
                selectedig=null;
            }else{
                ig.setColorFilter(ContextCompat.getColor(context, R.color.black));
                ratingText.setTextColor(Color.parseColor(colorBlack));
                selectedig=ig;
            }

        }else{
            selectedig=ig;
            ig.setColorFilter(ContextCompat.getColor(context, R.color.black));
            ratingText.setTextColor(Color.parseColor("#000000"));
        }

        return selectedig;

    }


}
