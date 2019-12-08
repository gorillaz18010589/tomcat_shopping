<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   	
<!DOCTYPE html>
<html>
<script	src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js" integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU=" crossorigin="anonymous"></script>
		<link href="show.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
nav{ /* 用flex沒有彈性固定尺寸因為logo:100px,跟menu:100px所以右邊全白 */
	display:flex; 
	height:70px;
}

nav>.logo{
	flex:none;
	width:170px;
	background-color:black;
	}
nav>.menu{
	background-color:black;
	flex:auto;
	
}
nav>.user{  /*盡量把容器空間裝滿*/
	flex:none;
	width:350px;
	background-color:black;
}
nav>.icon-bar{  /*盡量把容器空間裝滿*/
	background-color:black;
	flex:none;
	width:300px;
	background-color:black;
}
/* main樣式設定 */

main{ /* 容器 */
	display:flex;
	flex-wrap:wrap;
	justify-content:center;
	align-items:center;
	background-color:white;
	}

main>.item{ /* 項目 */
	flex:none;
	width:300px;
	margin:10px;
	background-color:white;

}

main>.item1{ /* 標題項目 */
	flex:none;
	width:270;
	margin:10px;
	background-color:black;

}
.title1{
		font-family:微軟正黑體;
		font-size:25px;
		font-weight:900;
}

.icon-bar {	
	
	display:flex;flex-wrap:wrap;
	justify-content:center;
	align-items:center
}

.icon-bar a {
 display:inline-block;;
  text-align: center;
  padding: 16px;
  transition: all 0.3s ease;
  color: white;
  font-size: 20px;
}

.icon-bar a:hover {
  background-color: #000;
}

.facebook {
  background: #3B5998;
  color: white;
}

.twitter {
  background: #55ACEE;
  color: white;

}

.google {
  background: #dd4b39;
  color: white;
 
}

.linkedin {
  background: #007bb5;
  color: white;
 
}

.youtube {
  background: #bb0000;
  color: white;
 
}


.sidepanel  {
  width: 0;
  position: fixed;
  z-index: 1;
  height: 350px;
  right: 0;
  top: 0;
  background-color: #111;
  overflow-x: hidden;
  transition: 0.5s;
  padding-top: 60px;
}

.sidepanel a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 25px;
  color: #818181;
  display: block;
  transition: 0.3s;
}

.sidepanel a:hover {
  color: #f1f1f1;
}

.sidepanel .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
}

.openbtn {
  display:block;
  position: fixed;
  right:0px;
  top:0px;
  font-size: 20px;
  cursor: pointer;
  background-color: #111;
  color: white;
  padding: 4px 10px;
  border: none;
}

.openbtn:hover {
  background-color:#444;
}

.hotbox{

	display:flex;flex-wrap:wrap;
	background-color:b;
	justify-content:center;
	align-items:center;
}
div>.hot{
	flex:none;
	background-color:white;
	width:370px;
	height:180px;
	margin:10px;
}
div>.hot1{
	flex:none;
	background-color:white;
	width:370px;
	height:180px;
	margin:10px;
}

.item img{
	flex:none;
	width:250px;
	margin:10px;
	height:200px;
}
#flip{
	
	padding:0px;
	text-align:center;
	color:#818181;
	background-color:black;
	font-family:微軟正黑體;
	border:1px solid black;
	font-weight:bold; 
	font-size:20px; 
	
		
}
#panel{
	padding:65px;
	display:none;
	background-color:black;

}

p{
	padding:5px;
	font-family:微軟正體黑;
}
h3{
	text-align:center;
}
.logo1{
	width:150px;
	height:50px;
	margin:10px;
	margin-right:-5px;
}
.amenu{
	padding:21px;
}
.itemPhopt{
	opacity:1;
	
}
.itemPhopt:hover{
	opacity:0.9;
}
.movenav{
	display:inline-block;
	background-color:black;
	width:60px;
	height:20px;
	
}
.movenav2{
	display:inline-block;
	background-color:black;
	width:60px;
	height:20px
}
.amove1{
	
	
	margin:3px 1px;
	font-family:微軟正體黑;
	text-decoration:none;
	color:white;
	font-size:6px;
	margin:5px;
	
	
}
.amove2{
	flex:none;
	margin:3px 1px;
	font-family:微軟正體黑;
	text-decoration:none;
	color:white;
	font-size:6px;
	width:-5px;
	height:-4px;
	margin:5px;
}
.a1{
	font-size:18px;
	font-family:微軟正黑體;
	text-decoration:none;
	color:white;
}
.a1:hover{
	color:FireBrick;
	font-size:19px;
	text-shadow: FireBrick 1px 1px;
  
}
.a2{
	font-family:微軟正黑體;
	color:black;
	text-decoration:none;
	}

