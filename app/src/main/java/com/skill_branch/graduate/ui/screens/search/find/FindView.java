package com.skill_branch.graduate.ui.screens.search.find;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.skill_branch.graduate.data.managers.DataManager;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.mvp.views.AbstractView;

import javax.inject.Inject;


public class FindView extends AbstractView<FindScreen.FindPresenter> {
    @Inject
    DataManager mDataManager;

    public FindView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<FindScreen.Component>getDaggerComponent(context).inject(this);

    }
}
