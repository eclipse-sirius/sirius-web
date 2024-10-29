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

package org.eclipse.sirius.components.collaborative.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Used to call services that will be called when receiving a ChangeDescription.
 *
 * @author mcharfadi
 */
@Service
public class ChangeDescriptionListener implements IChangeDescriptionListener {

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public ChangeDescriptionListener(IEditingContextPersistenceService editingContextPersistenceService) {
        this.editingContextPersistenceService = editingContextPersistenceService;
    }

    @Override
    public void onChangeDescription(ChangeDescription changeDescription, IEditingContext editingContext, Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink) {
        if (this.shouldPersistTheEditingContext(changeDescription)) {
            this.editingContextPersistenceService.persist(changeDescription.getInput(), editingContext);
        }
    }

    private boolean shouldPersistTheEditingContext(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }
}
