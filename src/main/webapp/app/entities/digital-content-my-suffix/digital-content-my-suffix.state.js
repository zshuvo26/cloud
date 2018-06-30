(function() {
    'use strict';

    angular
        .module('cloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('digital-content-my-suffix', {
            parent: 'entity',
            url: '/digital-content-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.digitalContent.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/digital-content-my-suffix/digital-contentsmySuffix.html',
                    controller: 'DigitalContentMySuffixController',
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
                    $translatePartialLoader.addPart('digitalContent');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('digital-content-my-suffix-detail', {
            parent: 'digital-content-my-suffix',
            url: '/digital-content-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cloudApp.digitalContent.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/digital-content-my-suffix/digital-content-my-suffix-detail.html',
                    controller: 'DigitalContentMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('digitalContent');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DigitalContent', function($stateParams, DigitalContent) {
                    return DigitalContent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'digital-content-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('digital-content-my-suffix-detail.edit', {
            parent: 'digital-content-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-content-my-suffix/digital-content-my-suffix-dialog.html',
                    controller: 'DigitalContentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DigitalContent', function(DigitalContent) {
                            return DigitalContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('digital-content-my-suffix.new', {
            parent: 'digital-content-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-content-my-suffix/digital-content-my-suffix-dialog.html',
                    controller: 'DigitalContentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                accessionNo: null,
                                title: null,
                                isbnNo: null,
                                authorName: null,
                                coverPhoto: null,
                                coverPhotoContentType: null,
                                coverPhotoType: null,
                                coverPhotoName: null,
                                content: null,
                                contentContentType: null,
                                contentType: null,
                                contentName: null,
                                createDate: null,
                                updateDate: null,
                                createBy: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('digital-content-my-suffix', null, { reload: 'digital-content-my-suffix' });
                }, function() {
                    $state.go('digital-content-my-suffix');
                });
            }]
        })
        .state('digital-content-my-suffix.edit', {
            parent: 'digital-content-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-content-my-suffix/digital-content-my-suffix-dialog.html',
                    controller: 'DigitalContentMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DigitalContent', function(DigitalContent) {
                            return DigitalContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('digital-content-my-suffix', null, { reload: 'digital-content-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('digital-content-my-suffix.delete', {
            parent: 'digital-content-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-content-my-suffix/digital-content-my-suffix-delete-dialog.html',
                    controller: 'DigitalContentMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DigitalContent', function(DigitalContent) {
                            return DigitalContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('digital-content-my-suffix', null, { reload: 'digital-content-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
