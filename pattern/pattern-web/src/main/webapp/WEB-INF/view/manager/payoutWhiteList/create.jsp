﻿ 
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="createPayoutWhiteListDialog" class="crudDialog">
	<form id="createPayoutWhiteListForm" method="post">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="fg-line">
						<div class="form-group">
							<label for="address">地址</label>
							<input id="address" type="text" class="form-control" name="address" maxlength="40">
						</div>
						<div class="form-group">
							<label for="remark">备注</label>
							<input id="remark" type="text" class="form-control" name="remark" maxlength="255">
						</div>
						<div class="radio">
							<div class="radio radio-inline radio-success">
								<input id="addrType_0" type="radio" name="addrType" value="0" style="margin-left: 11px;" checked>
								<label for="addrType_0">域名</label>
							</div>
							<div class="radio radio-inline radio-success">
								<input id="addrType_1" type="radio" name="addrType" value="1" style="margin-left: 11px;">
								<label for="addrType_1">IPv4</label>
							</div>
							<div class="radio radio-inline radio-info">
								<input id="addrType_2" type="radio" name="addrType" value="2" style="margin-left: 11px;">
								<label for="addrType_2">IPv6</label>
							</div>
						</div>
						<div class="radio">
							<div class="radio radio-inline radio-success">
								<input id="status_1" type="radio" name="status" value="0" style="margin-left: 11px;" checked>
								<label for="status_1">未生效</label>
							</div>
							<div class="radio radio-inline radio-info">
								<input id="status_2" type="radio" name="status" value="1" style="margin-left: 11px;">
								<label for="status_2">生效</label>
							</div>
						</div>
					</div>
				</div>
			</div>	
		</div>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="createPayoutWhiteListSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="createPayoutWhiteListDialog.close();">取消</a>
		</div>
	</form>
</div>
<script>
    function createPayoutWhiteListSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/payoutWhiteList/create/${mainAgentId}',
            data: $('#createPayoutWhiteListForm').serialize(),
            beforeSend: function() {
    			if ($('#address').val() == '') {
    				$('#address').focus();
    				return false;
    			}
    			if ($('#remark').val() == '') {
    				$('#remark').focus();
    				return false;
    			}
            },
            success: function(result) {
            	if (result.model.code == 1) {
                    layer.msg(result.model.data,{icon:1,time:1000},function () {
                    	createPayoutWhiteListDialog.close();
                        $tablePayoutWhiteList.bootstrapTable('refresh');
                    });
    			} else {
                    if (result.model.data instanceof Array) {
                        $.each(result.model.data, function(index, value) {
                            layer.alert(value.errorMsg,{icon:2})
                        });
                    } else {
                        layer.alert(result.model.data,{icon:2});
                    }
    			}
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
            	layer.alert(textStatus,{icon:2});
            }
        });
    }

   
</script>
