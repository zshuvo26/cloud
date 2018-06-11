(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-my-suffix', {
            parent: 'entity',
            url: '/student-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.student.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-my-suffix/studentsmySuffix.html',
                    controller: 'StudentMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('student');
                    $translatePartialLoader.addPart('gender');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-my-suffix-detail', {
            parent: 'student-my-suffix',
            url: '/student-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.student.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-my-suffix/student-my-suffix-detail.html',
                    controller: 'StudentMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('student');
                    $translatePartialLoader.addPart('gender');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Student', function($stateParams, Student) {
                    return Student.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-my-suffix-detail.edit', {
            parent: 'student-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-my-suffix/student-my-suffix-dialog.html',
                    controller: 'StudentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Student', function(Student) {
                            return Student.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-my-suffix.new', {
            parent: 'student-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-my-suffix/student-my-suffix-dialog.html',
                    controller: 'StudentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                fatherName: null,
                                motherName: null,
                                studentId: null,
                                email: null,
                                phoneNumber: null,
                                photo: null,
                                photoContentType: null,
                                photoType: null,
                                photoName: null,
                                signature: null,
                                signatureContentType: null,
                                signatureType: null,
                                signatureName: null,
                                dob: null,
                                nid: null,
                                birthCertNo: null,
                                gender: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-my-suffix', null, { reload: 'student-my-suffix' });
                }, function() {
                    $state.go('student-my-suffix');
                });
            }]
        })
        .state('student-my-suffix.edit', {
            parent: 'student-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-my-suffix/student-my-suffix-dialog.html',
                    controller: 'StudentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Student', function(Student) {
                            return Student.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-my-suffix', null, { reload: 'student-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-my-suffix.delete', {
            parent: 'student-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-my-suffix/student-my-suffix-delete-dialog.html',
                    controller: 'StudentMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Student', function(Student) {
                            return Student.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-my-suffix', null, { reload: 'student-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
