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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeDescriptionParameters;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionConsumer;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Used to clean up outdated data after a change description is received.
 * <p>
 *     This service is used to perform the following operations:
 * </p>
 * <ul>
 *     <li>Dispose the event processors of the deleted representations</li>
 *     <li>Dispose the event processors of the dangling representations</li>
 *     <li>Delete the dangling representations</li>
 * </ul>
 *
 * @author sbegaudeau
 * @since v2025.10.0
 */
@Service
public class EditingContextEventProcessorCleaner implements IChangeDescriptionConsumer {

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private final Logger logger = LoggerFactory.getLogger(EditingContextEventProcessorCleaner.class);

    public EditingContextEventProcessorCleaner(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IDanglingRepresentationDeletionService danglingRepresentationDeletionService) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);
    }

    @Override
    public void preAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        this.disposeDeletedRepresentations(canBeDisposedSink, editingContext, changeDescription);
        this.disposeDanglingRepresentations(canBeDisposedSink, editingContext);

        this.danglingRepresentationDeletionService.deleteDanglingRepresentations(changeDescription.getInput(), editingContext);
    }

    private void disposeDeletedRepresentations(Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        if (changeDescription.getKind().equals(ChangeKind.REPRESENTATION_DELETION)) {
            var representationIdParameter = changeDescription.getParameters().get(ChangeDescriptionParameters.REPRESENTATION_ID);
            if (representationIdParameter instanceof String representationId) {
                this.disposeRepresentation(canBeDisposedSink, editingContext, representationId);
            }
        }
    }

    /**
     * Disposes the representation when its target object has been removed.
     */
    private void disposeDanglingRepresentations(Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext) {
        for (var representationEventProcessor : this.representationEventProcessorRegistry.values(editingContext.getId())) {
            if (this.danglingRepresentationDeletionService.isDangling(editingContext, representationEventProcessor.getRepresentation())) {
                this.disposeRepresentation(canBeDisposedSink, editingContext, representationEventProcessor.getRepresentation().getId());
            }
        }
    }

    private void disposeRepresentation(Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, String representationId) {
        this.representationEventProcessorRegistry.disposeRepresentation(editingContext.getId(), representationId);

        if (this.representationEventProcessorRegistry.values(editingContext.getId()).isEmpty()) {
            Sinks.EmitResult emitResult = canBeDisposedSink.tryEmitNext(Boolean.TRUE);
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting that the processor can be disposed: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }
}
