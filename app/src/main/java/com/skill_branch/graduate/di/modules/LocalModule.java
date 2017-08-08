package com.skill_branch.graduate.di.modules;

import android.content.Context;

import com.skill_branch.graduate.data.managers.PreferencesManager;
import com.skill_branch.graduate.data.managers.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {
    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context){
        return new PreferencesManager(context);
    }

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        return new RealmManager();
    }
}
