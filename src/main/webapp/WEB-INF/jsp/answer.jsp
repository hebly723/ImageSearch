<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/10
  Time: 20:10
  note: 使用bootstrap输出后台返回的requestScope对象
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String appPath = request.getContextPath(); %>
<html>
<head>
    <title>图像列表</title>
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
                    HEBLY723 <small>图像搜索系统 - by ssm基础框架</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
<%--
            <div class="col-md-3">
                    <small>原图像</small>
            </div>
            <div class="col-md-3">
                <img height="200" width="200" src="${requestScope.get('origin')}">
            </div>
--%>

        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>图像列表 <small>显示当前图像查询结果</small></h1>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div>
            <table class="table table-hover table-striped">
                <thead class="col-md-12">
                <tr>
                    <th class="col-1">图像编号</th>
                    <th class="col-1">图像详情</th>
                    <th class="col-8">图像</th>
                    <th class="col-2">操作</th>
                </tr>
                </thead>
                <tbody class="col-md-12">
                <c:forEach var="image" items="${requestScope.get('list')}" varStatus="status">
                    <tr class="col-md-12">
                        <td class="col-1">${image.id}</td>
                        <td class="col-1">${image.detail}</td>
                        <td class="col-8"><img class="img-responsive" src="${image.location}"></td>
                        <td class="col-2">
                            <a href="<%=appPath%>/image/detail/${image.id}">详情</a> |
                            <a href="<%=appPath%>/image/del/${image.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>
</body>
</html>