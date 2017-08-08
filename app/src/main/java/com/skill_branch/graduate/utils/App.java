package com.skill_branch.graduate.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

//import com.facebook.stetho.Stetho;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.components.AppComponent;
import com.skill_branch.graduate.di.components.DaggerAppComponent;
import com.skill_branch.graduate.di.modules.AppModule;
//import com.skill_branch.graduate.di.modules.PicassoCacheModule;
//import com.skill_branch.graduate.di.modules.RootModule;
import com.skill_branch.graduate.mortar.ScreenScoper;
import com.skill_branch.graduate.di.modules.PicassoCacheModule;
import com.skill_branch.graduate.di.modules.RootModule;
import com.skill_branch.graduate.ui.activities.DaggerRootActivity_RootComponent;
import com.skill_branch.graduate.ui.activities.RootActivity;
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;


public class App extends Application {
    public static AppComponent sAppComponent;
    private static Context sContext;
    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;
    private static RootActivity.RootComponent mRootActivityRootComponent;

    @Override
    public Object getSystemService(String name) {
        if (mRootScope != null) {
            return mRootScope.hasService(name) ? mRootScope.getService(name) : super.getSystemService(name);
        }
        return super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        createAppComponent();
        createRootActivityComponent();
        sContext = getApplicationContext();

        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");
        mRootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(mRootActivityScope);

    }


    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    private void createAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    private void createRootActivityComponent() {
           mRootActivityRootComponent = DaggerRootActivity_RootComponent.builder()
                .appComponent(sAppComponent)
                .rootModule(new RootModule())
                .picassoCacheModule(new PicassoCacheModule())
                .build();

    }

    public static RootActivity.RootComponent getRootActivityRootComponent() {
        return mRootActivityRootComponent;
    }

    public static Context getContext() {
        return sContext;
    }
}
