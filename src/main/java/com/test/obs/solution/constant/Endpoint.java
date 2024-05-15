package com.test.obs.solution.constant;

import com.test.obs.solution.exception.BusinessException;

public final class Endpoint {

    private Endpoint() {
        throw new BusinessException(GlobalMessage.CANNOT_INSTANCE_HELPER_CLASS);
    }

    public static final String BASE = "/api/test-obs-solution";
    public static final String ITEM = BASE + "/items";
}
