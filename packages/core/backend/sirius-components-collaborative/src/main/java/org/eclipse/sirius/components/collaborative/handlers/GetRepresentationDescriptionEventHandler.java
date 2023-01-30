/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionPayload;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to retrieve a representation description from the editing context.
 *
 * @author sbegaudeau
 */
@Service
public class GetRepresentationDescriptionEventHandler implements IEditingContextEventHandler {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public GetRepresentationDescriptionEventHandler(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetRepresentationDescriptionInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        if (input instanceof GetRepresentationDescriptionInput getRepresentationDescriptionInput) {
            IRepresentationDescription representationDescription = this.representationMetadataSearchService.findByRepresentationId(getRepresentationDescriptionInput.representationId())
                    .map(RepresentationMetadata::getDescriptionId)
                    .flatMap(representationDescriptionId -> this.representationDescriptionSearchService.findById(editingContext, representationDescriptionId))
                    .orElse(null);
            var payload = new GetRepresentationDescriptionPayload(input.id(), representationDescription);

            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input));
        }
    }

}
