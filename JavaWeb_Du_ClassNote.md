# 特别声明



#### JavaWeb内容

​			>*从这里往后是web后端*

- Servlet为核心(Servlet Applet是服务端的java小程序)
- JSP(JSP使用较少了，但是还有用)
- Listener
- Filter
- session会话
- cookie
- 重定向
- EL表达式
- JSTL标签库
- AJAX（异步通信技术）
- jQuery（JavaScript库）
- MyBatis
- Spring
- SpringMVC  (分层--事务管理--动态代理模式--数据库连接池...)
- maven
- Linux操作系统
- Git软件版本控制工具
- SSM三大框架
- SpringBoot
- SpringCloud



#### 浏览器清除缓存

1. 浏览器清除缓存，ctrl+shift+delete
1. 硬盘里面存储的cookie也会被清除



#### URL相关问题

1. 前端所有超链接都要带项目名



#### 关于Tomcat服务器版本

1. Tomcat10之后，服务器不论请求体还是响应体都不会再出现中文乱码问题



#### Javaweb项目属性配置文件

1. 为避免一切不必要的麻烦，所有的属性配置文件放在src/resources目录下

   

#### 关于web文件小蓝点缺失

1. 解决链接：(https://blog.csdn.net/weixin_44015240/article/details/119212670)

2. 快捷键alt+control+shift+s能调出Project Structrue，用File菜单一样可以打开，解决服务器找不到资源重新配置lib和web文件的问题，具体操作看上一个链接，md痛苦死了

   [^重点]: 链接对了访问不了资源，排查是否web文件被动过的原因，可以重置lib，删除web文件，通过上述方法重新添加web文件，web文件出现蓝点404问题就会解决



#### 服务器的一些报错

1. java.lang.NoClassDefFoundError:Could not initialize class 完整类名
   + 意思是该类没编译，点击IDEA的Build—rebuild Project—all Artifacts—rebuild



#### 服务器改代码不重启

1. 改完项目Java代码后，可以直接点左边的小锤子，点reload，在属性网页就行了，可以不重启

2. 当然修改配置文件的话还是需要重启的




#### 批量修改文件后缀名

1. 把所有欲操作文件放到一个目录下

2. 创建一个新的记事本，在记事本中编写以下格式代码

   + ren *.txt *.doc

     [^注意1]: 两个*前面必须为英文空格
     [^注意2]: 这里的两个后缀名是示例，txt是示例的初始后缀名，doc是期望转变成的后缀名，实际使用时按需编写即可，如：ren *.html *.jsp

3. 把该记事本的后缀名改成bat，双击即可运行，没有任何提示，等待一会儿即可



#### 关于修改service参数名

1. 因为JSP翻译好的源码java文件中用的就是request和response，养成好习惯，这儿也改了，和JSP翻译后的文件一致，避免可能出现的问题



#### MD5加密

1. 了解一下，存储用户的账户密码等个人数据，数据库管理员也看不见，因为使用了数据加密

   

# 方法和配置总览

1. web.xml文件中注册Servlet实现类

   ```xml
   	<servlet>
   		<servlet-name>hello[名字1(随便写)]</servlet-name>
   		<servlet-class>com.bjpowernode.servlet.HelloServlet[带包名的全限定类名]</servlet-class>
   	</servlet>
   	
   	<servlet-mapping>
   		<servlet-name>hello[名字1]</servlet-name>
           <!--不含项目名很重要，不要配错了，加了项目名找不到资源-->
   		<url-pattern>/welcome/user/help[URL后面不含项目名的带斜杠请求路径]</url-pattern>
   	</servlet-mapping>
   ```

2. 获取输出流对象

   ```java
   PrintWriter out=response.getWriter();//response是ServletResponse类中的引用
   out.print(String s);//PrintWriter中的实例方法print专门向浏览器输出字符串
   ```

3. 设置响应内容是普通文本或者html代码

   ```java
   //不设置输出中文字符串到浏览器是乱码，不需要添加utf-8，直接"text/html"就可以输出中文字符
   //这一步必须在获取输出流对象前进行
   void response.setContentType("text/html");
   void response.setContentType("text/html;charset=UTF-8");
   ```

4. 服务器启动时创建Servlet对象

   ```xml
   <load-on-startup>0</load-on-startup>
   ```

5. web.xml文件中配置一个webapp共享配置信息的<context-param></context-param>标签

   ```xml
   <!--也叫上下文的初始化参数，通过ServletContext对象来获取-->    
   	<context-param>
           <param-name>pageSize</param-name>
           <param-value>10</param-value>
       </context-param>
       <context-param>
           <param-name>startIndex</param-name>
           <param-value>0</param-value>
       </context-param>
       <servlet>
   		<servlet-name>hello</servlet-name>
   		<servlet-class>com.bjpowernode.servlet.HelloServlet</servlet-class>
   	</servlet>
   <!--注意：以上的配置信息属于应用级的配置信息，一般一个项目中共享的配置信息会放到以上的标签当中。-->
   <!--如果你的配置信息只是想给某一个servlet作为参考，那么你配置到servlet标签当中即可，使用ServletConfig对象来获取。-->
   ```

6. 获取ServletContext对象

   ```xml
   //方式一
   //这个方式不能在Genericservlet类以外使用，因为config是Genericservlet私有的，只能先通过方法获取
   ServletContext application=config.getsevletContext();
   
   //方式二
   ServletContext application=this.getsevletContext();
   ```

7. 获取web.xml文件中<context-param></context-param>标签上下文初始化参数的方法

   ```java
   //方法一
   String pageSize=application.getInitParameter("pageSize"); 
   
   //方法二
   Enumeration<String> initParameterNames=application.getInitParameterNames(); 
   while(initParameterNames.hasMoreElements()){
       String parameterName=initParameterNames.nextElement();
   }
   ```

8. 获取ServletConfig对象

   ```
   ServletConfig config=this.getServletConfig();
   ```

9. 获取Servlet对象的web.xml中的servlet-name

   ```java
   public String getServletName();
   String name=this.getServletName();
   ```

10. 配置Servlet对象的初始化信息

   ```
   	<init-param>
           <param-name>driver</param-name>
           <param-value>com.mysql.cj.jdbc.Driver</param-value>
       </init-param>
       <init-param>
           <param-name>url</param-name>
           <param-value>jdbc:mysql://127.0.0.1:3306/bjpowernode</param-value>
       </init-param>
   ```

11. 获取Servlet对象的初始化信息

    ```java
    //方法一 
    String url=config.getInitParameter(String name);
    //方法二
    String url=this.getInitParameter(String name);
    
    //获取所有初始化信息
    //方法一
    Enumeration<String> initParameterNames=config.getInitParameterNames(); 
    //方法二
    Enumeration<String> initParameterNames=this.getInitParameterNames(); 
    while(initParameterNames.hasMoreElements()){
        String parameterName=initParameterNames.nextElement();
    }
    ```

12. 获取应用的根路径

    ```java
    //方法一
    String contextPath = application.getContextPath();
    //方法二
    String contextPath = request.getContextPath();
    ```

13. 获取文件的绝对路径

    ```java
    String realPath = application.getRealPath("welcome.html");
    ```

14. 记录日志(有疑惑)

    ```java
    //方法一
    public void log(String message);
    //方法二
    public void log(String message, Throwable t);
    ```

15. 操作应用域数据

    ```java
    //添加
    application.setAttribute("key","123456");
    //获取
    application.getAttribute("key");
    //移除
    application.removeAttribute("key");
    ```

16. 获取用户提交的数据

    ```java
    //获取所有提交数据
    Map<String,String[]> Datas=request.getParameterMap()
        
    //获取所有提交数据的name
    Enumeration<String> names=request.getParameterNames()
        
    //通过name获取value字符串数组
    String[] value=request.getParameterValues(String name)
        
    //通过name获取value字符串数组的第一个字符串元素
    String value=request.getParameter(String name)
    ```

17. 向请求域中存、放、取数据

    ```java
    // 向域当中绑定数据
    request.setsetAttribute(String name, Object obj);
    
    // 从域当中根据name获取数据
    Object value=requeat.getAttribute(String name); 
    
    // 将域当中绑定的数据移除
    request.removeAttribute(String name);
    ```

18. 请求域和响应转发

    ```java
    request.getRequestDispatcher("/welcome").forward(request,response);
    ```

19. 获取客户端的IP地址

    ```java
    String remoteAddr = request.getRemoteAddr();
    ```

20. 设置请求体的字符集

    ```java
    request.setCharacterEncoding("UTF-8");
    ```

21. 设置响应体的字符集

    ```java
    response.setContentType("text/html;charset=UTF-8");
    ```

22. 获取请求的URI

    ```java
    String uri = request.getRequestURI();
    ```

23. 获取servlet path

    ```java
    String servletPath = request.getServletPath();
    ```

    

















# servlet规范



## 系统架构



### 系统架构形式

1. C/S架构
2. B/S架构



### Client / Server架构

> *（客户端 / 服务器）*

1. C/S架构的软件或者系统举例
   
+ QQ
  
2. 特点：需要安装特定的客户端软件。

3. C/S架构的优缺点

   + 优点：

     + 系统速度快、服务器压力小

       ​	>*（数据大部分集成在客户端，少量的数据来自服务器端)*

     + 界面酷炫

       ​	>   *(有专门实现客户端界面的语言)*

     + 体验好

       ​	>*（速度快，界面酷炫）*

     + 系统比较安全

       ​	>*（大量的数据在多个客户端上有缓存，有存储，服务器受损，问题也不大）*

   + 缺点：

     + 升级维护麻烦。

       ​	>*（每个客户端都需要升级，有些软件安装不易）*



### Browser / Server架构

