package com.test.obs.solution.helper;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.exception.BusinessException;

public final class Helper {

    private Helper() {
        throw new BusinessException(GlobalMessage.CANNOT_INSTANCE_HELPER_CLASS);
    }

    public static void checkItemStock(int stock) {
        if (stock < 0) {
            throw new BusinessException(GlobalMessage.ITEM_STOCK_INSUFFICIENT);
        }
    }

    public static int getPage(int page) {
        return page - 1;
    }
}
