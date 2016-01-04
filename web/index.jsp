<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/12/30
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%String basePath = request.getContextPath();%>
    <title></title>
    <script src="<%=basePath%>/res/lib/jquery/jquery-1.11.3.min.js"></script>
    <script src="<%=basePath%>/res/lib/angular/angular.min.js"></script>
    <script>
        var aa = angular.module("myApp", []);

        aa.factory('MathService', function () {
            var factory = {};
            factory.multiply = function (a, b) {
                return a * b
            }
            return factory;
        });

        aa.service('CalcService', function (MathService) {
            this.greet = function () {
                alert("Hello,AngularJS")
            }
        });

        aa.controller("test", ["$scope", "$timeout", "$log", "CalcService", function ($scope, $timeout, $log, CalcService) {
            $scope.clock = "aaa";
            var obj = {
                name: "wuxd"
            }
            $log.info(obj)
            console.log(obj)
        }]);
          $(document).ready(function(){
              alert("xxxx");
          });
    </script>
</head>
<body>
<div ng-app="myApp" ng-controller="test">
    <input ng-model="name" placeholder="请输入您的名字："/><br>
    {{name}} <br>
    {{clock}}
    {{angular.version.full}}


</div>
</body>
</html>
