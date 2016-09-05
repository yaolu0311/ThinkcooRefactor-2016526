package com.example.administrator.publicmodule.model.exception;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.example.administrator.publicmodule.R;
import com.example.administrator.publicmodule.model.exception.account.LicenseNotAgreeException;
import com.example.administrator.publicmodule.model.exception.account.PhoneNumberOrPasswordErrorException;
import com.example.administrator.publicmodule.model.exception.user.EmptyException;


public class ErrorMessageFactory {

    public static ErrorMessageFactory instance;

    public static ErrorMessageFactory getInstance(Context context){
        if (instance == null) {
            synchronized (ErrorMessageFactory.class){
                if (instance == null) {
                    instance = new ErrorMessageFactory(context);
                }
            }
        }
        return instance;
    }

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
        }
        return errorMsg;
    }

    private ErrorMessageFactory(Context context) {
        applicationContext = context.getApplicationContext();
    }
    public Context getContext(){
        return applicationContext;
    }
    private String getString(int stringResId){
        return  applicationContext.getString(stringResId);
    }

}
