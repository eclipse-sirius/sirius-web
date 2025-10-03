/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.time.Duration;
import java.util.List;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessageInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemContextMenuInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Used to save mutations id.
 *
 * @author mcharfadi
 */
@Service
public class UndoRedoRecorder implements IInputPreProcessor, IInputPostProcessor {
    /**
     * Whitelist of input types that should not trigger undo/redo recording, either because it does not make sense
     * (undo/redo operations themselves), because we do not support under/redo operations on them (diagram layout), or
     * because they correspond to known read-only operations where recording can have performance drawbacks for no
     * benefits (as they do not actually change the backend state).
     */
    private static final List<Class<?>> INGORED_INPUT_TYPES = List.of(
            UndoInput.class,
            RedoInput.class,
            LayoutDiagramInput.class,
            TreePathInput.class,
            TreeItemContextMenuInput.class,
            EditingContextRepresentationDescriptionsInput.class,
            GetEditingContextActionsInput.class,
            GetRepresentationDescriptionInput.class,
            GetPaletteInput.class,
            GetSelectionDescriptionMessageInput.class,
            GetActionsInput.class,
            InitialDirectEditElementLabelInput.class,
            org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelInput.class
    );

    private final Logger logger = LoggerFactory.getLogger(UndoRedoRecorder.class);

    private boolean canHandle(IInput input)  {
        return INGORED_INPUT_TYPES.stream().noneMatch(type -> type.isInstance(input));
    }

    @Override
    public IInput preProcess(IEditingContext editingContext, IInput input, Sinks.Many<ChangeDescription> changeDescriptionSink) {
        if (editingContext instanceof EditingContext siriusEditingContext && this.canHandle(input)) {
            siriusEditingContext.getChangeRecorder().beginRecording(siriusEditingContext.getDomain().getResourceSet().getResources());
        }
        return input;
    }

    @Override
    public void postProcess(IEditingContext editingContext, IInput input, Sinks.Many<ChangeDescription> changeDescriptionSink) {
        if (editingContext instanceof EditingContext siriusEditingContext && this.canHandle(input)) {
            long start = System.nanoTime();
            var changeDescription = siriusEditingContext.getChangeRecorder().summarize();
            siriusEditingContext.getInputId2change().put(input.id(), changeDescription);
            siriusEditingContext.getChangeRecorder().endRecording();
            long stop = System.nanoTime();
            this.logger.debug("UndoRedoRecorder post-processing of input {} in {}ms", input.getClass().getSimpleName(), Duration.ofNanos(stop - start).toMillis());
        }
    }
}
