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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IComposedEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IComposedEditingContextEventHandlerFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.springframework.stereotype.Service;

/**
 * Used to create {@link IComposedEditingContextEventHandler}.
 *
 * @author gcoutable
 */
@Service
public class ComposedEditingContextEventHandlerFactory implements IComposedEditingContextEventHandlerFactory {

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    public ComposedEditingContextEventHandlerFactory(List<IEditingContextEventHandler> editingContextEventHandlers) {
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
    }

    @Override
    public IComposedEditingContextEventHandler createComposedEditingContextEventHandler(IRepresentationEventProcessorRegistry representationEventProcessorRegistry) {
        return new ComposedEditingContextEventHandler(this.editingContextEventHandlers, representationEventProcessorRegistry);
    }
}
