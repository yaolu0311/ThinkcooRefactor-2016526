package com.thinkcoo.mobile.presentation.mvp;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.delegate.BaseMvpDelegateCallback;
import com.hannesdorfmann.mosby.mvp.delegate.ViewGroupMvpDelegate;
import com.hannesdorfmann.mosby.mvp.delegate.ViewGroupMvpDelegateImpl;

/**
 * Created by Leevin
 * CreateTime: 2016/7/27  15:33
 */
public abstract class MvpRealView<V extends MvpView, P extends MvpPresenter<V>> extends View implements MvpView, BaseMvpDelegateCallback<V, P> {

    protected P presenter;
    protected ViewGroupMvpDelegate<V, P> mvpDelegate;

    public MvpRealView(Context context) {
        super(context);
    }

    public MvpRealView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MvpRealView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MvpRealView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Get the mvp delegate. This is internally used for creating presenter, attaching and detaching
     * view from presenter etc.
     *
     * <p><b>Please note that only one instance of mvp delegate should be used per android.view.View
     * instance</b>.
     * </p>
     *
     * <p>
     * Only override this method if you really know what you are doing.
     * </p>
     *
     * @return {@link ViewGroupMvpDelegateImpl}
     */
    @NonNull
    protected ViewGroupMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new ViewGroupMvpDelegateImpl<>(this);
        }

        return mvpDelegate;
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //getMvpDelegate().onAttachedToWindow();
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //getMvpDelegate().onDetachedFromWindow();
    }

    /**
     * Instantiate a presenter instance
     *
     * @return The {@link MvpPresenter} for this view
     */
    public abstract P createPresenter();

    @Override public P getPresenter() {
        return presenter;
    }

    @Override public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override public V getMvpView() {
        return (V) this;
    }

    @Override public boolean isRetainInstance() {
        return false;
    }

    @Override public void setRetainInstance(boolean retainingInstance) {
        throw new UnsupportedOperationException(
                "Retainining Instance is not supported / implemented yet");
    }

    @Override public boolean shouldInstanceBeRetained() {
        return false;
    }


    public void detach(){
        getMvpDelegate().onDetachedFromWindow();
    }

    public void attache(){
        getMvpDelegate().onAttachedToWindow();
    }

}
