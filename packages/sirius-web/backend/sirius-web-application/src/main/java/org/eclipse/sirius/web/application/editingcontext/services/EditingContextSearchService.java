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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to find and retrieve editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextSearchService implements IEditingContextSearchService {

    private static final String TIMER_NAME = "siriusweb_editingcontext_load";

    private final Logger logger = LoggerFactory.getLogger(EditingContextSearchService.class);

    private final IProjectSearchService projectSearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IResourceLoader resourceLoader;

    private final IEditingDomainFactory editingDomainFactory;

    private final List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders;

    private final List<IEditingContextProcessor> editingContextProcessors;

    private final Timer timer;

    public EditingContextSearchService(IProjectSearchService projectSearchService, ISemanticDataSearchService semanticDataSearchService, IResourceLoader resourceLoader, IEditingDomainFactory editingDomainFactory,
                                       List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders, List<IEditingContextProcessor> editingContextProcessors, MeterRegistry meterRegistry) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
        this.representationDescriptionProviders = Objects.requireNonNull(representationDescriptionProviders);
        this.editingContextProcessors = Objects.requireNonNull(editingContextProcessors);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .map(AggregateReference::<Project, UUID>to)
                .map(this.semanticDataSearchService::existsByProject)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IEditingContext> findById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(this.projectSearchService::findById)
                .map(this::toEditingContext);
    }

    private IEditingContext toEditingContext(Project project) {
        long start = System.currentTimeMillis();

        this.logger.debug("Loading the editing context {}", project.getId());

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain(project);
        EditingContext editingContext = new EditingContext(project.getId().toString(), editingDomain, new HashMap<>(), new ArrayList<>());
        this.editingContextProcessors.forEach(processor -> processor.preProcess(editingContext));

        this.semanticDataSearchService.findByProject(AggregateReference.to(project.getId()))
                .ifPresent(semanticData -> this.loadSemanticData(editingContext, semanticData));

        this.representationDescriptionProviders.forEach(representationDescriptionProvider -> {
            var representationDescriptions = representationDescriptionProvider.getRepresentationDescriptions(editingContext);
            representationDescriptions.forEach(representationDescription -> editingContext.getRepresentationDescriptions().put(representationDescription.getId(), representationDescription));
        });

        this.editingContextProcessors.forEach(processor -> processor.postProcess(editingContext));

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        this.logger.atDebug()
                .setMessage("{} objects have been loaded in {} ms")
                .addArgument(() -> {
                    var iterator = editingDomain.getResourceSet().getAllContents();
                    var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                    return stream.count();
                })
                .addArgument(end - start)
                .log();

        return editingContext;
    }

    private void loadSemanticData(EditingContext editingContext, SemanticData semanticData) {
        ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        semanticData.getDocuments().forEach(document -> this.resourceLoader.toResource(resourceSet, document.getId().toString(), document.getName(), document.getContent()));

        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
        // of inter-resources references
        resourceSet.eAdapters().add(new EditingContextCrossReferenceAdapter());

        this.logger.debug("{} documents loaded for the editing context {}", resourceSet.getResources().size(), editingContext.getId());
    }
}
