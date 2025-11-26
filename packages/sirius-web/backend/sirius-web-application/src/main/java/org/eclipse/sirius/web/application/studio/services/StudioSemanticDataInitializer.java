/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.project.services.api.ISemanticDataInitializer;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultDomainResourceProvider;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultViewResourceProvider;
import org.eclipse.sirius.web.application.studio.services.api.IDomainNameProvider;
import org.springframework.stereotype.Service;

/**
 * Used to initialize studio projects.
 *
 * @author sbegaudeau
 */
@Service
public class StudioSemanticDataInitializer implements ISemanticDataInitializer {

    private final IDomainNameProvider domainNameProvider;

    private final IDefaultDomainResourceProvider defaultDomainResourceProvider;

    private final IDefaultViewResourceProvider defaultViewResourceProvider;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public StudioSemanticDataInitializer(IDomainNameProvider domainNameProvider, IDefaultDomainResourceProvider defaultDomainResourceProvider, IDefaultViewResourceProvider defaultViewResourceProvider, IEditingContextPersistenceService editingContextPersistenceService) {
        this.domainNameProvider = Objects.requireNonNull(domainNameProvider);
        this.defaultDomainResourceProvider = Objects.requireNonNull(defaultDomainResourceProvider);
        this.defaultViewResourceProvider = Objects.requireNonNull(defaultViewResourceProvider);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @Override
    public boolean canHandle(String projectTemplateId) {
        return List.of(BLANK_STUDIO_TEMPLATE_ID, STUDIO_TEMPLATE_ID).contains(projectTemplateId);
    }

    @Override
    public void handle(ICause cause, IEditingContext editingContext, String projectTemplateId) {
        if (STUDIO_TEMPLATE_ID.equals(projectTemplateId) && editingContext instanceof IEMFEditingContext emfEditingContext) {
            var resourceSet = emfEditingContext.getDomain().getResourceSet();

            var domainName = this.domainNameProvider.getSampleDomainName();

            var domainResource = this.defaultDomainResourceProvider.getResource(domainName);
            var viewResource = this.defaultViewResourceProvider.getResource(domainName);

            resourceSet.getResources().add(domainResource);
            resourceSet.getResources().add(viewResource);

            this.editingContextPersistenceService.persist(new StudioTemplateInitialization(UUID.randomUUID(), emfEditingContext, domainResource, cause), editingContext);
        }
    }

}
