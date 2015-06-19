(function(angular) {
    angular.module('myApp.services').service('CaliforniaService', function ($http) {

        this.getTables = function () {
            return $http.get('/california/tables');
        };

        this.getColumns = function (tableName) {
            return $http.get('/california/tables/' + tableName);
        };

        this.generate = function (generateData) {
            return $http.post('/california/generate', generateData);
        };
    });
}(angular));
