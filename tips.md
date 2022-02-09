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



