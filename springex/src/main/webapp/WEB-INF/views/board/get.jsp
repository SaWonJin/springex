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
					<label for="">Bno</label> <input class="form-control" name="bno"
						value='<c:out value="${board.bno}"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>Title</label> <input class="form-control" name="title"
						value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>

				<div class="form-group">
					<label>Text area</label>
					<textarea class="form-group" rows="3" name="content"
						readonly="readonly"
						style="width: 100%; hedth: 6.25em; boarder: none; resize: none;"><c:out
							value="${board.content}" /></textarea>
				</div>

				<div class="form-group">
					<label>Writer</label> <input class="form-control" name="title"
						value='<c:out value="${board.writer}"/>' readonly="readonly">
				</div>

				<button wonjin='modify' class="btn btn-default" onclick="modi()">Modify</button>
				<button wonjin='list' class="btn btn-default" onclick="list()">List</button>

				<form action="/board/modify" id="operform" method="get">
					<input type="hidden" id="bno" name="bno"
						value='<c:out value="${board.bno}"/>'>
					<!-- 밑에 두개는 paging처리르 위한 값들로 hidden으로 보내준다. -->
					<!-- c태그 옆 value에 ${cri.pageNum}에서 cri는 컨트롤러에서 @ModelAttribute로 지정했던 cri이다. -->
					<input type="hidden" id="pageNum"
						value='<c:out value="${cri.pageNum}"/>'> <input
						type="hidden" id="amount" value='<c:out value="${cri.amount}"/>'>
					<!-- 검색기능이 추가되면서 아래 두 값들도 보내준다. -->
					<input type="hidden" id="keyword"
						value='<c:out value="${cri.keyword}"/>'> <input
						type="hidden" id="type" value='<c:out value="${cri.type}"/>'>
				</form>
			</div>
		</div>
	</div>
</div>
<div class='row'>
	<div class="col-lg-12">
		<div class="panel panel-default">
			<!-- <div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
			</div> -->
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
				<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New
					Reply</button>
			</div>
			<div class="panel-body">
				<ul class="chat">
					<li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">User00</strong> <small
									class="pull-right text-muted">fffffff2200202</small>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> <input class="form-control" name='reply'
						value='New Reply!!!!'>
				</div>
				<div class="form-group">
					<label>Replyer</label> <input class="form-control" name='replyer'
						value='replyer'>
				</div>
				<div class="form-group">
					<label>Reply Date</label> <input class="form-control"
						name='replyDate' value='2018-01-01 13:13'>
				</div>

			</div>
			<div class="modal-footer">
				<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
				<button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
				<button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
				<button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 모달창은 브라우저에서 댓글에 대한 여러 작업에서 활용할 것이므로 필요한 모든 내용을 담도록 하고 
	각 작업에 맞게 버튼이나 입력창이 보이거나 감춰지도록 합니다.  -->

<!-- 댓글 추가는 모달창을 이용해 진행합니다. 모달창은 별도로 화면 중앙에 위치하기 때문에 태그를 추가하는 위피는 신경쓰지 않아도 된다.  
모달창의 코드는 SBAdmin2의 pages 폴더 내 notifications.html 안에 포함되어 있습니다. -->

<%@include file="../includes/footer.jsp"%>

<!-- 댓글에 대한 스크립트 / 스크립트 파일을 따로 만들어서 거기서 가져다가 쓰기 위함으로 아래와 같이 작성한다. -->
<script type="text/javascript" src="/resources/js/reply.js"></script>

<script>
	$(document)
			.ready(
					function() {

						var bnoValue = '<c:out value="${board.bno}"/>';
						var replyUL = $(".chat");

						showList(1);

						/* showList()는 페이지 번호를 파라미터로 받도록 설계했다. 
							만일 파라미터가 없는 경우에는 자동으로 1페이지가 되도록 설정했다. */
						function showList(page) {
							replyService
									.getList(
											{
												bno : bnoValue,
												page : page || 1
											},
											function(list) {
												var str = "";

												if (list == null
														|| list.length == 0) {
													replyUL.html("");

													return;
												}

												for (var i = 0, len = list.lengh || 0; i < len; i++) {
													str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
													str += "	<div> <div class='header'><strong class='primary-font'>"
															+ list[i].replyer
															+ "</strong>";
													str += "	<small class='pull-right text-muted'>"
															+ replyService
																	.displayTime(list[i].replyDate)
															+ "</small></div>";
													str += "<p>"
															+ list[i].reply
															+ "</p></div></li>";
												}
												/* 위에 있는 displayTime는 js파일에 있는 함수인데 Ajax에서 데이터를 가져와서 HTML을 만들어 주는 부분에 
												넣은 후 24시간이 지난 댓글은 날짜만 표시되고 24시간 이내의 글은 시간으로 표시되게 만듭니다.*/
											});
						}
						
						var modal = $(".modal");
						var modalInputReply = modal.find("input[name='reply']");
						var modalInputReplyer = modal.find("input[name='replyer']");
						var modalInputReplyDate = modal.find("input[name='replyDate']");
						
						var modalModBnt = $("#modalModBnt");
						var modalRemoveBtn = $('#modalRemoveBtn');
						var modalRegisterBtn = $('#modalRegisterBtn');
						
						$("#addReplyBtn").on("click", function(e) {
							
							modal.find("input").val("");
							modalInputReplyDate.closest("div").hide();
							
							modalRegisterBtn.show();
							
							$(".modal").modal("show");
							
						});
						
					});
