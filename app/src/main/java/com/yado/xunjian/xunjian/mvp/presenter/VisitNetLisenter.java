package com.yado.xunjian.xunjian.mvp.presenter;

import android.icu.text.PluralRules;

/**访问网络返回的结果给presenter处理，数据类型根据接口提供的指明
 * Created by Administrator on 2017/11/17.
 */

public interface VisitNetLisenter<T, G> {
    public void visitNetSuccess(T t);
    public void visitNetFailed(G g);
}
