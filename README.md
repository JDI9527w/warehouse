#项目笔记
一、使用@CacheConfigure注解:

    启动类上标注@EnableCaching注解
    在使用缓存的类上标注@CacheConfig(cacheNames = "全限定类名")来指定缓存的键的前缀,一般是类的全路径).
    在方法上标注注解,并指定key: @Cacheable(key="key名称")
二、联合唯一索引
    
    可以在数据库中设置联合唯一索引，
    CREATE UNIQUE INDEX index_name ON table_name (column1, column2, ...);
    这种联合索引的组合必须是唯一的.
    如果想要插入时跳过重复的项,可以使用 INSERT IGNORE
    MySQL:使用show index from tableName 查询表的索引.
三、Myabtis的使用

    使用自带的UpdateById方法时，需要在实体类中使用@TableId标注出主键Id，
    否则会报：Invalid bound statement (not found) 错误。

