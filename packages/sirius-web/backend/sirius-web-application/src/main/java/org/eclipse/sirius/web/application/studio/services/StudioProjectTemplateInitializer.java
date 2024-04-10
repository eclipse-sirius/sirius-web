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
package org.eclipse.sirius.web.application.studio.services;

import static org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider.BLANK_STUDIO_TEMPLATE_ID;
import static org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider.STUDIO_TEMPLATE_ID;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultDomainResourceProvider;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultViewResourceProvider;
import org.eclipse.sirius.web.application.studio.services.api.IDomainNameProvider;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to initialize studio projects.
 *
 * @author sbegaudeau
 */
@Service
public class StudioProjectTemplateInitializer implements IProjectTemplateInitializer {

    private final IDomainNameProvider domainNameProvider;

    private final IDefaultDomainResourceProvider defaultDomainResourceProvider;

    private final IDefaultViewResourceProvider defaultViewResourceProvider;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider;

    public StudioProjectTemplateInitializer(IDomainNameProvider domainNameProvider, IDefaultDomainResourceProvider defaultDomainResourceProvider, IDefaultViewResourceProvider defaultViewResourceProvider, IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramCreationService diagramCreationService, IRepresentationPersistenceService representationPersistenceService, IDomainDiagramDescriptionProvider domainDiagramDescriptionProvider) {
        this.domainNameProvider = Objects.requireNonNull(domainNameProvider);
        this.defaultDomainResourceProvider = Objects.requireNonNull(defaultDomainResourceProvider);
        this.defaultViewResourceProvider = Objects.requireNonNull(defaultViewResourceProvider);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.domainDiagramDescriptionProvider = Objects.requireNonNull(domainDiagramDescriptionProvider);
    }

    @Override
    public boolean canHandle(String projectTemplateId) {
        return List.of(BLANK_STUDIO_TEMPLATE_ID, STUDIO_TEMPLATE_ID).contains(projectTemplateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String projectTemplateId, IEditingContext editingContext) {
        return switch (projectTemplateId) {
            case STUDIO_TEMPLATE_ID -> this.createStudio(editingContext);
            default -> Optional.empty();
        };
    }

    private Optional<RepresentationMetadata> createStudio(IEditingContext editingContext) {
        return new UUIDParser().parse(editingContext.getId())
                .map(AggregateReference::<Project, UUID>to)
                .flatMap(project -> this.getResourceSet(editingContext).flatMap(resourceSet -> {
                    Optional<RepresentationMetadata> result = Optional.empty();

                    var domainName = this.domainNameProvider.getSampleDomainName();

                    var domainResource = this.defaultDomainResourceProvider.getResource(domainName);
                    var viewResource = this.defaultViewResourceProvider.getResource(domainName);

                    resourceSet.getResources().add(domainResource);
                    resourceSet.getResources().add(viewResource);

                    var optionalDomainDiagramDescription = this.findDomainDiagramDescription(editingContext);
                    if (optionalDomainDiagramDescription.isPresent()) {
                        DiagramDescription domainDiagramDescription = optionalDomainDiagramDescription.get();
                        Object semanticTarget = domainResource.getContents().get(0);

                        Diagram diagram = this.diagramCreationService.create(domainDiagramDescription.getLabel(), semanticTarget, domainDiagramDescription, editingContext);
                        this.representationPersistenceService.save(editingContext, diagram);

                        result = Optional.of(new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId()));
                    }

                    return result;
                }));
    }

    private Optional<ResourceSet> getResourceSet(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet);
    }

    private Optional<DiagramDescription> findDomainDiagramDescription(IEditingContext editingContext) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescription -> diagramDescription.getId().equals(this.domainDiagramDescriptionProvider.getDescriptionId()))
                .findFirst();
    }
}
