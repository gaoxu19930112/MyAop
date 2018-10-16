package com.android.gaoxu.aoplibrary.annotation;

import com.android.gaoxu.aoplibrary.constant.PermissionConstants.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : xu.gao@aorise.org
 *     time   : 2018/10/15
 *     desc   : 权限申请注解
 *     version: 1.0
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplyPermission {
    @Permission String[] value();
}
