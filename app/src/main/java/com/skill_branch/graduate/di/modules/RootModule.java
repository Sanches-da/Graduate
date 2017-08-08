package com.skill_branch.graduate.di.modules;

import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.mvp.presenters.RootPresenter;
import com.skill_branch.graduate.ui.activities.RootActivity;

import dagger.Provides;

//import com.skill_branch.graduate.mvp.models.AccountModel;


@dagger.Module
public class RootModule {
    @Provides
    @DaggerScope(RootActivity.class)
    RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }

//    @Provides
//    @RootScope
//    AccountModel provideAccountModel(){
//        return new AccountModel();
//    }
}
