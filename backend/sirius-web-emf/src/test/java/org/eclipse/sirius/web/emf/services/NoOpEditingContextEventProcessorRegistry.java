/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

/**
 * Implementation of the editing context event processor registry which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpEditingContextEventProcessorRegistry implements IEditingContextEventProcessorRegistry {

    @Override
    public List<IEditingContextEventProcessor> getEditingContextEventProcessors() {
        return new ArrayList<>();
    }

    @Override
    public Optional<IPayload> dispatchEvent(UUID editingContextId, IInput input) {
        return Optional.empty();
    }

    @Override
    public Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(UUID editingContextId) {
        return Optional.empty();
    }

    @Override
    public void disposeEditingContextEventProcessor(UUID editingContextId) {
    }

}
