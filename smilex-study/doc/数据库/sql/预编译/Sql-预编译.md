[toc]

---

# 预编译的作用

1. 可以优化SQL执行

   预编译之后的 sql 多数情况下可以直接执行，DBMS 不需要再次编译，越复杂的sql，编译的复杂度将越大，预编译阶段可以合并多次操作为一个操作。可以提升性能。 

2. 防止sql注入

    使用预编译，而其后注入的参数将不会再进行SQL编译。也就是说其后注入进来的参数系统将不会认为它会是一条SQL语句，而默认其是一个参数，参数中的or或者and 等就不是SQL语法保留字了。 

# 数据库预编译

1,  数据库SQL编译特性

数据库接受到sql语句之后，需要词法和语义解析，优化sql语句，制定执行计划。这需要花费一些时间。但是很多情况，我们的一条sql语句可能会反复执行，或者每次执行的时候只有个别的值不同（比如query的where子句值不同，update的set子句值不同,insert的values值不同）。

2. 减少编译的方法

如果每次都需要经过上面的词法语义解析、语句优化、制定执行计划等，则效率就明显不行了。为了解决上面的问题，于是就有了预编译，预编译语句就是将这类语句中的值用占位符替代，可以视为将sql语句模板化或者说参数化。一次编译、多次运行，省去了解析优化等过程。

例如：

```sql
select * from user where id = ? -- 可以是1，2，3...
```

3. 缓存预编译

预编译语句被DB的编译器编译后的执行代码被缓存下来,那么下次调用时只要是相同的预编译语句就不需要编译,只要将参数直接传入编译过的语句执行代码中(相当于一个涵数)就会得到执行。
并不是所有预编译语句都一定会被缓存,数据库本身会用一种策略（内部机制）。 

4. 预编译实现方法

预编译是通过`PreparedStatement`和占位符来实现的。 

## 数据库开启预编译

1. 数据库是否默认开启预编译和 `Jdbc` 版本有关

   - 配置 `jdbc` 链接时强制开启预编译和缓存:`useServerPrepStmts`和`cachePrepStmts`参数。
   - 预编译和预编译缓存一定要同时开启或同时关闭。否则会影响执行效率 

   ```java
   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prepare_stmt_test?user=root&password=root&useServerPrepStmts=true&cachePrepStmts=true");  
   ```

2. `mysql`的预编译

   - 开启了预编译缓存后，connection之间，预编译的结果是独立的，是无法共享的，一个connection无法得到另外一个connection的预编译缓存结果。
   - 经过试验，`mysql`的预编译功能对性能影响不大，但在`jdbc`中使用`PreparedStatement`是必要的，可以有效地防止`sql注入`。
   - 相同`PreparedStatement`的对象 ，可以不用开启预编译缓存。

   ```java
   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prepare_stmt_test?user=root&password=root&useServerPrepStmts=true");  
   PreparedStatement stmt = conn.prepareStatement(sql);  
   stmt.setString(1, "aaa");  
   ResultSet rs1 = stmt.executeQuery();//第一次执行  
   s1.close();  
   stmt.setString(1, "ddd");  
   ResultSet rs2 = stmt.executeQuery();//第二次执行  
   rs2.close();  
   stmt.close(); 
   //查看mysql日志
   /*1 Prepare          select * from users where name = ?
       1 Execute          select * from users where name = 'aaa'
       1 Execute          select * from users where name = 'ddd'*/
   
   ```




# Mybatis 预编译

预编译分为客户端和服务端预编译，而 `Mybatis` 就是一种客户端预编译。

那么我们看一下 `XMLStatementBuilder` 看看他是怎么实现的预编译

