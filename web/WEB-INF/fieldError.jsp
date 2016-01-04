<%-- 验证错误页 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/taglibs.jsp" %>

<s:if test="%{hasFieldErrors()}">
    <table cellspacing="0" cellpadding="0" class="MkRe Mt9">
        <caption class="MkReTi" align="left">表单参数出错啦</caption>
        <s:iterator value="fieldErrors">
            <tr>
                <td user="${Key}" width="120px">

                    <script type="text/javascript" language="javascript">
                        try{
                            $(document).ready(function(){
                                $("td[user='${Key}']").text($("label[for='${Key}']").text());
                            } );
                        }catch(e){}
                    </script>
                </td>
                <td>
                    <s:iterator value="Value">
                        <s:property/><Br>
                    </s:iterator>
                </td>
            </tr>
        </s:iterator>
    </table>
</s:if>
<script type="text/javascript" language="javascript">
    try{
        $(document).ready(function(){
           $("td").each(function(){
               if(($.trim($(this).text()).length!=0) || ($(this).children().size()>0)) return;
               $(this).html("&nbsp;");
           });
        });
    }catch(e){}
</script>