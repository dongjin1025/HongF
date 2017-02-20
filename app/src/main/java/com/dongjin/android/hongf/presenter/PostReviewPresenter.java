package com.dongjin.android.hongf.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Review;
import com.dongjin.android.hongf.view.PostReviewActivity;
import com.dongjin.android.hongf.view.PostReview_View;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public class PostReviewPresenter implements Presenter<PostReview_View> {

    PostReview_View postReview_view;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference storeRef;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://hongf-153308.appspot.com");
    KaKaoInfo kaKaoInfo;

    public PostReviewPresenter(){
        kaKaoInfo=KaKaoInfo.getInstance();
    }

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

    public void postReview(String id,String content,float rate){
        Review review=new Review();
        review.setContent(content);
        review.setRate(rate);


        storeRef=myRef.child("Store").child(id);
        myRef.child("Review").child(id).child(kaKaoInfo.read_id_kakao()).push().setValue(review);
    }

    int c;

    String pushKey;
    public void postReviewAndPhotosAsWell(ArrayList<Image> images, final String id, String content, float rate){
        final Review review=new Review();
        c=0;
        review.setContent(content);
        review.setRate(rate);

        storeRef=myRef.child("Store").child(id);
        pushKey=myRef.child("Review").child(id).child(kaKaoInfo.read_id_kakao()).getKey();
        myRef.child("Review").child(id).child(kaKaoInfo.read_id_kakao()).push().setValue(review);

        for(int i=0; i<images.size();i++){
            Uri file = Uri.fromFile(new File(images.get(i).path));
            UploadTask uploadTask = storageRef.child("Store").child(file.getLastPathSegment()).putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ++c;
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    storeRef.child("Urls").push().setValue(downloadUrl.toString());
                    myRef.child("Review").child(id).child(kaKaoInfo.read_id_kakao()).child(pushKey)
                    .child("review_urls").push().setValue(downloadUrl.toString());


                }
            });



        }




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
