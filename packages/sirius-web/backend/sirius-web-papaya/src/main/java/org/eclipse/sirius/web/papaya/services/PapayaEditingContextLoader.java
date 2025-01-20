/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.services;

import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextLoader;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Used to load a Papaya editing context.
 *
 * @author mcharfadi
 */
@Service
public class PapayaEditingContextLoader implements IEditingContextLoader {

    private static final String DELIMITATOR = "\\+";

    private final Logger logger = LoggerFactory.getLogger(PapayaEditingContextLoader.class);

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IResourceLoader resourceLoader;

    private final List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders;

    private final List<IEditingContextProcessor> editingContextProcessors;

    private final IProjectSearchService projectSearchService;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    private final IEditingDomainFactory editingDomainFactory;

    public PapayaEditingContextLoader(ISemanticDataSearchService semanticDataSearchService, IResourceLoader resourceLoader, List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders, List<IEditingContextProcessor> editingContextProcessors, IProjectSearchService projectSearchService, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates, IEditingDomainFactory editingDomainFactory) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.representationDescriptionProviders = Objects.requireNonNull(representationDescriptionProviders);
        this.editingContextProcessors = Objects.requireNonNull(editingContextProcessors);
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
    }

    public IEditingContext load(String editingContextId) {
        var normalizedId = editingContextId.split(DELIMITATOR);
        var project = this.projectSearchService.findById(normalizedId[0]);

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain(project.get());
        EditingContext editingContext = new EditingContext(editingContextId, editingDomain, new HashMap<>(), new ArrayList<>());

        this.toEditingContext(editingContext);

        return editingContext;
    }

    public void toEditingContext(EditingContext editingContext) {
        this.editingContextProcessors.forEach(processor -> processor.preProcess(editingContext));

        var normalizedId = editingContext.getId().split(DELIMITATOR);
        var optionalSemanticDataId = new UUIDParser().parse(normalizedId[1]);

        if (optionalSemanticDataId.isPresent()) {
            var optionalSemanticData = this.semanticDataSearchService.findById(optionalSemanticDataId.get());
            optionalSemanticData.ifPresent(semanticData -> this.loadSemanticData(editingContext, semanticData));
        }

        this.representationDescriptionProviders.forEach(representationDescriptionProvider -> {
            var representationDescriptions = representationDescriptionProvider.getRepresentationDescriptions(editingContext);
            representationDescriptions.forEach(representationDescription -> editingContext.getRepresentationDescriptions().put(representationDescription.getId(), representationDescription));
        });

        this.editingContextProcessors.forEach(processor -> processor.postProcess(editingContext));
    }

    private void loadSemanticData(EditingContext editingContext, SemanticData semanticData) {
        ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        semanticData.getDocuments().forEach(document -> this.resourceLoader.toResource(resourceSet, document.getId().toString(), document.getName(), document.getContent(),
                this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId()))));

        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
        // of inter-resources references
        resourceSet.eAdapters().add(new EditingContextCrossReferenceAdapter());

        this.logger.debug("{} documents loaded for the editing context {}", resourceSet.getResources().size(), editingContext.getId());
    }

    @Override
    public boolean canHandle(String projectId) {
        var normalizedId = projectId.split(DELIMITATOR);
        if (normalizedId.length == 2 && new UUIDParser().parse(normalizedId[0]).isPresent() && new UUIDParser().parse(normalizedId[1]).isPresent()) {
            return this.projectSearchService.existsById(normalizedId[0]);
        }
        return false;
    }

}
