<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="container">
                <div class="row">
                    <div class="col-md-3"><h3 align="center">图像编号</h3></div>
                    <div class="col-md-3"><h3 align="center">图像详情</h3></div>
                    <div class="col-md-3"><h3 align="center">图像</h3></div>
                    <div class="col-md-3"><h3 align="center">操作</h3></div>
                </div>
                <div>
                <c:forEach var="image" items="${requestScope.get('list')}" varStatus="status">
                    <div class="row">
                        <div class="col-md-3" align="center">${image.id}</div>
                        <div class="col-md-3" align="center">${image.detail}</div>
                        <div class="col-md-3"><img class="img-responsive" src="${image.location}"></div>
                        <div class="col-md-3" align="center">
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