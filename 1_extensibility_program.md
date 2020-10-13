---
theme: solarized
highlightTheme: solarized-dark
revealOptions:
  width: 1200
  height: 900
  margin: 0.02
  minScale: 0.1
  maxScale: 4.0
  controls: false
  slidenumber: c/t
  showSlidenumber: speaker
  transition: none
  disableLayout: false
  showNotes: false
css: css/solarized2.css
cmd: reveal-md 1_extensibility_program.md --css css/solarized2.css -w
---

<!-- .slide: data-background="https://pic4.zhimg.com/v2-e3cd1bb36146a9d8ef257bd7e757c045.png" data-background-size="cover" -->

---

## ABOUT ME

* 姓名：吴新隆，邮箱: long.wu@klook.com
* 当前工作：Entertainment团队的后端工程师 & 全栈工程师
* 先后工作于：美团、水滴筹、KLOOK
* 代码理念：写简单且正确的代码。简单不等于粗糙，正确不等于完美。

Note:
简单与正确之间的平衡，就是我们追求的经验，我一直在努力。

---

## 可扩展性探讨——设计与实现

* 什么是可扩展？
* 为什么需要扩展性？
* 扩展性系统的设计与实现
    1. 从一道笔试题说起
    1. 扩展的本质——占位符
    1. 可扩展系统的设计框架
    1. 基础服务的可扩展性探讨

Note:

上面这几个主题范围从小到大逐渐展开。

另外扩展性的主题很大，需要我们持续学习与精进。我在这里算是抛砖引玉，希望后续大家可以有更多的分享和交流。

OK，我们步入正题。

---

## 什么是可扩展？

* 在面对变化时，能用最少的代价去实现。
* 代价比较：
  * 跨团队支持 >> 团队内部成员依赖 > 本人修改
  * 改底层设计 >> 改局部逻辑 >> (改配置 > 无需改动)

Note:
`>>`表示远大于。

为什么改局部代码的代价远大于改配置，而不是稍微大于呢？

因为只要改代码，就需要走完版本上线的全流程，大部分BUG都是在这个过程中出现的。

扩展性，就是尽可能地：只改配置或无需改动。

---

### 为什么需要扩展性？

* 世界是多变的
* 敏捷用来减少风险，但同时更突出多变
* 扩展性，就是为可能的变化预留空间

