package com.skill_branch.graduate.ui.screens.search.find;


import com.skill_branch.graduate.R;
import com.skill_branch.graduate.data.managers.DataManager;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.flow.AbstractScreen;
import com.skill_branch.graduate.flow.Screen;
import com.skill_branch.graduate.mvp.models.SearchModel;
import com.skill_branch.graduate.mvp.presenters.AbstractPresenter;
import com.skill_branch.graduate.ui.screens.search.SearchScreen;
import com.skill_branch.graduate.ui.screens.search.filter.FilterScreen;
import com.skill_branch.graduate.ui.screens.search.filter.FilterView;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_find)
public class FindScreen extends AbstractScreen<SearchScreen.Component> {


    @Override
    public Object createScreenComponent(SearchScreen.Component parentComponent) {
        return DaggerFindScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }


    //region ===================== DI =========================

    @dagger.Module
    public class Module{
        @Provides
        @DaggerScope(FindScreen.class)
        FindPresenter provideFindPresenter(){return new FindPresenter();}

        @Provides
        @DaggerScope(FindScreen.class)
        DataManager provideDataManager(){return DataManager.getInstance();}
    }

    @dagger.Component(dependencies = SearchScreen.Component.class, modules = Module.class)
    @DaggerScope(FindScreen.class)
    public interface Component{
        void inject(FindPresenter presenter);
        void inject(FindView view);

        DataManager getDataManager();
    }

    //endregion ================== DI =========================

    //region ===================== Presenter =========================
    public class FindPresenter extends AbstractPresenter<FindView, SearchModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ((FindScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);

        }

        @Override
        protected void initActionBar() {

        }
    }
    //endregion ================== Presenter =========================
}
