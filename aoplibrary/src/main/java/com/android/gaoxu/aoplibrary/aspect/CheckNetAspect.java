package com.android.gaoxu.aoplibrary.aspect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.gaoxu.aoplibrary.annotation.CheckNet;
import com.android.gaoxu.aoplibrary.reflect.Reflect;
import com.android.gaoxu.aoplibrary.reflect.ReflectException;
import com.android.gaoxu.aoplibrary.utils.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : xu.gao@aorise.org
 *     time   : 2018/10/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckNetAspect {
    private static final String POINTCUT_METHOD =
            "execution(@com.android.gaoxu.aoplibrary.annotation.CheckNet * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        if (Utils.isConnected()) {
            result = joinPoint.proceed();
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            CheckNet checkNet = method.getAnnotation(CheckNet.class);
            Object target = joinPoint.getTarget();
            FragmentActivity fragmentActivity;
            if (target instanceof FragmentActivity) {
                fragmentActivity = (FragmentActivity) target;
            } else if (target instanceof Fragment) {
                fragmentActivity = ((Fragment) target).getActivity();
            } else {
                throw new ReflectException("The annotation checkout can only be used in FragmentActivity or Fragment environment and its subclass environment");
            }
            final FragmentActivity finalFragmentActivity = fragmentActivity;
            if (checkNet == null) {
                Toast.makeText(finalFragmentActivity, "网络暂时不可用，请检查网络", Toast.LENGTH_LONG).show();
                return result;
            }

            String notNetMethod = checkNet.notNetMethod();
            if (!TextUtils.isEmpty(notNetMethod)) {
                try {
                    Reflect.on(finalFragmentActivity).call(notNetMethod);
                } catch (ReflectException e) {
                    e.printStackTrace();
                    Log.e("mark", "no method " + notNetMethod);
                }
            } else {
                Toast.makeText(finalFragmentActivity, "网络暂时不可用，请检查网络", Toast.LENGTH_LONG).show();
            }

        }
        return result;
    }
}
