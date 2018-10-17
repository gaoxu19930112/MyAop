## MyAop

面向切面编程,代码解耦,单一逻辑业务处理,减少代码重复性

### 配置
_____
**1.在全局build里面添加下面github仓库地址**
```
buildscript {
    ...
    dependencies {
        classpath 'com.gaoxu.plugin:aop-plugin:1.0.1'
    }
}
allprojects {
    repositories {
        maven {url 'https://dl.bintray.com/gaoxu930112/maven/'}
    }
}
```
**2.在app的build里面添加插件和依赖**
```
apply plugin:'aop-plugin'
...
dependencies {
    implementation 'com.github.gaoxu19930112:MyAop:1.0.6
}
```

### 使用
|注解名称  | 作用 | 备注 |
|:--------:|:----:|:----:|
|@CheckNet  |可以在调用某个方法之前，检查网络连接状况，没有连接，注解不带方法名参数则不操作并弹出Toast，假如有方法名参数则执行该方法|
|@ApplyPermission|多个权限同时申请,适配android8.0,可直接申请PermissionConstants权限组|1.只能在Fragment(v4)和FragmentActivity 以及它们的子类中使用 2.不要把注解打到有生命周期的方法上，否则可能会导致生命周期被拦截
|@ApplyPermissionFailedCallback|权限申请失败的注解,方法的参数必须为 String[] 或者没有参数,String[]为失败权限组|
|@SingleClick|防止按钮被连点，执行重复操作，默认点击间隔为600ms|可以自定义间隔时间 SingleClickAspect.MIN_CLICK_DELAY_TIME 
