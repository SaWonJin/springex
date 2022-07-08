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
							<a class="move" href=<c:out value="${board.bno}"/>>
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
				
				<!-- pageing 시작 -->
				<div class='pull-right'>
					<ul class="pagination">
						<c:if test="${pageMaker.prev}">
						<li class="paginate_button previous">
						<a href="${pageMaker.startPage -1}">Previous</a></li>
						</c:if>
						
						<c:forEach var="num" begin="${pageMaker.startPage}"
							end="${pageMaker.endPage}">
							<li class="paginate_button  ${pageMaker.cri.pageNum == num ? "active":""} ">
								<a href="${num}">${num}</a>
							</li>
						</c:forEach>
						
						<c:if test="${pageMaker.next}">
							<li class="paginate_button next"><a href="${pageMaker.endPage +1}">Next</a></li>
						</c:if>
						
						<!-- 위 <a>에 값만 넣어주게 된다면 숫자만 들어가기 때문에 URL이 존재하지 않는다고 뜬다. 
						따라서 스크립트를 이용해서 추가해주어야 한다. 
						스크립트는 하단에 페이징을 위한 스크립트를 보면 된다. -->
					</ul>
				</div>
				
				<form id="actionForm" action="/board/list" method="get">
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
				</form>
				<!-- List에 get방식으로 값들을 넘겨주기 위해 hidden 타입으로 만들었다.  -->

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

		/* 아래 함수는 방금 실행했던 페이지로 보내주기 위한 동작을 하는 함수다. 
		위 result에서 글 번호를 받아와서 bno에 매치시켜준다.*/
		function send() {
			location.href = "/board/get?bno=" + result;
		}
		
		
		$(document).ready(function() {

							checkModal(result);

							/* checkModal함수는 파라미터에 따라서 모달창을 보여주거나 내용을 수정한 뒤 보이도록 작성합니다. */
							/* 새로운 글이 작성되는 경우 RedirecAttributes로 게시물의 번호가 전송되므로 이를 이용합니다. */
							function checkModal(result) {
								/* checkModal이라는 함수를 만든다.  */

								history.replaceState({}, null, null);
								/* history를 요즘엔 많이 사용안하지만, 알아두면 좋다.  */
								
								if (result === '' || history.state) {
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
							});

							/* 버튼 클릭했을 때 페이지 새로고침  */
							$('#close').click(function name() {
								location.reload();
							});
							
							/* 페이징을 위한 스크립트 처리 */
							var actionForm = $("#actionForm");
							
							$(".paginate_button a").on("click", function (e) {
								
								e.preventDefault();
								
								console.log('click');
								
								/* 띄어쓰기도 영향을 준다. 주의해야한다. */
								actionForm.find("input[name='pageNum']").val($(this).attr("href"));
								
								actionForm.submit();
								/* 위 처리를 다하고 꼭 submit을 해줘야 페이징 처리가 된다.  */
							});
							
							
							/* 페이지 번호 같이 넘기기 위한 스크립트 
							title에 있는 <a>태그 안에 걸어줄 것이다.  */
							
							$(".move").on("click", function (e) {
								
								e.preventDefault();
								
								/* 게시물의 제목을 클릭했을 때 추가로 bno값을 전송하기 위해 input태그를 만들어 추가해준다.  */
								actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
							
								actionForm.attr("action","/board/get");
							
								actionForm.submit();
							})

						});
	</script>