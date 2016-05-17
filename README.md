# spring-aop-db-log  
use spring aop to do the db operation log  
  
1、AOP  
有两种类型的切点，一个是数据库层的增删改查，另一个是web请求层的业务操作，这样做是为了避免非业务人员的操作，数据改动日志也会持久化下来。  
所以，在@Before会去创建loggerAction，然后在@After去检查是否有对应的action的logger日志记录，如果没有，则清除action  
web请求层是切在@ModuleOperation这个注解上的，数据库层是切在service类的增删改查方法上的。  
  
2、自定义注解  
@ModuleManage模块管理的注解，用于区分业务类型，用在controller上  
@ModuleOperation模块具体操作的注解，用于辨识业务人员进行了哪种类型的操作，用在具体的请求方法上  
@Table数据库表注解  
@Column数据库表字段注解  
@SelectOne Service层用来标识findOne(Long id)这种查询的注解，原因是为了在记录update和delete操作日志时，查询具体模型改动前后的对象  

3、多线程
由于记录操作日志与主业务流程并无关联，所以使用多线程去处理具体记录日志的逻辑，使用ThreadLocal变量来保存loggerAction执行的feature，后面的logger对象的记录基于ThreadLocal中的feature去获取具体的loggerAction对象  
