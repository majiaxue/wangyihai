package com.xingshi.refundparticulars;

import com.xingshi.bean.RefundParticularsBean;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public interface RefundParticularsView extends IView {
    void initView(RefundParticularsBean refundParticularsBean);
}