.a2:hover{
	color:Orange ;
}
.hotphot{ width:370px;
}
.a3{
	margin-top:9px;
	font-family:微軟正黑體;
	color:black;
	font-size:18px;
	background-color:white;
}

.a3:hover{
	color:Orange ;
}
.a4{
	margin-top:0px;
	font-family:微軟正黑體;
	color:SlateGray;
	font-size:14px;
	background-color:white;
}
.databox{
	margin-bottom:0px;
	display:inline-block;
	width:60px;
	height:20px;
	
}
.tagbox{
	margin-bottom:0px;
	display:inline-block;
	width:60px;
	height:20px
}
.data{
	margin:3px 1px;
	font-family:微軟正體黑;
	text-decoration:none;
	color:SlateGray;
	font-size:6px;
	
	
}
.tag{
	margin:3px 1px;
	font-family:微軟正體黑;
	text-decoration:none;
	color:black;
	font-size:6px;
	margin-left:0px;
}
.tag:hover{
	color:SlateGray;
}

.tagbox1{
	background-color:white;
	border-style:solid;
	display:inline;
	margin:2px;
	padding:2px;
	border-width:medium;
	
}
.tagname{
	font-family:微軟正體黑;
	text-decoration:none;
	color:black;
	font-size:14px;
	padding:7px;
	display:inline-block;
	margin:4px 2px;
}
.tagname:hover{
	color:orange;
}
input[type=text] {
  width: 130px;
  box-sizing: border-box;
  border: 2px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  background-color: white;
  background-image: url('searchicon.png');
  background-position: 10px 10px; 
  background-repeat: no-repeat;
  padding: 12px 20px 12px 40px;
  -webkit-transition: width 0.4s ease-in-out;
  transition: width 0.4s ease-in-out;
}

input[type=text]:focus {
  width: 100%;
}
.showcartbox{
	display:flex;
	justify-content:center;
	align-items:center;
	background-color:black;
}
.showcart{
	flex:none;
	background-color:black;
    width:50px;
}

.userbox{
display:flex;
	justify-content:center;
	align-items:center;
	background-color:black;
}

.user{
	flex:none;
	background-color:black;
    width:50px;
}

