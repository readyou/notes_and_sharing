---
theme: solarized
highlight-theme: solarized
revealOptions:
  width: 1600
  height: 1200
  margin: 0.02
  minScale: 0.1
  maxScale: 2.0
  controls: false
  slideNumber: c/t
  showSlideNumber: speaker
  css: css/custom.css
---

## ABOUT ME

* Entertainment团队的后端工程师 & 全栈工程师
* 先后工作于：美团、水滴筹、KLOOK
* 致力于写简单且正确的代码

---

### 可扩展性探讨——设计、重构与实现

* What <!-- .element: class="fragment" -->
* Why <!-- .element: class="fragment" -->
* How <!-- .element: class="fragment" -->
    * What if ...?
    * Design of options
    * OOP & Principles
    * Design pattern

---

### 什么是可扩展？

在面对变化时，用最少的代价去实现。


---

### 为什么需要扩展性？

![](https://pic1.zhimg.com/v2-45e5a1e6b52a44bb5d4c5c5e1d5d1d76_b.jpg)
<!-- .element: class="fragment" -->

Note:
    减少程序员跳楼事件的发生。

---

### 认可多变的现实

敏捷、迭代与变化 

---

![](https://pic2.zhimg.com/v2-b64615711d2bdc89a9910b58856b2294_b.jpg)

---

![](https://pic4.zhimg.com/v2-80198d93276e56d37e79c4444dfeff88_b.jpg)

---

![](https://pic1.zhimg.com/v2-4151dc059b62fed649b68a5032196e85_b.jpg)

---

### 需求变更的苦恼

* 重写代码
* 重新测试
* 引入BUG
* 延期&加班

---

### 对应方法

* 更专业的产品
* 更专业的沟通
* 更专业的代码

---

### 扩展的本质——占位符

* 占位符是什么： 凡是可以表达变化的就是占位符
* 占位符怎么表达：变量、配置项、接口等
* 如何实现：如何在执行时动态找到实现类？识别和执行


---

### 应对可扩展的框架

* 规范
* 识别
* 注册
* 使用

---

### 规范

* 凡是可以表达变化的就能用它来定义
* 规范是对扩展方案的约定
* 常见定义规范的方法有：
    * 配置项
    * 变量
    * 接口
    * 注解等

---

### 识别

* 配置项通过特定的配置格式
* 注解是要扫描类来识别 annotation

---

### 注册

将规范统一管理，方便扩展与维护。

* 如果系统的交互只是一个，那么存储在本地就行
* 如果系统的交互是多个，那么要注册到一个注册中心

---

### 使用

暴露统一的使用方法。

---

### 可扩展的经典案例

* SPI 有它的规范，要到指定目录下加载对应文件；
* 找到文件后进行解析、识别并加载、注册；
* 最后就是使用。

---

## 可扩展性系统实践之路
* 识别变化
* 处理变化。常见手段有：
    * 野蛮处理, if/else, switch
    * 面向接口设计
    * 插件
    * 微服务

---

### Design of options

假设我们需要一个简单的server

```go
func NewServer(addr int)
```

Note:
    addr是服务监听的端口。

    第一期的需求很简单，就上面这些足够满足了。
    项目上线跑了一段时间发现，由于连接没有设置超时，
    很多连接一直得不到释放（异常情况），严重影响服务器性能。
    所以我们需要支持设置timeout。
---

能不能支持timeout？

![](https://pic.17qq.com/uploads/ctsuseweax.jpeg)
<!-- .element: class="fragment" -->

```go
func NewServer(addr int) {
    return NewServer(addr, defaultTimeout)
}

func NewServer(addr int, timeout time.Duration)
```
<!-- .element: class="fragment" -->


Note: 
    提问：请问，这时候你是怎么做的？有谁能回答一下我？

    等待回答。
    
    我们再添加一个方法，添加一个时间参数即可。
    老方法改写一下，使用默认timeout调用新方法即可。

    过一段时间，用户需要支持tls。

---

能不能支持tls？

![](http://wx4.sinaimg.cn/mw690/6a04b428gy1g0gvw312lxg202z02bt8k.gif)
<!-- .element: class="fragment" -->

```go
func NewServer(addr string) (*Server, error)

func NewServer(addr string, timeout time.Duration) (*Server, error)

func NewServer(addr string, timeout time.Duration, tls Tls) (*Server, error)
```
<!-- .element: class="fragment" -->


---

## ~~对旧代码侵入性大~~
<!-- .element: class="fragment" data-fragment-index="5" -->

1. 使用可变参数
<!-- .element: class="fragment" data-fragment-index="1" -->

```go
func NewServer(list ...interface{})
```
<!-- .element: class="fragment" data-fragment-index="2" -->


2. 封装参数到对象中
<!-- .element: class="fragment" data-fragment-index="3" -->

```go
type Param struct {
    x int
    y string
    ...
}
 
func NewServer(p *Param)
```
<!-- .element: class="fragment" data-fragment-index="4" -->

---

[self-referential-functions-and-design](https://commandcenter.blogspot.com/2014/01/self-referential-functions-and-design.html) -- Rob Pike

```go
func NewServer(addr string, options ...func(*Server)) (*Server, error) {
    srv := &Server{
        Addr: addr,
    }
 
   for _, option := range options {
       option(srv)
    }
 
   return srv
}
```

---

实现与使用option

```go 
func timeout(d time.Duration) func(*Server) {
    return func(srv *Server) {
        srv.timeout = d
    }
}
```
<!-- .element: class="fragment" -->
 
```go 
func tls(c *config) func(*Server) {
    return func(srv *Server) {
        Tls := loadConfig(c)
        srv.tls = Tls
    }
}
```
<!-- .element: class="fragment" -->
 
```go
src, err = NewServer("localhost:8080", timeout(1), tls(path/to/cert))
```
<!-- .element: class="fragment" -->