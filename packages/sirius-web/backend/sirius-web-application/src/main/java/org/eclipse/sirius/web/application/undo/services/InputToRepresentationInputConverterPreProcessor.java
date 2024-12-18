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
package org.eclipse.sirius.web.application.undo.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRedoInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramUndoInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRedoInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalUndoInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.core.api.RedoInput;
import org.eclipse.sirius.components.core.api.UndoInput;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Converts {@link org.eclipse.sirius.components.core.api.UndoInput} related to a representation into the right representation input.
 *
 * @author gcoutable
 */
@Service
public class InputToRepresentationInputConverterPreProcessor implements IInputPreProcessor {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public InputToRepresentationInputConverterPreProcessor(IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public IInput preProcess(IEditingContext editingContext, IInput input, Sinks.Many<ChangeDescription> changeDescriptionSink) {
        IInput convertedInput = input;
        if (editingContext instanceof EditingContext siriusEditingContext) {
            if (input instanceof UndoInput undoInput) {
                var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId());
                if (representationChangeEvents != null && !representationChangeEvents.isEmpty()) {
                    // We are considering that if there are many representation changes, they are all for the same representation.
                    // We have made this hypothesis to ensure that one coming input result in one change description and one payload.
                    var optionalConvertedInput = new UUIDParser().parse(representationChangeEvents.get(0).representationId())
                            .flatMap(this.representationMetadataSearchService::findMetadataById)
                            .map(metadata -> this.convertUndoInput(metadata, undoInput));
                    if (optionalConvertedInput.isPresent()) {
                        convertedInput = optionalConvertedInput.get();
                    }
                }
            }
            if (input instanceof RedoInput redoInput) {
                var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId());
                if (representationChangeEvents != null && !representationChangeEvents.isEmpty()) {
                    // We are considering that if there are many representation changes, they are all for the same representation.
                    // We have made this hypothesis to ensure that one coming input result in one change description and one payload.
                    var optionalConvertedInput = new UUIDParser().parse(representationChangeEvents.get(0).representationId())
                            .flatMap(this.representationMetadataSearchService::findMetadataById)
                            .map(metadata -> this.convertRedoInput(metadata, redoInput));
                    if (optionalConvertedInput.isPresent()) {
                        convertedInput = optionalConvertedInput.get();
                    }
                }
            }
        }


        return convertedInput;
    }

    private IRepresentationInput convertUndoInput(RepresentationMetadata metadata, UndoInput undoInput) {
        return switch (metadata.getKind()) {
            case Diagram.KIND -> new DiagramUndoInput(undoInput.id(), undoInput.editingContextId(), undoInput.mutationId(), metadata.getId().toString());
            case Portal.KIND -> new PortalUndoInput(undoInput.id(), undoInput.editingContextId(), undoInput.mutationId(), metadata.getId().toString());
            default -> null;
        };
    }

    private IRepresentationInput convertRedoInput(RepresentationMetadata metadata, RedoInput redoInput) {
        return switch (metadata.getKind()) {
            case Diagram.KIND -> new DiagramRedoInput(redoInput.id(), redoInput.editingContextId(), redoInput.mutationId(), metadata.getId().toString());
            case Portal.KIND -> new PortalRedoInput(redoInput.id(), redoInput.editingContextId(), redoInput.mutationId(), metadata.getId().toString());
            default -> null;
        };
    }
}