</script>

<script>
	/* get.jsp 내부에서는 Ajax호출은 replyService라는 이름의 객체에 감춰져 있으므로
	   필요한 파라미터들만 전달하는 형태로 간결해진다. 
	   
	   ReplyService의 add()에 던져야 하는 파라미터는 JavaScript의 객체 타입으로 만들어서 전송해주고,
	   Ajax전송 결과를 처리한는 함수를 파라미터로 같이 전달한다. */

	console.log("==================");
	console.log("JS TEST");

	var bnoValue = '<c:out value="${board.bno}"/>';

	/* replyService.add(
			{reply:"JS TEST", replyer:"tester", bno:bnoValue},
			function(result){
				alert("RESULT : " + result);
			}); */
	/* 댓글 리스트 가져오기 */
	replyService.getList({
		bno : bnoValue,
		page : 1
	}, function(list) {

		for (var i = 0, len = list.length || 0; i < len; i++) {
			console.log(ist[i]);
		}
	});

	/* 댓글 삭제 처리 */
/* 	replyService.remove(23, function(count) {

		console.log(count);

		if (conut === "success") {
			alert("REMOVE");
		}
	}, function(err) {
		alert("ERROR...")
	}); */

	/* 댓글 수정 처리 */
	/* replyService.update({
		rno : 22,
		bno : bnoValue,
		reply : "수정 답변..."
	}, function(result) {
		alert("수정 완료..")
	}); */

	/* 댓글 가져오기 
			단순히 댓글의 번호만을 전달한다. */
	/* replyService.get(10, function(date) {
		console.log(data);
	}); */
</script>

<!-- 버튼에 대한 스크립트 -->
<script>
	function modi() {
		location.href = "/board/modify?bno=" + $
		{
			board.bno
		}
		;
	}

	function list() {

		var type = "${cri.type}";
		var keyword = "${cri.keyword}";
		/* 
		그냥 처리하게 되면 
		location.href = "/board/list"; 이렇게 작성해도 상관없지만 
		list로 넘어갈때 내가 본 게시물이 있는 페이지로 넘어가고 싶다면 아래와 같이 URL을 바꿔주면 된다.
		 */
		if (type == "" || keyword == "") {
			/* type 과 keyword가 없을 때 = 검색기능 안 할때  */
			location.href = "/board/list?pageNum=" + $
			{
				cri.pageNum
			}
			+"&amount=" + $
			{
				cri.amount
			}

		} else {

			location.href = "/board/list?pageNum=" + $
			{
				cri.pageNum
			}
			+"&amount=" + $
			{
				cri.amount
			}
			+"&type=" + type + "&keyword=" + keyword;
		}
	}
</script>

<!-- form에 대한 스크립트 -->
<script>
	$(document).ready(function() {

		/* 위에 Form태그의 id값이 operForm이다. 그것을 가져와서 객체를 만들어준다. */
		var operForm = $("#operForm");

		/* 수정으로 넘어갈때는 글 번호를 들고 가야하기 때문에 아래와 같이 작성한다.  */
		$("button[wonjin='modify']").on("click", function(e) {

			operForm.attr("action", "/board/modify").submit();
		});

		/* 리스트로 넘어가게 된다면 bno값을 가지고 갈 필요가 없기 때문에 remove()로 삭제 시켜준다. */
		/* wonjin은 각 버튼에 이름을 임의로 지정한 것이다. 
		알아보기 쉽게 wonjin으로 작성한것이고 다른 경우 어떤 부분인지 명확하게 작성하는 것이 졸다.*/
		$("button[wonjin='list']").on("click", function(e) {

			operForm.find("#bno").remove();
			operForm.attr("action", "/boatd/list")
			operForm.submit();
		});
	});
</script>
