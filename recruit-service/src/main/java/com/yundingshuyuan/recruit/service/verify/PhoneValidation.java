package com.yundingshuyuan.recruit.service.verify;

import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class PhoneValidation extends AbstractUserInfoValidation {
    private String phone;

    @Override
    public boolean validate() {
        String pattern = "^[0-9]{11}$";
        if (!phone.matches(pattern)) {
            throw new InvalidParameterException("电话号码格式不正确");
        }
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }

    @Override
    public void next(AbstractUserInfoValidation nextTask) {
        super.next(nextTask);
    }
}
