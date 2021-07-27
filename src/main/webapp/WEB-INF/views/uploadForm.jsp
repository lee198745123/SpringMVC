<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<c:set var = "cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
<form action="${cpath}/uploadFormAction.do" method ="post" enctype="multipart/form-data">
	<!-- multiple: 여러개의 파일 업로드 가능, enctype="multipart/form-data": 인코딩을 무조건 해줘야함 -->
	<input type="text" name="title">
	<input type ="file" name="uploadFile" multiple="multiple">
	<button>파일업로드</button>
</form>
</body>
</html>