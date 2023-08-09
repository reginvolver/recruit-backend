package com.yundingshuyuan.recruit.service.verify;

import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class EmailValidation extends AbstractUserInfoValidation {
    private String email;

    @Override
    public boolean validate() {
        if (email == null) {
            return false;
        }
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(pattern)) {
            throw new InvalidParameterException("邮箱格式不正确");
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
