package com.android.gaoxu.aoplibrary.aspect;

import android.view.View;

import com.android.gaoxu.aoplibrary.R;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : xu.gao@aorise.org
 *     time   : 2018/10/16
 *     desc   : 防止多次点击切面
 *     version: 1.0
 * </pre>
 */
@Aspect
public class SingleClickAspect {
    private static final String TAG = SingleClickAspect.class.getSimpleName();
    public static int MIN_CLICK_DELAY_TIME = 600;
    static int TIME_TAG = R.id.click_time;
    private static final String POINTCUT_METHOD =
            "execution(@com.android.gaoxu.aoplibrary.annotation.SingleClick * *(..))";//方法切入点

    @Pointcut(POINTCUT_METHOD)
    private void methodAnnotatedWithSingleClick() {
    }

    @Around("methodAnnotatedWithSingleClick()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs())
            if (arg instanceof View) view = (View) arg;
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = ((tag != null) ? (long) tag : 0);
            long currentClickTime = Calendar.getInstance().getTimeInMillis();
            if (currentClickTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                view.setTag(TIME_TAG, currentClickTime);
                joinPoint.proceed();
            }
        }
    }
}
