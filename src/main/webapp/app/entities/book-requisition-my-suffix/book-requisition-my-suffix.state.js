(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-requisition-my-suffix', {
            parent: 'entity',
            url: '/book-requisition-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookRequisition.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-requisition-my-suffix/book-requisitionsmySuffix.html',
                    controller: 'BookRequisitionMySuffixController',
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
                    $translatePartialLoader.addPart('bookRequisition');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-requisition-my-suffix-detail', {
            parent: 'book-requisition-my-suffix',
            url: '/book-requisition-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookRequisition.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-requisition-my-suffix/book-requisition-my-suffix-detail.html',
                    controller: 'BookRequisitionMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookRequisition');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookRequisition', function($stateParams, BookRequisition) {
                    return BookRequisition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-requisition-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-requisition-my-suffix-detail.edit', {
            parent: 'book-requisition-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-requisition-my-suffix/book-requisition-my-suffix-dialog.html',
                    controller: 'BookRequisitionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookRequisition', function(BookRequisition) {
                            return BookRequisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-requisition-my-suffix.new', {
            parent: 'book-requisition-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-requisition-my-suffix/book-requisition-my-suffix-dialog.html',
                    controller: 'BookRequisitionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                edition: null,
                                authorName: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book-requisition-my-suffix', null, { reload: 'book-requisition-my-suffix' });
                }, function() {
                    $state.go('book-requisition-my-suffix');
                });
            }]
        })
        .state('book-requisition-my-suffix.edit', {
            parent: 'book-requisition-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-requisition-my-suffix/book-requisition-my-suffix-dialog.html',
                    controller: 'BookRequisitionMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookRequisition', function(BookRequisition) {
                            return BookRequisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-requisition-my-suffix', null, { reload: 'book-requisition-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-requisition-my-suffix.delete', {
            parent: 'book-requisition-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-requisition-my-suffix/book-requisition-my-suffix-delete-dialog.html',
                    controller: 'BookRequisitionMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookRequisition', function(BookRequisition) {
                            return BookRequisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-requisition-my-suffix', null, { reload: 'book-requisition-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
