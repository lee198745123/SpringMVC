<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var = "cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
 function goWrite(){
	 location.href= "${cpath}/boardForm.do"
 }
 function goJson(){
	 $.ajax({
		 url : "${cpath}/boardListAjax.do",
		 type : "get",    
		 dataType : "json",   // 받는 데이터 타입
		 success : resultHtml, // 성공시 callback함수임
		 error : function(){alert("error");}		 
	 });
	
 }							//		  1  2  3.......index    		
 function resultHtml(data){ // data=>[{},{},{}......]
 	 var html ="<table class='table'>";
 	 html+="<tr>";
 	 html+="<td>번호</td>";
 	 html+="<td>제목</td>";
 	 html+="<td>조회수</td>";
 	 html+="<td>작성자</td>";
 	 html+="<td>작성일</td>"; 	 
 	 html+="<td>삭제</td>"; 	 
 	 html+="</tr>";
 	 //반복문
 	 $.each(data,(index,obj)=>{// obj는 위에있는 data의 {}이런식의 하나의 데이터임
 		 html+="<tr>";
 	 	 html+="<td><a>"+obj.idx+"</a></td>";
 	 	 html+="<td>"+obj.title+"</td>";
 	 	 html+="<td>"+obj.count+"</td>";
 	 	 html+="<td>"+obj.writer+"</td>";
 	 	 html+="<td>"+obj.indate+"</td>"; 	 
 	 	 html+="<td><button class='btn btn-warning btn-sm' onclick='delBtn("+obj.idx+")' >삭제</button></td>"; 	 
 	 	 html+="</tr>";
 	});
 	 
 	 html+="</table>";	 
 	 $("#list").html(html);// 밑에 있는div태그에있는id =list에 보여주기 위한 코드
 	 
 }
 function delBtn(idx) {
	   if(confirm ("정말로 삭제 하시겠습니까?")==true){
	      $.ajax({
	         url: "${cpath}/boardDeleteAjax.do",
	         type:"get",		//보내는 데이터 타입
	         data:{"idx" : idx}, // 보내는 데이터
	         success : goJson,
	         error : function() {alert("error")}               
	         
	      });
	   }else{
	      return false;
	   }
	   
	}
 
</script>	
</head>
<body>
	<div class="container">
		<h2>Spring MVC BOARD</h2>
		<div class="panel panel-default">
			<div class="panel-heading">BOARD LIST</div>
			<div class="panel-body">
				<table class="table">
					<tr>
						<td>번호</td>
						<td>제목</td>
						<td>조회수</td>
						<td>작성자</td>
						<td>작성일</td>
						
					</tr>
					<c:forEach var="vo" items="${list}">
						<tr>
							<td>${vo.idx}</td>
							<td><a href="${cpath}/boardCount.do?idx=${vo.idx}">${vo.title}</a></td>
							<td>${vo.count}</td>
							<td>${vo.writer}</td>
							<td>${vo.indate}</td>
						</tr>
					</c:forEach>
				</table>
				<button class="btn btn-info btn-sm" onclick="goWrite()">글쓰기 </button><br><br>
				<button class="btn btn-success btn-sm" onclick="goJson()">JSON DATA 가져오기(Ajax)</button>
				<div id="list">여기에 게시판 리스트를 출력하시오</div>
			</div>
			<div class="panel-footer">빅데이터분석-4차(이길수)</div>
		</div>
	</div>
</body>
</html>