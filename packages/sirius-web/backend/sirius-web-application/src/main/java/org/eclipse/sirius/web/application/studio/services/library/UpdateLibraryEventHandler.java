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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextDependencyLoader;
import org.eclipse.sirius.web.application.library.dto.UpdateLibraryInput;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handles the update of a library.
 *
 * @author gdaniel
 */
@Service
public class UpdateLibraryEventHandler implements IEditingContextEventHandler {

    private final Logger logger = LoggerFactory.getLogger(UpdateLibraryEventHandler.class);

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IEditingContextDependencyLoader editingContextDependencyLoader;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public UpdateLibraryEventHandler(ILibrarySearchService librarySearchService, ISemanticDataSearchService semanticDataSearchService, ISemanticDataUpdateService semanticDataUpdateService, IEditingContextDependencyLoader editingContextDependencyLoader, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.editingContextDependencyLoader = Objects.requireNonNull(editingContextDependencyLoader);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UpdateLibraryInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), UpdateLibraryInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof UpdateLibraryInput updateLibraryInput) {
            if (editingContext instanceof EditingContext siriusWebEditingContext) {
                Optional<Library> optionalNewLibrary = this.librarySearchService.findById(updateLibraryInput.libraryId());
                if (optionalNewLibrary.isPresent()) {
                    Library newLibrary = optionalNewLibrary.get();

                    // Find the version of the library already in the dependencies.
                    Optional<Library> optionalOldLibrary = this.getLibraryDependencyWithDifferentVersion(siriusWebEditingContext, newLibrary);
                    if (optionalOldLibrary.isPresent()) {
                        Library oldLibrary = optionalOldLibrary.get();
                        List<Resource> oldLibraryResources = this.getAllResourcesFromLibraryRecursiveDependencies(siriusWebEditingContext, oldLibrary);

                        for (Resource oldLibraryResource : oldLibraryResources) {
                            oldLibraryResource.unload();
                            siriusWebEditingContext.getDomain().getResourceSet().getResources().remove(oldLibraryResource);
                        }

                        this.addLibraryDependency(updateLibraryInput, siriusWebEditingContext, newLibrary);
                        this.removeLibraryDependency(updateLibraryInput, siriusWebEditingContext, oldLibrary);

                        this.editingContextDependencyLoader.loadDependencies(siriusWebEditingContext);

                        payload = new SuccessPayload(input.id(), List.of(new Message("Library " + oldLibrary.getName() + " updated to version " + newLibrary.getVersion(), MessageLevel.SUCCESS)));
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
                    } else {
                        // We do not support update of transitive libraries for the moment.
                        payload = new ErrorPayload(input.id(), List.of(new Message("Cannot update Library " + newLibrary.getName() + ": the library is not a direct dependency", MessageLevel.ERROR)));
                    }
                }
            }

        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Library> getLibraryDependencyWithDifferentVersion(EditingContext editingContext, Library library) {
        return new UUIDParser().parse(editingContext.getId())
                .flatMap(this.semanticDataSearchService::findById)
                .map(SemanticData::getDependencies)
                .orElse(List.of())
                .stream()
                .map(SemanticDataDependency::dependencySemanticDataId)
                .map(this.librarySearchService::findBySemanticData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(libraryDependency -> Objects.equals(libraryDependency.getNamespace(), library.getNamespace())
                        && Objects.equals(libraryDependency.getName(), library.getName())
                        && !Objects.equals(libraryDependency.getVersion(), library.getVersion()))
                .findFirst();
    }

    private List<Resource> getAllResourcesFromLibraryRecursiveDependencies(EditingContext editingContext, Library library) {
        Set<Library> libraries = new LinkedHashSet<>();
        libraries.add(library);
        this.semanticDataSearchService.findAllDependenciesRecursivelyById(library.getSemanticData().getId()).stream()
                .map(SemanticData::getId)
                .map(AggregateReference::<SemanticData, UUID>to)
                .map(this.librarySearchService::findBySemanticData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(libraries::add);
        List<Resource> libraryResources = new ArrayList<>();
        for (Resource resource : editingContext.getDomain().getResourceSet().getResources()) {
            resource.eAdapters().stream()
                .filter(LibraryMetadataAdapter.class::isInstance)
                .map(LibraryMetadataAdapter.class::cast)
                .findFirst()
                .ifPresent(libraryMetadata -> {
                    if (libraries.stream().anyMatch(libraryDependency ->
                        Objects.equals(libraryMetadata.getNamespace(), libraryDependency.getNamespace())
                            && Objects.equals(libraryMetadata.getName(), libraryDependency.getName())
                            && Objects.equals(libraryMetadata.getVersion(), libraryDependency.getVersion())
                    )) {
                        libraryResources.add(resource);
                    }
                });
        }
        return libraryResources;
    }

    private void addLibraryDependency(UpdateLibraryInput updateLibraryInput, EditingContext editingContext, Library library) {
        Optional<SemanticData> optionalSemanticData = new UUIDParser().parse(editingContext.getId())
                .flatMap(this.semanticDataSearchService::findById);
        if (optionalSemanticData.isPresent()) {
            if (optionalSemanticData.get().getDependencies().stream().anyMatch(dependency -> dependency.dependencySemanticDataId().equals(library.getSemanticData()))) {
                this.logger.warn("Cannot add the dependency to library " + library.getNamespace() + ":" + library.getName() + ":" + library.getVersion() + ": the dependency already exists");
            } else {
                this.semanticDataUpdateService.addDependencies(updateLibraryInput, AggregateReference.to(optionalSemanticData.get().getId()), List.of(library.getSemanticData()));
            }
        }
    }

    private void removeLibraryDependency(UpdateLibraryInput updateLibraryInput, EditingContext editingContext, Library library) {
        new UUIDParser().parse(editingContext.getId())
            .flatMap(this.semanticDataSearchService::findById)
            .ifPresent(semanticData -> {
                this.semanticDataUpdateService.removeDependencies(updateLibraryInput, AggregateReference.to(semanticData.getId()), List.of(library.getSemanticData()));
            });
    }
}
