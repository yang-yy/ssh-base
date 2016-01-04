<%-- 运行错误页 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
         <table style="width: 100%;height: 100%" cellpadding="0" cellspacing="0">
             <tr>
                 <td width="10%">错误消息:</td>
                 <td bgcolor="gray">
                     <s:actionerror/>
                 </td>
             </tr>
             <tr>
                 <td>消息提示:</td>
                 <td bgcolor="gray">
                     <s:actionmessage/>
                 </td>
             </tr>
             <tr>
                 <td>堆栈值:</td>
                 <td>
                     <s:debug/>
                 </td>
             </tr>
             <tr>
                 <td colspan="2"  align="center">
                     <input type="button" value="返回" onclick="javascript:history.back()"/>
                 </td>
             </tr>
         </table>
    </body>
</html>