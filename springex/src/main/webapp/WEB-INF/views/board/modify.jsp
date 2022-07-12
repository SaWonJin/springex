<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C/DTD HTML 4.01 Transitional// EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../includes/header.jsp" %>
	<form role="form" action="/board/modify" method="post">
	<!-- 추가 
	pageNum 과 amount라는 값이 존재하므로 <form>태그내에서 같이 전송할 수 있게 수정해야 한다. -->
	<input type="hidden" name='pageNum' value="<c:out value="${cri.pageNum}"/>">
	<input type="hidden" name='amount' value="<c:out value="${cri.amount}"/>">
	<!-- 추가
	type 과 keyword도 같이 보내 검색 페이징도 보내준다.  -->
	<input type="hidden" name='type' value="<c:out value="${cri.type}"/>">
	<input type="hidden" name='keyword' value="<c:out value="${cri.keyword}"/>">
		<div class="form-group">
			<label>Bno</label>
			<input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
		</div>
		
		<div class="form-group">>
			<label>Title</label>
			<input class="form-control" name="title" value='<c:out value="${board.title}"/>'>
		</div>
		
		<div class="form-group">
			<label>Text area</label>
			<textarea class="form-control" rows="3" name="content"><c:out value="${baord.content}"/></textarea>
		</div>
		
		<div class="form-group">>
			<label>Writer</label>
			<input class="form-control" name="writer" value='<c:out value="${board.title}"/>' readonly="readonly">
		</div>
		
		<div class="form-group">>
			<label>RegDate</label>
			<input class="form-control" name="regDate" value='<fmt:formatDate pattern = "yyyy/MM/dd" value="${board.regdate}"/>' readonly="readonly">
		</div>
		
		<div class="form-group">>
			<label>Update Date</label>
			<input class="form-control" name="updateDate" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate}" />' readonly="readonly">
		</div>
		
		<button type="submit" data-oper='modify'class="btn btn-default">Modify</button>
		<button type="submit" data-oper='remove'class="btn btn-default">Remove</button>
		<button type="submit" data-oper='list'class="btn btn-default">List</button>
	</form>

<%@ include file="../includes/footer.jsp" %>

<script type="text/javascript">
	$(document).ready(function () {
		
		var formObj = $("form");
		
		$('button').on("click", function (e) {
			
			
			/* e.preventDefault();이거는 페이지를 이동시킨다거나 form안에 있는 input 등을 전송한다던가
			그러한 동작이 있는데 그 동작들을 중단 시키는 것임*/
			e.preventDefault();
			
			var operation = $(this).data("oper");
			
			console.log(operation);
			
			if(operation == 'remove') {
				
				formObj.attr("action", "/board/remove");
				
			}else if(operation == 'list'){
				
				//self.location = "/board/list";
				//return;
				
				formObj.attr("action","/board/list").attr("method","get");
				
				/* 아래는 왜 있는지 모르겠는 스크립트..  */
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				/* 페이징 + 검색기능이 추가되어 스크립츠에도 추가되어야 한다. */
				var keywordTag = $("input[name='keyword']").clone();
				var typeTag = $("input[name='type']").clone();
				
				
				//-----------------
				formObj.empty();
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				/* 검색 기능 추가로 아래 두개 추가 */
				formObj.append(keywordTag);
				formObj.append(typeTag);
		
				/* 책 설명으로는 
					만일 사용자가 "List"버튼을 클릭한다면 <form>태그에서 필요한 부분만 잠시 복사(clone)해서 보관해두고
					<form>태그 내의 모든 내용은 지워버린다.(empty)
					이후에 필요한 태그들만 추가해서 '/board/list'를 호출하는 형태를 이용합니다.
				*/
				
			}
			formObj.submit();
		});
		
	});
</script>