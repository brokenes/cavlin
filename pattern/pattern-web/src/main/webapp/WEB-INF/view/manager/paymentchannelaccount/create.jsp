<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div id="createDialog" class="crudDialog">
	<form id="createForm" method="post">
		<input type="hidden" id="paymentChannelId" name="paymentChannelId" value="${paymentChannelId}">
		<div class="form-group">
			<label for="accountName">账号名称：</label>
			<input id="accountName" type="text" class="form-control" name="accountName" maxlength="50">
		</div>
		<div class="radio">
			<div class="radio radio-inline radio-info">
				<input id="status_1" type="radio" name="status" value="1" checked>
				<label for="status_1">启用 </label>
			</div>
			<div class="radio radio-inline radio-danger">
				<input id="status_0" type="radio" name="status" value="0">
				<label for="status_0">锁定 </label>
			</div>
		</div>
		<div class="form-group">
			
		</div>
		<div class="form-group">
			<textarea rows="5" cols="5" name="remark" class="form-control" placeholder="备注"></textarea>
		</div>
		
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="createAccountSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="createAccountRowDialog.close();">取消</a>
		</div>
	</form>
</div>
<script type="text/javascript">
function createAccountSubmit() {
    $.ajax({
        type: 'post',
        url: '${basePath}/manage/paymentchannelaccount/create',
        data: $('#createForm').serialize(),
        beforeSend: function() {
        },
        success: function(result) {
			if (result.code != 1) {
				if (result.data instanceof Array) {
					$.each(result.data, function(index, value) {
						$.confirm({
							theme: 'dark',
							animation: 'rotateX',
							closeAnimation: 'rotateX',
							title: false,
							content: value.errorMsg,
							buttons: {
								confirm: {
									text: '确认',
									btnClass: 'waves-effect waves-button waves-light'
								}
							}
						});
					});
				} else if(result.code == '10110'){
                	layer.msg(result.msg);
                    location:top.location.href = '${basePath}/login';
                }  else {
						$.confirm({
							theme: 'dark',
							animation: 'rotateX',
							closeAnimation: 'rotateX',
							title: false,
							content: result.msg,
							buttons: {
								confirm: {
									text: '确认',
									btnClass: 'waves-effect waves-button waves-light'
								}
							}
						});
				}
			} else {
				createAccountRowDialog.close();
				var paymentChannelId = $('#paymentChannelId').val();
				var $childAccountTable = $('#child_account_table'+paymentChannelId);
				$childAccountTable.bootstrapTable('refresh');
			}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
			$.confirm({
				theme: 'dark',
				animation: 'rotateX',
				closeAnimation: 'rotateX',
				title: false,
				content: textStatus,
				buttons: {
					confirm: {
						text: '确认',
						btnClass: 'waves-effect waves-button waves-light'
					}
				}
			});
        }
    });
}
</script>