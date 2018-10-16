package com.android.gaoxu.aoplibrary.aspect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.gaoxu.aoplibrary.R;
import com.android.gaoxu.aoplibrary.annotation.ApplyPermission;
import com.android.gaoxu.aoplibrary.annotation.ApplyPermissionFailedCallback;
import com.android.gaoxu.aoplibrary.constant.PermissionConstants.Permission;
import com.android.gaoxu.aoplibrary.reflect.ReflectException;
import com.android.gaoxu.aoplibrary.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : xu.gao@aorise.org
 *     time   : 2018/10/15
 *     desc   : 权限申请切面
 *     version: 1.0
 * </pre>
 */
@Aspect
public class PermissionAspect {
    private static final String POINTCUT_METHOD =
            "execution(@com.android.gaoxu.aoplibrary.annotation.ApplyPermission * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithPermission() {
    }

    @Around("methodAnnotatedWithPermission()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApplyPermission annotation = method.getAnnotation(ApplyPermission.class);
        @Permission String[] permission = annotation.value();
        Object target = joinPoint.getTarget();
        FragmentActivity fragmentActivity;
        if (target instanceof FragmentActivity) {
            fragmentActivity = (FragmentActivity) target;
        } else if (target instanceof Fragment) {
            fragmentActivity = ((Fragment) target).getActivity();
        } else {
            throw new ReflectException("The annotation permissionAspect can only be used in FragmentActivity or Fragment environment and its subclass environment");
        }
        final FragmentActivity finalFragmentActivity = fragmentActivity;
        PermissionUtils.getInstance(finalFragmentActivity)
                .request(permission)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted() {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onDenied(String[] failedPermissions) {
                        Method failedCallBack = findFailedCallback(target);
                        if (failedCallBack == null) {
                            Toast.makeText(finalFragmentActivity, finalFragmentActivity.
                                    getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Class<?>[] types = failedCallBack.getParameterTypes();
                        try {
                            if (types.length == 1 &&
                                    types[0].isArray() &&
                                    types[0].getComponentType() == String.class) {
                                failedCallBack.invoke(target, (Object) failedPermissions);
                            } else if (types.length == 0) {
                                failedCallBack.invoke(target);
                            } else {
                                Toast.makeText(finalFragmentActivity, finalFragmentActivity.
                                        getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public Method findFailedCallback(Object object) {
        Class<?> aClass = object.getClass();
        for (Method method : aClass.getDeclaredMethods()) {
            boolean isCallback = method.isAnnotationPresent(ApplyPermissionFailedCallback.class);
            if (!isCallback) continue;
            method.setAccessible(true);
            return method;
        }
        return null;
    }
}
