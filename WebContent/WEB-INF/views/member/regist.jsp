<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
<script src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" />
<script type="text/javascript">
$(document).ready(function(){
	var idchecked = -1;	// -1:확인요망, 1:id존재함, 0:사용가능
	
	$('#user_id').change(function(){	// 아이디 중복 체크 메시지
		idcheck = -1;
		$('#check_id_result').html('아이디 중복체크를 하세요');
	});
	
	$('#check_id').click(function(){	// 아이디 중복 체크 처리
		var user_id = $('#user_id').val().trim();
	
		if (user_id === '') {
			alert('체크할 아이디를 입력하세요');
			$('#user_id').focus();
			return;
		}
		
		$.getJSON('checkID',{"user_id":user_id},
			function(data){
				console.log(data);
				console.log('id  : ' + data.id);
				console.log('cnt : ' + data.cnt);
				console.log('msg : ' + data.msg);
				$('#check_id_result').html(data.msg);
				idchecked = 0;
			}		
		);
	});
	
	$('#regist_form').bind('submit',function(){	// 회원가입 완료 처리
		if ($('#user_id').val().length === 0) {
			alert('가입할 아이디를 입력하세요');
			$('#user_id').focus();
			return false;
		}
		//idchecked = 0 인지 확인
		//이름, 비밀번호 비어있는지 확인...
		$('#regist_form').attr('action', 'registAction')
		                 .attr('method', 'POST');
		$('#regist_form').submit();	
	});
});
</script>
</head>
<body>
<form id="regist_form">
<table>
	<caption>회원 가입</caption>
<tr>
	<th>아이디</th>
	<td><input type="text"   id="user_id" autofocus="autofocus" name="user_id" />
		<input type="button" id="check_id" value="아이디 중복 체크" />
		<span id="check_id_result"></span>
	</td>
</tr>
<tr>
	<th>이름</th>
	<td><input type="text" name="user_name" id="user_name" /></td>
</tr>
<tr>
	<th>비밀번호</th>
	<td><input type="password" name="user_pw" id="user_pw" /></td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" id="regist_ok" value="가입 완료" />
	</td>
</tr>
</table>
</form>
</body>
</html>