>  *(浏览器 / 服务器）*

1. B/S架构的系统举例
   + http://www.baidu.com

2. B/S架构的系统是一个特殊的C/S系统

​			>	*(只是这个Client是一个固定不变浏览器软件)*

3. B/S架构的优缺点

   + 优点

     + 升级维护成本低。

       ​	> *（只需升级服务器端*）

     + 无需安装特定客户端软件

       ​	>	*(用户只需要打开浏览器输入网址)*

   + 缺点

     + 速度慢

       ​	>*（所有数据都在服务器上，用户每个请求都需要服务器全身心的响应数据，同时在网络中传送大量数据）*

     + 体验差

       ​	>*（浏览器只支持HTML CSS JavaScript，界面不够酷炫，且速度慢）*

     + 不安全

       ​	>*（所有的数据都在服务器上，服务器受损，数据可能全部丢失。）*

4. C/S和B/S架构的选择

   ​		> *不同架构的系统适用于不同的业务场景*

   + C/S 架构

     + 娱乐性软件

       ​	>	*(酷炫,如图像渲染很快)*

   + B/S 结构

     ​			>	*开发B/S结构的系统，就是开发网站 (WEB系统）*

     + 公司内部使用的一些业务软件

       ​	>	*(需要维护成本低)*

       ​	>	*(需求主要是进行数据的维护，不需要很酷炫)*

5. JavaEE

   > JavaEE包括13种规范，Servlet就是其中之一

   - Java包括三大块：
     - JavaSE | Java标准版

       ​	>*（一套写好的标准类库，作为走EE或者ME的基础）*

     - JavaEE | Java企业版

       ​	>（*专门为企业内部提供解决方案的一套（多套）写好的类库，可以开发web系统，帮助完成企业级项目的开发）*

     - JavaME | Java微型版

       ​	>*（一套帮助进行电子微型设备内核程序开发的类库）*

       ​	>	*如机顶盒内核程序，吸尘器内核程序，电冰箱内核程序，电饭煲内核程序...*	



## B/S结构系统的通信原理

### WEB系统的访问

1. 打开浏览器—地址栏输入合法的网址—回车—浏览器展示响应结果

### 通信原理

1. 网址：https://www.baidu.com/ 

   ​	>	也称为URL

2. 域名：www.baidu.com 

   > 地址栏上输入域名回车后，域名解析器会将域名解析成IP地址和端口号等。
   >
   > 解析结果也许是：http://110.242.68.3:80/index.html

3. IP地址：计算机在网络当中的一个身份证号。

   ​	>	*A计算机需要知道B计算机的IP地址才能建立连接进行通信*

   ​	>	*同一个网络中，IP地址唯一*

4. 端口号：代表计算机当中的一个应用或服务（软件）

   	>	*每一个软件启动之后都有一个端口号，同一个计算机上，端口号唯一*
   	>
   	>	*每个主机上能运行多个服务或软件，一个软件上能运行多个系统，资源存在系统中，8080端口代表Tomcat服务器（软件），oa是运行在Tomcat上的系统，index.html是oa下的一个静态资源*
   	>
   	>	*作为服务器的主机的CPU和内存都比较厉害*

5. 一个WEB系统的通信步骤

   + 第一步：用户输入网址

     ​		>	网址也即URL：统一资源定位符，http://IP地址:端口号/（http://www.baidu.com）

   + 第二步：域名解析器进行域名解析：http://110.242.68.3:80/index.html

   + 第三步：浏览器在网络中寻找110.242.68.3这台主机

   + 第四步：定位主机110.242.68.3上的80端口服务器软件

   + 第五步：80端口对应服务器软件得知浏览器想要的资源是：index.html

   + 第六步：服务器软件找到index.html文件，并将index.html文件中的内容直接输出响应到浏览器上。

   + 第七步：浏览器接收来自服务器的代码HTML代码

   + 第八步：浏览器渲染执行HTML代码并展示效果

### 请求和响应

​			>	请求和响应指发送特定流向数据的动作

1. 请求（request）：Browser端向Server端发送数据

2. 响应（response）：Server端向Browser端发送数据



## 关于WEB服务器软件

### 常见WEB服务器软件

1. 常见WEB服务器

   > *WEB服务器只实现了JavaEE中的Servlet和 JSP两个核心规范*

   + Tomcat

     	>	开源免费的轻量级WEB服务器：体积小、运行快
     	>
     	>	Tomcat用Java写的，Tomcat的运行需要JRE，即java运行环境（JDK是java开发工具，内含JRE）

   + Jetty

2. 常见应用服务器

   > *应用服务器实现了JavaEE的所有13个规范*
   >
   > 应用服务器包含WEB服务器

   + JBOSS

     ​	>	*JBOSS中内嵌了一个Tomcat服务器*

   + WebLogic

   + WebSphere

### 配置Tomcat环境变量

1. 配置环境变量CATALINA_HOME、JAVA_HOME

   > *Tomcat服务器中的程序写死了环境变量CATALINA_HOME和JAVA_HOME，必须设置Tomcat和JDK的根为这两个环境变量，并设置相应的path*

   + JAVA_HOME=D:\Java

     ​	>	JAVA_HOME该环境变量会被Tomcat服务器调用

   + PATH=%JAVA_HOME%\bin

     ​	>	*这个path作为JDK的命令路径*

     ​	>	%中间表示环境变量%

   + CATALINA_HOME=D:\dev\apache-tomcat-10.0.12

   + PATH=%CATALINA_HOME%\bin

     ​	>	*这个path作为Tomcat的命令路径*

### Tomcat的常用命令

1. startup.bat	

   > 启动Tomcat服务器，DOS窗口输入startup和startup.bat均可，浏览器访问http://127.0.0.1:8080/展示Tomcat页面即服务器启动成功

   + bat文件是windows系统的批处理文件，含有大量的dos命令，执行bat文件相当于批量执行dos命令。

2. startup.sh

   > 启动Tomcat服务器

   + shell文件是Linux系统的批处理文件，含有大量的shell命令，执行shell文件相当于批量执行shell命令。

     

> 1. 执行startup.bat实际上是执行catalina.bat文件
>
> 2. catalina.bat文件中有一行配置：MAINCLASS=org.apache.catalina.startup.Bootstrap ，该类即main方法所在的类，启动Tomcat服务器就是执行main方法
>
> 3. 初次启动Tomcat在DOS命令窗口和IDEA控制台存在乱码问题，解决办法：将CATALINA_HOME/conf/logging.properties文件中的内容修改如下：java.util.logging.ConsoleHandler.encoding = GBK，只改等号后面即可，该方法Tomcat10可行



3. stop.bat

   > 关闭Tomcat服务器，原文件名为shutdown.bat，直接输入shutdown命令，会导致windows关机，故更改为stop.bat，直接改文件名即可

- ### Tomcat服务器的目录

  1. bin ： 命令文件存放目录

  2. conf： 配置文件存放目录

     ​	>	*（server.xml文件中可以配置端口号，默认Tomcat端口是8080）*

  3. lib：核心程序目录

     ​	>	lib的jar包下都是class文件,Tomcat对Servlet规范的实现类的字节码也在里面

     ​	>	Tomcat的源码在apache-tomcat-10.0.12-src，是单独的一个文件

     ​			核心源码目录：apache-tomcat-10.0.12-src\java\org\apache\catalina\core

  4. logs：日志目录

     ​	>	Tomcat启动等信息都会在该目录下生成日志文件

  5. temp：临时目录

     ​	>	存储临时文件

  6. webapps：web应用存放目录

     ​	>	用来存放大量的webapp（web application：web应用）
  
  7. work：JSP处理文件存放目录
  
     ​	>	用来存放JSP文件翻译之后的java文件及编译之后的class文件。

## WEB应用

### 基本web应用的实现

1. 第一步：在CATALINA_HOME\webapps目录新建一个子目录并起名：oa

   ​		>	所有的webapp必须放在webapps目录下

   ​		>	oa即该webapp的名字

2. 第二步：在oa目录下新建资源文件如：index.html并编写HTML代码

3. 第三步：启动Tomcat服务器

4. 第五步：打开浏览器，在浏览器地址栏上输入URL：http://127.0.0.1:8080/oa/index.html

[^要点1]: URL可以访问多层目录下的资源，如http://127.0.0.1:8080/oa/test/debug/d.html
[^要点2]: 超链接与URL的写法要求及效果一致，超链接可以省略http://127.0.0.1:8080，以/oa开始，如：会自动访问当前主机和端口，注意省略http://127.0.0.1:8080要求超链接所在网页必须由URL打开，不能直接点击html文件打开，直接打开超链接的URL地址会以盘符作为起始路径，而不再以http://127.0.0.1:8080作为起始

```html
<a href="/oa/login.html">user login2</a>
<a href="/oa/test/debug/d.html">d page</a>
```

	>	*静态资源：网页写死在HTML文件中*
	>
	>	*动态资源：编写Java程序连接数据库，这种技术被称为动态网页技术（指数据动态展示，不是指flash动画）*
	>
	>	*一个请求路径代表一个资源，可能是静态资源，也可能是动态资源，动态资源即java小程序，也即一个Servlet程序*

### 动态web应用

​				>>	*四大角色，三大规范*

1. 参与动态web应用的角色
   + 浏览器软件
   + WEB服务器（包括WEB服务器和应用服务器）
   + DBMS
   + webapp（WEB应用由我们JavaWEB程序员开发）

2. 涉及到的规范和协议

   + Servlet规范

     > *webapp和WEB Server开发团队之间遵守的规范，Servlet规范的作用是实现WEB Server和webapp的解耦合，webapp可以在各种WEB Server上运行，SUN制定*
     >
     > *Servlet规范内容包括：*
     >
     > + 接口
     > + 类
     > + 配置文件：配置文件的名字、存放路径、文件内容
     > + web应用的目录结构

   + HTTP协议（超文本传输协议）

     > *Browser和WebServer之间的数据传输协议，由W3C制定*

   + JDBC规范

     > *webapp和DB Server的开发团队间遵守的规范，SUN公司制定*

### 动态web应用的开发(重点)

1. 开发步骤

   + 第一步：webapps目录下新建目录crm

     [^注意]: crm是项目名，可以随便取，crm就是该动态webapp的根

   + 第二步：crm下新建目录：WEB-INF

     [^注意]: WEB-INF是Servlet规范中规定的，必须全部大写，必须写死为WEB-INF

   + 第三步：WEB-INF下新建目录：classes

     [^注意]: classes是Servlet规范中规定的，必须全部小写，必须写死为classes，该目录下存放动态资源编译后的字节码文件

   + 第四步：WEB-INF下新建目录：lib

     [^注意]: lib由Servlet规范规定，必须写死为lib且全部小写，该目录存放第三方jar包，如连接数据库的驱动jar包，不包括JDK的jar包

   + 第五步：WEB-INF下新建文件：web.xml

     [^注意]: web.xml文件是配置文件，该配置文件描述请求路径和Servlet类间的对照关系，一个合格有效的动态webapp，web.xml必须写死，这个文件最好由IDE自动生成或者从其他webapp拷贝，不要手写

     + web.xml文件基本格式

       ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       
       <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                             https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0"
         metadata-complete="true">
       
       </web-app>
       ```
     
+ 第六步：编写必须实现Servlet接口的Java程序
  
  [^注意1]: Servlet接口不在JDK当中，IDEA中也没有，Servlet属于JavaEE，由Oracle提供，Tomcat服务器实现了Servlet规范，Tomcat服务器中有这个接口，是CATALINA_HOME\lib目录下的servlet-api.jar包下的Servlet.class文件，从JakartaEE9开始，Servlet接口的全名变成：jakarta.servlet.Servlet；此前为javax.servlet.Servlet
  [^注意2]: java源代码随便在哪里写，编译后的字节码文件必须放到classes目录下
  
+ 第七步：编译我们编写的Servlet类
  
  [^重点]: 要让Servlet类编译通过，必须要将包含jakarta.servlet.Servlet的servlet-api.jar配到环境变量CLASSPATH中
  [^注意]: 该CLASSPATH和Tomcat服务器运行没关系，只是为了让Servlet类能过编译
  
  >MySQL8的Driver完整类名为：com.mysql.cj.jdbc.Driver;                                                                                                       MySQL5的Driver完整类名为：com.mysql.jdbc.Driver;
     >
     >注册驱动的时候不要写错了
  
+ 第八步：将编译之后的HelloServlet.class文件拷贝到WEB-INF\classes目录下
  
  [^注意]: javac -d . *.java  (带包编译当前路径下的所有java文件，并将字节码存放在当前路径下，.表示字节码存在当前路径，*.java表示当前目录下的所有java文件，只有javac   java文件不能带包编译)
  
+ 第九步：在web.xml文件配置“请求路径”和“Servlet类名”关联的信息。
  
  + 专业描述为：在web.xml文件中注册Servlet类。
  
    ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       
       <web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee
                             https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0"
         metadata-complete="true">
         
       	<servlet>
       		<servlet-name>hello</servlet-name>
       		<servlet-class>com.bjpowernode.servlet.HelloServlet</servlet-class>
       	</servlet>
       	
       	<servlet-mapping>
       		<servlet-name>hello</servlet-name>
       		<url-pattern>/welcome/user/help</url-pattern>
            <!--注意：多个路径可以对应一个静态资源或动态资源，已测试验证-->
            <url-pattern>/welcome/vipuser/help</url-pattern>
       	</servlet-mapping>
       	
       </web-app>
    ```
  
+ 第十步：启动Tomcat服务器
  
+ 第十一步：打开浏览器，在地址栏输入一个url如：http://127.0.0.1:8080/crm/welcome/user/help
  
  [^注意]: 这个URL的项目名后面的路径必须和web.xml文件中的url-pattern一致，服务器接收请求，截取URL的路径/crm/welcome/user/help，找到crm项目，由/welcome/user/help这个伪路径在xml文件中找到<servlet-name>下的名字hello，并由hello找到并执行对应的Servlet类，通过反射机制创建该类对象，然后通过对象调用该对象的service方法

>[^要点1]: xml文件中的url-pattern不带项目名
>[^要点2]: html页面必须且只能放到WEB-INF目录外面
>[^要点3]: Tomcat已经写好并执行main方法，Javaweb程序员只需要编写Servlet实现类并注册该实现类到配置文件中，不需要写main方法
>[^要点4]: 合法的webapp目录结构
>
>```
>webapproot
>     |------WEB-INF
>     		  |------classes(存放Servlet类的字节码)
>     		  |------lib(第三方jar包)
>     		  |------web.xml(注册Servlet)
>     |------html
>     |------css
>     |------javascript
>     |------image
>....
>```

### 关于JavaEE的版本

1. JavaEE最高版本是 JavaEE8，此后Oracle将JavaEE规范捐献给Apache，Apache把JavaEE改名叫做 jakarta EE

2. JavaEE8版本升级之后的版本叫做JakartaEE9，对应的Servlet类名是：jakarta.servlet.Servlet ，包名都换了

3. 之前使用javax.servlet.Servlet的项目无法直接部署到Tomcat10+版本上，因为Tomcat无法识别javax.servlet

   > 但是Tomcat官网提供了工具migration tool来解决识别不了javax这个问题

4. Tomcat9及Tomcat9之前的版本中还是能够识别javax.servlet这个包。

### 关于JDK的版本

1. 环境配好闪退，检查JDK版本，JDK8只支持到Tomcat10.0.x-10.0.21之间，Tomcat10.1-M15需要JDK11以上

### Servlet实现类连接数据库

- Servlet是Java程序，在Servlet的service方法中编写JDBC代码连接数据库。

  [^注意]: 在一个webapp中去连接数据库，需要将驱动jar包放到WEB-INF/lib目录下。
  [^要点1]: Servlet接口中有五个抽象方法，需要其子类实现，列举如下

  ```java
  public void service(ServletRequest request,ServletResponse response) 
  		throws ServletException,IOException{}
  
  public void init(ServletConfig config) throws ServletException {
  }
  
  public void destroy(){
  }
  
  public String getServletInfo(){
      return "";
  }
  
  public ServletConfig getServletConfig(){
      return null;
  }
  ```

  [^要点2]: 输出流PrintWriter由ServletResponse类中的引用.getWriter()获取，PrintWriter中的print方法负责输出字符串到浏览器，该输出流不需要手动刷新和关闭，由Tomcat维护，程序员只管使用

  ```java
  PrintWriter out=response.getWriter();
  ```

  [^要点3]: 通过response.setContentType("text/html")设置响应的内容是普通文本或html代码，如果不设置，不输出html页面的标准格式而输出单个html标签不会起作用，只会显示字符串，设置以后会显示相应的标签，在IDEA中验证一下没有完整代码能否显示标签以及不加charset=utf-8是否会中文乱码,注意：设置响应类型必须在获取获取输出流之前设置才有效

  ```java
  void response.setContentType("text/html");
  void response.setContentType("text/html;charset=UTF-8");
  ```

  

## 使用IDEA开发Servlet程序

### 开发步骤

1. 第一步：创建New Project，在空工程下新建Module【模块】

   >这里新建的是一个普通的JavaSE模块，这里不要直接建Java Enterprise模块

2. 第二步：让Module变成JavaEE的模块,让Module符合JavaEE规范。

   + Module上右键—Add Framework Support—Web Application

     [^要点]: IDEA工具会根据Web Application模板生成一个web目录，这个目录就代表webapp的根

3. 第三步：将CATALINA_HOME/lib/servlet-api.jar和jsp-api.jar添加到IDEA的classpath当中

   + File --> Project Structrue --> Modules --> Dependencies-->+ 加号 --> 添加JSP和Servlet的jar包

     [^注意]: 不添加这两个jar包，Servlet类IDEA找不到

4. 第四步：编写Servlet的实现类（StudentServlet）	

   [^要点]: 实现Servlet中的五个抽象方法

5. 第五步：在Servlet当中的service方法中编写业务代码

6. 第六步：在WEB-INF目录下新建子目录：lib，将连接数据库的驱动jar包放到lib目录下。

   [^要点]: 不添加这个驱动jar包到lib目录下，Driver类找不到

7. 第七步：在web.xml文件中完成Servlet接口的实现类StudentServlet的注册。

8. 第八步：有可能静态资源中有超链接，写点HTML文件

   > 所有的HTML文件不能放到WEB-INF目录里面，只能放到WEB-INF目录外面	

9. 第九步：让IDEA工具去关联Tomcat服务器。

   [^注意]: 关联过程当中将webapp部署到Tomcat服务器当中。

   >+ IDEA右上角绿色小锤子右边Add Configuration—左上角加号，点击Tomcat Server --> local，仅第一次添加时执行，否则会部署两个Tomcat服务器
   >
   >+ 在弹出的界面中设置服务器Server的参数，一般都是默认的，仅第一次添加时操作，之后都不咋动
   >
   >+ 在当前窗口中有一个Deployment，点击加号用以部署webapp
   >
   >+ Deployment窗口下拉修改 Application context，填入的就是项目名
   >
   > [^?疑问]: 如何从服务器移除某个webapp

10. 第十步：开发中建议点击绿色小虫子采用debug模式启动Tomcat服务器

11. 第十一步：打开浏览器，在浏览器地址栏上输入URL访问资源

## Servlet接口

### Servlet对象的生命周期

> 这里的Servlet对象实际是Servlet接口的实现类
>
> Servlet对象的生命周期包括：对象创建—实例方法的调用—对象销毁
>
> Tomcat服务器又称为：WEB容器【WEB Container】，Servlet对象的生命周期由WEB容器全权负责，WEB容器创建的Servlet对象都会被放到一个HashMap集合中被WEB容器统一管理，这个集合中存储了Servlet对象和请求路径之间的关系(key为路径，value为引用)，程序员自己new的Servlet对象不会被放入到这个集合中。
>
> Javaweb程序员创建的Servlet对象并不会被Tomcat统一管理

1. Servlet对象的创建

   + 默认情况下服务器在启动的时候Servlet对象并不会被实例化

     > 用户发送请求前就创建好所有的Servlet对象，会导致Servlet对象没起作用且耗费内存资源

   + 默认情况下，用户发送第一次请求—Servlet对象被实例化—实例化后Tomcat马上调用Servlet对象的init方法—init方法执行之后，Tomcat马上调用Servlet对象的service方法。

     [^要点1]: Servlet对象响应第一次请求后不会被销毁，此后每次收到请求都只会去执行service方法，该Servlet对象会在服务器关闭的时候才被销毁
     [^要点2]: Servlet对象是单实例的，因为自始至终Tomcat只创建了一个，但是Servlet类并不符合单例模式。被称为假单例真单例模式，构造方法是私有化的。
     [^要点3]: Servlet对象整个生命周期中，无参构造、init方法、destory方法只被调用一次；service方法收到一次请求就会调用一次
     [^要点4]: Tomcat通过反射机制调用无参构造方法实例化Servlet对象，Servlet类中没有无参构造无法实例化Servlet对象，浏览器报500错误，表示服务器内部错误，Servlet开发中，不建议定义构造方法，构造不当一不小心就会导致Servlet对象无法实例化
     [^要点5]: init方法与无参构造几乎同时执行且均只执行一次，但是init方法单独设置而不默许程序员写进无参构造方法中就是避免手动编写构造方法，导致Servlet对象无法实例化
     [^?Q]: 什么是单实例，单例模式是什么，假单例是什么，构造方法私有化什么意思?

   + 服务器启动时即创建Servlet对象的设置方法

     在web.xml文件中的servlet标签中添加<load-on-startup>整数</load-on-startup>子标签能让服务器在启动的时候就创建Servlet对象，整数越小服务器启动时Servlet对象创建的优先级越高，可以是0

     ```xml
     <servlet>
         <servlet-name>aservlet</servlet-name>
         <servlet-class>com.bjpowernode.javaweb.servlet.AServlet</servlet-class>
         <load-on-startup>1</load-on-startup>
     </servlet>
     <servlet-mapping>
         <servlet-name>aservlet</servlet-name>
         <url-pattern>/a</url-pattern>
     </servlet-mapping>
     ```

2. Servlet对象的销毁
   + 服务器关闭时要销毁Servlet对象的内存，在销毁Servlet对象的内存前会自动调用Servlet对象的destroy方法。当destroy方法执行结束后，Servlet对象的内存才被Tomcat释放。

### Servlet接口中的方法

1. Servlet接口中有5个抽象方法，其中常用的有3个

   [^service方法]: 处理用户请求的核心方法
   [^init方法]: 只需要执行一次的初始化操作，如：初始化数据库连接池，初始化线程池....
   [^destory方法]: 在对象销毁之前，执行destory方法来进行资源(流或数据库连接)的关闭和数据保存

   


### GenericServlet

#### 适配器设计模式Adapter

1. 编写一个Servlet类直接实现Servlet接口的缺点
   + 大部分情况下只需要频繁使用service方法，其他的也需要实现但不使用，代码丑陋

2. 编写一个标准通用的GenericServlet类直接实现Servlet接口作为适配器

   + 编写一个标准通用的抽象类GenericServlet类直接实现Servlet接口，实现Servlet中的五个方法，其中只有service方法被设置为抽象方法，以后编写的所有Servlet类继承GenericServlet，必须重写service方法，其他方法按需重写

   + GenericServlet类实现了Servlet、Servletconfig和java.io.Serializable接口

     [^要点1]: GenericServlet中有两个init方法，即init方法的重载，一个含参，一个无参，Tomcat会调用含参init方法时会传入一个主方法创建的ServletConfig对象，在含参init方法中调用无参init方法，一般情况下，程序员在Servlet对象中只需要重写无参init方法即可，效果和直接实现Servlet接口效果相同

     ```java
      	@Override
         public void init(ServletConfig config) throws ServletException {
         //this.config是GenericServlet类中定义私有不可序列化的成员变量
             this.config = config;
             this.init();
         }
     	//这个init方法供子类重写
         public void init() throws ServletException {
             // NOOP by default
         }
     ```

     [^要点2]: Servlet里面有ServletConfig getServletConfig()方法，可以在实现类中使用this.getServletConfig()来获取ServletConfig对象


> 注意：实际上程序员编写Servlet对象的时候，不会直接继承GenericServlet类，而是直接继承HttpServlet类。因为B/S架构的系统是基于HTTP超文本传输协议的，Servlet规范中提供了继承GenericServlet类的HttpServlet类专门处理HTTP协议。整个继承结构如下：
>
> ```java
> jakarta.servlet.Servlet
> jakarta.servlet.GenericServlet implements Servlet, ServletConfig,java.io.Serializable
> jakarta.servlet.http.HttpServlet extends GenericServlet
> //程序员开发的Servlet对象直接继承httpServlet
> ```
>
> 其中Servlet是接口，GenericServlet和HttpServlet都是抽象类(因为最后Service抽象方法必须实现)，GenericServlet中的Service方法与Servlet保持一致，没有变化；HttpServlet中的Service方法不是抽象的
>
> HttpServlet实现了方法service(ServletRequest req , ServletResponse res) 并在该方法中调用了service的重载方法service(HttpServletRequest request , HttpServletResponse response)
>
> request和response分别是由req和res向下强转来的

### ServletConfig

1. ServletConfig接口

   + 配置信息对象，一个Servlet对象对应一个ServletConfig对象，ServletConfig接口是Servlet规范中的一员

   + ServletConfig对象默认情况下由Tomcat服务器在用户发送第一次请求时创建

   + Tomcat会自动解析web.xml文件，将<servlet></servlet>标签中的配置信息自动包装到ServletConfig对象中

     ```java
     //多态，实际创建的实现类是org.apache.catalina.core.StandardWrapperFacade()
     //该实现类由Tomcat实现的,换个服务器类名就变了
     ServletConfig servletConfig = new org.apache.catalina.core.StandardWrapperFacade();
     ```

2. ServletConfig对象中包装的信息

   + 包装的信息指的不是servlet-name或servlet-class

   + 包装的是单独给对应的Servlet对象配置的<init-param></init-param>标签中的信息,可以配置多个<init-param>标签

     ```xml
     <!--ServletConfig对象中封装了web.xml文件中的<servlet>标签中的<init-param>标签中的配置信息-->
     	<servlet>
             <servlet-name>logservlet</servlet-name>
             <servlet-class>com.atlisheng.javaweb.servlet.Loginservlet</servlet-class>
             <!--通过init-param标签配置Servlet对象的初始化信息-->
             <init-param>
                 <param-name>driver</param-name>
                 <param-value>com.mysql.cj.jdbc.Driver</param-value>
             </init-param>
             <init-param>
                 <param-name>url</param-name>
                 <param-value>jdbc:mysql://127.0.0.1:3306/bjpowernode</param-value>
             </init-param>
             <init-param>
                 <param-name>user</param-name>
                 <param-value>root</param-value>
             </init-param>
             <init-param>
                 <param-name>password</param-name>
                 <param-value>Haworthia715</param-value>
             </init-param>
         </servlet>
     ```

3. ServletConfig接口中只有四个方法

   + 获取Servlet对象初始化参数

     ```java
     //通过<servlet>标签中子标签<init-param>的初始化参数的param-name获取value
     //具体实现
     //方法一 
     public String getInitParameter(String name); 
     //方法二
     String url=this.getInitParameter(String name);
     
     //获取<servlet>标签中子标签<init-param>的所有初始化参数的param-name
     //这个方法实际上是在ServletConfig的另一个实现类StandardWrapper中实现的
     //在StandardWrapperFacade(实际的配置信息对象)中引入StandardWrapper对象并调用其getInitParameterNames()获得的集合
     public Enumeration<String> getInitParameterNames(); 
     //具体实现
     //方法一
     Enumeration<String> initParameterNames=config.getInitParameterNames(); 
     //方法二
     Enumeration<String> initParameterNames=this.getInitParameterNames(); 
     //遍历返回集合
     //Enumeration<String>总共就这两个方法
     while(initParameterNames.hasMoreElements()){//Enumeration<String>中的实例方法hasMoreElements()，有就返回true
         String parameterName=initParameterNames.nextElement();//集合实例方法nextElement()获取当前元素
     }
     ```

   + 获取ServletContext对象

     ```java
     public ServletContext getServletContext(); 
     
     //具体实现
     //这个方式不能在Genericservlet类以外只能用this调用
     //因为config是Genericservlet私有的，只能先通过方法获取config才能使用以下代码
     ServletContext application=config.getsevletContext();
     ```
   
   + 获取ServletConfig所在Servlet类的web.xml文件中的servlet-name
   
     ```java
     public String getServletName(); 
     ```
   
   > Servlet实现类继承了GenericServlet，且GenericServlet实现方法时调用的是config中的方法返回的结果，以上四种方法Servlet实现类可以通过this去调用，返回结果和config直接调用是一样的
   >
   > config表面是ServletConfig，实际是StandardWrapperFacade

### ServletContext

1. ServletContext被称为Servlet上下文对象(或Servlet对象的四周环境对象）
   + 一个ServletContext对象通常对应的是一个web.xml文件，一个应用对应一个ServletContext对象，webapps下有100个webapp，那么就有100个ServletContext对象，同一个webapp中，所有的Servlet对象都共享同一个ServletContext对象，ServletContext对象是应用级对象
   + ServletContext对象在服务器启动阶段启动webapp的时候创建，在服务器关闭的时候销毁，由Tomcat完成
   + ServletContext是一个接口，Tomcat服务器对ServletContext接口进行了实现，实现类是核心包的ApplicationContextFacade

2. ServletContext接口中部分常用方法(不包含应用域数据操作)

   + 获取上下文初始化参数

     ```java
     //获取上下文初始化参数的方法名和获取Servlet对象初始化参数一样，引用不同
     ///方法一
     //通过单个param-name如"pageSize"获取value"10"
     public String getInitParameter(String name); 
     //具体实现
     String pageSize=application.getInitParameter("pageSize"); 
     
     //方法二
     public Enumeration<String> getInitParameterNames(); 
     //具体实现
     Enumeration<String> initParameterNames=application.getInitParameterNames(); 
     //遍历返回集合
     while(initParameterNames.hasMoreElements()){
         String parameterName=initParameterNames.nextElement();
     }
     ```

   + 获取应用的根路径（非常重要）

     ```java
     //应用的根路径指的是/项目名，如/src
     //java源代码中一些地方可能会需要应用的根路径，如相应超链接，JSP文件超链接,这个方法可以动态获取应用的根路径
     public String getContextPath();
     //具体用法
     String contextPath = application.getContextPath();
     ```
     
     不要将应用的根路径写死，因为文件一多就会搞忘项目在最终部署的时候起的什么名字。
     
   + 获取文件的绝对路径（真实路径）

     ```java
     //这里的String path指的是项目根目录下不带根目录/src的相对路径，如welcome.html
     public String getRealPath(String path);
     //具体用法
     String realPath = application.getRealPath("welcome.html");
     //测试结果
     //C:\Users\Earl\IdeaProjects\javaweb\out\artifacts\test_Servlet02_war_exploded\welcome.html
     ```

   + 记录日志

     ```java
     public void log(String message);
     //具体实现
     application.log("对不起，您已超速");
     
     public void log(String message, Throwable t);
     //Throwable t指的是实现可抛出接口的实现类，如RuntimeException
     //具体实现
     application.log("对不起，您未成年，请绕行",new RuntimeException("小屁孩，快走开，不适合你"))
         
     /**
     两条日志都存在正确的文件中，日志信息分别如下所示:
     06-Mar-2023 00:30:10.256 信息 [http-nio-8080-exec-5] org.apache.catalina.core.ApplicationContext.log 超速了大哥!
     06-Mar-2023 00:30:10.256 严重 [http-nio-8080-exec-5] org.apache.catalina.core.ApplicationContext.log 未成年出去!
     java.lang.RuntimeException: 那个小孩干啥呢!
     以下是打印异常信息
     */
     ```
     
     > 这些日志信息记录到Tomcat的logs目录下的localhost.2021-11-05.log中
     >
     > Tomcat的logs目录下的日志文件类型
     >
     > + catalina.2021-11-05.log                               服务器端java程序运行的控制台信息
     > + localhost.2021-11-05.log                             ServletContext对象的log方法记录的日志信息
     > + localhost_access_log.2021-11-05.txt         访问日志
     >
     > 使用Tomcat裸奔日志文件存在D:\dev\apache-tomcat-10.0.12\logs中
     >
     > 使用IDEA部署日志文件存在C:\Users\Earl\AppData\Local\JetBrains\IntelliJIdea2021.1\tomcat\c8179fe0-f5ba-4f0d-abb9-cf7757f99ebf\logs中

3. ServletContext对象还被称为应用域

   + 可被存入应用域的数据特点

     + 数据量小

       > 应用域生命周期较长，服务器关闭时才会被销毁。数据量较大，太占堆内存，影响服务器的性能

     + 数据被所有用户共享

       > 不共享可以放在<init-param>标签中
       
     + 数据几乎不修改
     
       所有用户共享的数据涉及到修改操作存在线程并发安全问题，所以放在应用域中的数据一般都是只读的

   + 应用域的优点
   
     + 应用域相当于一个缓存，缓存中的数据使用时不需要从数据库或者硬盘中获取，会大大提升效率
   
   + 应用域中操作数据的常用方法
   
     + 向ServletContext应用域中存数据
   
       ```java
       public void setAttribute(String name, Object value);
       //具体实现
       application.setAttribute("key","123456");
       ```
   
     + 从ServletContext应用域中取数据
   
       ```java
       public Object getAttribute(String name);
       //具体实现
       application.getAttribute("key");
       ```
   
     + 删除ServletContext应用域中的数据
   
       ```java
       public void removeAttribute(String name);
       //具体实现
       application.removeAttribute("key");
       ```
   
     >应用域底层用Map集合存储数据，操作数据与Map集合的数据操作方式极其相似
     >
     >```java
     >// 以上的操作类似于Map集合的操作。
     >Map<String, Object> map;
     >map.put("name", obj); // 向map集合中放key和value
     >Object obj = map.get("name"); // 通过map集合的key获取value
     >map.remove("name"); // 通过Map集合的key删除key和value这个键值对。
     >```
     >
     >检验上下文初始化参数是否在应用域中？不能通过getAttribute(String name)直接从应用域中取出上下文初始化参数，遗留问题，上下文初始化参数是否在应用域或缓存中
     >
     >```java
     >System.out.println(application.getAttribute("pageSize"));//null,不能用名字在应用域中取上下文初始化参数
     >```

### 缓存机制小结

1. 字符串常量池

   > "abc" 先在堆内存中的字符串常量池中查找，如果有，直接拿来用。如果没有则新建，然后再放入字符串常量池

2. 整数型常量池

   > [-128 ~ 127] 一共256个Integer类型的引用，放在堆内存中的整数型常量池，数字没有超出这个范围，直接从常量池中取

3. 连接池(Connection Cache)

   > 连接指java语言连接数据库的连接对象：java.sql.Connection
   >
   > JVM和MySQL数据库各自都是一个进程。进程之间建立连接打开通道很耗费资源的。可以提前先创建好N个Connection连接对象放到一个集合中，这个放有Connection对象的集合就称为连接池。
   >
   > 每次用户连接时省去新建连接对象的环节，直接从连接池获取连接对象，大大提升访问效率。且连接池规定最大最小连接数，如果每个用户访问都建连接对象，连接对象太多数据库会崩掉，连接池也是对数据库的一种保护方式

4. 线程池

   >Tomcat服务器本身就是支持多线程的,Tomcat启动时会先创建好N多个线程Thread对象放到集合当中，该集合称为线程池。接收到用户请求后，就会直接从线程池中拿线程对象，效率比较高
   >
   >所有的WEB服务器或应用服务器，都支持多线程，都有线程池机制

5. redis

   >NoSQL数据库，非关系型数据库，也称缓存数据库

6. ServletContext应用域

   >向应用域中存储数据，也等于是将数据存放到缓存cache当中
   
7. mongoDB

### HTTP协议

[^协议]: 协议就是一套规范或标准，遵守该规范可以沟通无障碍，如普通话

1. HTTP协议
   + HTTP协议是W3C制定的一种超文本传输协议,是一种通信协议，提前制定好发送消息的消息模板
   + HTTP协议不但可以传送普通字符串，还支持传递声音、视频、图片等流媒体信息
   + Browser和Server通过遵守HTTP协议实现了Browser和Server之间的解耦合，即无所谓什么牌子的服务器和浏览器
   + HTTP协议包括请求协议和响应协议

2. 超文本
   + 不是普通文本，比如流媒体：声音、视频、图片等

3. 请求协议

   + 这套标准中规定了浏览器向WEB服务器发送数据需要遵守的具体格式，是一套标准

   + HTTP请求协议包括4部分内容

     >1. 请求行		请求方式+URI+HTTP协议版本号(HTTP/1.1)【三者用空格隔开】
     >
     > [^注意]: URL是统一资源定位符，代表网络中某个资源，通过URL可以定位到该资源；URI是统一资源标识符，代表网络中某个资源的名字，通过URI是无法定位资源的；URL是包含URI的，URL去掉http://localhost:8080就是URI/servlet05/index.html 
     >
     >2. 请求头
     >
     >   >+ Host:请求的主机和端口
     >   >+ 浏览器信息
     >   >+ 平台信息
     >   >+ cookie等信息
     >   >+ ....
     >
     >3. 空白行        空白行用来区分“请求头”和“请求体”
     >
     >4. 请求体        浏览器向服务器发送的具体数据

   + 请求方式有七种

     >- get（常用）
     >- post（常用）
     >- delete
     >- put
     >- head
     >- options
     >- trace

   + GET请求具体报文

     ```
     GET /servlet05/getServlet?username=lucy&userpwd=1111 HTTP/1.1                           请求行
     Host: localhost:8080                                                                    请求头
     Connection: keep-alive
     sec-ch-ua: "Google Chrome";v="95", "Chromium";v="95", ";Not A Brand";v="99"
     sec-ch-ua-mobile: ?0
     sec-ch-ua-platform: "Windows"
     Upgrade-Insecure-Requests: 1
     User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36
     Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
     Sec-Fetch-Site: same-origin
     Sec-Fetch-Mode: navigate
     Sec-Fetch-User: ?1
     Sec-Fetch-Dest: document
     Referer: http://localhost:8080/servlet05/index.html
     Accept-Encoding: gzip, deflate, br
     Accept-Language: zh-CN,zh;q=0.9
                                                                                             空白行
                                                                                             请求体
     ```

   + POST请求具体报文

     ```
     POST /servlet05/postServlet HTTP/1.1                                                  请求行
     Host: localhost:8080                                                                  请求头
     Connection: keep-alive
     Content-Length: 25
     Cache-Control: max-age=0
     sec-ch-ua: "Google Chrome";v="95", "Chromium";v="95", ";Not A Brand";v="99"
     sec-ch-ua-mobile: ?0
     sec-ch-ua-platform: "Windows"
     Upgrade-Insecure-Requests: 1
     Origin: http://localhost:8080
     Content-Type: application/x-www-form-urlencoded
     User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36
     Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
     Sec-Fetch-Site: same-origin
     Sec-Fetch-Mode: navigate
     Sec-Fetch-User: ?1
     Sec-Fetch-Dest: document
     Referer: http://localhost:8080/servlet05/index.html
     Accept-Encoding: gzip, deflate, br
     Accept-Language: zh-CN,zh;q=0.9
                                                                                           空白行
     username=lisi&userpwd=123                                                             请求体
     ```

4. 响应协议

   + 这套标准中规定了WEB服务器向浏览器发送数据需要遵守的具体格式，是一套标准

   + HTTP响应协议包括4部分内容

     >1. 状态行 	协议版本号（HTTP/1.1）+响应状态码+状态的描述信息 【三者用空格隔开】
     >
     > >状态码:HTTP协议中规定不同的响应结果对应不同的号码
     > >
     > >+ 200 表示请求响应成功，正常结束
     > >
     > >+ 404表示访问的资源不存在，前端错误，要么路径写错了，要么对应资源没启动成功
     > >
     > >+ 405表示请求方式与后端请求的处理方式不一致（如get请求，结果后端是dopost）,前端请求方式后端说了算
     > >
     > >[^注意]:4开始的一般是前端错误，5开始的一般是后端错误
     > >
     > >
     > >状态的描述信息：
     > >
     > >+ ok 表示正常成功结束
     > >+ not found 表示资源找不到
     >
     >2. 响应头
     >
     > >+ 响应的内容类型
     > >+ 响应的内容长度
     > >+ 响应的时间
     >
     >3. 空白行      用来分隔“响应头”和“响应体”
     >
     >4. 响应体      响应的正文,服务器传送过来的HTML代码，被浏览器解释执行呈现画面

   - HTTP响应协议的具体报文：

     ```
     HTTP/1.1 200 ok                                     状态行
     Content-Type: text/html;charset=UTF-8               响应头
     Content-Length: 160
     Date: Mon, 08 Nov 2021 13:19:32 GMT
     Keep-Alive: timeout=20
     Connection: keep-alive
                                                         空白行
     <!doctype html>                                     响应体
     <html>
         <head>
             <title>from get servlet</title>
         </head>
         <body>
             <h1>from get servlet</h1>
         </body>
     </html>
     ```

5. 查看协议内容

   + 查看协议内容的方法

     打开谷歌浏览器(IE不行)—F12—找到network—点击all查看协议的具体内容

   + 区分请求方式

     + 到目前为止只有form表单一种情况发送POST请求，且需要设置form标签中的属性method="post"

     + 其他情况一律都是get请求

       >- 地址栏上直接输入URL回车
       >- 超链接
       >- form表单提交数据时没有写method属性默认是get请求，或者设置method="get"也是get请求
       >- ....

6. GET请求和POST请求的区别

   - get请求在“请求行”上发送请求数据

       > get请求请求协议的请求行在URI后面添加一个？，？后面跟请求数据，请求数据会回显在地址栏上
       >
       > 请求行 : http://localhost:8080/servlet05/getServlet?username=zhangsan&userpwd=1111

   - get请求只能发送普通字符串，且字符串长度有限制，无法发送大数据量

   - get请求比较适合想要获得服务器数据的请求

   - get请求支持缓存

       > 任何一个get请求的“响应结果”都会被浏览器缓存起来，在浏览器缓存当中：每个get请求的路径都对应一个资源
       >
       > 只要发送get请求，浏览器先从缓存中找，找不到才去服务器获取，缓存机制主要为了提高用户体验
       >
       > 不想走缓存只需要每次请求路径都不同，在路径后面加一个时间戳即可，如https://n.sinaimg.cn/finance/590/w240h350/20211101/7cabc342ff5b9dc018b4b00cc.jpg?t=系统毫秒数

   - post请求在请求体当中发送请求数据。

       > post发送的数据，不会回显到浏览器的地址栏上，更安全

   - post请求可以发送任何类型的数据，包括普通字符串，视频、声音、图片等流媒体信息，理论上没有数据长度限制，可以用于大数据量发送

   - post请求比较适合打个大包向服务器传送数据

   - post请求不支持缓存，服务器“响应的结果”不会被浏览器缓存起来


  >对服务器来说
  > 
  >   + get请求是绝对安全的，因为get请求只是为了从服务器上获取数据，不会对服务器造成威胁
  >  + post请求是危险的，因为post请求是向服务器提交数据的，某些数据对服务器来说是很危险的，大部分拦截请求的情况都是拦截（监听）post请求。

7. GET请求和POST请求的选择

   + 想从服务器上获取资源使用GET请求，为了向服务器提交数据使用POST请求
   + 大部分的form表单提交都是收集保存修改用户的信息，一般都是post方式
   + 敏感信息如提交用户账号密码使用post请求，避免地址栏回显，不会把用户信息暴露在地址栏
   + 文件上传一定是post请求
   + 其他情况都可以使用get请求

   >不管是那种请求，最后提交的数据格式是完全相同的，格式都为name=value&name=value&name=value&name=value



### 模板方法设计模式

1. 设计模式

   + 设计模式是某种问题可被重复使用的固定解决方案

   + 常见设计模式

     + [^GoF设计模式]: 即常说的23种设计模式，【Gang of Four】四人组提出的设计模式

       >+ 模板方法设计模式	
       >+ 单例模式
       >+ 工厂模式
       >+ 代理模式
       >+ 门面模式
       >+ 责任链设计模式
       >+ 观察者模式
       >+ .....

     + [^JavaEE设计模式]: 

       >- DAO
       >- DTO
       >- VO
       >- PO
       >- pojo
       >- ....

2. 模板方法设计模式

   - 在模板类的模板方法中定义核心算法骨架，具体的实现步骤设计成抽象方法延迟到子类当中完成。

   - 模板类通常是一个抽象类，一般通过final修饰(也可以不用final修饰)的模板方法定义核心算法，在模板方法中调用子类继承实现的抽象方法(实际也可能不设置为抽象方法，由用户手动重写)
   - 模板设计方法举例：老师和学生的作息差不多，定义一个学校作息类，定义好所有的作息方法，并定义一个模板方法调用所有的日常作息，把学生和老师不同的日常作息设置成抽象方法，由学生类和老师类单独实现，再让老师类和学生类都去继承这个学校作息类并重写各自的差异作息方法

### HttpServlet

1. HttpServlet类概述
   + HttpServlet类专为HTTP协议准备，相较GenericServlet更适合HTTP协议下的开发
   + jakarta.servlet.http.HttpServlet类在jakarta.servlet.http包下

2. HttpServletRequest

   + HttpServletRequest简称request对象，request对象中封装了请求协议的全部内容

   + WEB服务器会解析“请求协议”中的全部数据并将这些数据全部封装到request对象中

   + 程序员从HttpServletRequest可以获取请求协议中的数据

   + HttpServletRequest中的getMethod()方法可获取请求的方式

     ```java
     String method=request.getMethod();//获取的是七种请求方式之一，可能GET POST PUT DELETE HEAD OPTIONS TRACE
     //HttpServlet中声明定义了相应的七个常量
     ```

3. HttpServletResponse
   + HttpServletResponse是专门用来响应HTTP协议到浏览器的

4. HttpServlet源码粗略分析

   + HttpServlet中的doGet和doPost等方法不是抽象方法，需要按需手动重写

     > 这儿与请求方式对应的方法被设计成有请求不执行相应方法会抛sendMethodNotAllowed(requset, response, msg)
     >
     > msg是对应特定方法的异常信息字符串

   + Servlet对象一般重写的init方法仍是GenericServlet改良后的init无参构造方法，init方法没有被HttpServlet影响

   + HttpServlet的部分核心源码

     + Tomcat调用的service还是从Servlet继承来的service(ServletRequest req, ServletResponse res)，在该service方法中去调用重载后的service(HttpServletRequest req, HttpServletResponse resp)方法，重载后的service方法获取请求方式并与内置常量进行对比分配响应请求的方法，在Servlet对象中需要重写的只是重载后service方法调用的对应请求方式的doGet或doPost方法
     + 重载后的service方法支持重写，但是设计好的针对HTTP协议的核心算法会完全失效

     ```java
     // HttpServlet模板类。
     public abstract class HttpServlet extends GenericServlet {
         // 用户只要发送一次请求,这个被子类继承的service(req,res)就会执行一次
         @Override
         public void service(ServletRequest req, ServletResponse res)
             throws ServletException, IOException {
             HttpServletRequest  request;
             HttpServletResponse response;
             try {
                 // ServletRequest和ServletResponse向下转型为HttpServletRequest和HttpServletResponse
                 request = (HttpServletRequest) req;
                 response = (HttpServletResponse) res;
             } catch (ClassCastException e) {
                 throw new ServletException(lStrings.getString("http.non_http"));
             }
             // 调用重载的service方法
             service(request, response);
         }
         
         // 这个重载的service是一个模板方法,该方法中定义核心算法骨架，具体的实现步骤延迟到子类中去完成。
         protected void service(HttpServletRequest req, HttpServletResponse resp)
             throws ServletException, IOException {
             // 获取请求方式
             String method = req.getMethod();
             
             // 如果请求方式是GET请求，则执行doGet方法。
             if (method.equals(METHOD_GET)) {
                 long lastModified = getLastModified(req);
                 if (lastModified == -1) {
                     doGet(req, resp);
                 } else {
                     long ifModifiedSince;
                     try {
                         ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                     } catch (IllegalArgumentException iae) {
                         ifModifiedSince = -1;
                     }
                     if (ifModifiedSince < (lastModified / 1000 * 1000)) {
                         maybeSetLastModified(resp, lastModified);
                         doGet(req, resp);
                     } else {
                         resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                     }
                 }
             } else if (method.equals(METHOD_HEAD)) {
                 long lastModified = getLastModified(req);
                 maybeSetLastModified(resp, lastModified);
                 doHead(req, resp);
             } else if (method.equals(METHOD_POST)) {
                
                 // 如果请求方式是POST请求，则执行doPost方法。
                 doPost(req, resp);
             } else if (method.equals(METHOD_PUT)) {
                 doPut(req, resp);
             } else if (method.equals(METHOD_DELETE)) {
                 doDelete(req, resp);
             } else if (method.equals(METHOD_OPTIONS)) {
                 doOptions(req,resp);
             } else if (method.equals(METHOD_TRACE)) {
                 doTrace(req,resp);
             } else {
                 String errMsg = lStrings.getString("http.method_not_implemented");
                 Object[] errArgs = new Object[1];
                 errArgs[0] = method;
                 errMsg = MessageFormat.format(errMsg, errArgs);
     
                 resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
             }
         }
         
         protected void doGet(HttpServletRequest req, HttpServletResponse resp)
             throws ServletException, IOException{
             // 假设前端发送的请求是get请求，后端程序员重写的方法是doPost，对应doGet方法不重写默认报405错误
             String msg = lStrings.getString("http.method_get_not_supported");
             sendMethodNotAllowed(req, resp, msg);
         }
         
         protected void doPost(HttpServletRequest req, HttpServletResponse resp)
             throws ServletException, IOException {
             // 假设前端发送的请求是psot请求，后端程序员重写的方法是doGet，对应doPost方法不重写默认报405错误
             String msg = lStrings.getString("http.method_post_not_supported");
             sendMethodNotAllowed(req, resp, msg);
         }
     }
     /*
     除有特殊需求，不建议为了避免405错误，在Servlet类当中将doGet和doPost方法都进行了重写，405错误有作用，是处理HTTP协议的一种反馈机制，该报错就让其报错。为了避免405错误同时重写了doGet和doPost，不如你直接重写service方法。代码还能少写一点。
     */
     ```



### 最终的Servlet对象开发步骤

1. 第一步：编写一个Servlet类，直接继承HttpServlet
2. 第二步：后端程序员按需求重写doGet方法或者重写doPost方法
3. 第三步：将Servlet类配置到web.xml文件中
4. 第四步：准备前端的页面（如form表单，form表单指定请求路径）【这一步按需设置】



### servlet规范中常用接口和类

1. jakarta.servlet.Servlet  									    核心接口（接口）
2. jakarta.servlet.ServletConfig 						      Servlet配置信息接口（接口）
3. jakarta.servlet.ServletContext 						   Servlet上下文接口（接口）
4. jakarta.servlet.ServletRequest 						   Servlet请求接口（接口）
5. jakarta.servlet.ServletResponse 						Servlet响应接口（接口）
6. jakarta.servlet.ServletException 						Servlet异常（类）
7. jakarta.servlet.GenericServlet 							标准通用的Servlet类（抽象类）
8. jakarta.servlet.http.HttpServlet 						  HTTP协议专用的Servlet类（抽象类）
9. jakarta.servlet.http.HttpServletRequest 		   HTTP协议专用的请求对象(接口)
10. jakarta.servlet.http.HttpServletResponse 		HTTP协议专用的响应对象



## web站点的欢迎页面

> webapp也称web站点
>
> 实际上使用Tomcat服务器有两个地方可以配置欢迎页面：
>
> + 一个是webapp内部的web.xml文件(此处为局部配置)
> + 一个是CATALINA_HOME/conf/web.xml文件（此处为全局配置）

1. web站点的欢迎页面
   - 一个webapp可以设置它的固定欢迎页面,访问该web站点的路径只到项目名，没有项目名下的资源路径，会默认访问被设置的欢迎页面
   - 资源的一般访问方式 ：http://localhost:8080/servlet06/login.html 指定访问的就是login.html资源
   - 直接访问站点，未指定具体资源路径 ：http://localhost:8080/servlet04 会默认访问被设置的欢迎页面

2. 在webapp的web.xml文件中设置欢迎页面

   + 第一步：在IDEA的web目录下新建了一个login.html

   + 第二步：在web.xml文件中配置<welcome-file-list>标签

     ```xml
     	<welcome-file-list>
             <!--设置欢迎页面的路径不需要以“/”开始，且该路径默认是从webapp的根下开始查找-->
             <welcome-file>login.html</welcome-file>
             
             <!--欢迎页面的资源还可以设置在webapp的多层目录中，如/web/page1/page2/page.html-->
             <!--一个webapp可以设置多个欢迎页面，越靠上的资源优先级越高，找不到继续向下找-->
             <welcome-file>page1/page2/page.html</welcome-file>
             
         </welcome-file-list>
     ```

     > 注意：设置欢迎页面的时候，这个路径不需要以“/”开始。且这个路径默认从webapp的根下开始查找，IDEA中的web就是该webapp的根，web直接省略。

   - 第三步：启动服务器，浏览器地址栏输入访问站点的地址如 http://localhost:8080/servlet04


3. 在Tomcat的CATALINA_HOME/conf/web.xml文件中配置欢迎页面

   + 在web.xml文件中配置<welcome-file-list>标签

     ```xml
     <welcome-file-list>
         <welcome-file>index.html</welcome-file>
         <welcome-file>index.htm</welcome-file>
         <welcome-file>index.jsp</welcome-file>
     </welcome-file-list>
     ```

     [^要点1]: 如果一个站点没有配置局部的欢迎页面，Tomcat会选择全局配置作为站点的欢迎页面
     [^要点2]: 如果站点有局部配置，则采用局部优先的原则，优先采用局部配置的欢迎页面
     [^?Q]: 全局配置，欢迎页面的根是哪里?

     + 全局配置中的3条路径是CATALINA_HOME/conf/web.xml文件写死的，不能更改，更改会被要求改回来，全局配置三个文件的含义是，当web站点没有配置局部欢迎页面，如果web站点根目录下有index.html 、index.htm 、index.jsp，访问站点直接使用这三个index文件作为欢迎页面，优先级向下依次递减，且每个index文件只对所在站点有效，部署在同一台服务器上的其他web站点不共享
     + Tomcat服务器启动设置的自动打开的页面与全局配置、局部配置均无关

4. 欢迎页可以是一个Servlet对象

   + 欢迎页只能配置局部配置吗(是的，全局配置不行，把url-pattern设置成/index也不行)

   + 在web.xml文件中配置欢迎页

     ```xml
     	<servlet>
             <servlet-name>welcomservlet</servlet-name>
             <servlet-class>com.atlisheng.servlet.WelcomeServlet</servlet-class>
         </servlet>
         <servlet-mapping>
             <servlet-name>welcomservlet</servlet-name>
             <url-pattern>/welcome/myFriend</url-pattern>
         </servlet-mapping>
         
         <welcome-file-list>
             <welcome-file>welcome/myFriend</welcome-file>
         </welcome-file-list>
     ```

     [^要点]: 把url-pattern的路径去掉第一个斜杠即可

[^WEB-INF目录]: WEB-INF目录下的资源是受保护的，在浏览器上不能够通过路径直接访问，会出现404错误。所以像HTML、CSS、JS、image等静态资源一定要放到WEB-INF目录之外。



## HttpServletRequest

1. jakarta.servlet.http.HttpServletRequest是一个接口，是Servlet规范中的一员，父接口是ServletRequest

   ```java
   public interface HttpServletRequest extends ServletRequest {}
   ```

   + org.apache.catalina.connector.RequestFacade 实现了 HttpServletRequest接口

     ```java
     System.out.println(request);//org.apache.catalina.connector.RequestFacade@602e59f3
     ```

   [^猜测]: Tomcat主方法多态创建ServletRequest的孙类RequestFacade并给service方法传参

   + HttpServletRequest的"request对象"又称为“请求域”对象

2. HttpServletRequest对象中的包装信息

   + Tomcat接收到HTTP的请求协议，创建HttpServletRequest对象，将请求协议的信息解析封装到HttpServletRequest对象中，并提供方法供程序员获取请求信息

     + 用户提交的数据被封装到HttpServletRequest中的一个Map集合中

       ```
       用户提交数据被解析封装到一个Map<String, String[]>中
           key存储String
           value存储String[]
           例如：
           key				value
           -------------------------------
           username		{"abc"}
           userpwd			{"111"}
           aihao			{"s","d","tt"}
       ```

       [^注意]: 前端提交的永远是字符串，后端获取并存入Map集合内的也永远是字符串

3. request和response对象的生命周期
   - request对象和response对象只在当前请求中有效。创建到销毁只存在于接收请求到响应请求

4. HttpServletRequest接口中常用方法

   - 获取前端浏览器用户提交的数据的方法

     + 用户提交的数据被封装到HttpServletRequest中的一个Map集合中
     
     ```java
     //这个是获取提交数据的Map集合，value部分是一个字符串数组，存进value的是这个字符串数组的引用
     Map<String,String[]> getParameterMap() 
     //具体实现
     Map<String,String[]> Datas=request.getParameterMap()
     //遍历Map集合
     //获取所有的key
     Set<String> keys=parameterMap.keySet();
     Iterator<String> iter=keys.iterator();
     while(iter.hasNext()){
         String key=it.next();
         System.out.println(key);
     }
         
     //获取提交数据的封装Map集合的所有key，即提交数据对应的name
     Enumeration<String> getParameterNames() 
     //具体实现
     Enumeration<String> names=request.getParameterNames()
         
     //通过name即封装Map集合的key获取value的字符串数组，这个也比较常用，用来获取value含多个数据的
     String[] getParameterValues(String name)
     //具体实现
     String[] value=request.getParameterValues(String name)
         
     //通过name即封装Map集合的key获取value字符串数组的第一个元素,这个方法最常用，因为大部分value是单个
     String getParameter(String name)  
     //具体实现
     String value=request.getParameter(String name)
     ```

   + 获取客户端的IP地址

     + 客户端是指发送请求的浏览器

     ```java
     String remoteAddr = request.getRemoteAddr();  //127.0.0.1
     ```

   + 设置请求体的字符集

     + 这个方法用于处理POST请求的乱码问题，这种方式不能解决get请求的乱码问题，因为Get请求在请求行提交数据
     + Tomcat9及此前的版本，如果前端请求体提交的是中文，后端获取之后出现乱码，执行以下代码解决乱码问题

     ```java
     //从集合中获取用户提交数据前进行
     request.setCharacterEncoding("UTF-8");
     ```

     [^注意]: Tomcat10之后，request请求体当中的字符集默认就是UTF-8，无需设置字符集，不会出现乱码问题

   + 设置响应体的字符集

     + Tomcat9及此前的版本，响应体中文也会出现乱码，使用以下代码解决乱码问题

     ```java
     response.setContentType("text/html;charset=UTF-8");
     ```

     [^注意]: Tomcat10之后，response响应体中文不再出现乱码问题，上述代码无需再设置UTF-8

   + get请求乱码问题解决方法

     [^方案]: 修改CATALINA_HOME/conf/server.xml配置文件的<Connector URIEncoding="UTF-8" />
     [^注意]: Tomcat8之后，URIEncoding的默认值就是UTF-8，所以GET请求也没有乱码问题了；Tomcat7以前，URI的默认字符编码方式URIEncoding的默认值是ISO-8859-1，存在中文乱码问题

   + 获取应用的根路径（即项目根路径）

     ```java
     String contextPath = request.getContextPath();//  /ser04
     ```

   + 获取请求方式

     ```java
     String method = request.getMethod();
     ```

   + 获取请求的URI

     + 直接获取显示地址栏上的URI，比如web站点的URI，不一定是资源的url-pattern

     ```java
     String uri = request.getRequestURI();//以站点的方式访问访问结果 /ser04/
     ```

   + 获取servlet path，即url-pattern

     ```java
     String servletPath = request.getServletPath(); //   /welcome/myFriend
     ```

5. “请求域”对象

   - “请求域”对象要比“应用域”对象的范围小很多、生命周期短很多。

   - 请求域只在一次请求内有效。一次请求对应一个请求域对象，请求结束后请求域就会被销毁

   - 请求域不仅存放用户提交数据，程序员还可以按需要存取移除数据


   - 请求域对象也有存、放、取数据的方法，且和应用域的三个方法名字用法一模一样，调用者不同

     ```java
     // 向域当中绑定数据
     void setAttribute(String name, Object obj); 
     //具体实现
     request.setsetAttribute(String name, Object obj);
     
     // 从域当中根据name获取数据
     Object getAttribute(String name); 
     //具体实现
     Object value=requeat.getAttribute(String name); 
     
     // 将域当中绑定的数据移除
     void removeAttribute(String name); 
     //具体实现
     request.removeAttribute(String name);
     ```

     [^?Q]: 用户提交的数据可以通过名字移除吗？
     [^AnsQ]: 不能，以上三个方法对用户提交数据完全无效

     

6. 绑定数据请求域或应用域的选用原则

   + 净量使用小的域对象，因为其占用的资源较少

7. 转发

   + 转发是浏览器只发送一次请求，第一个Servlet对象把同一个请求域、response转发给其他Servlet对象

   + 支持多次转发

   + 转发的方法

     + 获取请求转发器RequestDispatcher对象dispatcher(调度，分发)

       ```java
       RequestDispatcher dispatcher = request.getRequestDispatcher("url-pattern的值");
       //具体实现
       RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome");
       ```

     + 调用转发器dispatcher的forward方法完成跳转/转发

       ```java
       dispatcher.forward(request,response);
       
       //两行代码可合并在一起，因为转发器一般不会多次使用
       request.getRequestDispatcher("/welcome").forward(request,response);
       ```

     [^注意]: 转发的路径以“/”开始，不加项目名（*****），带了项目名会导致找不到资源
     [^?Q]: 转发以后会直接执行转发后servlet对象的方法来响应请求吗。还是原servlet对象和转发对象共享同一个响应数据，都可以继续执行同时响应请求。感觉有点像A接收到请求加工一下比如加个当前时间然后把请求转给其他servlet对象去接收请求执行响应，或者直接去访问其他资源?
     [^AnsQ]: redirect重定向后，原Servlet对象确认了要跳转的页面的 url后，继续执行 redirect 下面没执行完的代码；执行完后，断开当前与用户发出请求的连接，也即断开了 request 的引用指向，因此 request 里存放的所有数据信息也会丢失；然后再与用户建立新的请求连接，创建新的 request 对象，地址栏的 url 内容会发生变化 !                  forward转发确定要跳转页面的url后直接暂停执行当前Servlet对象的后续代码，跳转执行目标Servlet类的代码，执行结束后再回来继续执行最初Servlet类后面的代码，期间几个Servlet对象共享一个request和response对象，转发浏览器地址栏上的url不会发生变化!

   - 多个Servlet怎么共享数据？
     - 将数据绑定到应用域或者请求域中，应用域同一个web应用中可以到处访问
     - 请求域通过转发到吗目标Servlert对象，在目标Servlet对象中访问

   - 转发的下一个资源不一定必须是一个Servlet对象，只要是Tomcat中的合法资源，都可以转发，如html..

     [^?Q]: 转发到非Servlet类的静态资源怎么转发，转发后怎么响应浏览器的请求?自己测试一下
     [^测试结果]: 转发静态资源会直接把静态资源打到浏览器上，原Servlet对象后续的输出不显示，但是后续代码确实执行了，控制台输出了信息

     ```java
     //浏览器最终显示这个静态网页
     request.getRequestDispatcher("/aidPage.html").forward(request,response);
     ```

     

8. 关于request对象中两个非常容易混淆的方法：
   + request.getParameter("username")：获取的是用户在浏览器上提交的数据
   + request.getAttribute("name")：获取的是请求域当中绑定的数据



## 纯Servlet实现单表CRUD

> 使用纯粹的多个Servlet对象使用B/S结构完成单表【对部门的】的增删改查操作

实现步骤

1. 第一步：准备一张数据库表sql脚本t_Dept,并导入MySQL数据库

2. 第二步：准备一套HTML页面（项目原型）

   - 设计页面原型

     - 欢迎页面：index.html
     - 列表页面：list.html（以列表页面为核心，展开其他操作。）
     - 新增页面：add.html
     - 修改页面：edit.html
     - 详情页面：detail.html

     > 然后将HTML页面中的链接都能够跑通

3. 第三步：分析系统包括哪些功能

   [^功能]: 一个操作连接了数据库，就表示一个独立的功能

   - 系统应该包括的功能
     - 查看部门列表
     - 新增部门
     - 删除部门
     - 查看部门详细信息
     - 跳转到修改页面
     - 修改部门

4. 第四步：在IDEA当中搭建开发环境

   - 创建一个webapp

   - 向webapp中添加mysql驱动
     - WEB-INF目录下新建lib目录—将mysql的驱动jar包拷贝到lib目录下

   - 写一个JDBC的工具类DButil

   - 将所有HTML页面拷贝到web目录下。

5. 第五步：实现第一个查看部门动态列表功能

   [^非常重要]: Servlet对象中使用`ResourceBundle`绑定属性配置文件，第一一定不能带properties后缀，第二属性配置文件的根是src，必须省略；从com算起，com前面不带/，最多不能超过三层目录，超过就找不到属性配置文件，bundle直接失效，报500错误。非常难查找这个错误。我特么找了三个多小时，草                                                                                           原因还是不对，经过测试，另一个模块下四层包也行，就那个包三层行，四层不行，别管那么多，没搞出来具体原因，结合老杜视频做法和网上的一些博文，认为服务器打包方式的问题会导致不在src下的resources中的properties文件可能存在找不到的问题，以后在服务器上部署，所有属性配置文件一律放到src/resources下，提前规避所有可能的问题，md折磨死我了        
   [^再次强调]: Javaweb项目一定要把属性配置文件放到resources目录下，不放一旦出错极难排查，原因不清楚，ResourceBundle.getBundle("resources.dbinfo")中 仍然要带包名resources，就使用点连接，跟老杜一样，避免不必要的错误                                                         

   - 从前端用户点击按钮那里开始理逻辑，一步步发送请求到响应请求最后把响应打到浏览器上

   - 先确认能查到数据

   - 再把Hbuilder写好的前端页面中的双引号全部换成单引号拷贝到Servlet对象中分成固定死的和动态的分开处理

     [^注意]: JS代码最好全部加上分号，此前遇到if前一句不带分号查了几个小时，最后加上分号就好了的情况，提示根本提示不了错误，有了错误第一时间拿着错误信息去百度

   - 在web.xml文件中注册ListDept类

   - 重写ListDept类的doGet方法实现链接数据库动态获取展示数据

     [^注意]: 在Servlet对象中写的超链接里的提交数据前面的问号一定要是英文问号，中文的会出错，到时候也排查半天

6. 第六步：依次按点击按钮—发送请求—查询数据—处理前端HTML语句的顺序依次实现后续功能

   + 删除数据必须提醒用户删了不可恢复，避免用户误点甩锅，以下代码做范例

     ```html
     <a href="javascript:void(0)" onclick="del(30)" >删除</a>
     <script type="text/javascript">
     	function del(dno){
     		if(window.confirm("亲，删了不可恢复哦！")){
     			document.location.href = "/oa/dept/delete?deptno=" + dno;
     		}
     	}
     </script>
     ```

[^注意]: post请求转发不能直接转发没有重写doPost方法的Servlet对象，但是可以通过重写转发对象的doPost方法，并在doPost方法里调用该对象的doGet方法。当然适当的时候可以使用重定向



## 重定向

> 重定向和转发跳转的资源只要是服务器内部合法的资源即可，包括Servlet、JSP、HTML.....

1. web应用中资源跳转的两种方式

   [^转发]: request调用请求转发器对象的forward方法完成转发，将当前的request和response对象传递给下一个Servlet对象，不管转发多少次，都是最初的用户请求请求域和response，但是转发静态资源后用户接收到静态资源后并不能显示转发完毕最初Servlet类后面的代码执行效果
   [^重定向]: response直接调用response.sendRedirect("目标路径");目标路径以/项目名开始；这种方式将路径发送给浏览器，浏览器自发向服务器发送一次全新的请求,如：/ser05/index.html，浏览器会自动补前面的东西

2. 转发与重定向的区别

   + 转发是一次请求，重定向是两次请求
   + 转发浏览器的地址栏提交的请求页面跳转后仍保持最初的地址不会变，刷新会再次提交相同请求；重定向地址栏的请求地址会变成了新的请求地址，刷新提交的和之前的不是相同的请求

   + 本质上转发的跳转动作由WEB服务器内部完成，重定向跳转资源是浏览器完成的

   + 张三去银行借钱再借给李四是转发，张三给李四银行的地址让李四自己去银行借钱是重定向

3. 转发和重定向的选用
   + Servlet对象间共享绑定数据用转发
   + 剩下全部使用重定向，如数据删除、修改、保存之后都用重定向避免多次刷新提交请求的问题
   + 转发存在浏览器刷新问题，即多次刷新如果存储完数据以后转发到某个静态资源，刷新会再次提交最初的请求，会导致刷新一次就存入一次重复数据，但是存储完以后用重定向，地址栏地址变了，多次刷新就不会存在该问题,这个很难说

[^要点1]: 重定向时浏览器发送的请求相当于地址栏发送url是get请求，表单提交保存后跳转到重写doGet方法的Servlet对象使用重定向好一些，否则目标Servlet对象需要重写doPost方法，通过doPost方法去执行doGet方法



## 注解简化web.xml配置

1. 使用注解能简化web.xml文件的配置信息
   + 一个大的项目来说,采用web.xml文件来配置所有信息，会导致web.xml非常庞大，可能最后会有几十兆
   + web.xml文件中进行servlet信息的配置，每个Servlet对象都要配置一下，开发效率比较低
   + 而这些配置很少被修改，可以考虑写到java类当中去

2. Servlet3.0版本之后，推出了各种Servlet基于注解式开发

   + 优点：
     + 直接在java类上使用注解进行标注，不需要编写大量的配置信息，开发效率高
     + web.xml文件体积变小
   + web.xml文件主要配置一些需要变化的信息

   [^要点]: 不会经常修改的配置信息建议使用注解，一些可能被修改的配置信息建议写到配置文件中

3. WebServlet注解

   + 全限定包名：jakarta.servlet.annotation.WebServlet

   + @WebServlet注解可在Servlet类上使用

   + 注解对象的使用格式：@注解名称(属性名=属性值, 属性名=属性值, 属性名=属性值....)

   + WebServlet注解中的属性

     + name属性：用来指定Servlet的名字，等同于：<servlet-name>

     + urlPatterns属性：用来指定Servlet的映射路径，等同于：<url-pattern>

       [^注意]: 可以指定多个<url-pattern>字符串，表示多个url指向同一个资源

     + loadOnStartUp属性：用来指定在服务器启动阶段是否加载该Servlet，等同于：<load-on-startup>

     + initParams属性：用来配置Servlet对象的初始化信息，他的属性名是value，属性类型是WebInitParam注解，WebInitParam注解中有name属性、value属性和description属性，只有description属性有默认值空字符串，等同于：<init-param>

     + value属性：value属性的效果和urlPatterns属性是一样的，当注解的属性名是value时，value属性名是可以省略，可以直接写<url-pattern>

       [^疑惑]: 不是只有value一个属性才可以省略吗
       [^注意1]: 属性是一个数组，如果数组中只有一个元素，属性值的大括号可以省略，比如urlPatterns属性
       [^注意2]: 不是必须将所有属性都写上，需要什么写什么

4. WebServlet注解模板

   ```java
   @WebServlet(name = "testWS",urlPatterns{"/testWebServlet","/testWS","/testwebservlet"} ,initParams ={@WebInitParam(name="url",value="jdbc:mysql://127.0.0.1:3306/bjpowernode" ) ,@WebInitParam(name="username",value ="root") , @WebInitParam(name="password", value="Haworthia0715")},loadOnStartup = 0)
   public class TestWebServlet extends HttpServlet {
       @Override
       protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       }
   }
   ```
   
5. 利用反射机制获取注解属性值

   ```java
   //使用反射机制解析TestWebServlet类上面的WebServlet注解
   //获取类Class对象
   Class<?> testWebServlet=Class.forName("com.atlisheng.servlet.TestWebServlet");
   //获取WebServlet注解对象
   if (testWebServlet.isAnnotationPresent(WebServlet.class)){
       //WebServlet竟然不用强转，之前JavaSE需要强转
       WebServlet webServletAnnotation= testWebServlet.getAnnotation(WebServlet.class);
       
       //获取name属性值
       System.out.println(webServletAnnotation.name());//testWS
       
       //获取urlPatterns属性值
       String[] urlPatterns=webServletAnnotation.urlPatterns();
       for (String urlPattern:urlPatterns) {
           System.out.println(urlPattern);
       }
       /**
        * 执行结果：
        *      /testWebServlet
        *      /testWS
        *      /testwebservlet
        * */
       
       //获取initParams属性值
       WebInitParam[] initParams=webServletAnnotation.initParams();
       for (WebInitParam initParam:initParams) {
           System.out.println(initParam.name()+"="+initParam.value());
       }
       /**
        * 执行结果：
        *      url=jdbc:mysql://127.0.0.1:3306/bjpowernode
        *      username=root
        *      password=Haworthia0715
        * */
       
       //获取loadOnStartup属性值
       System.out.println(webServletAnnotation.loadOnStartup());//0
   ```

   

## 纯Servlet项目的优化

> 注解解决配置文件臃肿的问题，但是项目仍存在简单功能Servlet对象太多的问题，一个单标的CRUD，就写了6个Servlet对象，这种项目开发方式类的数量太多，专业术语类爆炸问题
>
> 同时Servlet中还有同时编写HTML/CSS/JavaScript等前端代码的问题

1. 使用模板方法设计模式优化Servlet项目
   + 此前一个请求对应一个Servlet对象，100个不同请求对应100个Servlet类
   + 可以转变思路，一个业务对应一个Servlet类，不同请求对应不同的方法，即使用模板方法设计模式

2. 纯Servlet开发Javaweb应用的缺陷

   + java程序中编写前端代码，编写难度大，因为java认为前端代码是字符串，前端代码的bug很难发现

   + java程序与前端代码耦合度非常高，改个界面颜色都需要改java源码，之后重新调试，编译，打新的war包，重新发布，维护成本非常高

   + 代码非常不美观

     [^解决思路]: 提供一种机制，程序员只需要单独写前端代码，机器可以自动把前端代码翻译生成java程序嵌入Servlet类当中，然后机器自动取把该java程序编译生成class文件，再通过JVM去执行这个字节码文件，这就是JSP

     

## session会话机制

1. B/S结构的session(会话)

   + 一次会话：打开浏览器—操作—关闭浏览器，这一整个操作过程叫一次会话
   + 一个会话在服务器端有一个对应的session对象
   + 一次请求：浏览器上点击一下到页面停下来，粗略认为是一次请求，对应的服务器的request对象

   [^注意]: 一次会话中包含N次请求

   + session机制是B/S结构的一种规范，不同的语言对这种会话机制都有实现，java的servlet规范中，session对应的全限定类名是：jarkata.servlet.http.HttpSession

   + session对象的最主要作用是保存会话状态

     [^例如]: 保存用户使用同一个浏览器期间登录成功的状态，用请求域保持登录的时间太短，用应用域保持登录的时间太长，用session刚刚好

2. 需要session对象的原因

   + HTTP是一种无状态协议，无状态指的是请求的时候，服务器和浏览器处于连接状态；请求结束后连接就断掉，这样设计是为了降低服务器的压力
   + 在连接断掉期间，浏览器关闭这个操作，服务器无从知晓，但是有一种可能，系统提供了“安全退出服务”，用户点击该按钮后，服务器就知道用户退出了系统，会自动销毁对应的session对象

3. session机制的实现原理

   + 用户打开浏览器，第一次访问服务器时，不会在请求协议中携带sessionid，服务端会生成用户浏览器专属的session对象，session对象列表以Map集合的形式存储在服务器中，key是sessionid，value是session对象的引用

   + 同时生成sessionid，将sessionid=xxxxxx发送给浏览器，浏览器以Cookie的形式保存在内存中，浏览器只要关闭，这个id就没有了

   + 此后用户的每次向该服务器的请求，都会将内存中的id发送给浏览器，浏览器根据id查找session对象

   + 关闭浏览器，内存销毁，Cookie消失，jsesessionid消失，会话等同于结束，但是服务器不知道浏览器关闭了，服务器中的session对象有超时销毁机制，超时没有收到用户的请求会自动销毁session对象

   + session对象有两种销毁机制：超时销毁和手动销毁

   + 可以通过Javaweb项目的web.xml的<session-config>标签设定session的超时时长,超过超时时长session对象没有被访问，则session对象会被销毁

     ```xml
     <!--设置session的超时时长为10分钟-->
         <session-config>
             <session-timeout>10</session-timeout>
         </session-config>
     ```
     
     + 获取提交请求的用户的session对象，并手动销毁用户的session对象
     
       ```java
       //获取提交请求的用户的session对象，如果没有获取到任何对象，则新建
       HttpSession session = request.getSession();
       
       //从服务器获取当前的session对象，如果获取不到，则不会新建，返回一个null
       HttpSession session=request.getSession(false);
       
       //手动销毁用户的session对象
       session.invalidate();
       ```

4. Cookie禁用

   + cookie禁用以后，服务器正常发送sessionid，但是浏览器拒收，因为收了也存不下来，所以每次请求都会获取新的session对象，生成新的sessionid，session机制会失效

   + cookie禁用后，可以使用URL重写机制实现session机制，每次请求都要写成：

     http://localhost:8080/servlet12/test/session;jsessionid=19D1C99560DCBF84839FA43D58F56E16

     [^注意]: URL重写机制会大大提高开发者成本，开发者编写任何请求路径时都要添加sessionid，开发难度和成本都很高，大部分网站的策略是"用户要是禁用cookie，那网站你也别用了"

5. 总结目前所了解的域对象：

   - request（对应的类名：HttpServletRequest）
     
     > 请求域（请求级别）

   - session（对应的类名：HttpSession）
     
     > 会话域（用户级别）

   - application（对应的类名：ServletContext）
     
     > 应用域（项目级别且所有用户共享）

   - 三个域对象的大小关系：request < session < application

   - 三个域对象都有以下三个方法：
     
     > setAttribute（向域中绑定数据）
     >
     > getAttribute（从域中获取数据）
     >
     > removeAttribute（删除域中的数据）

   - 使用原则：尽量使用小的域

6. 用session机制优化oa项目中的登录功能

   - 用户登录成功后，把登录信息存到session对象中，即会话域中如果有用户的信息就代表用户已登录否则表示用户未登录，则跳转到登录页面

   - 退出登录手动销毁session对象：

     ```java
     HttpSession session = request.getSession();
     session.invalidate();
     ```

[^注意]: 在JSP文件中用page标签<%@ page session="false" %>会使内置对象session不再起作用
[^注意]: QQ浏览器和Chrome对sessionid进行了隐藏



## Cookie

1. cookie对象
   + 如JSESSIONID=41C481F0224664BDB28E95081D23D5B8就是session关联的cookie，这个cookie保存在浏览器运行内存中，浏览器不关闭再次发送请求这个cookie就会发送被发送给服务器
   + cookie除了保存在浏览器内存中，还可以被永久保存在硬盘文件中
   + cookie其实就是保存浏览器上一些数据的一个东西，存的是name和value，不止是保存sessionid
   
2. cookie对象的作用

   + cookie对象和session对象实际都是为了保存会话的状态，cookie对象是将会话状态保持在浏览器中，session对象是将会话状态保持在服务器上

   + 把cookie看成浏览器上的session对象即可

     [^注意]: cookie应该还有更多含义，这个以后再学

3. cookie的经典应用

   + 未登录添加商品到购物车

     > 未登录情况下浏览京东商城，把放入购物车的商品放到cookie中存在硬盘上，下次打开浏览器查看购物车，会读取本地的cookie，动态展示购物车
     >
     > 购物车商品的cookie可能是：productIds=xxxxx,yyyy,zzz,kkkk
     >
     > 如果把这个cookie清除掉，购物车里的商品就会消失

   + 126邮箱十天免登录

     > 浏览器客户端用一个cookie保存用户名和密码信息，将这个cookie保存在硬盘文件中，并设置十天有效，十天内浏览器会自动提交126关联的cookie给服务器，服务器自动验证登录
     >
     > cookie会在十天后自动失效，改密码或在浏览器上清除cookie都会让这个cookie失效

[^注意]: 实际上cookie机制和session机制都是HTTP协议的一部分，HTTP协议规定任何一个cookie都是由字符串类型的name和value组成的，且浏览器发送请求时会自动携带该path下关联的cookie数据给服务器，所有的编程语言都对cookie和session机制进行实现

4. servlet规范中对cookie提供的支持

   + jakarta.servlet.http.Cookie：提供了一个Cookie类来专门表示cookie数据

     [^注意]: java中的cookie对象只有有参数构造，没有无参数构造，任何一个cookie对象的创建都必须传入字符串类型的参数name和value

   + response中的addCookie（cookie）方法，专门供java程序把cookie数据发送给浏览器

     ```java
     //配合上一条使用
     response.addCookie(cookie);
     ```

   + 用java程序设置cookie数据的有效时间(cookie表示Cookie对象，猜测)

     ```
     cookie.setMaxAge(60 * 60); //设置cookie在一小时之后失效,显然参数是秒
     //浏览器上响应体中显示的cookie过期时间是按国际标准时间显示的，要早八个小时
     ```
     
     > 关于有效时间：
     >
     > 没有设置有效时间或者有效时间设置为小于0，均保存在内存中，浏览器关闭cookie消失
     >
     > 有效时间只要大于0，就一定会存储到硬盘文件中
     >
     > 有效时间等于0，同名cookie会立即被删除（意思就是cookie有效时间为0，就是直接无效，等同于删除cookie），主要应用在删除浏览器上的同名cookie
     >
     > [^注意]: cookie的销毁是根据名字和关联路径确定的，只要名字和路径相同，cookie对象不同，设置其中一个cookie对象的有效时间为0，所有的同名同关联路径cookie全部失效，相当于这个有效时间只有浏览器记住了，浏览器发现服务器要求销毁这个同名且关联路径相同的cookie，就会去销毁，只要有一个不同，销毁就不会执行

5. 关于cookie的path(cookie关联的路径)

   + cookie默认的path

     > 如果cookie没有设置path，由请求路径“http://localhost:8080/servlet13/cookie/generate”生成的cookie ，该cookie关联的路径是http://localhost:8080/servlet13/cookie 以及它的子路径，只要访问该cookie关联的路径，浏览器都会把该cookie发送到服务器，如果路径在浏览器中没有响应，显示404，但是仍然在请求头中携带了对应cookie，即cookie提不提交完全取决于路径是否与cookie关联

   + 手动设置cookie的path

     ```java
     //表示只要是这个servlet13项目的请求路径，都会提交这个cookie给服务器
     cookie.setPath(“/servlet13”);
     ```

6. 使用cookie.setDomain方法设置跨域共享cookie【需要总结】

   + 通常，cookie却不能跨越域传递，只有那些创建它的域才能访问，同一根域名下的二级域名，三级域名可以直接共享。但你可以利用重定向来间接的获取cookies。

     ```
     A机所在的域：home.langchao.com,A有应用cas
     B机所在的域：jszx.com，B有应用webapp_b
     1）在cas下面设置cookie的时候，增加cookie.setDomain(".jszx.com");，这样在webapp_b下面就可以取到cookie。
     2）这个参数必须以“.”开始。
     3）输入url访问webapp_b的时候，必须输入域名才能解析。比如说在A机器输入：http://lc-bsp.jszx.com:8080/webapp_b,可以获取cas在客户端设置的cookie，而B机器访问本机的应用，输入：http://localhost:8080/webapp_b则不可以获得cookie。
     4）设置了cookie.setDomain(".jszx.com");，还可以在默认的home.langchao.com下面共享。
     ```

7. 服务器中java程序接收cookie

   + 如果请求中没有携带任何cookie，接收的cookies不是长度为0的数组而是null，一定要注意

   ```java
   Cookie[] cookies = request.getCookies(); // 这个方法可能返回null
   if(cookies != null){
       for(Cookie cookie : cookies){
           // 获取cookie的name的cookie.getName()方法
           String name = cookie.getName();
           // 获取cookie的valuecookie.getValue()方法
           String value = cookie.getValue();
       }
   }
   ```

8. 使用cookie实现OA系统十天内免登录功能

   - 先实现登录功能
     - 登录成功
       
       > 跳转到部门列表页面
     - 登录失败
       
       > 跳转到登录失败页面

   - 修改前端页面
     - 在登录页面给一个复选框，复选框后面给提示：十天内免登录

   - 修改Servlet中的login方法
     - 如果用户登录成功，且选择了十天内免登录，此时应在Servlet的login方法中创建cookie，用来存储用户名和密码，并且设置路径、有效期，将cookie响应给浏览器
     
       > 浏览器会将其自动保存在硬盘文件当中10天

   - 用户再次访问该网站的时候，会提交存入cookie的账号和密码，再访问这个网站的首页的时候，有两个走向:
     - 要么跳转到部门列表页面
     - 要么跳转到登录页面
     - 以上两个走向，需要编写java程序进行控制的



# JSP

> 写JSP的最高境界，眼前是JSP代码，心里呈现的是java代码，对JSP的错误进行调试是，直接打开JSP文件对应的java源代码
>
> JSP就当做HTML文件写就行了，带所有标签格式，用双引号没毛病

## JSP的本质

1. JSP是JavaServer Pages的缩写，是基于Java语言实现的服务器端的页面

2. JSP本质上就是一个Servlet

   > 假设有一个index.jsp文件，该文件被访问时会自动生成index_jsp.java，自动编译生成index_jsp.class ，index_jsp实际就是一个类
   >
   > 查看这个index_jsp.java，发现index_jsp 继承 HttpJspBase，而HttpJspBase继承的是HttpServlet，所以index_jsp类就是一个Servlet类
   >
   > 因此，jsp的生命周期与Servlet对象的生命周期完全相同，没有任何区别，且均为假单例

3. JSP与servlet规范一样是JavaEE的13个子规范之一，所有的web服务器都会内置一个JSP翻译引擎，并遵循JSP规范进行前端代码的“翻译”

4. JSP和一般Servlet对象的区别主要是职责不同

   Servlet的职责：常规Servlet的强项是业务逻辑处理，链接数据库，获取/收集数据

   > JSP的职责：JSP的强项是做数据展示

5. xxx.jsp文件对于Tomcat来说，只是一个普通的文本文件，Tomcat会从带这种后缀的文件中的文本自动翻译到java程序里形成Servlet类，执行的时候和xxx.jsp文件没有任何关系。.jsp这个后缀只是一种标识，可以在Tomcat的web.xml文件中自定义成其它的

   

## JSP文件的访问

1. 在WEB-INF目录之外创建一个index.jsp文件

2. jsp项目部署并启动服务器后，通过URLhttp://localhost:8080/jsp/index.jsp访问该文件，实际访问的是底层执行的index_jsp.class 这个java程序

   > index.jsp会被tomcat翻译生成index_jsp.java文件，然后被Tomcat编译生成index_jsp.class文件

3. jsp文件第一次访问很麻烦，Tomcat会自动进行如下一系列操作

   + 把jsp文件翻译生成java源文件

   + java源文件编译生成class字节码文件

   + 通过class去创建servlet对象

   + 调用servlet对象的init方法

   + 调用servlet对象的service方法

     [^注意1]: 第二次访问直接调用单例servlet对象的service方法，会快很多；所以大部分运维人员向客户演示项目时会提前将所有的jsp文件先访问一遍
     [^注意2]: index_jsp.java和index_jsp.class被存放在work/Catalina/localhost/jsp/org/apache/jsp目录下

     

## JSP基础语法

> 下面类似于标签一样的标识符，都可以像html一样拆开不写在同一行，两个标识之间的内容仍然有效，相当于<%%>隔出的几行完全可以看做就在写java代码，而且里面的注释用对应代码自己的注释

1. jsp文件中直接编写文字

   + JSP中直接编写的文字，包括HTML CSS JS代码，会被当做普通的字符串，被自动翻译到XXX_jsp类的service方法的out.write("翻译到这里")的双引号里，被java程序当做普通字符串打印输出到浏览器。

     > 一如纯Servlet类中手打前端代码

2. JSP的page指令

   + 通过page指令设置响应的内容类型，在内容类型的最后面添加：charset=UTF-8解决响应时的中文乱码问题：

     ```jsp
     表示响应的内容类型是text/html，采用的字符集UTF-8
     <%@page contentType="text/html;charset=UTF-8"%>
     ```

   - 导入java程序的包，感觉一般是非servlet下原包的其他包，包括自己建的类，看具体报错信息

     ```jsp
     <%@page import="java.util.List,java.util.ArrayList"%>
     ```

3. 在JSP中编写Java程序

   + <% java语句; %>

     + 这个符号中编写的语句被视为java程序，被自上而下翻译到Servlet类的service方法的方法体中
     + 一个JSP中可以有多个 <%%> 

     > 这里面不能写静态代码块、方法、定义成员变量，写了浏览器会显示500服务器内部错误，实际是编译过不去

   + <%!  java语句;%>

     + 这个符号当中编写的java程序会自动自上而下翻译到service方法之外的类体中
     + 主要写一些静态变量和实例变量

     [^注意]: 这种语法很少用，也不建议使用，因为Servlet对象是单实例的，多线程并发的环境下，静态变量和实例变量的修改操作，必然会存在线程安全问题

4.  JSP的输出语句

   + 向浏览器输出一个java变量的值

     ```jsp
     该语句的意思写java程序，通过service方法体拼接字符串打到浏览器上
     <% String name = “jack”;  out.write("name = " + name); %>
     ```

     [^注意1]: out是JSP的九大内置对象之一。可以直接拿来用，但只能在service方法内部使用
     [^注意2]: 如果只想输出一个固定的字符串，可以直接在jsp中编写，不需要写到<%%>中，用这个等于脱了裤子放屁

   + 向浏览器输出java代码的结果

     + <%= %>，等号后面的内容会被原封不动的放到service方法的方法体的out.print()的括号中

     [^例如]: <%=100+200 %>会被翻译成java代码：out.print(100+200)
     [^注意]: <%= %>的使用场景一般是输出的内容中含有java的变量，输出的内容是动态的，不是死的字符串

5. JSP的专业注释

   - <%--JSP的专业注释，不会被翻译到java源代码当中。--%>

     [^注意]: <!--这种注释属于HTML的注释，这个注释信息仍然会被翻译到java源代码当中-->

     

### JSP基础语法总结

1. JSP中直接编写普通字符串
   + 被翻译到service方法的out.write("这里")

2. <%%>
   - 翻译到service方法里，作为一条一条的java语句。

3. <%! %>
   - 翻译到service方法之外，作为一条条java语句

4. <%= %>
   - 翻译到service方法体内部，翻译为：out.print();括号中为等号后面的内容

5. <%@page  contentType="text/html;charset=UTF-8"%>
   - page指令，通过contentType属性用来设置响应的内容类型和字符编码格式。

[^要点]: 像路径什么的空格一定不要多写，java程序里无所谓，但是显示成html页面，路径中多了空格，就会偶吼



## JSP九大内置对象

1. jakarta.servlet.jsp.PageContext            

   + pageContext			页面作用域

2. jakarta.servlet.http.HttpServletRequest 

   + request					请求作用域

3. jakarta.servlet.http.HttpSession 

   + session    			    会话作用域

4. jakarta.servlet.ServletContext 

   + application			  应用作用域

   [^注意]: pageContext < request < session < application；以上四个作用域都有：setAttribute、getAttribute、removeAttribute方法；以上作用域的使用原则：尽可能使用小的域。

5. java.lang.Throwable exception   

6. jakarta.servlet.ServletConfig 

   + config

7. java.lang.Object 

   + page   						其实是this，代表当前的servlet对象

8. jakarta.servlet.jsp.JspWriter 

   + out                              负责输出

9. jakarta.servlet.http.HttpServletResponse 

   + response 				  负责响应



## JSP优化oa项目

> 结合Servlet 和JSP完成oa项目的开发，用Servlet处理业务，用JSP展示数据

1. 开发步骤

   + 将原型页面的html文件全部修改为jsp文件

   + 在所有jsp文件头部添加page指令指定contentType="text/html;charset=UTF-8"，把所有jsp文件拷贝到web下

   + 完成所有页面的正常流转

     > 使用<%=request.getContextPath() %>  在JSP中动态的获取应用的根路径

   - 在常规Servlet对象中连接数据库，查询所有的部门，遍历结果集，取出所有信息并封装成java对象，并将java对象放到List集合中，将List集合放到request域中，forward转发到jsp中
   - 在JSP中从request域当中取出List集合，遍历List集合，取出每个部门对象，动态生成表格
   - 剩下的步骤如法炮制，总之就是在写java程序的感觉就对了，一步一步走

[^注意1]: 只用JSP也能完成所有的功能，用<%%>里面写的代码就能完成所有工作；但是这样就失去了发挥各自优点的作用了，又变成了另一个极端，前端代码里面写java代码，且没有解耦合
[^注意2]: JSP文件的扩展名不是必须为xxx.jsp，可以在CATALINA_HOME/conf/web.xml中的servlet映射标签中任意修改

```xml
//初始配置
<servlet-mapping>
    <servlet-name>jsp</servlet-name>
    <url-pattern>*.jsp</url-pattern>
    <url-pattern>*.jspx</url-pattern>
</servlet-mapping>
```

2. javabean

   + javabean可以理解为符合某种规范的java类，比如：
     + 有无参数构造方法
     + 属性私有化
     + 对外提供公开的set和get方法
     + 实现java.io.Serializable接口
     + 重写toString
     + 重写hashCode+equals
     + ....

   - javabean其实就是java中负责数据的封装的实体类

   - javabean符合javabean规范，具有很强的通用性

     

3. 实现OA系统的登录功能

   > 只有登录成功的用户可以访问该系统，登录失败不能访问

   + 先在数据库中添加一个用户表t_user,插入用户名和密码

     > 密码一般都是用密文的形式存储，这里先用明文的方式

   + 把index.jsp改成登录页面，点击登录，form表单以post的方式提交

   + 后台有特点的Servlet来处理这个登录请求，登录成功跳转部门列表，登录失败，则跳转到失败的页面

   + 还要在执行模板方法以前对用户的登录状态进行判定，判定依据是session对象里面存了用户名，如果没有，重定向到登录页面
   
     

## JSP的指令

1. 指令的作用
   + 指导JSP的翻译引擎如何翻译JSP文件
2. JSP指令分类
   + include指令：包含指令，在JSP中完成静态包含，很少用了，这里不讲
   + taglib指令：引入标签库的指令。这个到JJSTL标签库的时候再学习。现在先不管。
   + page指令：目前重点

3. 指令的使用语法
   + <%@指令名  属性名=属性值  属性名=属性值  属性名=属性值....%>

4. page指令中常用属性

   + <%@page session="true|false" %>

     ```
     <%@page session="true|false" %>
     true表示启用JSP的内置对象session，表示一定启动session对象。没有session对象会创建。
     如果没有设置，默认值就是session="true"
     session="false" 表示不启动内置对象session。当前JSP页面中无法使用内置对象session。
     ```

   + <%@page contentType="text/json" %>

     ```
     <%@page contentType="text/json" %>
     contentType属性用来设置响应的内容类型，
     其他的属性值如html、
     但同时也可以设置字符集。
     <%@page contentType="text/json;charset=UTF-8" %>
     ```

   + <%@page pageEncoding="UTF-8" %>

     ```
     <%@page pageEncoding="UTF-8" %>
     pageEncoding="UTF-8" 表示设置响应时采用的字符集。
     字符集还可以是GBK等编码方式
     ```

   + <%@page import="java.util.List, java.util.Date, java.util.ArrayList" %>
     <%@page import="java.util.*" %>

     ```
     <%@page import="java.util.List, java.util.Date, java.util.ArrayList" %>
     <%@page import="java.util.*" %>
     import语句，导包。
     ```

   + <%@page errorPage="/error.jsp" %>

     ```
     <%@page errorPage="/error.jsp" %>
     当前页面出现异常之后，跳转到error.jsp页面。该页面可以自定义，对用户很友好，但是错误信息不再显示
     errorPage属性用来指定出错之后的跳转位置。
     ```

   + <%@page isErrorPage="true" %>

     ```
     <%@page isErrorPage="true" %>
     表示启用JSP九大内置对象之一：exception，默认值是false。
     
     在JSP中写如下java代码可以把异常堆栈信息打印输出到后台控制台
     exception.printStackTrace();
     ```



## EL表达式

1. EL表达式

   - Expression Language（表达式语言）

   - EL表达式可以代替JSP中的java代码，让JSP文件中的java程序看起来更加整洁，美观。

     [^注意1]: JSP中夹杂着如<% java代码 %>、<%=%>等各种java代码，导致JSP文件混乱，不好看，不好维护，jsp2 .0之后才出现了EL表达式，EL表达式归属于JSP，是JSP语法的一部分，主打一个代码美观

   - EL表达式只负责从四个域中取数据，不负责存数据，存是在一般是在Servlet对象中进行的

2. EL表达式的三个功效

   + 自动也只能从四个域中取数据

     > 四个域：
     >
     > + pageContext
     > + request
     > + session
     > + application
     >
     > 不需要声明是哪个域，会自动去这四个域找对应的key，当然也可以根据喜好去声明

   + 取出的数据会被自动转成字符串

     > 如果是一个java对象，也会自动调用java对象的toString方法将其转换成字符串

   + 会自动将转换的字符串输出到浏览器

     和<%= %>效果一样，将变量的值转换成字符串输出到浏览器

3. EL表达式的语法格式

   + ${表达式}

     > 注意：
     >
     > 表达式是四大域中数据的名字，EL表达式会自动向浏览器输出对应名字的value
     >
     > 数据必须有存进四大范围的操作，因为EL表达式只能从这四个范围中取数据
     >
     > 变量名必须是存进域中取得名字，但是不要加双引号

   + EL表达式用法举例

     ```
     1.表达式直接写名字，不要加双引号
             request.set("userObj",user);
             正确取法：${userObj}
             错误写法：${"userObj"}
             该EL表达式等同于java代码：<%=request.getAttribute("userObj")%>
         ${userObj} 底层依次从域中取取出user对象，调用user对象的toString方法，将结果转换成字符串并输出到浏览器。
         
     2.${abc} 和 ${"abc"}的区别
     	${abc}表示从域中取出数据的name是"abc"，之前一定有如下代码: 域.setAttribute("abc", 对象)
     	${"abc"} 表示直接将"abc"当做普通字符串输出到浏览器，不会从某个域中取数据。(可直接用JSP实现)
     	
     3.想要用EL表达式输出对象的属性值
             ${userObj.username} 使用这个语法的前提是：User对象有getUsername()方法。
             ${userObj.password} 使用这个语法的前提是：User对象有getPassword()方法。
             ${userObj.age} 使用这个语法的前提是：User对象有getAge()方法。
             ${userObj.email} 使用这个语法的前提是：User对象有getEmail()方法。
             
             ${userObj["username"]} 使用这个语法的前提是：User对象有getUsername()方法。
         EL表达式中的. 这个语法，实际上调用了底层的getXxx()方法。可以没有该属性，但是一定要有对应的getXxx()方法，没有对应的get方法，则出现异常，报500错误。get方法不满足驼峰命名规范不会报错
         ${userObj.addr222.zipcode}对应于java代码：
         		request.getAttribute("userObj"). getAddr222().getZipcode()
         
     4.EL表达式取数据的时候有两种形式：
     	第二种主要用于名字中有特殊符号导致第一种失效的
         - 第一种：.  （大部分使用这种方式）
         - 第二种：[ ] （如果存储到域的时候，这个name中含有特殊字符，可以使用 [ ]）
           - request.setAttribute("abc.def", "zhangsan");
           - ${requestScope.abc.def} 这样是无法取值的。
           - 应该这样：${requestScope["abc.def"]}
           
     5.EL表达式从Map集合中取数据
     	${Map集合引用名.key名}
     	
     6.EL表达式从数组和List集合中取数据
     	- ${数组引用名[0]}
     	- ${数组引用名[1]}
     	- ${list集合引用名[0]}
     	EL表达式中不存在数组下标越界异常，写多大的下标都没事，只是会在浏览器上输出空字符串
     	注意：set集合不能通过下标取数据
     ```

4. EL表达式的注意事项

   + EL表达式默认优先从小范围中读取数据
     + pageContext < request < session < application

   + EL表达式中有四个隐含的隐式的范围

     + pageScope 对应的是 pageContext范围。

     + requestScope 对应的是 request范围。

     + sessionScope 对应的是 session范围。

     + applicationScope 对应的是 application范围。

       [^要点1]: 可以通过${requestScope.数据名}或${requestScope["数据名"]}从指定的域中获取数据，主要用于获取不同域中有同名数据的情况
       [^要点2]: 注意requestScope只代表请求范围，不等同于request对象，EL表达式中有一个隐式对象pageContext，该pageContext和JSP的九大内置对象pageContext是同一个对象

   + EL表达式对null进行了预处理

     + 如果是null，则默认向浏览器输出一个空字符串

     [^要点]: ${username}其实等同的java代码是：<%=request.getAttribute("username")==null?"":request.getAttribute("username")%>

   + page指令中有一个isELIgnored属性，可以忽略EL表达式

     ```
     <%@page contentType="text/html;charset=UTF-8" isELIgnored="true" %>
     isELIgnored="true" 表示忽略EL表达式
     isELIgnored="false" 表示不忽略EL表达式。（这是默认值）
     
     isELIgnored="true" 这个是全局的控制，该JSP不能使用EL表达式
     可以使用反斜杠进行局部控制：\${username} 这样也可以忽略EL表达式,但是这种会直接向浏览器输出该字符串
     ```

5. EL表达式的隐式对象

   + pageContext  

     [^要点1]: 页面上下文，属于jakarta.servlet.jsp.PageContext，九大内置对象的一种
     [^要点2]: pageContext中有getRequest()方法，获取request对象，返回ServletRequest类型；request对象在JSP中是内置对象，但是EL表达式中没有request这个隐式对象，只有pageContext这个隐式对象，所以需要pageContext.getRequest()来获取request对象，在EL表达式中可省略get方法的get，且会自动向下转型，如：

     ```
     通过EL表达式获取应用的根：
     ${pageContext.request.contextPath}
     等价于java代码：
     <%=((HttpServletRequest)pageContext.getRequest()).getContextPath()%>
     ```

   + param

     [^要点1]: EL表达式中虽然没有request隐式对象，但是可以直接使用param对象获取用户提交的数据，如：

     ```
     java语句获取用户提交数据
     	<%=request.getParameter("数据名")%>
     EL表达式，注意数据名不带双引号
         ${param.数据名},如：
         ${param.username}
         ${param.aihao}
         数据名可以是复选框的数据名，但是param只会显示第一个元素，相当于getParameter()
     
     ```

   + paramValues

     [^要点1]: 可以使用隐式对象paramValues获取如复选框的同一数据名的多条value，注意，返回的是一维数组，需要通过下标取出具体的value，相当于getParameterValues()

     ```
     java代码，返回的是一维数组，需要遍历
     <%=request.getParameterValues("aihao")%>
     EL表达式
     ${paramValues.aihao[0]}、${paramValues.aihao[1]}、${paramValues.aihao[2]}
     ```

   + initParam

     [^要点1]: initParam用于取application应用域中的上下文初始化参数

     ```
     java代码
     每页显示的记录条数：<%=application.getInitParameter("pageSize")%>
     页码：<%=application.getInitParameter("pageNum")%>
     EL表达式
     每页显示的记录条数：${initParam.pageSize}
     页码：${initParam.pageNum}
     ```

6. EL表达式的运算符

   + 算术运算符

     + +、-、*、/、%

       [^要点1]: 用于EL表达式的数字运算，结果为数字
       [^要点2]: EL表达式中的加号只能做求和运算，不能进行字符串拼接操作
       [^要点3]: 加号两边不是数字会被尝试转换为数字，如果转不成数字就会报错：NumberFormatException

   + 关系运算符

     + ==、 eq、 !=、 >、 >=、 <、 <= 

       [^要点1]: 结果为true/false

       ```
       等于关系的情况
       ${"abc"=="abc"}---true
       和java一样，会自动调用equals方法，
       如果是引用地址相同为true，
       如果是变量，值相同为true
       ```

       [^要点2]: eq方法和==的作用是一样的
       [^要点3]: !=也会调用对象的equals方法

   + 逻辑运算符

     + ! 、 &&、 ||、  not、 and、 or

       [^要点1]: 逻辑!的优先级比较高，如高于eq，写表达式的时候需要注意加小括号
       [^要点2]: not运算符和！运算符的效果完全相同

   + 条目运算符

     + ? 、：

   + 取值运算符

     + []、.

       [^要点1]: 用在EL表达式中用于属性值取值

   + empty运算符

     + empty

       [^要点1]: empty运算符的运算结果是boolean类型、判断是否为空，为空返回true、不为空返回false
       [^要点2]: empty的优先级高于==

       

## JSTL标签库

1. JSTL标签库

   - Java Standard Tag Lib（Java标准的标签库）

   - JSTL标签库常结合EL表达式一起使用，目的是模仿HTML用标签代替java程序，让JSP中的java代码消失，虽然JSP中写的是JSTL标签，但最后执行的还是对应的java程序,JSP中写java代码太难维护。
   - JSTL标签的java程序在jar包中，需要自己找，且存在与服务器的适配问题

2. JSTL的标签库的使用

   + tomcat10后的JSTL标签库对应的jar包

     + jakarta.servlet.jsp.jstl-2.0.0.jar
     + jakarta.servlet.jsp.jstl-api-2.0.0.jar

   + 第一步：IDEA中引入JSTL对应java程序jar包

     + 和mysql的数据库驱动一样，放在WEB-INF/lib目录下，然后点击add as lib

       > 需要放到lib目录下的原因是tomcat服务器和JDK中都没有

   - 第二步：在JSP中用taglib指令引入要使用的标签库

     - JSTL提供了很多种标签，重点掌握core核心标签库

       ```
       <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       这个就是核心标签库。
       
       prefix="这里随便起一个名字就行了，核心标签库，大家默认的叫做c，可以随意。"
       
       有多种标签库，每种标签库后的uri后面的路径实际上指向了一个xxx.tld文件。
       tld文件实际上是一个xml配置文件，该文件描述了“标签”和“java类”之间的关系。
       
       核心标签库对应的tld文件是：c.tld文件，
       在jakarta.servlet.jsp.jstl-2.0.0.jar里面META-INF目录下，有一个c.tld文件。
       ```

   - 第三步：在需要使用标签的位置使用即可。表面使用的是标签，底层实际上还是java程序。

     [^注意1]: JSTL还有很多其他标签库，如专门负责格式化操作的格式化标签库、sql标签库等等

3. JSTL标签的原理

   + 标签库对应tld文件源码解析

     ```
     <tag>
         <description>对该标签的描述</description>
         <name>catch</name> 标签的名字
         <tag-class>org.apache.taglibs.standard.tag.common.core.CatchTag</tag-class> 标签对应的java类。
         <body-content>JSP</body-content> 标签体当中可以出现的内容，如果是JSP，就表示标签体中可以出现符合JSP所有语法的代码。例如EL表达式。
         <attribute>
             <description>
             	对这个属性的描述
             </description>
             <name>var</name> 属性名
             <required>false</required> false表示该属性不是必须的。true表示该属性是必须的。
             <rtexprvalue>false</rtexprvalue> 该标签说明该属性是否支持EL表达式。false表示不支持。true表示支持。
         </attribute>
     </tag>
     <c:catch var="">
     	JSP....
     </c:catch>
     ```

4. 核心标签库core中的常用标签

   + c:if

     ```
     <c:if test="boolean类型，支持EL表达式"></c: if>
     
     示例1
     <c:if test="${empty para.username}">
     <h1>用户名不能为空</h1>
     </c: if>
     
     示例2
     <c:if test="${not empty para.username}">
     <h1>欢迎${para.username}</h1>
     </c: if>
     
     c：if标签中还有var和scope属性
     <c:if test="${not empty para.username}" var="v" scope="request">
     <h1>欢迎${para.username}</h1>
     </c: if>
     var中存的是test属性的值，v是给这个数据起的名字，scope="request"表示将v这个数据放到request域中，该数据可用如下EL表达式取出：${v}
     ```

   + c:forEach

     ```
     <!--varStatus属性表示var的状态对象，是一个java对象，该java对象代表了var的状态，状态对象的名字随意，该对象有一个count属性，可以随着集合或数组遍历自加1，该状态对象主要用于编号或者序号-->
     <!--var属性代表集合中的每一个元素，var中的名字是随意的-->
     <!--java程序取出集合为Object类型，需要向下强转，而标签使用EL表达式不需要向下强转-->
     <c:forEach items="需要遍历的集合，支持EL表达式" var="集合中的元素" varStatus="元素状态对象"> 
     ${元素状态对象.count} 
     标签体中还可以出现其他JSP和EL表达式
     </c: forEach>
     
     示例1：
     <c:forEach items="${stuList}" var="s" varStatus="stuStatus"> 
     编号：${stuStatus.count},id:${s,id},name:${s.name}<br>
     </c: forEach>
     <!--元素状态对象名字随意，该对象有一个count属性，可以随着集合或数组遍历自加1-->
     <!--var表示给取出的集合中的每一个元素-->
     
     c:forEach的第二种用法
     
     示例2：
     <c:forEach var="i" begin="1" end="10" step="2"> 
     ${i}<br> 
     </c: forEach>
     var用来决定循环的变量
     begin表示变量i的起始值
     end表示变量i的结束值
     step表示步长
     <!--底层实际上会把变量i存到pageContext域中，以供EL表达式取出-->
     ```

   - c:choose  c:when  c:when  ... c:otherwise

     ```
     JSTL标签库中用c:choose  c:when  c:when  ... c:otherwise表示if...else if...else if...else 
     示例1：
     <c:choose>
         <c:when test="${param.age < 18}">
             青少年
         </c:when>
         <c:when test="${param.age < 35}">
             青年
         </c:when>
         <c:when test="${param.age < 55}">
             中年
         </c:when>
         <c:otherwise>
             老年
         </c:otherwise>
     </c:choose>
     这个嵌套结构不能随便改，只能按这种结构嵌套
     ```



## OA终极改造

1. 使用Servlet + JSP + EL表达式 + JSTL标签对OA系统进行改造

2. base标签

   + HTML中有一个base标签，该标签可以设置整个网页的基础路径，该标签设置在head标签中，注意是base标签不是属性

     ```
     < base href="http://localhost:8080/oa/">
     ```

   + 当前网页中，路径凡是没有以“/”开始的，都会自动将base中的路径添加到这些路径之前，路径以"/"开始的，不会使用base标签的路径。

     ```
     < a href="ab/def"></ a>
     等同于
     < a href="http://localhost:8080/oa/ab/def"></ a>
     ```

     [^注意1]: JS代码中的路径，保险起见，最好不要依赖base标签，JS代码中的路径最好写上全路径。

   + base标签动态写法

     ```
     <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
     
     ${pageContext.request.scheme}---http
     ${pageContext.request.serverName}---localhost
     ${pageContext.request.serverPort}---8080
     ${pageContext.request.contextPath}---/项目名
     ```

[^注意]: 还差过滤器和监听器



# Filter过滤器

1. 当前的OA项目的缺陷

   + 一个系统有很多处理特定业务的Servlet类，这些Servlet类执行前都需要判断用户是否登录，判断用户是否登录的代码是固定的，相似的还有解决中文乱码问题，安全控制问题，显然代码没有得到复用
   + 可以使用Servlet规范中的Filter过滤器来解决该问题

2. Filter过滤器

   + Filter是过滤器是自己编写的实现了接口jarkata.servlet.Filter的java程序，Filter过滤器可以在Servlet目标程序执行前后添加过滤规则，一般都是在过滤器中编写公共代码来达到同一功能的代码复用，执行Servlet对象前会先默认执行过滤器中的代码，也可以在Servlet对象执行后在执行过滤器中特定的代码

   + 过滤器也通过请求路径来对应用户的请求，一个请求可以使用多个过滤器

   + Filter过滤器需要实现接口jarkata.servlet.Filter的所有方法

     [^init方法]: Filter对象第一次被创建之后调用，并且只调用一次，Filter对象默认情况下在服务器启动时创建对象，且在服务器关闭时销毁
     [^doFilter方法]: 在doFilter方法中编写过滤规则，用户发送一次请求就执行一次
     [^destory方法]: 在Filter对象被释放/销毁之前调用，并且只调用一次
     [^注意1]: Filter和Servlet一样都是单实例的，Filter和Servlet的生命周期是相同的，只是Filter在服务器启动时就会默认实例化

   + Filter对象需要在web.xml文件中进行配置，该配置和Servlet的配置很像，也可以使用注解@WebFilter({"*.do"})进行配置

     ```
     <filter>
         <filter-name>filter2</filter-name>
         <filter-class>com.bjpowernode.javaweb.servlet.Filter2</filter-class>
     </filter>
     <filter-mapping>
         <filter-name>filter2</filter-name>
         <!--模糊匹配，对所有的带.do的路径都进行过滤，可以写多个url-pattern标签-->
         <url-pattern>*.do</url-pattern>
     </filter-mapping>
     ```

     [^注意]: 推荐使用web.xml文件进行配置，方便调整过滤器的过滤顺序，使用注解配置只能依赖过滤器的类名来处理过滤器的顺序，改了源码，很麻烦

3. chain.doFilter(request, response);的作用

   + 过滤器中必须写上chain.doFilter(request, response);，该代码的作用是执行下一个过滤器，如果没有过滤器了，就执行最终的Servlet对象
   + 如果没有这行代码，不会执行下一个过滤器和最终的Servlet类

4. Filter的优先级天生比Servlet优先级高
   + /a.do 对应一个Filter，也对应一个Servlet。那么一定是先执行Filter，然后再执行Servlet。

5. Filter的配置路径方式：

   + 精确匹配：/a.do、/b.do、/dept/save

   + 模糊匹配所有路径：/* 

   + 模糊后缀匹配：*.do 后缀匹配 （注意不要以 / 开始，该匹配方式属于扩展匹配）

   + 模糊前缀匹配：/dept/*  

     [^注意1]: web.xml文件中进行配置的时，Filter的执行顺序依靠filter-mapping标签的配置位置越靠上，对应Filter优先级越高。且只与filter-mapping标签相关，与filter标签位置无关
     [^注意2]: 过滤器的调用遵循栈数据结构，最先过滤最后再结束过滤
     [^注意3]: 使用注解@WebFilter配置时，Filter的执行顺序是通过比较Filter的类名决定的，如：FilterA和FilterB，则先执行FilterA；Filter1和Filter2，则先执行Filter1

6. 责任链设计模式

   + 核心思想：程序运行阶段，动态的组合程序的调用顺序，一般代码都是在编译阶段就已经定好了程序的调用顺序了，要改调用顺序需要改java源码，违背OCP原则（开闭原则：对扩展开放、对修改关闭），项目扩展过程最好不修改之前写好的代码。
   + 过滤器最大的优点：通过web.xml文件的filter-mapping标签可以在程序运行阶段动态的调整Filter的执行顺序，即Filter使用了责任链设计模式

7. 使用过滤器改造OA项目



# Listener监听器

1. Listener监听器

   + 监听器Listener也是规范中的一员，Servlet中提供的所有监听器接口都是以“Listener”结尾的
   + 监听器的作用是Servlet规范留给Javaweb程序员的一系列特殊时机，特殊时刻使用对应的监听器执行想执行的代码

2. Servlet规范中提供了以下监听器

   + jakarta.servlet包下：

     - ServletContextListener

       [^要点1]: 自己编写的监听器类实现ServletContextListener接口并重写其中两个方法contextInitialized（）和contextDestoryed（），分别在ServletContext对象被创建和被销毁时调用执行对应的方法。
       [^注意]: 需要实现接口，需要使用@WebListener注解，对ServletContext对象的创建和销毁进行监听

     - ServletContextAttributeListener

     - ServletRequestListener

       [^要点1]: 需要实现接口，需要使用@WebListener注解，对request对象的创建和销毁进行监听，在对应时刻执行requestInitialized（）和requestDestoryed（）方法

     - ServletRequestAttributeListener

   + jakarta.servlet.http包下：

     - HttpSessionListener

       [^要点1]: 需要实现接口，需要使用@WebListener注解，对session对象的创建和销毁进行监听，在对应时刻执行sessionInitialized（）和sessionDestoryed（）方法

     - HttpSessionAttributeListener

       [^要点1]: 该监听器需要使用@WebListener注解进行标注，同样是自己写的类实现该接口，重写接口中的attributeAdded（）和attributeRemoved（）及attributeReplaced（）方法，分别对应session域中存储、删除、替换数据时执行对应的方法
       [^要点2]: 该监听器监听session域中数据的变化。只要session域对象上数据变化，则执行相应的方法。

     - HttpSessionBindingListener

       [^要点1]: 该监听器不需要使用@WebListener进行标注，只需要具体的类对该类进行实现
       [^要点2]: 该接口的实现类对象被放入session域的时候触发bind事件，从session中删除的时候，触发unbind事件
       [^要点3]: 在user类中通过HttpSessionBindingListener传入的event获取session对象

       ```java
       public void valueBound(HttpSessionBindingEvent event) {
               //用户登录了
               ServletContext application=event.getSession().getServletContext();
           }
       ```

       [^注意]: 假设Customer类没有实现该监听器，那么Customer对象放入session或者从session删除的时候，不会触发bind和unbind事件

     - HttpSessionIdListener

       [^要点1]: session的sessionid发生改变的时，监听器中的唯一一个方法就会被调用

     - HttpSessionActivationListener

       [^要点1]: 监听session对象的钝化和活化
       [^钝化]: session对象从内存存储到硬盘文件
       [^活化]: 从硬盘文件把session恢复到内存

3. 实现一个监听器的步骤

   > 以ServletContextListener为例

   - 第一步：编写一个类实现ServletContextListener接口，并且实现里面的方法

     ```
     void contextInitialized(ServletContextEvent event)
     void contextDestroyed(ServletContextEvent event)
     ```

   - 第二步：在web.xml文件中对ServletContextListener进行配置

     ```
     <listener>
         <listener-class>com.bjpowernode.javaweb.listener.MyServletContextListener</listener-class>
     </listener>
     ```

     [^注意1]: 可以不使用配置文件，可以使用注解@WebListener

   [^注意2]: 所有监听器中的方法由服务器在发生某个特殊事件发生后来负责调用

4. 使用监听器对OA项目在线用户个数进行实时统计

   + 方案一：用一个session对象代表一个用户，使用HttpSessionListener，在session新建的时候count++，销毁的时候count--，页面在线展示count即可，但是这种方式不好，因为统计了所有一段时间内访问站点的用户
   + 方案二：使用HttpSessionBindingListener监听向session对象中存入user对象的时刻，user对象存入session域，就count++，user对象移除session或session超时销毁，就count--，（也有bug，比如直接关浏览器，会隔好长时间才更新在线人数）
