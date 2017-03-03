<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/19
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="<%=request.getContextPath()%>/res/lib/angular/angular.js"></script>
    <script>

        var aa = angular.module("dddd", []);


        aa.directive("test2", function () {
            return {
                restrict: "ECMA",
                compile: function (tEle) {
                    tEle.append("<div>{{p}}</div>");
                }
            }
        })
        aa.directive("test", function () {
            return {
                restrict: "ECMA",
                compile: function (tEle) {
                    tEle.append(angular.element("<test2></test2>"));
                    return {
                        post: function () {
                            console.log("cccc");
                        }
                    }
                }
            }
        })
    </script>
</head>
<body data-ng-app="dddd">
<div ng-repeat="p in [1,2,3]">
    <test></test>
</div>
</body>
</html>
