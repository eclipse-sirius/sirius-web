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

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeDescriptionParameters;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionConsumer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Used to publish to the services subscribed to the events of the editing context some data from the change descriptions.
 *
 * @author sbegaudeau
 * @since v2025.10.0
 */
@Service
public class ChangeDescriptionPublisher implements IChangeDescriptionConsumer {

    private final Logger logger = LoggerFactory.getLogger(ChangeDescriptionPublisher.class);

    @Override
    public void preAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        if (payloadSink.currentSubscriberCount() > 0) {
            IInput input = changeDescription.getInput();
            UUID correlationId = input.id();

            if (ChangeKind.REPRESENTATION_RENAMING.equals(changeDescription.getKind()) && !changeDescription.getParameters().isEmpty()) {
                Map<String, Object> parameters = changeDescription.getParameters();

                var optionalRepresentationId = Optional.ofNullable(parameters.get(ChangeDescriptionParameters.REPRESENTATION_ID))
                        .filter(String.class::isInstance)
                        .map(String.class::cast);
                var optionalRepresentationLabel = Optional.ofNullable(parameters.get(ChangeDescriptionParameters.REPRESENTATION_LABEL))
                        .filter(String.class::isInstance)
                        .map(String.class::cast);

                if (optionalRepresentationId.isPresent() && optionalRepresentationLabel.isPresent()) {
                    var representationId = optionalRepresentationId.get();
                    var representationLabel = optionalRepresentationLabel.get();

                    Sinks.EmitResult emitResult = payloadSink.tryEmitNext(new RepresentationRenamedEventPayload(correlationId, representationId, representationLabel));
                    if (emitResult.isFailure()) {
                        String pattern = "An error has occurred while emitting a RepresentationRenamedEventPayload: {}";
                        this.logger.warn(pattern, emitResult);
                    }
                }
            }
        }
    }
}
