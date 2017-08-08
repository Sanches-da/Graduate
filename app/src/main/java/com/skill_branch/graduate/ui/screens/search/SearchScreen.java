package com.skill_branch.graduate.ui.screens.search;


import android.support.annotation.NonNull;

import com.skill_branch.graduate.R;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.flow.AbstractScreen;
import com.skill_branch.graduate.flow.Screen;
import com.skill_branch.graduate.mvp.models.SearchModel;
import com.skill_branch.graduate.mvp.presenters.AbstractPresenter;
import com.skill_branch.graduate.mvp.presenters.RootPresenter;
import com.skill_branch.graduate.ui.activities.RootActivity;
import com.skill_branch.graduate.ui.screens.photo_list.PhotoListScreen;

import javax.inject.Inject;

import dagger.Provides;
import flow.TreeKey;
import mortar.MortarScope;

@Screen(R.layout.screen_search)
public class SearchScreen extends AbstractScreen<RootActivity.RootComponent> implements TreeKey {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerSearchScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }



    //region ===================== DI =========================
    @dagger.Module
    public class Module{
        @Provides
        @DaggerScope(SearchScreen.class)
        SearchPresenter provideSearchPresenter(){
            return new SearchPresenter();
        }

        @Provides
        @DaggerScope(SearchScreen.class)
        SearchModel provideSearchModel(){
            return new SearchModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(SearchScreen.class)
    public interface Component{
        void inject(SearchPresenter presenter);
        void inject(SearchView view);

        SearchModel getDetailsModel();
        RootPresenter getRootPresenter();
    }

    //endregion ================== DI =========================

    //region ===================== SearchPresenter =========================
    public class SearchPresenter extends AbstractPresenter<SearchView, SearchModel> {
        @Inject
        SearchModel mModel;

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTabs(getView().getViewPager())
                    .build();
        }
    }
    //endregion ================== SearchPresenter =========================

    //region ===================== TreeKey =========================

    @NonNull
    @Override
    public Object getParentKey() {
        return new PhotoListScreen();
    }

    //endregion ================== TreeKey =========================


}
