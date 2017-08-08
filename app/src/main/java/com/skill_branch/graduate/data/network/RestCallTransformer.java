package com.skill_branch.graduate.data.network;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.skill_branch.graduate.data.managers.ConstantManager;
import com.skill_branch.graduate.data.managers.DataManager;
import com.skill_branch.graduate.data.network.error.ErrorUtils;
import com.skill_branch.graduate.data.network.error.NetworkAvailableError;
import com.skill_branch.graduate.utils.NetworkStatusChecker;

import retrofit2.Response;
import rx.Observable;


public class RestCallTransformer<R> implements Observable.Transformer<Response<R>, R>{
    @Override
    @RxLogObservable
    public Observable<R> call(Observable<Response<R>> responseObservable) {

        return NetworkStatusChecker.isInternetAvailiableObs()
                .flatMap(aBoolean -> aBoolean ? responseObservable : Observable.error(new NetworkAvailableError()))
                .flatMap(rResponse ->{
                    switch (rResponse.code()){
                        case 200:
                            String lastModified = rResponse.headers().get(ConstantManager.HEADER_LAST_MODIFIED);
                            if (lastModified != null){
                                DataManager.getInstance().getPreferencesManager().saveLastGalleryUpdate(lastModified);
                            }
                            return Observable.just(rResponse.body());
                        case 304:
                            return Observable.empty();
                        default:
                            return Observable.error(ErrorUtils.parseError(rResponse));
                    }

                });

    }
}
