<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String appPath = request.getContextPath(); %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>文件上传下载</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="col-md-12 column">
        <div class="row-md-1">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <form  action="http://localhost:8080/ImageSearch/Image/file/upload" method="post" enctype="multipart/form-data">
                    选择文件:<input type="file" name="file" width="120px">
                    <input type="submit" value="上传">
                </form>
            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</div>
<hr>
</body>
</html>
