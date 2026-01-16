/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.project.dto.UnserializeImportedRepresentationDataInput;
import org.eclipse.sirius.web.application.project.dto.UnserializeImportedRepresentationSuccessPayload;
import org.eclipse.sirius.web.application.representation.services.api.IRepresentationContentMigrationService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Event handler to unserialize and migrate an imported representation.
 *
 * @author tgiraudet
 */
@Service
public class UnserializeImportedRepresentationDataEventHandler implements IEditingContextEventHandler {

    private final IMessageService messageService;

    private final Counter counter;

    private final IRepresentationContentMigrationService representationContentMigrationService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(UnserializeImportedRepresentationDataEventHandler.class);

    public UnserializeImportedRepresentationDataEventHandler(IMessageService messageService, ObjectMapper objectMapper,
            IRepresentationContentMigrationService representationContentMigrationService, MeterRegistry meterRegistry) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
        this.representationContentMigrationService = representationContentMigrationService;

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UnserializeImportedRepresentationDataInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof UnserializeImportedRepresentationDataInput importDataInput) {
            var serializedData = importDataInput.serializedData();
            var metadata = importDataInput.metadata();
            try {
                var node = this.representationContentMigrationService.getMigratedContent(editingContext,
                        serializedData.representation(),
                        serializedData.kind(),
                        metadata.lastMigrationPerformed(),
                        metadata.migrationVersion());
                if (node.isPresent()) {
                    var migratedContent = this.objectMapper.treeToValue(node.get(), IRepresentation.class);
                    var data = new RepresentationImportData(
                            serializedData.id(),
                            serializedData.projectId(),
                            serializedData.descriptionId(),
                            serializedData.targetObjectId(),
                            serializedData.label(),
                            serializedData.kind(),
                            migratedContent
                    );
                    payload = new UnserializeImportedRepresentationSuccessPayload(importDataInput.id(), data);
                }
            } catch (JsonProcessingException e) {
                logger.warn(e.getMessage(), e);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
