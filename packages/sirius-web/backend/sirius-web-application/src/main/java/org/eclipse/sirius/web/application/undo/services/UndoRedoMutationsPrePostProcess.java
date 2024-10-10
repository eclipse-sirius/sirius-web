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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Used to save mutations id.
 *
 * @author mcharfadi
 */
@Service
public class UndoRedoMutationsPrePostProcess implements IInputPreProcessor, IInputPostProcessor {

    private boolean canHandle(IInput input)  {
        return !(input instanceof UndoInput || input instanceof RedoInput || input instanceof LayoutDiagramInput);
    }

    @Override
    public IInput preProcess(IEditingContext editingContext, IInput input, Sinks.Many<ChangeDescription> changeDescriptionSink) {
        if (editingContext instanceof EditingContext siriusEditingContext && canHandle(input)) {
            siriusEditingContext.getChangeRecorder().beginRecording(siriusEditingContext.getDomain().getResourceSet().getResources());
        }
        return input;
    }

    @Override
    public void postProcess(IEditingContext editingContext, IInput input, Sinks.Many<ChangeDescription> changeDescriptionSink) {
        if (editingContext instanceof EditingContext siriusEditingContext && canHandle(input)) {
            var changeDescription = siriusEditingContext.getChangeRecorder().summarize();
            siriusEditingContext.getChangesDescription().put(input.id().toString(), changeDescription);
            siriusEditingContext.getChangeRecorder().endRecording();
        }

    }
}