![](https://pic4.zhimg.com/v2-80198d93276e56d37e79c4444dfeff88_b.jpg)

Note:
大家都知道，我们公司使用敏捷项目管理。敏捷有好处也有挑战，扩展性能帮助我们更从容地应对变化。

---

### 为什么需要扩展性？

需求变更的苦恼

* 重写代码
* 重新测试
* 重新上线
* 引入BUG
* 延期&加班

![](https://pic1.zhimg.com/v2-45e5a1e6b52a44bb5d4c5c5e1d5d1d76_b.jpg)

Note:
上面的场景估计大家都很熟悉，我就不复述了。扩展性并不能解决全部问题，但很多时候能让我们少加班，不跳楼。

---

## 扩展性系统的设计与实现

1. **_从一道笔试题说起_**
1. 扩展的本质——占位符
1. 可扩展系统的设计框架
1. 基础服务的可扩展性探讨

Note:
接下来我们进入第1个主题

---

### 笔试题：插入整数

给一个整数n，在该数字的某个位置插入5，使得插入之后的数字最大，如：

* n = 268, max = 5268
* n = 673, max = 6753 
* n = -678, max = -5678
* n = -1234, max = -12345

Note:
这里有一个笔试题，有的同学可能做过，但不影响哈。 请大家先阅读一下。

1分钟后提问：请问题目有不清楚的吗？需要我再解释一遍吗？

OK，现在请大家花3分钟时间写一下伪码。

2分钟后：做完的同学，可以在群里面分享一下哈。

3分钟后：OK，时间有限，先到这里哈。有没有同学分享一下答案？

没有的话，那就公布答案。

---

### 笔试题：插入整数

给一个整数n，在该数字的某个位置插入5，使得插入之后的数字最大。

```python
if n >= 0:
    找到第一个小于5的地方插入5
    if 没找到插入的地方：
        将5追加到最后
else:
    找到第一个大于5的地方插入5
    if 没找到插入的地方：
        将5追加到最后
```

Note:
这里有一段伪码，大家花1分钟看一下有没有问题。

解法很多，有其他解法的同学，可以发出来供同学们参考。

1分钟后：再来看看代码实现。（这是Java实现，其他语言的话，同学们自行练习哈）

---

```java
// 给一个整数n，在该数字的某个位置插入5，使得插入之后的数字最大。
public static int solution1(int n) {
    StringBuilder builder = new StringBuilder(String.valueOf(n));
    int len = builder.length();
    int i = 0;
    if (n >= 0) {
        // 找到第一个小于5的地方插入5
        for (; i < len; i++) {
            int v = builder.charAt(i) - '0';
            if (v < 5) {
                builder.insert(i, 5);
                break;
            }
        }
        if (i == len) {
            // 没找到，将5追加到最后
            builder.append(5);
        }
    } else {
        // 找到第一个大于5的地方插入5
        for (i = 1; i < len; i++) {
            int v = builder.charAt(i) - '0';
            if (v > 5) {
                builder.insert(i, 5);
                break;
            }
        }
        if (i == len) {
            // 没找到，将5追加到最后
            builder.append(5);
        }
    }
    return Integer.parseInt(builder.toString());
}
```

Note:
满分10分的话，大家觉得这个答案可得几分？

考虑扩展性的话，这个答案可得几分呢？

现在需求有点变化 ，我们来看看（翻页）

---

### 需求变化

* 如果要求插入6呢？
* 如果要求插入4，且使插入之后的数最小呢?

Note:
有的同学可能纳闷了，你这题目不是只需要插入5吗？怎么又变来变去的？

大家都工作过，应该都能理解哈：业务就是变来变去的。

我们来看一下，通常的做法：复制、粘贴、修改。

---

### “快速”做法：复制、粘贴、修改

```java
// 插入6，使插入后的值最大
public static int insertSix(int n) {
    StringBuilder builder = new StringBuilder(String.valueOf(n));
    int len = builder.length();
    int i = 0;
    if (n >= 0) {
        for (; i < len; i++) {
            int v = builder.charAt(i) - '0';
            if (v < 6) {
                builder.insert(i, 6);
                break;
            }
        }
        if (i == len) {
            builder.append(6);
        }
    } else {
        for (i = 1; i < len; i++) {
            int v = builder.charAt(i) - '0';
            if (v > 6) {
                builder.insert(i, 6);
                break;
            }
        }
        if (i == len) {
            builder.append(6);
        }
    }
    return Integer.parseInt(builder.toString());
}
```

---

### “快速”做法：复制、粘贴、修改

```java
// 插入4，且使插入之后的数最小
public static int insert4ToBeMinimum(int n) {
    StringBuilder builder = new StringBuilder(String.valueOf(n));
    int len = builder.length();
    int i = 0;
    if (n >= 0) {
        for (; i < len; i++) {
            int v = builder.charAt(i) - '0';
            if (v > 4) {
                builder.insert(i, 4);
                break;
            }
        }
        if (i == len) {
            builder.append(4);
        }
    } else {
        for (i = 1; i < len; i++) {
            int v = builder.charAt(i) - '0';
            if (v < 4) {
                builder.insert(i, 4);
                break;
            }
        }
        if (i == len) {
            builder.append(4);
        }
    }
    return Integer.parseInt(builder.toString());
}
```

Note:
这种快速做法，并不建议。大家应该都是比较有追求的。下面我们做点优化。

---

### 扩展性优化

```python
if n >= 0:
    找到第一个小于5的地方插入5
    if 没找到插入的地方：
        将5追加到最后
else:
    找到第一个大于5的地方插入5
    if 没找到插入的地方：
        将5追加到最后
```

Note:
首先可以把魔数5提取到变量中。

---

### 扩展性优化

```python
if n >= 0:
    找到第一个小于 {{5}} 的地方插入 {{5}}
    if 没找到插入的地方：
        将 {{5}} 追加到最后
else:
    找到第一个大于 {{5}} 的地方插入 {{5}}
    if 没找到插入的地方：
        将 {{5}} 追加到最后
```

Note:
翻页

---

### 扩展性优化

```python
x = 5
if n >= 0:
    找到第一个小于 x 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
else:
    找到第一个大于 x 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
```

Note:
然后，可以把”小于x"和”大于x“这两个逻辑表达式，也作为变量提取出来。

---

### 扩展性优化

```python
x = 5
if n >= 0:
    找到第一个 {{大于 x}} 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
else:
    找到第一个 {{小于 x}} 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
```

Note:
然后，可以把”小于x"和”大于x“这两个逻辑表达式，也作为变量提取出来。

---

### 扩展性优化

```python
x = 5
if n >= 0:
    找到第一个满足 condition1 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
else:
    找到第一个满足 condition2 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
```

Note:
进一步分析发现外层的if和else子句中的代码重复了，我们可以把重复的逻辑片段也抽到变量中去，不过这个变量比较特殊，它是个函数。

---

### 扩展性优化

```python
x = 5
if n >= 0:
    {{
    找到第一个满足 condition1 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
    }}
else:
    {{
    找到第一个满足 condition2 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
    }}
```

Note:
进一步分析发现外层的if和else子句中的代码重复了，我们可以把重复的逻辑片段也抽到变量中去，不过这个变量比较特殊，它是个函数。

---

### 扩展性优化

```python
x = 5
if n >= 0:
    doInsert(n, condition1, x)
else:
    doInsert(n, condition2, x)

# 提取公共函数
def doInsert(n, condition, x):
    找到第一个满足 condition 的地方插入 x
    if 没找到插入的地方：
        将 x 追加到最后
```

Note:
到这里，大家觉得还有进一步优化的空间吗？

我们来看一下最终实现。

---

### 扩展性优化

```java
// 在数字n的某个位置插入insertValue，使插入之后的数字最大
public static int insertToBeMax(int n, int insertValue) {
    if (n >= 0) {
        return getSolution(n, 0, v -> v < insertValue, insertValue);
    } else {
        return getSolution(n, 1, v -> v > insertValue, insertValue);
    }
}

// 在数字n的某个位置插入insertValue，使插入之后的数字最小
public static int insertToBeMinimum(int n, int insertValue) {
    if (n >= 0) {
        return getSolution(n, 0, v -> v > insertValue, insertValue);
    } else {
        return getSolution(n, 1, v -> v < insertValue, insertValue);
    }
}

private static int getSolution(int n, int start, Predicate< Integer > condition, int insertValue) {
    assert insertValue >= 0 && insertValue < 10;
    StringBuilder sb = new StringBuilder(String.valueOf(n));
    int i = start;
    for (; i < sb.length(); i++) {
        int num = sb.charAt(i) - '0';
        // 找到第一个满足条件的地方，插入指定的字符
        if (condition.test(num)) {
            sb.insert(i, insertValue);
            break;
        }
    }
    // 没有满足条件的，则在最后面添加
    if (i == sb.length()) {
        sb.append(insertValue);
    }
    return Integer.parseInt(sb.toString());
}
```

Note:
原来复制20多行代码，现在只要复制5行代码，且清晰易读。

---

### 结论：扩展的本质是占位符

* 占位符是什么： 凡是可以表达变化的东西
* 占位符怎么表达：变量、函数、接口、子系统等
* 如何实现：学习和掌握常用的技术实现方法（上面学习了变量提取，后面还有其他技巧）

Note:
上面我们讲了通过提取变量，让程序更具扩展性。

但是变量容易跟编程语言中的变量引起歧义。

所以我们可以用“占位符”来替代“变量”。

---

### 小结：培养应对变化的意识

* 多想一步：它会变化吗？会怎么变？如果变了，对我有什么影响？
* 多做一步：如果处理可能的变化并不复杂，那么一开始就去处理它
* 多点重构：一开始粗糙点没关系，但要有重构的习惯

Note:
上面讲的内容比较简单，大家平时都能做到，关键是意识培养。

复述一下上面3条内容。

---

### 扩展性意识小练习
请分析下面的题目，在不离题的情况下，提取出可能的变化并写成伪码。

Note:
下面我们再做两个小练习。

---

### 扩展性意识小练习
#### 创建3个线程, 线程1打印A, 2打印B, 3打印C（即依次打印ABC），共打印10轮。

```bash
# 输出
ABCABCABCABCABCABCABCABCABCABC
```

Note:
考虑到时间关系，就不花时间互动了，我带大家过一遍。

---

### 扩展性意识小练习
#### 创建3个线程, 线程1打印A, 2打印B, 3打印C（即依次打印ABC），共打印10轮。

* 创建 {n} 个线程
* 依次打印长度为{n}的任意字符串 {s}
* 共打印 {m} 轮

```java
multiThreadPrint(int n, String printStr, int times)

// or
multiThreadPrint(String printStr, int times)
```

Note:
题外话：用go的协程来实现的话，比较简单。但如果用Java来实现，还是有点挑战的。大家课后可以练习一下。

---

### 扩展性意识小练习

#### BD提出要一个报表需求：查看当天售卖情况（假设报表字段已经明确）

```python
# 只满足BD当前需求
def report():
    查询当天订单，填充其他相关信息，发送给老王
```

Note:
上面这段代码就是典型的没有扩展性意识。

如果BD要求查看过去1周的数据，或者说BD需要将报表发送给其他人，都需要改动代码重新上线。

---

### 扩展性意识小练习

#### BD提出要一个报表需求：查看当天售卖情况（假设报表字段已经明确）

```python
def report():
    查询{{某一时间段}}的订单，填充其他相关信息，发送给{{某些收件人}}
```

Note:
这里我们显然可以把 "时间段" 和 "收件人" 作为变量提取出来，既能满足当前的需求，也能满足可能的变化。

---

### 扩展性意识小练习

#### BD提出要一个报表需求：查看当天售卖情况（假设报表字段已经明确）

```python
def report(start_time, end_time, receivers):
    if start_time is None:
        start_time = 当日开始时间
    if end_time is None:
        end_time = 当日结束时间
    查询当天订单，填充其他相关信息，发送给 receivers
```

Note:
除了上面的小练习，大家回头可找一些其他的场景练习一下。

本小节到这里还没完，请大家思考一个问题（翻页）

---

### 请思考

针对第一个笔试题，我们是否需要考虑："从一个数字中删除一个字符，使删除之后的数字最大？"

答案是：不需要。因为问题的核心算法变了。
<!-- .element: class="fragment" -->

---

### 避免过度设计

* 聚焦：只解决一个核心问题
* 不要钻牛角尖
* 没有银弹

---

### 扩展性系统的设计与实现

1. 从一道笔试题说起
1. 扩展的本质——占位符
1. **_可扩展系统的设计框架_**
1. 基础服务 的可扩展性探讨

Note:
接下来我们进入：可扩展性系统的设计框架
---

### 可扩展系统的设计框架

1. 规范
1. 识别
1. 注册
1. 使用

Note:
这里提供一个设计系统的框架供大家参考，主要是为了让大家在研究或者实现系统的可扩展性时，有一个比较确定的思路。

---

### 可扩展系统的设计框架之
## 规范

* 凡是可以表达变化的就能用它来定义
* 规范是对扩展方案的约定
* 常见定义规范的方法有：
    * 配置项
    * 注解
    * 接口（interface)
    * REST/RPC等

Note:
一开始看上面这些文字，可能不太能理解，不过没关系，后面会有详细的例子来帮助大家理解与记忆。

---

### 可扩展系统的设计框架之
## 识别

* 配置项通过约定的配置格式识别
* annotation通过扫描类来识别
* 平台系统可以通过唯一的业务标志来识别某业务线

Note:
我们设计系统是给人用的，那我怎么知道有谁在使用以及怎样使用呢？

所以我们需要提供一种或多种机制去识别使用方。

---

### 可扩展系统的设计框架之
## 注册

* 将识别出的使用者（或者说实现者）统一管理起来，方便使用、扩展与维护。
* 如果交互的系统只有一个，一般注册到本地内存中
* 如果交互的系统的有多个，那么要注册到某个注册中心（独立部署的服务）

---

### 可扩展系统的设计框架之
## 使用

暴露统一的使用方法，如：
* Annotation
* Abstract/interface method
* REST/RPC。

---

### 常见扩展性系统设计示例

* 配置项
* SPI
* 模板方法

Note:
这里演示一下实现扩展性系统的3种常用技巧。

---

### 配置项

* 规范：spring-boot的配置可以从application.yml, application-{profile}.yml, 命令行参数, 配置中心等地方获取，且优先级规则和格式要求。
* 识别：框架会识别和处理各处的配置。
* 注册：将收集到的配置项，统一注册到spring的属性管理中心。
* 使用：通过`@Value`, `@ConfigurationProperties`, `Environment`等方式获取配置项的值，然后使用。

Note:
其他语言框架想必也是类似的。

首先我们来看一下配置项在项目中的应用。（演示代码，不要翻页）

我们再来看看SPI的示例（翻页）

---

### SPI (Service Provider Interface)

* 规范：
  * 定义待实现接口
  * 实现方在目录resources/META-INF/services/下新建与接口同名（带包名）的文件
  * 上一步文件中的内容为实现类的名称（带包名）
* 识别：找到文件后进行解析、识别其中的类（系统自动）；
* 注册：找到上一步找到的类，并注册到某个地方（如放到变量中）；
* 使用：使用上一步的获取的对象。

![](https://pic3.zhimg.com/v2-b9422bb254580da46dd5e2498c913b32_b.jpg)

Note:

SPI的经典例子就是数据库驱动的实现，我们要切换驱动，只要修改一下包依赖即可。

我们看看自己该怎样写一个SPI的demo。演示代码，查看效果。

---

### SPI示例

* 定义接口：SpiBase, 定义一个方法：`void hello()`来输出一个字符串。
* 注册逻辑：使用找到的第一个实现类。

---

### 模板方法

* 规范：约定几个方法，分别用来实现业务逻辑和提供适用场景。
* 识别：实现类实现共同的接口，通过框架依赖注入相关实现类。
* 注册：管理类（如示例中的`CallCenterService`）将识别出来的对象统一管理起来，必要的时候做一些加工。
* 使用：管理类提供方法给客户端使用，对外屏蔽接口实现类的存在。

Note:
演示spring代码。

下面简单讲一下我对平台相关系统的思考与建模，这是一个思路，不一定完全正确。不知道有没有平台的同学，我们可以一起讨论一下。

---

### 模板方法示例

需求：
* 用户通过http接口呼叫客服中心
* 客服中心根据用户的channel来分发到不同的处理方
* 不同的处理方会返回不同的内容

Note:
我们来看一下代码示例

---

### 扩展性系统的设计与实现

1. 从一道笔试题说起
1. 扩展的本质——占位符
1. 可扩展系统的设计框架
1. **_基础服务的可扩展性探讨_**

---

### 基础服务的可扩展性探讨

* 跨团队系统比本团队系统修改，资源耗费大几个数量级
* 微服务的拆分原则：低耦合，高内聚
* 理清服务边界，不做多余的事情

---

### 基础服务的可扩展性探讨

## 多业务线的平台能力建设

* 全局唯一业务标识
* 套用模板：规范、识别、注册、使用


```yml
# 垂直业务线注册与配置
biz_config:
  # 通过业务标识，识别对应的业务线
  biz_id: 1
  name: entertainment
  description: entertainment
  # 配置需要遵循统一的规范
  order_meta:
    order_list:
      android_order_item_component: AndroidOrderListEntertainment
      ios_order_item_component: IOSOrderListEntertainment
      web_order_item_component: WebOrderListEntertainment
    order_detail:
      android_route: /android-pages/entertainment/order-detail
      ios_route: /ios-pages/entertainment/order-detail
      web_route: /web-pages/entertainment/order-detail
  # 这里的配置只是一个示例，配置不一定需要集中在一个地方
  pay_meta:
    callback_url: /v1/entertainmet/pay/callback
```

Note:
平台各系统，都从这里获取垂直业务线的相关配置。上面这里只是一个示例，大家可以灵活地设计配置的数据结构。

---

### 平台订单列表

[美团平台订单列表示例](https://pic3.zhimg.com/v2-796682dd813535f5b4b88d1b4f53c051_b.jpg)

下面这些逻辑平台订单列表应该处理吗？

* 图片
* 接机时间
* 业务线自定义的订单状态
* 凭证按钮
* 退款按钮
* 详情按钮

---

### 平台订单列表
* 平台只保存订单号、业务标识等关键信息
* 各业务线自定义自己的信息：包括自己的订单状态
* 各业务线前端可以定制自己的组件统一注册到订单列表父组件上，根据业务标识分别渲染 

```yml
order_list:
- order_uuid: tWZmaqbqWb1QM1qtE3xnXuKSD5F1GOar
  biz_id: 1
  create_time: 2020/09/30
  order_detail: {"title":"机场接送-2020/10/01 15:00-大兴机场","image_url":"https://res.klook.com/image/upload/activities/wb5ehbn0llqa2cr2si0j.webp","pickup_time":"2020/10/01","order_status":3,"order_status_desc":"已发凭证","show_voucher":true,"show_refund":true}
```

Note:
演示前端demo。

---

### 平台支付系统

下面这些逻辑应不应该放在支付信息中？

* 商品信息，如：sku_id
* 订单数据，如：order_id

---

### 平台支付系统

只处理支付相关逻辑：支付类型、支付单号、时间、金额、状态等

```yml
pay_info:
  pay_uuid: zZX9y1opz6XJ3r7QZN6n554lfkNaiWGY
  biz_id: 1
  biz_pay_uuid: niXT6LB91MgY8wB5Dd3drtwNVcOo6g9q
  amount: 12.34
  currency: USD
  create_time: 2020-10-10 10:00:00
  pay_time: 2020-10-10 10:20:00
  status: 1
  ...
```

---

## 总结
1. 培养意识：需求可能会变化
2. 占位符及应用
3. 一种设计思路：规范、识别、注册、使用
4. 几个通用的技术方案介绍
5. 基础服务的可扩展性探讨

Note:
由于时间有限，另外，不在平台干活，素材也少，所以第5部分讲得比较少，希望将来有机会跟大家再次探讨。

---

<!-- .slide: data-background="https://pic2.zhimg.com/v2-e3b74e1df7385c8568b6e421e2f326e5.png" data-background-size="cover" -->
