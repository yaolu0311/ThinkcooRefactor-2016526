package com.thinkcoo.mobile.presentation.mvp;


import com.thinkcoo.mobile.model.entity.User;
import com.thinkcoo.mobile.presentation.mvp.views.WelcomeView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Subscriber;
import static org.mockito.Mockito.*;

/**
 * Created by Robert.yao on 2016/3/28.
 */
public class TestWelcomePresenter {

//    WelcomePresenter tWelcomePresenter;
//
//    @Mock
//    CopyExternalDbUseCase mockCopyExternalDbUseCase;
//    @Mock
//    TryGetLastLoginUseCase mockTryGetLastLoginUseCase;
//    @Mock
//    WelcomeView mockWelcomeView;
//
//    @Captor
//    private ArgumentCaptor<Subscriber> mCopyExternalDbSubscriber;
//    @Captor
//    private ArgumentCaptor<Subscriber> mTryGetLastLoginUserDbSubscriber;
//
//    @Before
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//        tWelcomePresenter = new WelcomePresenter(mockCopyExternalDbUseCase,mockTryGetLastLoginUseCase);
//        tWelcomePresenter.attachView(mockWelcomeView);
//    }
//
//    @Test
//    public void testCopyExternalDb(){
//
//        tWelcomePresenter.copyExternalDb();
//        verify(mockCopyExternalDbUseCase,times(1)).execute(mCopyExternalDbSubscriber.capture());
//        mCopyExternalDbSubscriber.getValue().onNext(any(Boolean.class));
//        verify(mockTryGetLastLoginUseCase, times(1)).execute(any(Subscriber.class));
//    }
//
//    @Test
//    public void testCopyExternalDbHappenThrowable(){
//
//        tWelcomePresenter.copyExternalDb();
//        verify(mockCopyExternalDbUseCase,times(1)).execute(mCopyExternalDbSubscriber.capture());
//        mCopyExternalDbSubscriber.getValue().onError(new Throwable("may be error"));
//        verify(mockWelcomeView).toastMsg("may be error");
//        verify(mockWelcomeView).closeSelf();
//
//    }
//
//    @Test
//    public void testGetLastLoginUser(){
//
//        tWelcomePresenter.tryGetLastLoginUser();
//        verify(mockTryGetLastLoginUseCase,times(1)).execute(mTryGetLastLoginUserDbSubscriber.capture());
//        mTryGetLastLoginUserDbSubscriber.getValue().onNext(any(User.class));
//        verify(mockWelcomeView).gotoNextPageByUser(any(User.class));
//
//        mTryGetLastLoginUserDbSubscriber.getValue().onError(any(Throwable.class));
//        verify(mockWelcomeView).gotoNextPageByUser(User.NULL_USER);
//
//    }

}