```java
/**
   * 解析mapper中的SQL语句
   */
  public void parseStatementNode() {
      // 获取sql ID
    String id = context.getStringAttribute("id");
      
      // 获取数据库ID，判断databaseId是否匹配
    String databaseId = context.getStringAttribute("databaseId");
    if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
      return;
    }

      // 获取标签属性
      // 获取节点名称<select> <insert>等
    String nodeName = context.getNode().getNodeName();
      // 转换指令类型
    SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
      // 判断是否是查询
    boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
    boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
    boolean useCache = context.getBooleanAttribute("useCache", isSelect);
    boolean resultOrdered = context.getBooleanAttribute("resultOrdered", false);

    // Include Fragments before parsing
    XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
    includeParser.applyIncludes(context.getNode());

      // 获取参数类型，转换出class
    String parameterType = context.getStringAttribute("parameterType");
    Class<?> parameterTypeClass = resolveClass(parameterType);

      // 获取驱动
    String lang = context.getStringAttribute("lang");
    LanguageDriver langDriver = getLanguageDriver(lang);

    // Parse selectKey after includes and remove them.
    processSelectKeyNodes(id, parameterTypeClass, langDriver);

    // Parse the SQL (pre: <selectKey> and <include> were parsed and removed)
      // 转换SQl， 主键生成器
    KeyGenerator keyGenerator;
    String keyStatementId = id + SelectKeyGenerator.SELECT_KEY_SUFFIX;
    keyStatementId = builderAssistant.applyCurrentNamespace(keyStatementId, true);
    if (configuration.hasKeyGenerator(keyStatementId)) {
      keyGenerator = configuration.getKeyGenerator(keyStatementId);
    } else {
      keyGenerator = context.getBooleanAttribute("useGeneratedKeys",
          configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType))
          ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
    }

      // 重要:解析SQL语句,封装成一个SqlSource
    SqlSource sqlSource = langDriver.createSqlSource(configuration, context, parameterTypeClass);
    StatementType statementType = StatementType.valueOf(context.getStringAttribute("statementType", StatementType.PREPARED.toString()));
    Integer fetchSize = context.getIntAttribute("fetchSize");
    Integer timeout = context.getIntAttribute("timeout");
    String parameterMap = context.getStringAttribute("parameterMap");
    String resultType = context.getStringAttribute("resultType");
    Class<?> resultTypeClass = resolveClass(resultType);
    String resultMap = context.getStringAttribute("resultMap");
    String resultSetType = context.getStringAttribute("resultSetType");
    ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);
    if (resultSetTypeEnum == null) {
      resultSetTypeEnum = configuration.getDefaultResultSetType();
    }
    String keyProperty = context.getStringAttribute("keyProperty");
    String keyColumn = context.getStringAttribute("keyColumn");
    String resultSets = context.getStringAttribute("resultSets");

      // 解析完毕,最后通过MapperBuilderAssistant创建MappedStatement对象
      // 统一保存到Configuration的mappedStatements属性中
    builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
        fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
        resultSetTypeEnum, flushCache, useCache, resultOrdered,
        keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets);
  }
```

1. `Mybaits` 会对 `Sql标签` 做解析，然后转换成一个`SqlSource` 在生成一个`MappedStatement` 保存。
2. `langDriver.createSqlSource`这个方法。这该方法会通过`LanguageDriver`对`SQL`语句进行解析，生成一个`SqlSource`。 
3.  **`SqlSource`封装了映射文件或者注解中定义的`SQL`语句，它不能直接交给数据库执行，因为里面可能包含动态`SQL`或者占位符等元素**。 
4.  而`MyBatis`在实际执行`SQL`语句时，会调用`SqlSource`的`getBoundSql`()方法获取一个`BoundSql`对象，**`BoundSql`是将`SqlSource`中的动态内容经过处理后，返回的实际可执行的`SQL`语句，其中包含?占位符List封装的有序的参数映射关系，此外还有一些额外信息标识每个参数的属性名称等。** 

看一下 `XMLLanguageDriver.createSqlSource` 这个方法

```java
// 创建SqlSource
@Override
public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
     //创建XMLScriptBuilder对象
    XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
    
  //通过XMLScriptBuilder解析SQL脚本
    return builder.parseScriptNode();
}

```

 `XMLScriptBuilder.parseScriptNodes` 进行解析 

```java
/**
   * 解析SQL脚本
   */
public SqlSource parseScriptNode() {
  //解析动态标签,包括动态SQL和${}。执行后动态SQL和${}已经被解析完毕。
  //此时SQL语句中的#{}还没有处理,#{}会在SQL执行时动态解析
  MixedSqlNode rootSqlNode = parseDynamicTags(context);

  //如果是dynamic的,则创建DynamicSqlSource,否则创建RawSqlSource
  SqlSource sqlSource = null;
  if (isDynamic) {
    sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
  } else {
    sqlSource = new RawSqlSource(configuration, rootSqlNode, parameterType);
  }
  return sqlSource;
}
```

 是否为`动态SQL`的判断在`parseDynamicTags`方法中

```java
 protected MixedSqlNode parseDynamicTags(XNode node) {
    List<SqlNode> contents = new ArrayList<>();
    NodeList children = node.getNode().getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      XNode child = node.newXNode(children.item(i));
        // 文本节点
      if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE) {
          // 封装到 TextSqlNode 
        String data = child.getStringBody("");
        TextSqlNode textSqlNode = new TextSqlNode(data);
          // 如果包含${},则是动态Sql
        if (textSqlNode.isDynamic()) {
          contents.add(textSqlNode);
          isDynamic = true;
        } else {
            // 除了${}外，其它的都是静态
          contents.add(new StaticTextSqlNode(data));
        }
      } else if (child.getNode().getNodeType() == Node.ELEMENT_NODE) { // issue #628
        String nodeName = child.getNode().getNodeName();
        NodeHandler handler = nodeHandlerMap.get(nodeName);
        if (handler == null) {
          throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
        }
        handler.handleNode(child, contents);
        isDynamic = true;
      }
    }
    return new MixedSqlNode(contents);
  }
```

 如果是动态标签，创建的就是`DynamicSqlSource`，其获取的`BoundSql`就是直接进行字符串的替换。对于非动态标签，则创建`RawSqlSource`，对应`?占位符`的`SQL`语句

