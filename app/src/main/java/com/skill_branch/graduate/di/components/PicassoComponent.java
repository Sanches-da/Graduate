package com.skill_branch.graduate.di.components;


import com.skill_branch.graduate.di.modules.PicassoCacheModule;
import com.skill_branch.graduate.di.scopes.DaggerScope;
import com.skill_branch.graduate.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@DaggerScope(RootActivity.class)
public interface PicassoComponent {
    Picasso getPicasso();
}
