(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('book-fine-setting-my-suffix', {
            parent: 'entity',
            url: '/book-fine-setting-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookFineSetting.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-fine-setting-my-suffix/book-fine-settingsmySuffix.html',
                    controller: 'BookFineSettingMySuffixController',
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
                    $translatePartialLoader.addPart('bookFineSetting');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('book-fine-setting-my-suffix-detail', {
            parent: 'book-fine-setting-my-suffix',
            url: '/book-fine-setting-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.bookFineSetting.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book-fine-setting-my-suffix/book-fine-setting-my-suffix-detail.html',
                    controller: 'BookFineSettingMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookFineSetting');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BookFineSetting', function($stateParams, BookFineSetting) {
                    return BookFineSetting.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'book-fine-setting-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('book-fine-setting-my-suffix-detail.edit', {
            parent: 'book-fine-setting-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-fine-setting-my-suffix/book-fine-setting-my-suffix-dialog.html',
                    controller: 'BookFineSettingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookFineSetting', function(BookFineSetting) {
                            return BookFineSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-fine-setting-my-suffix.new', {
            parent: 'book-fine-setting-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-fine-setting-my-suffix/book-fine-setting-my-suffix-dialog.html',
                    controller: 'BookFineSettingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                maxDayForStaff: null,
                                maxDayForStudent: null,
                                finePerDayForSatff: null,
                                finePerDayForStudent: null,
                                maxBooksForStaff: null,
                                maxBooksForStudnt: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('book-fine-setting-my-suffix', null, { reload: 'book-fine-setting-my-suffix' });
                }, function() {
                    $state.go('book-fine-setting-my-suffix');
                });
            }]
        })
        .state('book-fine-setting-my-suffix.edit', {
            parent: 'book-fine-setting-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-fine-setting-my-suffix/book-fine-setting-my-suffix-dialog.html',
                    controller: 'BookFineSettingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BookFineSetting', function(BookFineSetting) {
                            return BookFineSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-fine-setting-my-suffix', null, { reload: 'book-fine-setting-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('book-fine-setting-my-suffix.delete', {
            parent: 'book-fine-setting-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/book-fine-setting-my-suffix/book-fine-setting-my-suffix-delete-dialog.html',
                    controller: 'BookFineSettingMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BookFineSetting', function(BookFineSetting) {
                            return BookFineSetting.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('book-fine-setting-my-suffix', null, { reload: 'book-fine-setting-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
