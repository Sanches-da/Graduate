package com.skill_branch.graduate.ui.screens.profile;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.mvp.views.AbstractView;
import com.skill_branch.graduate.ui.screens.photo_list.PhotoListScreen;

public class ProfileView extends AbstractView<ProfileScreen.ProfilePresenter> {
    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //region ===================== AbstractView =========================

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<ProfileScreen.Component>getDaggerComponent(context).inject(this);
    }

    //endregion ================== AbstractView =========================

}
