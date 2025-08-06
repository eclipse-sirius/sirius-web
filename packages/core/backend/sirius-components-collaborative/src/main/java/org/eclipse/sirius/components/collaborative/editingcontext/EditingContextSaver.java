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
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionConsumer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Used to trigger the persistence of the editing context after receiving a change description.
 *
 * @author sbegaudeau
 * @since v2025.10.0
 */
@Service
public class EditingContextSaver implements IChangeDescriptionConsumer {

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public EditingContextSaver(IEditingContextPersistenceService editingContextPersistenceService) {
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @Override
    public void postAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        if (ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind())) {
            this.editingContextPersistenceService.persist(changeDescription.getInput(), editingContext);
        }
    }
}
