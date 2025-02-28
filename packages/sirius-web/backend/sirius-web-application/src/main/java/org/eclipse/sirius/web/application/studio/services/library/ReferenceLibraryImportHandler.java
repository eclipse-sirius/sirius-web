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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
import org.eclipse.sirius.web.application.library.dto.ImportLibrariesInput;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
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
 * Handles the import of a library as a reference.
 *
 * @author gdaniel
 */
@Service
public class ReferenceLibraryImportHandler implements IEditingContextEventHandler {

    private final Logger logger = LoggerFactory.getLogger(ReferenceLibraryImportHandler.class);

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IEditingContextDependencyLoader editingContextDependencyLoader;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public ReferenceLibraryImportHandler(ILibrarySearchService librarySearchService, ISemanticDataSearchService semanticDataSearchService, ISemanticDataUpdateService semanticDataUpdateService, IEditingContextDependencyLoader editingContextDependencyLoader, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
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
        return input instanceof ImportLibrariesInput importLibrariesInput
                && importLibrariesInput.type().equals("import");
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), ImportLibrariesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        if (input instanceof ImportLibrariesInput importLibrariesInput && Objects.equals(importLibrariesInput.type(), "import")) {
            if (editingContext instanceof EditingContext siriusWebEditingContext) {
                List<Library> libraries = this.librarySearchService.findAllById(importLibrariesInput.libraryIds().stream()
                        .map(UUID::fromString)
                        .toList());
                Optional<SemanticData> optionalSemanticData = new UUIDParser().parse(siriusWebEditingContext.getId())
                        .flatMap(this.semanticDataSearchService::findById);

                if (optionalSemanticData.isPresent()) {
                    var editingContextSemanticData = optionalSemanticData.get();

                    List<Library> newLibraries = new ArrayList<>();
                    for (Library library : libraries) {
                        var isAlreadyUsed = editingContextSemanticData.getDependencies().stream()
                                .anyMatch(dependency -> dependency.dependencySemanticDataId().getId().equals(library.getSemanticData().getId()));
                        if (isAlreadyUsed) {
                            this.logger.warn("Cannot add a dependency to library " + library.getNamespace() + ":" + library.getName() + ":" + library.getVersion() + " since the dependency already exists");
                        } else {
                            newLibraries.add(library);
                        }
                    }

                    var semanticDataDependencies = newLibraries.stream()
                            .map(Library::getSemanticData)
                            .toList();
                    this.semanticDataUpdateService.addDependencies(input, AggregateReference.to(editingContextSemanticData.getId()), semanticDataDependencies);
                }

                this.editingContextDependencyLoader.loadDependencies(editingContext);

                payload = new SuccessPayload(input.id(), List.of(new Message("Libraries imported", MessageLevel.SUCCESS)));
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            }
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
