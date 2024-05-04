/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.papaya.factories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;

/**
 * Used to create the spring data commons project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SpringDataCommonsProjectFactory {

    public Project create(IEObjectIndexer eObjectIndexer) {
        var springDataCommons = PapayaFactory.eINSTANCE.createProject();
        springDataCommons.setName("Spring Data Commons");
        springDataCommons.getComponents().add(this.springDataCommons());

        eObjectIndexer.index(springDataCommons);

        return springDataCommons;
    }

    private Component springDataCommons() {
        var orgSpringFrameworkData = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkData.setName("org.springframework.data");
        orgSpringFrameworkData.getPackages().addAll(List.of(
                this.orgSpringFrameworkDataAnnotation(),
                this.orgSpringFrameworkDataDomain(),
                this.orgSpringFrameworkDataRepository()
        ));

        var springDataCommons = PapayaFactory.eINSTANCE.createComponent();
        springDataCommons.setName("spring-data-commons");
        springDataCommons.getPackages().add(orgSpringFrameworkData);
        return springDataCommons;
    }

    private Package orgSpringFrameworkDataAnnotation() {
        var createdByAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        createdByAnnotation.setName("CreatedBy");

        var createdDateAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        createdDateAnnotation.setName("CreatedDate");

        var idAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        idAnnotation.setName("Id");

        var lastModifiedByAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        lastModifiedByAnnotation.setName("LastModifiedBy");

        var lastModifiedDateAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        lastModifiedDateAnnotation.setName("LastModifiedDate");

        var types = List.of(createdByAnnotation, createdDateAnnotation, idAnnotation, lastModifiedByAnnotation, lastModifiedDateAnnotation);

        var orgSpringFrameworkDataAnnotation = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkDataAnnotation.setName("annotation");
        orgSpringFrameworkDataAnnotation.getTypes().addAll(types);
        return orgSpringFrameworkDataAnnotation;
    }

    private Package orgSpringFrameworkDataDomain() {
        var abstractAggregateRootClassATypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        abstractAggregateRootClassATypeParameter.setName("A");
        var abstractAggregateRootClass = PapayaFactory.eINSTANCE.createClass();
        abstractAggregateRootClass.setName("AbstractAggregateRoot");
        abstractAggregateRootClass.setAbstract(true);
        abstractAggregateRootClass.getTypeParameters().add(abstractAggregateRootClassATypeParameter);

        var afterDomainEventPublicationAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        afterDomainEventPublicationAnnotation.setName("AfterDomainEventPublication");

        var auditableInterfaceUTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        auditableInterfaceUTypeParameter.setName("U");
        var auditableInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        auditableInterfaceIDTypeParameter.setName("ID");
        var auditableInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        auditableInterfaceTTypeParameter.setName("T");
        var auditableInterface = PapayaFactory.eINSTANCE.createInterface();
        auditableInterface.setName("Auditable");
        auditableInterface.getTypeParameters().addAll(List.of(auditableInterfaceUTypeParameter, auditableInterfaceIDTypeParameter, auditableInterfaceTTypeParameter));

        var auditorAwareInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        auditorAwareInterfaceTTypeParameter.setName("T");
        var auditorAwareInterface = PapayaFactory.eINSTANCE.createInterface();
        auditorAwareInterface.setName("AuditorAware");
        auditableInterface.getTypeParameters().add(auditorAwareInterfaceTTypeParameter);

        var domainEventsAnnotation = PapayaFactory.eINSTANCE.createAnnotation();
        domainEventsAnnotation.setName("DomainEvents");

        var persistableInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        persistableInterfaceIDTypeParameter.setName("ID");
        var persistableInterface = PapayaFactory.eINSTANCE.createInterface();
        persistableInterface.setName("Persistable");
        persistableInterface.getTypeParameters().add(persistableInterfaceIDTypeParameter);

        List<Type> types = new ArrayList<>();
        types.addAll(List.of(
                abstractAggregateRootClass,
                afterDomainEventPublicationAnnotation,
                auditableInterface,
                auditorAwareInterface,
                domainEventsAnnotation,
                persistableInterface
        ));
        types.addAll(this.paginationTypes());
        types = types.stream()
                .sorted(Comparator.comparing(NamedElement::getName))
                .toList();

        var orgSpringFrameworkDataDomain = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkDataDomain.setName("domain");
        orgSpringFrameworkDataDomain.getTypes().addAll(types);
        return orgSpringFrameworkDataDomain;
    }

    private List<Type> paginationTypes() {
        var abstractPageRequestClass = PapayaFactory.eINSTANCE.createClass();
        abstractPageRequestClass.setName("AbstractPageRequest");
        abstractPageRequestClass.setAbstract(true);

        var chunkClassTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        chunkClassTTypeParameter.setName("T");
        var chunkClass = PapayaFactory.eINSTANCE.createClass();
        chunkClass.setName("Chunk");
        chunkClass.setAbstract(true);
        chunkClass.getTypeParameters().add(chunkClassTTypeParameter);

        var pageInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        pageInterfaceTTypeParameter.setName("T");
        var pageInterface = PapayaFactory.eINSTANCE.createInterface();
        pageInterface.setName("Page");
        pageInterface.getTypeParameters().add(pageInterfaceTTypeParameter);

        var pageableInterface = PapayaFactory.eINSTANCE.createInterface();
        pageableInterface.setName("Pageable");

        var pageImplClassTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        pageImplClassTTypeParameter.setName("T");
        var pageImplClass = PapayaFactory.eINSTANCE.createClass();
        pageImplClass.setName("PageImpl");
        pageImplClass.getTypeParameters().add(pageImplClassTTypeParameter);

        var pageRequestClass = PapayaFactory.eINSTANCE.createClass();
        pageRequestClass.setName("PageRequest");

        var sliceInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        sliceInterfaceTTypeParameter.setName("T");
        var sliceInterface = PapayaFactory.eINSTANCE.createInterface();
        sliceInterface.setName("Slice");
        sliceInterface.getTypeParameters().add(sliceInterfaceTTypeParameter);

        var sliceImplClassTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        sliceImplClassTTypeParameter.setName("T");
        var sliceImplClass = PapayaFactory.eINSTANCE.createClass();
        sliceImplClass.setName("SliceImpl");
        sliceImplClass.getTypeParameters().add(sliceImplClassTTypeParameter);

        var sortClass = PapayaFactory.eINSTANCE.createClass();
        sortClass.setName("Sort");

        var windowInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        windowInterfaceTTypeParameter.setName("T");
        var windowInterface = PapayaFactory.eINSTANCE.createInterface();
        windowInterface.setName("Window");
        windowInterface.getTypeParameters().add(windowInterfaceTTypeParameter);

        var windowImplClassTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        windowImplClassTTypeParameter.setName("T");
        var windowImplClass = PapayaFactory.eINSTANCE.createClass();
        windowImplClass.setName("WindowImpl");
        windowImplClass.getTypeParameters().add(windowImplClassTTypeParameter);

        return List.of(
                abstractPageRequestClass,
                chunkClass,
                pageInterface,
                pageableInterface,
                pageImplClass,
                pageRequestClass,
                sliceInterface,
                sliceImplClass,
                sortClass,
                windowInterface,
                windowImplClass
        );
    }

    private Package orgSpringFrameworkDataRepository() {
        var crudRepositoryInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        crudRepositoryInterfaceTTypeParameter.setName("T");
        var crudRepositoryInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        crudRepositoryInterfaceIDTypeParameter.setName("ID");
        var crudRepositoryInterface = PapayaFactory.eINSTANCE.createInterface();
        crudRepositoryInterface.setName("CrudRepository");
        crudRepositoryInterface.getTypeParameters().addAll(List.of(crudRepositoryInterfaceTTypeParameter, crudRepositoryInterfaceIDTypeParameter));

        var listCrudRepositoryInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        listCrudRepositoryInterfaceTTypeParameter.setName("T");
        var listCrudRepositoryInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        listCrudRepositoryInterfaceIDTypeParameter.setName("ID");
        var listCrudRepositoryInterface = PapayaFactory.eINSTANCE.createInterface();
        listCrudRepositoryInterface.setName("ListCrudRepository");
        listCrudRepositoryInterface.getTypeParameters().addAll(List.of(listCrudRepositoryInterfaceTTypeParameter, listCrudRepositoryInterfaceIDTypeParameter));

        var listPagingAndSortingRepositoryInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        listPagingAndSortingRepositoryInterfaceTTypeParameter.setName("T");
        var listPagingAndSortingRepositoryInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        listPagingAndSortingRepositoryInterfaceIDTypeParameter.setName("ID");
        var listPagingAndSortingRepositoryInterface = PapayaFactory.eINSTANCE.createInterface();
        listPagingAndSortingRepositoryInterface.setName("ListPagingAndSortingRepository");
        listPagingAndSortingRepositoryInterface.getTypeParameters().addAll(List.of(listPagingAndSortingRepositoryInterfaceTTypeParameter, listPagingAndSortingRepositoryInterfaceIDTypeParameter));

        var pagingAndSortingRepositoryInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        pagingAndSortingRepositoryInterfaceTTypeParameter.setName("T");
        var pagingAndSortingRepositoryInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        pagingAndSortingRepositoryInterfaceIDTypeParameter.setName("ID");
        var pagingAndSortingRepositoryInterface = PapayaFactory.eINSTANCE.createInterface();
        pagingAndSortingRepositoryInterface.setName("PagingAndSortingRepository");
        pagingAndSortingRepositoryInterface.getTypeParameters().addAll(List.of(pagingAndSortingRepositoryInterfaceTTypeParameter, pagingAndSortingRepositoryInterfaceIDTypeParameter));

        var repositoryInterfaceTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        repositoryInterfaceTTypeParameter.setName("T");
        var repositoryInterfaceIDTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        repositoryInterfaceIDTypeParameter.setName("ID");
        var repositoryInterface = PapayaFactory.eINSTANCE.createInterface();
        repositoryInterface.setName("Repository");
        repositoryInterface.getTypeParameters().addAll(List.of(repositoryInterfaceTTypeParameter, repositoryInterfaceIDTypeParameter));

        var types = List.of(
                crudRepositoryInterface,
                listCrudRepositoryInterface,
                listPagingAndSortingRepositoryInterface,
                pagingAndSortingRepositoryInterface,
                repositoryInterface
        );

        var orgSpringFrameworkDataRepository = PapayaFactory.eINSTANCE.createPackage();
        orgSpringFrameworkDataRepository.setName("repository");
        orgSpringFrameworkDataRepository.getTypes().addAll(types);
        return orgSpringFrameworkDataRepository;
    }

    public void link(IEObjectIndexer eObjectIndexer) {
        var springBeansComponent = eObjectIndexer.getComponent("spring-beans");
        var springCoreComponent = eObjectIndexer.getComponent("spring-core");
        var springDataCommonsComponent = eObjectIndexer.getComponent("spring-data-commons");

        springDataCommonsComponent.getDependencies().addAll(List.of(springBeansComponent, springCoreComponent));
    }
}
