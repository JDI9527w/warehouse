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

三、Mybatis

    使用自带的UpdateById方法时，需要在实体类中使用@TableId标注出主键Id，
    否则会报：Invalid bound statement (not found) 错误。
三.一、递归删除.
        
    mysql8.0以上支持CTE(公用表表达式),递归删除可以使用:
    -- 假设表名为`your_table`，包含`id`和`parent_id`字段
    WITH RECURSIVE cte AS (  
    -- 基础查询：查找具有特定parent_id的记录  
    SELECT id  
    FROM your_table  
    WHERE parent_id = ? -- 这里的问号应替换为您要查找的父ID
    UNION ALL
    -- 递归查询：查找所有子记录  
    SELECT yt.id  
    FROM your_table yt  
    INNER JOIN cte ON yt.parent_id = cte.id  
    )  
    -- 执行删除操作（注意：此操作应在事务中执行，以便在出现问题时能够回滚）  
    DELETE FROM your_table  
    WHERE id IN (SELECT id FROM cte);

  三.二、请求参数处理
  
    get请求没有请求体，所以使用@RequestBody无法接收参数，
    get请求的数据是通过url参数或路径变量传递的.
    所以接收时,参数对象不需要标注@RequestBody注解,
    如果是单独的参数,可以使用@RequsetParam注解注释.

四、Tree工具类（需要仔细体会


    public class TreeUtil {
    
      /**
        * 将list合成树
        *
        * @param list 需要合成树的List
        * @param rootCheck 判断E中为根节点的条件，如：x->x.getPId()==-1L , x->x.getParentId()==null,x->x.getParentMenuId()==0
        * @param parentCheck 判断E中为父节点条件，如：(x,y)->x.getId().equals(y.getPId())
        * @param setSubChildren   E中设置下级数据方法，如： Menu::setSubMenus
        * @param <E>  泛型实体对象
        * @return   合成好的树
          */
          public static <E> List<E> makeTree(List<E> list, Predicate<E> rootCheck, BiFunction<E, E, Boolean> parentCheck, BiConsumer<E, List<E>> setSubChildren) {
          return list.stream().filter(rootCheck).peek(x -> setSubChildren.accept(x, makeChildren(x, list, parentCheck, setSubChildren))).collect(Collectors.toList());
          }
    
    
        /**
         *  将树打平成tree
         * @param tree  需要打平的树
         * @param getSubChildren  设置下级数据方法，如： Menu::getSubMenus,x->x.setSubMenus(null)
         * @param setSubChildren 将下级数据置空方法，如： x->x.setSubMenus(null)
         * @return  打平后的数据
         * @param <E> 泛型实体对象
         */
        public static <E> List<E> flat(List<E> tree, Function<E, List<E>> getSubChildren, Consumer<E> setSubChildren) {
            List<E> res = new ArrayList<>();
            forPostOrder(tree, item -> {
                setSubChildren.accept(item);
                res.add(item);
            }, getSubChildren);
            return res;
        }
    
    
        /**
         * 前序遍历
         *
         * @param tree 需要遍历的树
         * @param consumer  遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
         * @param setSubChildren  设置下级数据方法，如： Menu::getSubMenus,x->x.setSubMenus(null)
         * @param <E> 泛型实体对象
         */
        public static <E> void forPreOrder(List<E> tree, Consumer<E> consumer, Function<E, List<E>> setSubChildren) {
            for (E l : tree) {
                consumer.accept(l);
                List<E> es = setSubChildren.apply(l);
                if (es != null && es.size() > 0) {
                    forPreOrder(es, consumer, setSubChildren);
                }
            }
        }
    
    
        /**
         * 层序遍历
         *
         * @param tree 需要遍历的树
         * @param consumer 遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
         * @param setSubChildren 设置下级数据方法，如： Menu::getSubMenus,x->x.setSubMenus(null)
         * @param <E> 泛型实体对象
         */
        public static <E> void forLevelOrder(List<E> tree, Consumer<E> consumer, Function<E, List<E>> setSubChildren) {
            Queue<E> queue = new LinkedList<>(tree);
            while (!queue.isEmpty()) {
                E item = queue.poll();
                consumer.accept(item);
                List<E> childList = setSubChildren.apply(item);
                if (childList != null && !childList.isEmpty()) {
                    queue.addAll(childList);
                }
            }
        }
    
    
        /**
         * 后序遍历
         *
         * @param tree 需要遍历的树
         * @param consumer 遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
         * @param setSubChildren 设置下级数据方法，如： Menu::getSubMenus,x->x.setSubMenus(null)
         * @param <E> 泛型实体对象
         */
        public static <E> void forPostOrder(List<E> tree, Consumer<E> consumer, Function<E, List<E>> setSubChildren) {
            for (E item : tree) {
                List<E> childList = setSubChildren.apply(item);
                if (childList != null && !childList.isEmpty()) {
                    forPostOrder(childList, consumer, setSubChildren);
                }
                consumer.accept(item);
            }
        }
    
        /**
         * 对树所有子节点按comparator排序
         *
         * @param tree 需要排序的树
         * @param comparator  排序规则Comparator，如：Comparator.comparing(MenuVo::getRank)按Rank正序 ,(x,y)->y.getRank().compareTo(x.getRank())，按Rank倒序
         * @param getChildren 获取下级数据方法，如：MenuVo::getSubMenus
         * @return 排序好的树
         * @param <E> 泛型实体对象
         */
        public static <E> List<E> sort(List<E> tree, Comparator<? super E> comparator, Function<E, List<E>> getChildren) {
            for (E item : tree) {
                List<E> childList = getChildren.apply(item);
                if (childList != null && !childList.isEmpty()) {
                    sort(childList,comparator,getChildren);
                }
            }
            tree.sort(comparator);
            return tree;
        }
        
        private static <E> List<E> makeChildren(E parent, List<E> allData, BiFunction<E, E, Boolean> parentCheck, BiConsumer<E, List<E>> children) {
            return allData.stream().filter(x -> parentCheck.apply(parent, x)).peek(x -> children.accept(x, makeChildren(x, allData, parentCheck, children))).collect(Collectors.toList());
        }
    }

