package com.skill_branch.graduate.ui.screens.profile;


import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.skill_branch.graduate.R;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.flow.AbstractScreen;
import com.skill_branch.graduate.flow.Screen;
import com.skill_branch.graduate.mvp.models.ProfileModel;
import com.skill_branch.graduate.mvp.presenters.AbstractPresenter;
import com.skill_branch.graduate.mvp.presenters.MenuItemHolder;
import com.skill_branch.graduate.mvp.presenters.RootPresenter;
import com.skill_branch.graduate.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_profile)
public class ProfileScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent){
        return DaggerProfileScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ===================== DI =========================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ProfileScreen.class)
        ProfileModel provideProfileModel(){ return new ProfileModel(); }

        @Provides
        @DaggerScope(ProfileScreen.class)
        ProfilePresenter provideProfilePresenter(){ return new ProfilePresenter(); }

    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(ProfileScreen.class)
    public interface Component{
        void inject(ProfilePresenter profilePresenter);
        void inject(ProfileView profileView);

        ProfileModel profileModel();

        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================

    public class ProfilePresenter extends AbstractPresenter<ProfileView, ProfileModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component)scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            if (getRootView() != null) {

                mRootPresenter.newActionBarBuilder()
                        .setTitle("Профиль")
                        .setBackArrow(false)
                        .setVisible(true)
                        .addAction(new MenuItemHolder("Новый альбом", 0, this::menuItemSelect, false))
                        .addAction(new MenuItemHolder("Редактировать профиль", 0, this::menuItemSelect, false))
                        .addAction(new MenuItemHolder("Изменить аватар", 0, this::menuItemSelect, false))
                        .addAction(new MenuItemHolder("Выход из профиля", 0, this::menuItemSelect, false))
                        //.setTabs(getView().getViewPager())
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

