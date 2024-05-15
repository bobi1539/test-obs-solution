package com.test.obs.solution.constant;

import com.test.obs.solution.exception.BusinessException;

public final class Constant {

    private Constant() {
        throw new BusinessException(GlobalMessage.CANNOT_INSTANCE_HELPER_CLASS);
    }

    public static final String ERROR = "Error : ";
    public static final String SUCCESS = "Success";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String NAME_REQUIRED = "Name is required";
    public static final String PRICE_REQUIRED = "Price is required";
}
