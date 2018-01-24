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
    <div class="row-md-3">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    HEBLY723 <small>图像搜索系统 - by ssm基础框架</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row-md-9">
        <div class="col-md-12 column">
            <button class="col-md-3">本地上传图片</button>
            <input placeholder="粘贴图片链接" class="col-md-8">
            <button class="col-md-1">搜索</button>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>
</body>
</html>