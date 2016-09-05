package com.thinkcoo.mobile.presentation;

import android.accounts.NetworkErrorException;
import android.content.Context;
import com.thinkcoo.mobile.R;
import com.thinkcoo.mobile.model.exception.ServerResponseException;
import com.thinkcoo.mobile.model.exception.account.LicenseNotAgreeException;
import com.thinkcoo.mobile.model.exception.account.PhoneNumberOrPasswordErrorException;
import com.thinkcoo.mobile.model.exception.user.EmptyException;
import com.thinkcoo.mobile.model.repository.impl.ClassListEmptyException;
import javax.inject.Inject;


public class ErrorMessageFactory {

    private Context applicationContext;

    public String createErrorMsg(Throwable e){

        String errorMsg = getString(R.string.default_error_msg);

        if (e instanceof NetworkErrorException) {
            errorMsg = getString(R.string.net_no);
        }else if( e instanceof PhoneNumberOrPasswordErrorException){
            errorMsg = getString(R.string.errTips_phone_pws_error);
        }else if (e instanceof LicenseNotAgreeException){
            errorMsg = getString(R.string.license_must_agree);
        }else if (e instanceof ServerResponseException){
            errorMsg = e.getMessage();
        }else if (e instanceof EmptyException){
            errorMsg = e.getMessage();
        }else if (e instanceof ClassListEmptyException){
            errorMsg = "班组列表为空";
        }
        return errorMsg;
    }
    @Inject
    public ErrorMessageFactory(Context context) {
        applicationContext = context.getApplicationContext();
    }
    public Context getContext(){
        return applicationContext;
    }
    private String getString(int stringResId){
        return  applicationContext.getString(stringResId);
    }

}
