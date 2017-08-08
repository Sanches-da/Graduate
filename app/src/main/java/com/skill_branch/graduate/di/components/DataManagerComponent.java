package com.skill_branch.graduate.di.components;

import com.skill_branch.graduate.data.managers.DataManager;
import com.skill_branch.graduate.di.modules.LocalModule;
import com.skill_branch.graduate.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {LocalModule.class, NetworkModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
