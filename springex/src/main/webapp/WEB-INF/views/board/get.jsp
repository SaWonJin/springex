<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			
			<div>
				<div class="form-group">
					<label for="">Bno</label>
					<input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
				</div>
				
				<div class="form-group">
					<label>Title</label>
					<input class="form-control" name="title" value='<c:out value="${board.title }"/>'readonly="readonly">
				</div>
				
				<div class="form-group">
					<label>Text area</label>
					<textarea class="form-group" rows="3" name="content" readonly="readonly"
					style="width:100%; hedth:6.25em; boarder:none; resize:none;"><c:out value="${board.content}"/></textarea>
				</div>
				
				<div class="form-group">
					<label>Writer</label>
					<input class="form-control" name="title" value='<c:out value="${board.writer}"/>'readonly="readonly">
				</div>
				
				<button wonjin='modify' class="btn btn-default" onclick="modi()">Modify</button>
				<button wonjin='list' class="btn btn-default" onclick="list()">List</button>
				
				<form action="/board/modify" id="operform" method="get">
					<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}"/>'>
					<!-- 밑에 두개는 paging처리르 위한 값들로 hidden으로 보내준다. -->
					<!-- c태그 옆 value에 ${cri.pageNum}에서 cri는 컨트롤러에서 @ModelAttribute로 지정했던 cri이다. -->
					<input type="hidden" id="pageNum" value='<c:out value="${cri.pageNum}"/>'>
					<input type="hidden" id="amount" value='<c:out value="${cri.amount}"/>'>
					<!-- 검색기능이 추가되면서 아래 두 값들도 보내준다. -->
					<input type="hidden" id="keyword" value='<c:out value="${cri.keyword}"/>'>
					<input type="hidden" id="type" value='<c:out value="${cri.type}"/>'>
				</form>
			</div>
		</div>
	</div>
</div>
<%@include file="../includes/footer.jsp"%>

<!-- 버튼에 대한 스크립트 -->
<script>
	function modi() {
		location.href = "/board/modify?bno="+${board.bno};
	}
	
	function list() {
		
		var type = "${cri.type}";
		var keyword = "${cri.keyword}";
		/* 
		그냥 처리하게 되면 
		location.href = "/board/list"; 이렇게 작성해도 상관없지만 
		list로 넘어갈때 내가 본 게시물이 있는 페이지로 넘어가고 싶다면 아래와 같이 URL을 바꿔주면 된다.
		*/
		if(type == ""|| keyword == "") {
			/* type 과 keyword가 없을 때 = 검색기능 안 할때  */
			location.href = "/board/list?pageNum="+${cri.pageNum}+"&amount="+${cri.amount}
			
		}else{
			
		location.href = "/board/list?pageNum="+${cri.pageNum}+"&amount="+${cri.amount}+"&type="+type+"&keyword="+keyword;
		}
	}
</script>

<!-- form에 대한 스크립트 -->
<script>

	$(document).ready(function () {
		
		/* 위에 Form태그의 id값이 operForm이다. 그것을 가져와서 객체를 만들어준다. */
		var operForm = $("#operForm");
		
		/* 수정으로 넘어갈때는 글 번호를 들고 가야하기 때문에 아래와 같이 작성한다.  */
		$("button[wonjin='modify']").on("click", function(e) {
			
			operForm.attr("action", "/board/modify").submit();
		});
		
		
		/* 리스트로 넘어가게 된다면 bno값을 가지고 갈 필요가 없기 때문에 remove()로 삭제 시켜준다. */
		/* wonjin은 각 버튼에 이름을 임의로 지정한 것이다. 
		알아보기 쉽게 wonjin으로 작성한것이고 다른 경우 어떤 부분인지 명확하게 작성하는 것이 졸다.*/
		$("button[wonjin='list']".on("click", function(e) {
			
			operForm.find("#bno").remove();
			operForm.attr("action","/boatd/list")
			operForm.submit();
		});
	});
</script>
