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
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultDomainResourceProvider;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultViewResourceProvider;
import org.eclipse.sirius.web.application.studio.services.api.IDomainNameProvider;
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

    public StudioProjectTemplateInitializer(IDomainNameProvider domainNameProvider, IDefaultDomainResourceProvider defaultDomainResourceProvider, IDefaultViewResourceProvider defaultViewResourceProvider) {
        this.domainNameProvider = Objects.requireNonNull(domainNameProvider);
        this.defaultDomainResourceProvider = Objects.requireNonNull(defaultDomainResourceProvider);
        this.defaultViewResourceProvider = Objects.requireNonNull(defaultViewResourceProvider);
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
                    var domainName = this.domainNameProvider.getSampleDomainName();

                    var domainResource = this.defaultDomainResourceProvider.getResource(domainName);
                    var viewResource = this.defaultViewResourceProvider.getResource(domainName);

                    resourceSet.getResources().add(domainResource);
                    resourceSet.getResources().add(viewResource);

                    return Optional.empty();
                }));
    }

    private Optional<ResourceSet> getResourceSet(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet);
    }
}
