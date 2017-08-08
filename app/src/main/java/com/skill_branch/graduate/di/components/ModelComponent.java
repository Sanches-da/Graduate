package com.skill_branch.graduate.di.components;

import com.skill_branch.graduate.di.modules.ModelModule;
import com.skill_branch.graduate.mvp.models.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
