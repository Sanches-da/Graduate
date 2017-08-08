package com.skill_branch.graduate.ui.screens.photo_list;


import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.skill_branch.graduate.R;
import com.skill_branch.graduate.data.managers.DataManager;
import com.skill_branch.graduate.data.storage.realm.PhotoCardRealm;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.ui.screens.photo_card.PhotoCardView;
import com.skill_branch.graduate.utils.PicassoCache;
import com.skill_branch.graduate.utils.UiHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private List<PhotoCardRealm> mPhotoCardList = new ArrayList<>();
    private Picasso mPicasso;
    private int spanCount;




    public PhotoListAdapter(Picasso picasso) {
        mPicasso = picasso;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_card, parent, false);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int spanCount = ((GridLayoutManager)((RecyclerView)parent).getLayoutManager()).getSpanCount();
        lp.width = parent.getWidth() / spanCount;
        lp.height = lp.width;
        view.setLayoutParams(lp);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoCardRealm photoCardRealm = mPhotoCardList.get(position);
        holder.bind(photoCardRealm);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPhotoCardList.size();
    }


    public void addItem(PhotoCardRealm photoCard) {
        mPhotoCardList.add(photoCard);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_preview)
        ImageView mImgPreview;
        @BindView(R.id.likes_count_txt)
        TextView mLikesCountTxt;
        @BindView(R.id.views_count_txt)
        TextView mViewsCountTxt;

        @BindDrawable(R.drawable.splash)
        Drawable mDummyImage;

        PhotoCardRealm mPhotoCard = null;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PhotoCardRealm photoCard){
            mPhotoCard = photoCard;
            refreshInfo();
        }

        public void refreshInfo(){
            if (mPhotoCard != null) {
                mLikesCountTxt.setText((Integer.toString(mPhotoCard.getFavorites())));
                mViewsCountTxt.setText((Integer.toString(mPhotoCard.getViews())));

                UiHelper.getCachedImagePicasso(mPicasso, mPhotoCard.getPhoto(), mImgPreview, mDummyImage, false, true );
            }
        }

    }

}
