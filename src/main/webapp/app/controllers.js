(function (angular) {
    angular.module("myApp.controllers").controller("AppController", function ($scope, CaliforniaService) {

        $scope.packages = ['com.mnametag.core'];
        $scope.tables = [];
        $scope.sourceTypes = ['domain', 'mybatisMapper'];
        $scope.generateForm = {};

        $scope.init = function () {
            $scope.generateForm = {
                packagePrefix: $scope.packages[0],
                tableName: '',
                createPath: '/Users/yoyojyv/Projects/company/PlanM/california'
            };

            $scope.generateForm.sourceTypes = [];
            for (var index in $scope.sourceTypes) {
                $scope.generateForm.sourceTypes.push($scope.sourceTypes[index]);
            }

            CaliforniaService.getTables()
                .success(function (data) {
                    $scope.tables = data;
                })
                .error(function (err) {
                });
        };


        $scope.saveGenerateSource = function () {
            CaliforniaService.generate($scope.generateForm)
                .success(function (result) {
                    alert(result)
                })
                .error(function (err) {
                });
        };

        $scope.init();

    });

}(angular));
