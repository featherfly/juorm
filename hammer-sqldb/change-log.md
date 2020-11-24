# 0.4.9 2020-11-24
    1.使用ASM替换javassist修复自定义的Mapper继承GenericHammer接口并重载了get(Serializable)方法报错的问题
    
# 0.4.8 2020-11-17
    1.Hammer加入delete(Ids,Class)方法,delete(Ids),delete(Entities)方法实现一条sql进行批量删除
    2.升级common-core、common-db、common-model-repository

# 0.4.7 2020-7-16

    1.修复自定义Mapper接口覆盖了GenericHammer，Hammer接口定义的方法，没有继承来默认实现的问题（目前只是先了方法不带泛型的接口，后续实现带泛型的）
    2.加入JdbcImpl，临时实现，后续会剔除对SpringJdbcTemplateImpl的继承
    3.修复dsl中的条件参数为空时异常的问题
    
# 0.4.6 2020-7-14

    1.修复save(List) list.size = 0 时的空指针异常
    2.修复javassit动态创建Mapper在spring boot中出错的问题
    
    
# 0.4.5 2020-6-30

    1.所有参数为SerializableFunction, Value的方法加入同名参数为的SerializableSupplier方法
    2.部分参数为SerializableFunction更改为各种ReturnXXXFunction
    3.兼容性升级，LangUtils修改为Lang，StringUtils修改为Strings
    4.升级依赖包

# 0.4.4 2020-5-23

    1.include标签的file参数改为namespace参数
                        
# 0.4.3 2020-4-20

    1.完成batch save优化，如果数据库支持批量插入并且当使用数据库生成主键策略时数据库对应驱动能够返回批量生成的主键，则使用批量插入sql
                    否则使用循环插入
                    
# 0.4.2 2020-4-18

    1.重构项目，把builder，mapping移动到common-model-repository和common-db
                    抽象模型在common-model-repository模块,sqldb的实现在common-db模块
    
# 0.4.1 2020-4-18

    1.修复获取type时判断用错参数的bug
    2.更新文档
    
# 0.4.0 2020-4-17

    1.为模板执行加入操作数据能力，可以使用模板sql进行insert,update,delete操作
    2.@Template加入type参数，默认值AUTO
    2.Mapper中int xxx(ar1, ar2.. arn)， void yyy(ar1, ar2...arn)
                    当返回值是void时，执行execute（即执行insert,update,delete等操作）
                    当返回值是int并且type参数是AUTO或者EXECUTE时，执行execute（即执行insert,update,delete等操作）
                    如果要执行一个查询返回一个int值，可以加上type=QUERY或者使用包装类型Integer作为返回值
    
# 0.3.3 2020-4-15

    1.重命名@TplExecution为@Template,@TplParam为@Param，@TplParamType为ParamType
    2.@Template加入value()，用于直接在注解上使用模板（如sqldb模块的sql模板）,加入isTemplate()用于在执行value()时是否使用模板引擎
                用于value()是一个不可变的字符串就可以禁用其使用模板引擎，可以提高一丢丢的性能-_-，默认是开启的，当前版本未实现禁用模板引擎
    3.@Param加入name(),而value()作为name()的快捷别名，name()优先级高于value()
    4.加入SqldbHammer接口
    
# 0.3.2 2020-4-11

    1.修正依赖BUG
    
# 0.3.1 2020-3-18

    1.Hammer加入update(String),delete(String)方法
    
# 0.3.0 2020-3-17

    1.项目整体重命名（模块名，包名）为正式名称hammer
    2.加入Mapper注解，相当于以前TplExecution标注类的功能，移除TplExecution标注类的功能，现在只能标注方法
    
    
# 0.2.6 2020-2-17

    1.加入count方法,
        query.find(repo).count();
        query.find(repo).where().lt("age", 18).count();
    2.加入property(String,AggregateFunction)方法
        query.find(repo).property("price", AggregateFunction.SUM);

# 0.2.5 2019-12-03

    1.升级依赖
    
# 0.2.4 2019-11-28

    1.Juorm和GenericJuorm加入多个重载的delete方法
    
# 0.2.3 2019-11-21

    1.升级constant版本，支持constant无配置文件的默认配置

# 0.2.2 2019-11-15

    1.Juorm api 方法名称变更

# 0.2.1 2019-11-15

    1.QueryEntityExpression加入sort()
    2.加入QueryLimitExecutor和TypeQueryLimitExecutor，调用limit后的表达式可以使用pagination方法返回分页包装对象
    

# 0.2.0 2019-10-28

    1.ClassMapping移动到core模块
    2.Query加入with() on() property()用于联合查询
    3.Updater的propertyNumber和increase方法中的SerializableFunction加入Number类型约束
        
    
# 0.1.4 2019-9-18

    1.find(Class type)后的条件在执行时使用TypeExecutor，只能返回find时传入的对象
    
# 0.1.3 2019-9-17

    1.加入SQLiteDialect
    2.加入SerializableFunction支持

# 0.1.2 2019-9-3

    1.加入SqlDbConfigurator，可以从配置直接获取可运行的JuormJdbcImpl
    2.Jdbc接口去掉spring依赖
    
# 0.1.1 2019-8-21

    1.升级依赖，解决版本导致出错问题
    2.tpl mapper interface加入default method支持
    3.tpl加入wrap模板方法 ${tpl_wrap("user")}
    
# 0.1.0 2019-8-20

    1.实现基本的crud
    2.实现dsl风格的query,update,delete
    3.实现基于模板的sql查询（类似mybatis）