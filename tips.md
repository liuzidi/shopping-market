登录：

前端部分校验流程：

v-model实时渲染input框

设置isRight判断账号密码的正确性，周期登陆前可以先判断这个参数的值是否为true

window.location.href = xxxUrl;用于跳转网页

@keyUp 实时校验账号密码是否为空，长度是否合法

@click 作为提交按钮执行的函数，先判断isRight参数的正确性，避免一些无效的访问后端服务器的行为，然后再格式正确的情况下进行后端的axios的异步请求后端数据

axios请求后端数据的步骤

```javascript
<script type="text/javascript">
    var baseUrl = "http://localhost:8080/";
	var url = baseUrl + "/user/login";
	axios.get(
        	url,
            {
                params:{
                    username:vm.username, 
                    password:vm.password
                }
    		}
    		)
		 .then(
        (res)=>{
        console.log(res);
    });
</script>
```

设置cookie

```javascript
var operator = "=";
function setCookieValue(key,value){
	document.cookie = key+operator+value;
}
function getCookieValue(keyStr){
	var value = null;
	var s = window.document.cookie;
	var arr = s.split("; ");
	for(var i=0; i<arr.length; i++){
		var str = arr[i];
		var k = str.split(operator)[0];
		var v = str.split(operator)[1];
		if(k == keyStr){
			value = v;
			break;
		}
	}
	return value;
}
```



注册阶段采用事务，要么成功，要么全失败

注册和登录的代码

```javascript
@Transactional
    public ResultVO userResgit(String name, String pwd) {
        synchronized (this) {
            //1.根据用户查询，这个用户是否已经被注册
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", name);
            List<Users> users = usersMapper.selectByExample(example);

            //2.如果没有被注册则进行保存操作
            if (users.size() == 0) {
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int i = usersMapper.insertUseGeneratedKeys(user);
                if (i > 0) {
                    return new ResultVO(ResStatus.OK, "注册成功！", user);
                } else {
                    return new ResultVO(ResStatus.NO, "注册失败！", null);
                }
            } else {
                return new ResultVO(ResStatus.NO, "用户名已经被注册！", null);
            }
        }
    }

    @Override
    public ResultVO checkLogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(example);

        if(users.size() == 0){
            return new ResultVO(ResStatus.NO,"登录失败，用户名不存在！",null);
        }else{
            String md5Pwd = MD5Utils.md5(pwd);
            if(md5Pwd.equals(users.get(0).getPassword())){
                //如果登录验证成功，则需要生成令牌token（token就是按照特定规则生成的字符串）
                //使用jwt规则生成token字符串
                JwtBuilder builder = Jwts.builder();
                
                HashMap<String,Object> map = new HashMap<>();
                map.put("key1","value1");
                map.put("key2","value2");

                String token = builder.setSubject(name)                     //主题，就是token中携带的数据
                        .setIssuedAt(new Date())                            //设置token的生成时间
                        .setId(users.get(0).getUserId() + "")               //设置用户id为token  id
                        .setClaims(map)                                     //map中可以存放用户的角色权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000)) //设置token过期时间
                        .signWith(SignatureAlgorithm.HS256, "QIANfeng6666")     //设置加密方式和加密密码
                        .compact();

                return new ResultVO(ResStatus.OK,token,users.get(0));
            }else{
                return new ResultVO(ResStatus.NO,"登录失败，密码错误！",null);
            }
        }
    }
```

关于注册使用synchronized关键字，原因如下：

1.注册并发量不大

2.如果不使用synchronized，可以设置数据库不可重复字段，根据抛出异常，进行java代码上的优化



为何登录要使用用户认证：

为了控制受限资源在用户未登录情况下不允许访问

加密算法：HS256，Base64，MD5，RS256

jwt是一种token ：json web token

jwt是一段被base64编码过的字符序列

### 优点：

在分布式系统中，可以有效的解决单点登录问题以及SESSION共享问题；服务器不保存token或者用户session信息，可以减少服务器压力。

### 缺点：

没有失效策略；设置失效时间后，只能等待token过期，无法改变token里面的失效时间



相对于其他token的优越性在于：

1. 安全性，其他token在反解析之后容易暴露原来，jwt为什么可以做到安全的原因在于：在解析前需要给signature中的密码才能开始解析

单点登录：

### 1、HTTP无状态协议

　　web应用采用browser/server架构，http作为通信协议。http是无状态协议，浏览器的每一次请求，服务器会独立处理，不与之前或之后的请求产生关联，这个过程用下图说明，三次请求/响应对之间没有任何联系

​	   但这也同时意味着，任何用户都能通过浏览器访问服务器资源，如果想保护服务器的某些资源，必须限制浏览器请求；要限制浏览器请求，必须鉴别浏览器请求，响应合法请求，忽略非法请求；要鉴别浏览器请求，必须清楚浏览器请求状态。既然http协议无状态，那就让服务器和浏览器共同维护一个状态吧！这就是会话机制

