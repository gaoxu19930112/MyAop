package com.android.gaoxu.aoplibrary.aspect;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.gaoxu.aoplibrary.R;
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
 *     desc   : 网络判断切面
 *     version: 1.0
 * </pre>
 */
@Aspect
public class CheckNetAspect {
    private static final String TAG = CheckNetAspect.class.getSimpleName();
    private static final String POINTCUT_METHOD =
            "execution(@com.android.gaoxu.aoplibrary.annotation.CheckNet * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckNet checkNet = method.getAnnotation(CheckNet.class);
        Object target = joinPoint.getTarget();
        Context context = getContext(target);

        if (Utils.isConnected(context) || context == null) {
            result = joinPoint.proceed();
        } else {
            if (checkNet == null) {
                Toast.makeText(context, context
                        .getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
                return result;
            }

            String notNetMethod = checkNet.notNetMethod();
            if (!TextUtils.isEmpty(notNetMethod)) {
                try {
                    Reflect.on(context).call(notNetMethod);
                } catch (ReflectException e) {
                    e.printStackTrace();
                    Log.e("提示", "no method " + notNetMethod);
                }
            } else {
                Toast.makeText(context, context
                        .getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
            }

        }
        return result;
    }

    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