@media screen and (max-width:1200px){
    main>.item{width:45%;}
	.hot1{
		display:none;
	}
	.item img{
		width:90%;
	}
	.title1{
		font-size:30px;
	}
	.openbtn{
		display:block;
	}
}
@media screen and (max-width:600px){
    main>.item{
		width:100%;		
	}
	.openbtn{
		display:block;
	}
	.amenu{
		display:none;
	}
	.item img{
		width:90%
	}
	.user a{
		font-family:微軟正黑體;
		font-size:18px;
	}
	menu.item2{
		display:none;
	}
}
		</style>
		<script>
		$(function(){
				$("#flip").click(function(){
					$("#panel").slideToggle("slow");  //可用一秒1000,也可用slow
				});
		});
		
		
		
		/*function Alert(){
			alert("心動就點進去看看吧~");
		}

		function Settime(){
			var t = window.setTimeout(Alert,3000);
		}
		*/
		function openNav() {
			document.getElementById("mySidepanel").style.width = "250px";
		}

		function closeNav() {
			document.getElementById("mySidepanel").style.width = "0";
		}
		
		</script>
	</head>
	
	<body style="margin:0px 0px;">
	
		<c:set var="x" value="0"></c:set>
	<c:forEach items="${cartlist }" var="i">
		<c:set var="x" value="${x+1 }"></c:set>
	</c:forEach>
	
	
	<!-- 導覽列 -->
	<nav>
		<div class="logo">
			<a href="Controller?page=index" target=_top class="a1">
			<img src="photot/logo.jpg"class="logo1"></a>
		</div>
		
		<div class="menu">
			<div class="amenu">
				<a href="Controller?page=mobiles" class="a1">Apple</a>
				<a href="Controller?page=laptops" class="a1">三星</a>
				<a href="Controller?page=clothing" class="a1">Sony</a>
				<a href="Controller?page=home-decor" class="a1">Oppo</a>
				<a href="title.html" target=_top class="a1">購買須知</a>
				<a href="#" class="a1">聯絡我們</a>
			</div>	
		</div>
		
		<div class="userbox">	
			<div class="user">
				<a href="Controller?page=login"><img src="img/user3.png">(<c:out value="${user.getName}"/>)</a>
			</div>
		</div>
		
	<div class="showcartbox">	
		<div class="showcart">
			<a href="Controller?page=showcart"><img src="img/showcat4.png">(<c:out value="${x}"/>)</a>
		</div>
	</div>
	
	
		
		<div class="user" >
				<div class="search">
					<div>
						<form action="Controller" method="get" style="border: none;margin:0px;padding: 0px;margin-bottom: 20px;">
							<input type="hidden" name="page" value="search_product">
							<input type="hidden" name="action" value="search">
							<input type="text" name="name" placeholder="Search..">
							 <img src="img/search.png"><input type="submit" value="Go!">  
						</form>
					</div>
				</div>
		
		
		
			<div id="mySidepanel" class="sidepanel">
				<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
				<a href="Controller?page=index">首頁</a>
				<c:choose>
					<c:when test="${session == null}">
						<li><a href="Controller?page=login">會員登入</a></li>
						<li><a href="Controller?page=sign-up">會員註冊</a></li>
					</c:when>
					<c:when test="${session != null}">
						<li><a href="Controller?page=logout" style="color: #F24638;">會員登出</a></li>
						<li><a href="#">My Account(<c:out value="${username }"></c:out>)</a></li>
					</c:when>
				</c:choose>
			</div>

		<button class="openbtn" onclick="openNav()">☰</button>
		</div>
	</nav>

			 
	<!-- 1.RWD 回應是設計的關鍵動作:欄為變化
		 Flex 如何控制欄位的變化?
		 例如:4欄>2欄>1欄
		 大螢幕-4欄,中螢幕-2欄-小螢幕-1欄
		 
		 2.規劃:
			1200px 以上的規劃:4欄,固定尺寸 (電腦筆電) 大多1344px
			600px ~ 1200px 之間的規劃:2欄,彈性尺寸 (拿直拿橫時都100%寬彈性
			600px  以下:智慧型手機- 1欄,彈性尺寸
	-->
	<!-- 幻燈片 -->
 	<c:forEach items="${list }" var="product">
	 	<c:if test="${product.getFeatured() == 'yes' }">
			<div class="slideshow-container">
				 <!-- Full-width images with number and caption text -->
				<div class="mySlides fade" style="display:block;">
					<div class="numbertext"></div>
					<img src="${product.getImage() }" style="width:100%">
					<div class="text"></div>
				</div>
	
	  <!-- 幻燈片左右按鈕 -->
			<a class="prev" onclick="plusSlides(-1)">&#10094;</a>
			<a class="next" onclick="plusSlides(1)">&#10095;</a>
			</div>
			</c:if>
	</c:forEach>
	

<!-- 幻燈片下3個小圓點 -->
	<div class="dot1">
		<span class="dot" onclick="currentSlide(1)"></span> 
		<span class="dot" onclick="currentSlide(2)"></span> 
		<span class="dot" onclick="currentSlide(3)"></span> 
	</div>

		<script>
	var slideIndex = 1;
		showSlides(slideIndex);

		// Next/previous controls
		function plusSlides(n) {
			showSlides(slideIndex += n);
		}

		// Thumbnail image controls
		function currentSlide(n) {
			showSlides(slideIndex = n);
		}

		function showSlides(n) {
			var i;
			var slides = document.getElementsByClassName("mySlides");
			var dots = document.getElementsByClassName("dot");
			if (n > slides.length) {slideIndex = 1} 
			if (n < 1) {slideIndex = slides.length}
			for (i = 0; i < slides.length; i++) {
				slides[i].style.display = "none"; 
			}
			for (i = 0; i < dots.length; i++) {
				dots[i].className = dots[i].className.replace(" active", "");
			}
			slides[slideIndex-1].style.display = "block"; 
			dots[slideIndex-1].className += " active";
		}
		</script>
	<nav class="nav2" style="display:flex; justify-content:center; align-items:flex-end; margin:20px;">
			<div class="blankbox1" style="background-color:white; flex:auto;width:200px; height:50px; "></div>
			<a  style="text-decoration: none"href="https://www.google.com.tw/">
				<div class="morenext" style="background-color:white; width:200px; height:40px; flex:none; text-align:center;line-height:50px; font-family:微軟正黑體; font-weight:bold; font-size:28px;   color:black; text-decoration:underline; " >推薦商品</div></a>
			<div class="blankbox2" style="background-color:white; flex:auto;width:200px; height:50px;"></div>
	</nav>
	
	
	
		<!-- 推薦商品 -->
			<main>  
			<c:forEach items="${list }" var="product">
			<c:if test="${product.getFeatured() == 'yes' }">
				<div class="item">
				<a  href="Controller?page=intoProduct&id=<c:out value="${product.getId()}"/>">
							<img src="${product.getImage()}" class="itemPhopt" > </a>
						<nav class="movenav">
					<a href="#" class="amove1">$<c:out  value="${product.getPrice() }"></c:out></a>
						</nav>
						<nav class="movenav2">
							<a  href="Controller?page=favorite&id=<c:out value="${product.getId()}"/>" class="amove2">我的最愛</a>
						</nav>
					<a  style="text-decoration: none"href="https://www.google.com.tw/">
						<p class="a2"><c:out value="${ product.getName() }"></c:out></p></a>
				</div>
					</c:if>
			</c:forEach>
			</main>
	

		
		<nav class="nav2" style="display:flex; justify-content:center; align-items:center; margin:20px;">
			<div class="blankbox1" style="background-color:white; flex:auto;width:200px; height:50px; "></div>
			<a  style="text-decoration: none"href="https://www.google.com.tw/">
				<div class="morenext" style="background-color:white; width:200px; height:50px; flex:none; text-align:center;line-height:50px; font-family:微軟正黑體;border:1px outset black; font-weight:bold; font-size:20px; border-width:3px; border-style:solid; color:black;" >+ 看更多商品</div></a>
			<div class="blankbox2" style="background-color:white; flex:auto;width:200px; height:50px;"></div>
		</nav>
		
		
		
		
	<c:forEach items="${list }" var="product">
	<div class="hotbox">  
	
		<div class="hot">
			<a  href="Controller?page=intoProduct&id=<c:out value="${product.getId()}"/>"  style="text-decoration: none">
				<img src="${product.getImage()}" class="hotphot" style="width:370px; height:180px;"></a>
		</div>
		
		<div class="hot">
			<nav class="movenav">
				<a href="#" class="amove1">特搜報導</a>
			</nav>
			<nav class="movenav2">
				<a href="#" class="amove2">漫威系列</a>
			</nav>
				<a  style="text-decoration: none"href="https://www.google.com.tw/">
				<p class="a3"><c:out value="${ product.getName() }"></c:out></p></a>
				
				<a  style="text-decoration: none"href="https://www.google.com.tw/">
				<p class="a4">$<c:out  value="${product.getPrice() }"></c:out></p></a>
				
					<nav class="databox">
						<span class="data">2019-04-23</span>
					</nav>
					<nav class="tagbox">
						<a href="#" class="tag">漫威系列</a>
					</nav>		
		</div>
		<div class="hot1">
			<div class="morenext" style="background-color:white; width:200px; height:40px; flex:none; text-align:center;line-height:50px; font-family:微軟正黑體; font-weight:bold; font-size:28px;   color:black;font-style:italic; text-decoration:underline; margin-bottom:0px;" >熱門商品</div>
			<nav class="tagbox1">
				<a  href="Controller?page=favorite&id=<c:out value="${product.getId()}"/> class="tagname">${ product.getName() }</a>
			</nav>
		</div>
			</c:forEach>
		
	
	
		

	
	
	<nav style="margin-top:50px; margin-bottom:0px;"> 
		<div class="logo">
			<img src="logo.jpg"class="logo1">
		</div>
		<div class="menu">
			<div class="amenu">
				<a href="#" class="a1">Fb粉絲團</a>
				<a href="#" class="a1">電影好康</a>
				<a href="#" class="a1">作者專區</a>
				<a href="#" class="a1">關於我們</a>
				<a href="#" class="a1">聯絡我們</a>
				<a href="#" class="a1">下載App</a>
			</div>
		</div>
		
			<div class="icon-bar">
				<a href="https://www.facebook.com/" class="facebook"><i class="fa fa-facebook"></i></a> 
				<a href="https://www.google.com.tw/" class="google"><i class="fa fa-google"></i></a> 
				<a href="https://line.me/zh-hant/" class="linkedin"><i class="fa fa-linkedin"></i></a>
				<a href="https://www.youtube.com/" class="youtube"><i class="fa fa-youtube"></i></a> 
			</div>
	</nav>
		
</body>
</html>