package com.yundingshuyuan.recruit.service.verify;


public abstract class AbstractUserInfoValidation {
    protected AbstractUserInfoValidation nextTask;

    /**
     * 校验
     *
     * @return 是否成功
     */
    public abstract boolean validate();

    /**
     * 校验链下一个
     *
     * @param nextTask 下一个校验任务
     */
    public void next(AbstractUserInfoValidation nextTask) {
        this.nextTask = nextTask;
    }

    /**
     * Builder 内部类
     */
    public static class Builder {

        private AbstractUserInfoValidation firstTask;
        private AbstractUserInfoValidation currentTask;

        public AbstractUserInfoValidation.Builder add(AbstractUserInfoValidation validationTask) {
            if (firstTask == null) {
                firstTask = validationTask;
            } else {
                currentTask.next(validationTask);
            }
            currentTask = validationTask;
            return this;
        }

        public AbstractUserInfoValidation build() {
            return this.firstTask;
        }
    }
}
