package com.qianbing.blog.exception;

import com.qianbing.blog.constrant.SmsContrant;
import com.qianbing.blog.constrant.UserContrant;

public class PhoneExistException extends RuntimeException {

    public PhoneExistException(){
        super(UserContrant.PHONE_EXIST);
    }
}
