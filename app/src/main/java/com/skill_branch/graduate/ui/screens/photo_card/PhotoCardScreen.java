package com.skill_branch.graduate.ui.screens.photo_card;

import android.os.Bundle;

import com.skill_branch.graduate.R;
import com.skill_branch.graduate.data.storage.realm.PhotoCardRealm;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.flow.AbstractScreen;
import com.skill_branch.graduate.flow.Screen;
import com.skill_branch.graduate.mvp.models.PhotoCardsModel;
import com.skill_branch.graduate.mvp.presenters.AbstractPresenter;
import com.skill_branch.graduate.ui.activities.RootActivity;
import com.skill_branch.graduate.ui.screens.photo_list.PhotoListScreen;

import dagger.Provides;
import io.realm.RealmChangeListener;
import mortar.MortarScope;

@Screen(R.layout.item_photo_card)
public class PhotoCardScreen extends AbstractScreen<PhotoListScreen.Component>{

    private PhotoCardRealm mPhotoCardRealm;


    @Override
    public Object createScreenComponent(PhotoListScreen.Component parentComponent) {
        return DaggerPhotoCardScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }



    public PhotoCardScreen(PhotoCardRealm productRealm) {
        mPhotoCardRealm = productRealm;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PhotoCardScreen && mPhotoCardRealm.equals(((PhotoCardScreen)o).mPhotoCardRealm);
    }

    @Override
    public int hashCode() {
        return mPhotoCardRealm.hashCode();
    }




    //region ===================== DI =========================
    @dagger.Module
    public class Module{
        @DaggerScope(PhotoCardScreen.class)
        @Provides
        PhotoCardPresenter providePhotoCardPresenter() {return new PhotoCardPresenter(mPhotoCardRealm);}
    }

    @dagger.Component(dependencies = PhotoListScreen.Component.class, modules = Module.class)
    @DaggerScope(PhotoCardScreen.class)
    public interface Component{
        void inject(PhotoCardPresenter photoCardPresenter);
        void inject(PhotoCardView photoCardView);

    }
    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================
    public class PhotoCardPresenter extends AbstractPresenter<PhotoCardView, PhotoCardsModel>{
        private PhotoCardRealm mPhotoCard;
        private RealmChangeListener mListener;

        public PhotoCardRealm getPhotoCard() {
            return mPhotoCard;
        }

        public PhotoCardPresenter(PhotoCardRealm photoCardRealm) {
            mPhotoCard = photoCardRealm;
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

        }

        @Override
        public void dropView(PhotoCardView view) {
            super.dropView(view);
        }

        @Override
        protected void initActionBar() {

        }
    }

    //endregion ================== SearchPresenter =========================
}