### 2、会话机制

　　浏览器第一次请求服务器，服务器创建一个会话，并将会话的id作为响应的一部分发送给浏览器，浏览器存储会话id，并在后续第二次和第三次请求中带上会话id，服务器取得请求中的会话id就知道是不是同一个用户了，这个过程用下图说明，后续请求与第一次请求产生了关联。

服务器在内存中保存会话对象，浏览器怎么保存会话id呢？你可能会想到两种方式

1. 请求参数
2. cookie

　　将会话id作为每一个请求的参数，服务器接收请求自然能解析参数获得会话id，并借此判断是否来自同一会话，很明显，这种方式不靠谱。那就浏览器自己来维护这个会话id吧，每次发送http请求时浏览器自动发送会话id，cookie机制正好用来做这件事。cookie是浏览器用来存储少量数据的一种机制，数据以”key/value“形式存储，浏览器发送http请求时自动附带cookie信息

　　tomcat会话机制当然也实现了cookie，访问tomcat服务器时，浏览器中可以看到一个名为“JSESSIONID”的cookie，这就是tomcat会话机制维护的会话id，使用了cookie的请求响应过程如下图

### 3、单系统登陆和多系统登陆

web系统早已从久远的单系统发展成为如今由多系统组成的应用群，面对如此众多的系统，用户难道要一个一个登录、然后一个一个注销吗？

web系统由单系统发展成多系统组成的应用群，复杂性应该由系统内部承担，而不是用户。无论web系统内部多么复杂，对用户而言，都是一个统一的整体，也就是说，用户访问web系统的整个应用群与访问单个系统一样，登录/注销只要一次就够了。

虽然单系统的登录解决方案很完美，但对于多系统应用群已经不再适用了，为什么呢？

==单系统登录解决方案的核心是cookie，cookie携带会话id在浏览器与服务器之间维护会话状态。但cookie是有限制的，这个限制就是cookie的域（通常对应网站的域名）==，浏览器发送http请求时会自动携带与该域匹配的cookie，而不是所有cookie

既然这样，为什么不将web应用群中所有子系统的域名统一在一个顶级域名下，例如“*.baidu.com”，然后将它们的cookie域设置为“baidu.com”，这种做法理论上是可以的，甚至早期很多多系统登录就采用这种同域名共享cookie的方式。

　　然而，可行并不代表好，共享cookie的方式存在众多局限。首先，应用群域名得统一；其次，应用群各系统使用的技术（至少是web服务器）要相同，不然cookie的key值（tomcat为JSESSIONID）不同，无法维持会话，共享cookie的方式是无法实现跨语言技术平台登录的，比如java、php、.net系统之间；第三，cookie本身不安全。

