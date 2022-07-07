<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C/DTD HTML 4.01 Transitional// EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Tables</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				Board List Page
				<button id="regBtn" type="button" class="btn btn-xs pull-right">Register
					New Board</button>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>수정일</th>
						</tr>
					</thead>
					<c:forEach items="${list}" var="board">
						<tr>
							<td><c:out value="${board.bno}" /></td>
							<td>
							<a href='/board/get?bno=<c:out value="${board.bno}"/>'>
							<c:out value="${board.title}" /></a></td>
							<td><c:out value="${board.writer}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.regdate}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.updateDate}" /></td>
						</tr>
					</c:forEach>
				</table>
				<!-- table 태그의 끝 -->

				<!-- Modal  추가 -->
				<div class="modal fade" id="myModal">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" id="close">&times;</button>
								<h4 class="modal-title" id="myModalLabel">Modal title</h4>
							</div>
							<div class="modal-body"></div>
							<div class="modal-footer">
								<button type="button" id="close" class="btn btn-default">Close</button>
								<button type="button" onclick="send()" class="btn btn-primary">내가쓴글로</button>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
					<!-- /.modal -->
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../includes/footer.jsp"%>

	<!-- 상황에 따른 메시지를 확인 할 수 있는 스크립트 -->
	<script type="text/javascript">
		/* result 객체를 생성하는데 controller 에서 bno를 받아오는 값이 result다 */
		/* 받아온 글번호를 checkModal이라는 것에 넣는다.  */
		var result = '<c:out value="${result}"/>';

		function send() {
			location.href = "/board/get?bno=" + result;
		}
		$(document).ready(function() {

							checkModal(result);

							/* checkModal함수는 파라미터에 따라서 모달창을 보여주거나 내용을 수정한 뒤 보이도록 작성합니다. */
							/* 새로운 글이 작성되는 경우 RedirecAttributes로 게시물의 번호가 전송되므로 이를 이용합니다. */
							function checkModal(result) {
								/* checkModal이라는 함수를 만든다.  */

								if (result === '') {
									/* 만약 result가 가져오는 값이 없다면 돌려보낸다.  */
									/* 글 쓰기 실패했을 경우 */
									return;
								}

								/* parseInt 문자열을 정수로 바꾸는 함수입니다.  */
								/* 정수 바꾼 값이 0보다 클 경우 */
								if (parseInt(result) > 0) {
									$(".modal-body").html(
											"게시글" + parseInt(result)
													+ " 번이 등록되었습니다. ");
								}
								/* (modal-body)부분에 .html을 이용해서 글을 넣어준다. */

								$("#myModal").modal("show");
								/* id 가 myModal 인 것을 보여주라 */

							}

							/* 버튼을 클릭했을 때 register로 이동되게 해놓았다. */
							$('#regBtn').on("click", function name() {
								self.location = "/board/register";
							})

							/* 버튼 클릭했을 때 페이지 새로고침  */
							$('#close').click(function name() {
								location.reload();
							})

						});
	</script>