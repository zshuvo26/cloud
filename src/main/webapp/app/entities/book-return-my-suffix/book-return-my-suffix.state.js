(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-return-my-suffix', {
            parent: 'entity',
            url: '/book-return-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookReturn.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-return-my-suffix/book-returnsmySuffix.html',
                    controller: 'BookReturnMySuffixController',
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
                    $translatePartialLoader.addPart('bookReturn');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-return-my-suffix-detail', {
            parent: 'book-return-my-suffix',
            url: '/book-return-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookReturn.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-return-my-suffix/book-return-my-suffix-detail.html',
                    controller: 'BookReturnMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookReturn');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookReturn', function($stateParams, BookReturn) {
                    return BookReturn.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-return-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-return-my-suffix-detail.edit', {
            parent: 'book-return-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-return-my-suffix/book-return-my-suffix-dialog.html',
                    controller: 'BookReturnMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookReturn', function(BookReturn) {
                            return BookReturn.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-return-my-suffix.new', {
            parent: 'book-return-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-return-my-suffix/book-return-my-suffix-dialog.html',
                    controller: 'BookReturnMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                receivedStatus: null,
                                totalFine: null,
                                fineDeposit: null,
                                remissionStatus: null,
                                compensation: null,
                                compensationDeposit: null,
                                compensationFineDeposit: null,
                                remissionCompensationStatus: null,
                                cfFineStatus: null,
                                cfCompensationStatus: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book-return-my-suffix', null, { reload: 'book-return-my-suffix' });
                }, function() {
                    $state.go('book-return-my-suffix');
                });
            }]
        })
        .state('book-return-my-suffix.edit', {
            parent: 'book-return-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-return-my-suffix/book-return-my-suffix-dialog.html',
                    controller: 'BookReturnMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookReturn', function(BookReturn) {
                            return BookReturn.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-return-my-suffix', null, { reload: 'book-return-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-return-my-suffix.delete', {
            parent: 'book-return-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-return-my-suffix/book-return-my-suffix-delete-dialog.html',
                    controller: 'BookReturnMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookReturn', function(BookReturn) {
                            return BookReturn.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-return-my-suffix', null, { reload: 'book-return-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
