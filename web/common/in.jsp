<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path ;
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %> --%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="session" />
<c:set var="staticPage" value="<%=basePath%>" scope="session" />
<!-- 单价小数点后保留的位数 -->
<c:set var="dpforprice" value="5" scope="session"/>	
<!-- 总价小数点后保留的位数 -->
<c:set var="dpfortotalprice" value="2" scope="session"/>	
<!-- 数量小数点后保留的位数 -->
<c:set var="dpfornum" value="2" scope="session"/>

<!--jQuery  js文件 -->
<script type="text/javascript" src="${ctx}/script/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript">
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function formatDate (strTime) {
	if(strTime == null)
		return "";
    var date = new Date(strTime);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
}
function formatTime (strTime) {
	if(strTime == null)
		return "";
    var date = new Date(strTime);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
}
/**格式化数字，精确到小数点后n位*/
function fmoney(s, n)   
{
   if(s == null || s == "")
	   return 0;
   n = (n != null && n > 0 && n <= 20) ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
}
function rmoney(s)   
{   
   return parseFloat(s.replace(/[^\d\.-]/g, ""));   
}
function $$(text){
	try {
		console.dir(text);
	} catch (e) {
		alert(text);
	}
}
document.onkeydown = function(event_e) {
	if (window.event)
		event_e = window.event;
	var int_keycode = event_e.charCode || event_e.keyCode;
	if (int_keycode == 13) {
		var $formsubmit = $($(":button[name='formsubmit']")[0]);
		if($formsubmit!=null){
			$formsubmit.focus();
		}
	}
};
</script>