package com.skill_branch.graduate.data.managers;


import com.skill_branch.graduate.data.network.RestCallTransformer;
import com.skill_branch.graduate.data.network.RestService;
import com.skill_branch.graduate.data.network.res.PhotoCardRes;
import com.skill_branch.graduate.data.storage.dto.PhotoCardDto;
import com.skill_branch.graduate.data.storage.realm.PhotoCardRealm;
import com.skill_branch.graduate.di.DaggerService;
import com.skill_branch.graduate.di.components.DaggerDataManagerComponent;
import com.skill_branch.graduate.di.components.DataManagerComponent;
import com.skill_branch.graduate.di.modules.LocalModule;
import com.skill_branch.graduate.di.modules.NetworkModule;
import com.skill_branch.graduate.utils.App;
import com.skill_branch.graduate.utils.AppConfig;
import com.skill_branch.graduate.utils.NetworkStatusChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;


public class DataManager {
    private static DataManager ourInstance;

    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;
    @Inject
    Retrofit mRetrofit;
    @Inject
    RealmManager mRealmManager;

    private boolean userAuth=false;


    private DataManager() {
        DataManagerComponent dmComponent = DaggerService.getComponent(DataManagerComponent.class);
        if (dmComponent==null){
            dmComponent = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, dmComponent);
        }
        dmComponent.inject(this);

        updateLocalDataWithTimer();
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    private void updateLocalDataWithTimer() {
        Observable.interval(AppConfig.JOB_UPDATE_DATA_INTERVAL, TimeUnit.SECONDS) //генерируем последовательность из элдементов каждые 30 сек
                .flatMap(aLong -> NetworkStatusChecker.isInternetAvailiableObs()) //проверяем состяние интернета
                .filter(aBoolean -> aBoolean) //идем дальше только если интернет есть
                .flatMap(aBoolean -> getPhotoCardsObsFromNetwork()) //получаем новые товары из сети
                .subscribe(productRealm -> {

                }, throwable -> {

                });
    }
//
//
//    public Observable<CommentRes> sendComment(String productId, CommentRes comment) {
//        return mRestService.sendComment(productId, comment);
//    }
//
//    public Observable<AvatarUrlRes> uploadUserPhoto(MultipartBody.Part mBody) {
//        return mRestService.uploadUserAvatar(mBody);
//    }

//    public Observable<UserRes> loginUser(UserLoginReq userLoginReq) {
//    return mRestService.loginUser(userLoginReq).flatMap(userResResponse -> {
//        switch (userResResponse.code()) {
//            case 200:
//                return Observable.just(userResResponse.body());
//            case 403:
//                return Observable.error(new AccessError());
//            default:
//                return Observable.error(new ApiError(userResResponse.code()));
//
//        }
//    });
//}

    //region ===================== Getters =========================

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    //endregion ================== Getters =========================


    //region ===================== UserInfo =========================

    public boolean isAuthUser() {
        //TODO check auth token in shared preferences
        return userAuth;
    }

    public void setUserAuth(boolean state){
        userAuth = state;
    }

//    public UserInfoDto getUserProfileInfo() {
//        return mPreferencesManager.getUserProfileInfo();
//    }
//
//    public UserSettingsDto getUserSettings() {
//        return mPreferencesManager.getUserSettings();;
//    }
//
//    public ArrayList<UserAddressDto> getUserAddress() {
//        mPreferencesManager.getUserAddresses();
//    }
//
//    public void saveProfileInfo(UserInfoDto userInfoDto) {
//    }
//
//    public void saveSettings(UserSettingsDto userSettings) {
//        mPreferencesManager.saveUserSettings(userSettings);
//    }
//
//    public void addAddress(UserAddressDto addressDto){
//        mUserAddress.add(addressDto);
//    }
//
//    public void removeAddress(UserAddressDto address) {
//        mPreferencesManager.removeAddress(address);
//    }
//
//
//    public void updateAddress(UserAddressDto address) {
//        if (address.getId() == 0){
//            mPreferencesManager.addUserAddress(address);
//        }else {
//            for (UserAddressDto curAddress : mPreferencesManager.getUserAddresses()) {
//                if (curAddress.getId() == address.getId()) {
//                    mPreferencesManager.updateUserAddress(address);
//                    break;
//                }
//            }
//        }
//    }
//




    //endregion ================== UserInfo =========================

    //region ===================== PhotoCards =========================
    public List<PhotoCardDto> getPhotoCardList() {
        return new ArrayList<>();
    }

    public Observable<PhotoCardRealm> getPhotoCardsObsFromNetwork(){
        return mRestService.getPhotoCardResObs(mPreferencesManager.getLastGalleryUpdate(), 60, 0)
                .compose(new RestCallTransformer<List<PhotoCardRes>>()) //трансформируем response и выбрасываем ApiError в слуае ошибки, проверяем статус сети перед запросом, обрабатываем коды ответов
                .flatMap(Observable::from) //List of ProductRes
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(productRes -> {
//                    if (!productRes.isActive()) {
//                        mRealmManager.deleteFromRealm(ProductRealm.class, productRes.getId()); //удалить запись из локальной БД
//                    }
                })
                //.filter(PhotoCardRes::isActive) //только активные товары
                .doOnNext(productRes ->  mRealmManager.savePhotoCardResponseToRealm(productRes)) //Save data on disk
                .retryWhen(errorObservable -> errorObservable
                        .zipWith(Observable.range(1, AppConfig.GET_DATA_RETRY_COUNT), (throwable, retryCount) -> retryCount)  // последовательность попыток от 1 до 5\
                        .doOnNext(retryCount -> {
                        })
                        .map(retryCount -> (long) (AppConfig.INITIAL_BACK_OFF_IN_MS * Math.pow(Math.E, retryCount))) //генерируем задержку экспоненциально
                        .doOnNext(delay -> {
                        })
                        .flatMap(delay -> Observable.timer(delay, TimeUnit.MILLISECONDS)))  //запускаем таймер
                .flatMap(productRes -> Observable.empty());

    }


    public Observable<PhotoCardRealm> getPhotoCardsFromRealm() {
        return mRealmManager.getAllPhotoCardsFromRealm();
    }



    //endregion ================== PhotoCards =========================



}
