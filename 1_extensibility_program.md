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
    * OOP & Principles <!-- .element: class="fragment" -->
    * Generics <!-- .element: class="fragment" -->
    * Design of options <!-- .element: class="fragment" -->
* 重构与实现 <!-- .element: class="fragment" -->
    * 测试驱动开发<!-- .element: class="fragment" -->
    * 1W2H <!-- .element: class="fragment" -->

Note:
    1W2H = When, How, How much

    2W2H = What, When, How, How much

---

### Design of options

假设我们需要一个简单的server。

```go
func NewServer(port int)
```

添加timeout选项。

```go
func NewServer(port int, timeout time.Duration)
```

Note:
    第一期的需求很简单，就上面这些足够满足了。项目上线跑了一段时间发现，由于连接没有设置超时，很多连接一直得不到释放（异常情况），严重影响服务器性能。好，那第二期我们加个timeout。


