<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/6
  Time: 20:10
  note: 使用bootstrap输出后台返回的requestScope对象
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String appPath = request.getContextPath(); %>
<html>
<head>
    <title>图书列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    HiSEN <small>图书管理系统 - by ssm基础框架</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <ul class="nav nav-tabs">
                <li class="active"><a href="<%=appPath%>/image/list">首页</a></li>
                <li><a href="<%=appPath%>/image/detail/1000">图书具体信息</a></li>
                <li><a href="<%=appPath%>/add.jsp">添加图书信息</a></li>
                <li class="disabled"><a href="#">信息</a></li>
            </ul>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>图书列表 <small>显示当前图书库存信息</small></h1>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="row">
                    <div class="col-md-3">图书编号</div>
                    <div class="col-md-3">图书名字</div>
                    <div class="col-md-3">图书数量</div>
                    <div class="col-md-3">操作</div>
            </div>
                <div>
                <c:forEach var="image" items="${requestScope.get('list')}" varStatus="status">
                    <div class="row">
                        <div class="col-md-3">${image.id}</div>
                        <div class="col-md-3">${image.detail}</div>
                        <div class="col-md-3"><img src="${image.location}"></div>
                        <div class="col-md-3">
                            <a href="<%=appPath%>/image/detail/${image.id}">详情</a> |
                            <a href="<%=appPath%>/image/del/${image.id}">删除</a>
                        </div>

                    </div>
                </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>
</body>
</html>