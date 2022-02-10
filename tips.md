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