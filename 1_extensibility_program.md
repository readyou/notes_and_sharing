---
theme: solarized
highlight-theme: solarized
revealOptions:
    controls: false
    slideNumber: c/t
    showSlideNumber: speaker
---

# ABOUT ME

* Entertainment团队的后端工程师 & 全栈工程师
* 先后工作于：美团、水滴筹、KLOOK
* 钟情于写简单且正确的代码

---

### 可扩展性探讨——设计、重构与实现

* 为什么需要扩展性 <!-- .element: class="fragment" -->
* 设计更具扩展性的系统 <!-- .element: class="fragment" -->
    * What if ...? <!-- .element: class="fragment" -->
    * Design of options <!-- .element: class="fragment" -->
    * OOP & Principles <!-- .element: class="fragment" -->
    * Generics <!-- .element: class="fragment" -->
* 重构与实现 <!-- .element: class="fragment" -->
    * 测试驱动开发<!-- .element: class="fragment" -->
    * 1W2H <!-- .element: class="fragment" -->

Note:
    1W2H = When, How, How much

    2W2H = What, When, How, How much

---

## 为什么需要扩展性？

---

修改代码的代价——从入职到划水
<!-- .element: class="fragment" -->

![](images/a_bug_before_off_work.gif)


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