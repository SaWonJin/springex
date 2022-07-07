<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C/DTD HTML 4.01 Transitional// EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../includes/header.jsp" %>
	<form role="form" action="/board/modify" method="post">
		<div class="form-group">
			<label>Bno</label>
			<input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
		</div>
		
		<div class="form-group">>
			<label>Title</label>
			<input class="form-control" name="title" value='<c:out value="${board.title}"/>'>
		</div>
		
		<div class="form-group">>
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
				formObj.empty();
		
			}
			formObj.submit();
		});
		
	});
</script>