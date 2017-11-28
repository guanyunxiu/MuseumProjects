package com.gyx.museum.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyx.museum.R;
import com.gyx.museum.image.ImageManager;
import com.gyx.museum.model.ImageBean;
import com.gyx.museum.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 内容：
 * Created by 关云秀 on 2017\8\9 0009.
 */

public class MuseumClassifyAdapter extends BaseQuickAdapter<ImageBean> {
    private Context context;
    private List<Integer> mHeights = new ArrayList<>();;
    public MuseumClassifyAdapter(List<ImageBean> list, Context context) {
        super(R.layout.item_image_text,list);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, ImageBean imageBean) {
        //helper.setImageResource(R.id.icon_img,homeMenu.getImage());
       // helper.setText(R.id.title_tv,homeMenu.getDesp());
    /*    int height=ImageUtil.getNewHeight(details.getWidth(),details.getHeight(),mItemWidth);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mItemWidth, height);
        holder.ivPic.setLayoutParams(params);*/

        /*float ratio = ImageUtil.getAspectRatio(imageBean.getSource().getw.getWidth(), bean.getFile().getHeight());
        //长图 "width":440,"height":5040,
        holder.img_card_image.setAspectRatio(ratio);//设置宽高比*/


        ImageView imageView = helper.getView(R.id.imageview);
      //  String tag= (String) imageView.getTag();
      //  if (!imageBean.getSource().equals(tag)){
           // imageView.setTag(imageBean.getSource());
            //设置图片
           // int width = ((Activity)imageView.getContext()).getWindowManager().getDefaultDisplay().getWidth();

        final int[] widthMax = {0};
        final int[] heightMax = { 0 };
        ImageView imageView1 = new ImageView(context);
        Glide.with(context)
                .load(imageBean.getSource())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        widthMax[0] = resource.getIntrinsicWidth();
                        heightMax[0] = resource.getIntrinsicHeight();
                        Log.i("heightMax", resource.getIntrinsicWidth() +"***33333"+ resource.getIntrinsicHeight() +"&&&&44444"+getScreenWidth(context)/2);
                        return false;
                    }
                })
               // .placeholder(errorImageId)
               // .error(errorImageId)
                .into(imageView1);
        Glide.with(context).load(imageBean.getSource()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                widthMax[0] = resource.getWidth();
                 heightMax[0] = resource.getHeight();
                Log.i("heightMax", resource.getWidth() +"***33333"+ resource.getHeight() +"&&&&44444"+getScreenWidth(context)/2);
            }
        });

           Log.i("heightMax", widthMax[0] +"***"+ heightMax[0] +"&&&&"+getScreenWidth(context)/2);
          ViewGroup.LayoutParams params = imageView.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            //params.width = width/2;

           // params.height =  (int) (200 + Math.random() * 400) ;
            params.height = (getScreenWidth(context)/2)/ widthMax[0] * heightMax[0];
            imageView.setLayoutParams(params);


            ImageManager.getInstance().loadImage(context,imageBean.getSource(),imageView);
       // }

    }
    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
    public void getRandomHeight(List<ImageBean> mList){
        for(int i=0; i < mList.size();i++){
            //随机的获取一个范围为200-600直接的高度
            mHeights.add((int)(300+ Math.random()*400));
        }
    }
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
