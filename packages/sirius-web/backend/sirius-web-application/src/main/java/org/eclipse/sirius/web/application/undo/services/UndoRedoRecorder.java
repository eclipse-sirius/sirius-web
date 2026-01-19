/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.IUndoRedoIgnoreInputPredicate;
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

    private final List<IUndoRedoIgnoreInputPredicate> undoRedoIgnoreInputPredicates;

    private final Logger logger = LoggerFactory.getLogger(UndoRedoRecorder.class);

    public UndoRedoRecorder(List<IUndoRedoIgnoreInputPredicate> undoRedoIgnoreInputPredicates) {
        this.undoRedoIgnoreInputPredicates = Objects.requireNonNull(undoRedoIgnoreInputPredicates);
    }

    private boolean canHandle(IInput input)  {
        return this.undoRedoIgnoreInputPredicates.stream().noneMatch(undoRedoIgnoreInputPredicate -> undoRedoIgnoreInputPredicate.test(input));
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
