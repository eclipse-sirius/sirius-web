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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Used to save the editing context.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextPersistenceService implements IEditingContextPersistenceService {

    private static final String TIMER_NAME = "siriusweb_editingcontext_save";

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final List<IEditingContextPersistenceFilter> persistenceFilters;

    private final Timer timer;

    public EditingContextPersistenceService(ISemanticDataUpdateService semanticDataUpdateService, IResourceToDocumentService resourceToDocumentService, List<IEditingContextPersistenceFilter> persistenceFilters, MeterRegistry meterRegistry) {
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.persistenceFilters = Objects.requireNonNull(persistenceFilters);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    public void persist(IEditingContext editingContext) {
        long start = System.currentTimeMillis();

        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            new UUIDParser().parse(editingContext.getId())
                    .map(AggregateReference::<Project, UUID>to)
                    .ifPresent(project -> {

                        var resources = emfEditingContext.getDomain().getResourceSet().getResources().stream()
                                .filter(resource -> IEMFEditingContext.RESOURCE_SCHEME.equals(resource.getURI().scheme()))
                                .filter(resource -> this.persistenceFilters.stream().allMatch(filter -> filter.shouldPersist(resource)))
                                .toList();
                        var resourcesToSave = this.findResourcesToSave(resources);
                        var documentData = resourcesToSave.stream()
                                .map(this.resourceToDocumentService::toDocument)
                                .flatMap(Optional::stream)
                                .collect(Collectors.toSet());

                        var documents = new LinkedHashSet<Document>();
                        var domainUris = new LinkedHashSet<String>();

                        documentData.forEach(data -> {
                            documents.add(data.document());
                            domainUris.addAll(data.ePackageEntries().stream().map(EPackageEntry::nsURI).toList());
                        });

                        this.semanticDataUpdateService.updateDocuments(project, documents, domainUris);
                    });
        }

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
    }

    private Collection<Resource> findResourcesToSave(List<Resource> resources) {
        // "Dirty" resources are known as impacted and need to be saved
        Set<Resource> dirty = resources.stream().filter(Resource::isModified).collect(Collectors.toCollection(LinkedHashSet::new));

        // For "unknown" resources, we do not know yet; they are not directly modified themselves but may point to an impacted resource (in which case they are, at least potentially, impacted)
        Set<Resource> unknown = resources.stream().filter(r -> !r.isModified()).collect(Collectors.toCollection(LinkedHashSet::new));

        // "Clean" resources are known not to be impacted. We only need to keep track of their count.

        int cleanCount = 0;
        int totalCount = dirty.size() + unknown.size();

        // Which resource contains (at least one) references to which other one(s)?
        Map<Resource, Set<Resource>> topology = new HashMap<>();
        for (Resource res : unknown) {
            res.getAllContents().forEachRemaining(sourceObject -> {
                sourceObject.eCrossReferences().forEach(targetObject -> {
                    var targetResource = targetObject.eResource();
                    if (targetResource != res) {
                        topology.computeIfAbsent(res, uri -> new HashSet<>()).add(targetResource);
                    }
                });
            });
        }
        // Loop until we've classified all unknown resources as either definitely clean or potentially impacted (dirty)
        while (dirty.size() + cleanCount < totalCount) {
            for (Resource candidate : Set.copyOf(unknown)) {
                Set<Resource> referenced = topology.getOrDefault(candidate, Set.of());
                if (referenced.stream().anyMatch(dirty::contains)) {
                    dirty.add(candidate);
                    unknown.remove(candidate);
                } else if (referenced.stream().noneMatch(unknown::contains)) {
                    // The candidate does not reference neither dirty or unknown (potentially dirty) resources, we can consider it clean
                    cleanCount += 1;
                }
            }
        }

        return dirty;
    }
}
