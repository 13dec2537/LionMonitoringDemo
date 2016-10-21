package com.example.kuybeer26092016.lionmonitoringdemo.service;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by KuyBeer26092016 on 18/10/2559.
 */
public class AnimationListitem {
    public static  void animate(RecyclerView.ViewHolder holder,boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown == true ? 100:100,0);
        animatorTranslateY.setDuration(1000);
        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();
    }
}
