package com.skill_branch.graduate.ui.screens.add_photo_card;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.mvp.presenters.RootPresenter;
import com.skill_branch.graduate.mvp.views.AbstractView;
import com.skill_branch.graduate.ui.screens.profile.ProfileScreen;

import javax.inject.Inject;

public class PhotoEditorView extends AbstractView<PhotoEditorScreen.PhotoEditorPresenter> {

    public PhotoEditorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //region ===================== AbstractView =========================

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<PhotoEditorScreen.Component>getDaggerComponent(context).inject(this);
    }

    //endregion ================== AbstractView =========================

}
