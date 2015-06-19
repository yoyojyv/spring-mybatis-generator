(function(angular) {
    angular.module('myApp.controllers', []);
    angular.module('myApp.services', []);
    angular.module('myApp', ['checklist-model', 'myApp.controllers', 'myApp.services']); // 'ngResource' 뺌
}(angular));
