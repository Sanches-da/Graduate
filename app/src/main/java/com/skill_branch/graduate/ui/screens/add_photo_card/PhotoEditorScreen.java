package com.skill_branch.graduate.ui.screens.add_photo_card;


import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.skill_branch.graduate.R;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.flow.AbstractScreen;
import com.skill_branch.graduate.flow.Screen;
import com.skill_branch.graduate.mvp.models.PhotoCardsModel;
import com.skill_branch.graduate.mvp.presenters.AbstractPresenter;
import com.skill_branch.graduate.mvp.presenters.MenuItemHolder;
import com.skill_branch.graduate.mvp.presenters.RootPresenter;
import com.skill_branch.graduate.ui.activities.RootActivity;
import com.skill_branch.graduate.ui.screens.profile.DaggerProfileScreen_Component;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_photo_editor)
public class PhotoEditorScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent){
        return DaggerPhotoEditorScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ===================== DI =========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(PhotoEditorScreen.class)
        PhotoCardsModel providePhotoCardsModel(){ return new PhotoCardsModel(); }

        @Provides
        @DaggerScope(PhotoEditorScreen.class)
        PhotoEditorPresenter provideProfilePresenter(){ return new PhotoEditorPresenter(); }

    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(PhotoEditorScreen.class)
    public interface Component{
        void inject(PhotoEditorPresenter photoEditorPresenter);
        void inject(PhotoEditorView photoEditorView);

        PhotoCardsModel photoCardsModel();

        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================

    public class PhotoEditorPresenter extends AbstractPresenter<PhotoEditorView, PhotoCardsModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            if (getRootView() != null) {

                mRootPresenter.newActionBarBuilder()
                        .setVisible(false)
                        .build();
            }
        }

        private boolean menuItemSelect(@NonNull MenuItem item){
            if (getRootView() != null) {
                getRootView().showMessage(item.getTitle().toString());
            }
            return true;
        }
    }
    //endregion ================== SearchPresenter =========================



}