　　因此，我们需要一种全新的登录方式来实现多系统应用群的登录，这就是[单点登录](https://so.csdn.net/so/search?q=单点登录&spm=1001.2101.3001.7020)，具体例子，登陆了支付宝账户就相当于登录了支付宝，淘宝，天猫，阿里云等等多系统内容，以前采用共享cookie的方式。

## 三、单点登录

　　什么是单点登录？单点登录全称Single Sign On（以下简称SSO），是指在多系统应用群中登录一个系统，便可在其他所有系统中得到授权而无需再次登录，包括单点登录与单点注销两部分

### 1、登录

　　相比于单系统登录，sso需要一个独立的认证中心，只有认证中心能接受用户的用户名密码等安全信息，其他系统不提供登录入口，只接受认证中心的间接授权。间接授权通过令牌实现，sso认证中心验证用户的用户名密码没问题，创建授权令牌，在接下来的跳转过程中，授权令牌作为参数发送给各个子系统，子系统拿到令牌，即得到了授权，可以借此创建局部会话，局部会话登录方式与单系统的登录方式相同。这个过程，也就是单点登录的原理，用下图说明

![img](https://images2015.cnblogs.com/blog/797930/201612/797930-20161203152650974-276822362.png)

下面对上图简要描述

1. 用户访问系统1的受保护资源，系统1发现用户未登录，跳转至sso认证中心，并将自己的地址作为参数
2. sso认证中心发现用户未登录，将用户引导至登录页面
3. 用户输入用户名密码提交登录申请
4. sso认证中心校验用户信息，创建用户与sso认证中心之间的会话，称为全局会话，同时创建授权令牌
5. sso认证中心带着令牌跳转会最初的请求地址（系统1）
6. 系统1拿到令牌，去sso认证中心校验令牌是否有效
7. sso认证中心校验令牌，返回有效，注册系统1
8. 系统1使用该令牌创建与用户的会话，称为局部会话，返回受保护资源
9. 用户访问系统2的受保护资源
10. 系统2发现用户未登录，跳转至sso认证中心，并将自己的地址作为参数
11. sso认证中心发现用户已登录，跳转回系统2的地址，并附上令牌
12. 系统2拿到令牌，去sso认证中心校验令牌是否有效
13. sso认证中心校验令牌，返回有效，注册系统2
14. 系统2使用该令牌创建与用户的局部会话，返回受保护资源

　　用户登录成功之后，会与sso认证中心及各个子系统建立会话，用户与sso认证中心建立的会话称为全局会话，用户与各个子系统建立的会话称为局部会话，局部会话建立之后，用户访问子系统受保护资源将不再通过sso认证中心，全局会话与局部会话有如下约束关系

1. 局部会话存在，全局会话一定存在
2. 全局会话存在，局部会话不一定存在
3. 全局会话销毁，局部会话必须销毁

　　你可以通过博客园、百度、csdn、淘宝等网站的登录过程加深对单点登录的理解，注意观察登录过程中的跳转url与参数

在访问多系统中的一个系统时，首先该系统会跳转到sso认证中心去验证是否登录，如果以及登录，sso认证中心会把令牌传递给该系统，从而创建局部会话

个人理解： 假设此时有A系统（[www.a.com](https://blog.csdn.net/xiaoguan_liu/article/details/www.a.com)）、B系统（[www.b.com](https://blog.csdn.net/xiaoguan_liu/article/details/www.b.com)）和SSO系统（[www.c.com](https://blog.csdn.net/xiaoguan_liu/article/details/www.c.com)）,当用户第一次访问A系统时，并没有携带以a.com为path的cookie，所以A系统认为用户还未登录，所以A系统会将请求重定向到SSO系统，请求的参数是a.com,也就是说请求路径为[www.c.com?service=a.com](https://blog.csdn.net/xiaoguan_liu/article/details/www.c.com?service=a.com)，SSO系统接收到请求之后，发现用户并没有携带以c.com为path的cookie，所以SSO系统会引导用户进行登录，登录成功之后，会生成一个唯一的票据ticket，然后以ticket为key，用户对象为value，保存到Redis中。然后，SSO系统以c.com为path，在客户端上写一个cookie，假设key为userTicket，value则为对应的票据ticket。之后，SSO系统重定向到A系统（因为A系统重新定向的时候，传递了自己的路径。），并以ticket为参数进行传递，A系统拿到ticket之后，会去SSO系统中验证ticket的有效性，当验证成功之后，A会以a.com为path，在客户端浏览器写入一个cookie，key为userTicket，value为对应的ticket（SSO系统发过来的），然后还会更新Redis中票据过期的时间。 此时，如果用户向B系统发起请求，因为客户端并没有携带以b.com为path的cookie，所以B也会以自己的路径为参数，重定向到SSO系统，因为用户已经在A系统登录的时候，保存了以c.com为path的cookie，所以SSO系统拿到cookie之后（cookie中保存的是票据信息ticket），去Redis中查询对应的用户对象，如果ticket还存在且没有过期，SSO系统会以ticket为参数重定向到B系统，B系统接收到ticket之后会向SSO系统验证票据ticket的有效性，验证通过之后，同样会更新Redis中票据的过期时间，然后会以b.com为path，在客户端的浏览器中写入一个cookie，key为userTicket，value为对应的票据ticket（由SSO系统发过来的）。此后，当用户访问其他的系统时，会以同样的方式进行登录。



JWT

包含 header，payload 和 signature

header和payload用base64加密

签名部分，包含了header和payload，

```json
HMACSHA256(
    base64UrlEncode(header) + "." +  
    base64UrlEncode(payload),  
    secret)
```

这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串(头部在前)，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。

==前后端通过请求头作为交互时，会有个浏览器预检过程，method为options，此为浏览器的第一次试探性检查，拦截器应该予以放行，给予正常相应，这样浏览器才会发送第二次的正常请求==

关于浏览器的预检：

### 浏览器在什么情况下会发起options预检请求？

在非简单请求且跨域的情况下，浏览器会发起options预检请求。

### 关于简单请求和复杂请求：

1 简单请求

简单请求需满足以下两个条件

1. 请求方法是以下三种方法之一：

- HEAD
- GET
- POST

1. HTTP 的头信息不超出以下几种字段

- Accept
- Accept-Language
- Content-Language
- Last-Event-ID
- Content-Type: 只限于 (application/x-www-form-urlencoded、multipart/form-data、text/plain)

2 复杂请求

非简单请求即是复杂请求

常见的复杂请求有：

1. 请求方法为 PUT 或 DELETE
2. Content-Type 字段类型为 application/json
3. 添加额外的http [header](https://so.csdn.net/so/search?q=header&spm=1001.2101.3001.7020) 比如access_token

在跨域的情况下，非简单请求会先发起一次空body的OPTIONS请求，称为"预检"请求，用于向服务器请求权限信息，等预检请求被成功响应后，才发起真正的http请求。



VUE的生命周期

![Vue 实例生命周期](https://cn.vuejs.org/images/lifecycle.png)