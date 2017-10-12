<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#speak').click(function(){
		$('#myaudio').stop();
		$('#myaudio').attr('src','../Speaker?&' + $('#myform').serialize());
		$('#myaudio').attr('autoplay','autoplay');
	});
});
</script>
</head>
<body>
	<form id="myform" method="post">
		<div class="container">
			<div class="jumbotron">
				<h2>text2speech sample example</h2><br />
				<p>텍스트의 내용은 선택한 보이스이 언어와 일치해야 합니다. 혼합언어(예 :영어 텍스트와 일본 여성음성)는 유효한
					결과를 생헝하지 않습니다. 한글은 조만간 지원됩니다.</p>
			</div>
			<div class="row">
				<div class="col-lg-12 col-sm-12">
			<textarea rows="7" name="statement" class="form-control">
Dog goes "woof"
Cat goes "meow"
Bird goes "tweet"
And mouse goes "squeek"
Cow goes "moo"
Frog goes "croak"
And the elephant goes "toot"
Ducks say "quack"
And fish go "blub"
And the seal goes "ow ow ow"
But there's one sound
That no one knows
What does the fox say?
"Ring-ding-ding-ding-dingeringeding!
Gering-ding-ding-ding-dingeringeding!
Gering-ding-ding-ding-dingeringeding!"
What the fox say?
"Wa-pa-pa-pa-pa-pa-pow!
Wa-pa-pa-pa-pa-pa-pow!
Wa-pa-pa-pa-pa-pa-pow!"
What the fox say?
"Hatee-hatee-hatee-ho!
Hatee-hatee-hatee-ho!
Hatee-hatee-hatee-ho!"
			</textarea>
		</div>
	</div> <br/>
	<div class="row">
	<div class="col-lg-10 col-sm-10">
			<select name="voice" class="form-control">
				<c:forEach items="${voices}" var="voice">
					<option value="${voice.name}">${voice.name}: ${voice.language}</option>
				</c:forEach>
			</select>
			</div>
			<div class="col-lg-2 col-sm-2">
			 <input type="button" id="speak" value="읽기" class="btn btn-default btn-danger btn-block" />
			 </div>
			</div>
			</div>
			<div class="container">
			<div class="row">
			<div class="col-lg-12">
			<audio id="myaudio" style="width:100%; "controls="controls" preload="auto" ></audio>
			</div>
			</div>
			</div>
	</form>
</body>
</html>