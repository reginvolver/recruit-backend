package com.yundingshuyuan.recruit.service.otverify;

/**
 * 预约面试时间参数校验
 */
public abstract class OpenTimeValidation {

    protected OpenTimeValidation nextTask;

    /**
     * 校验
     * @return
     */
    public abstract boolean validate();

    /**
     * 校验链下一个
     * @param nextTask
     */
    public void next(OpenTimeValidation nextTask) {
        this.nextTask = nextTask;
    }
}
