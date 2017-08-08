package com.skill_branch.graduate.di.components;



import android.content.Context;

import com.skill_branch.graduate.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
