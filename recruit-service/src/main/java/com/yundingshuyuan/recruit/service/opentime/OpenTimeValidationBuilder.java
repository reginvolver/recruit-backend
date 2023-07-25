package com.yundingshuyuan.recruit.service.opentime;

import com.yundingshuyuan.recruit.api.OpenTimeValidation;
import org.springframework.stereotype.Component;

@Component
public class OpenTimeValidationBuilder {
        private OpenTimeValidation firstTask;
        private OpenTimeValidation currentTask;

        public OpenTimeValidationBuilder add(OpenTimeValidation validationTask) {
            System.out.println("task: " + validationTask);
            if (firstTask == null) {
                firstTask = validationTask;
                currentTask = validationTask;
            } else {
                currentTask.next(validationTask);
                currentTask = validationTask;
            }
            return this;
        }

        public OpenTimeValidation build() {
            return firstTask;
        }
}
