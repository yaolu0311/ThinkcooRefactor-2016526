package com.thinkcoo.mobile.presentation.mvp;

/**
 * Created by Robert.yao on 2016/3/28.
 */
public class TestLoginPresenter {
/*
    LoginPresenter tLoginPresenter;

    User user;

    @Mock
    UserLoginUseCase mockUserLoginUseCase;
    @Mock
    InitUserEnvironmentUseCase mockInitUserEnvironmentUseCase;
    @Mock
    InputCheckUtil mockInputCheckUtil;
    @Mock
    ErrorMessageFactory mockErrorMessageFactory;
    @Mock
    LoginView mockLoginView;
    @Captor
    private ArgumentCaptor<Subscriber> mUserLoginSubscriber;
    @Captor
    private ArgumentCaptor<Subscriber> mInitUserEnvironmentSubscriber;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        tLoginPresenter = new LoginPresenter(mockUserLoginUseCase,mockInitUserEnvironmentUseCase,mockInputCheckUtil,mockErrorMessageFactory);
        tLoginPresenter.attachView(mockLoginView);
        user = getFakeUser();

    }

    @Test
    public void testLoginAndInitUserEnvironmentSuccess(){
        makeLoginSuccess();
        makeInitUserEnvironmentSuccess();
    }

    @Test
    public void testLoginHappenException(){

        tLoginPresenter.login();
        verify(mockLoginView).showProgressDialog(R.string.loading);
        verify(mockUserLoginUseCase,times(1)).execute(mUserLoginSubscriber.capture());
        mUserLoginSubscriber.getValue().onError(getPhoneNumberOrPasswordErrorException());
        verify(mockLoginView).hideProgressDialogIfShowing();
        verify(mockLoginView).showToast(mockErrorMessageFactory.createErrorMsg(getPhoneNumberOrPasswordErrorException()));

    }

    @Test
    public void testLoginSuccessButInitUserEnvironmentFailure(){
        makeLoginSuccess();
        makeInitUserEnvironmentFailure();
    }

    private Throwable getPhoneNumberOrPasswordErrorException(){
        return new PhoneNumberOrPasswordErrorException();
    }

    private User getFakeUser(){
        User user = new User();
        return user;
    }

    private void makeLoginSuccess(){

        tLoginPresenter.login();
        verify(mockLoginView).showProgressDialog(R.string.loading);
        verify(mockUserLoginUseCase,times(1)).execute(mUserLoginSubscriber.capture());
        mUserLoginSubscriber.getValue().onNext(user);

    }

    private void makeInitUserEnvironmentSuccess(){

        verify(mockInitUserEnvironmentUseCase).setUser(eq(user));
        verify(mockInitUserEnvironmentUseCase, times(1)).execute(mInitUserEnvironmentSubscriber.capture());
        mInitUserEnvironmentSubscriber.getValue().onNext(true);
        verify(mockLoginView).hideProgressDialogIfShowing();
        verify(mockLoginView).gotoHomePage();
    }

    private void makeInitUserEnvironmentFailure(){

        verify(mockInitUserEnvironmentUseCase).setUser(eq(user));
        verify(mockInitUserEnvironmentUseCase, times(1)).execute(mInitUserEnvironmentSubscriber.capture());
        mInitUserEnvironmentSubscriber.getValue().onError(getInitUserEnvironmentException());
        verify(mockLoginView).hideProgressDialogIfShowing();
        verify(mockLoginView).showToast(mockErrorMessageFactory.createErrorMsg(getInitUserEnvironmentException()));

    }

    private Throwable getInitUserEnvironmentException(){
        return new InitUserEnvironmentException();
    }*/
}
