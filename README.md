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
    implementation 'com.github.gaoxu19930112:MyAop:1.0.4
}
```

### 初始化
_____
**自定义application的onCreate初始化……**

    MyAOPHelper.getInstance().init(this);
### 使用
|注解名称  | 作用 | 备注 |
|:--------:|:----:|:----:|
|@CheckNet  |可以在调用某个方法之前，检查网络连接状况，没有连接，注解不带方法名参数则不操作并弹出Toast，假如有方法名参数则执行该方法|


