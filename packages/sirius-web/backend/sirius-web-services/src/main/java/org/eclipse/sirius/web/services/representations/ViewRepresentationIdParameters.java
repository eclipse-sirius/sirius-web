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
package org.eclipse.sirius.web.services.representations;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.task.IDeckIdProvider;
import org.eclipse.sirius.components.view.emf.task.IGanttIdProvider;
import org.springframework.stereotype.Service;

/**
 * This class is used to group multiple parameters related to representation id providers.
 *
 * @author fbarbin
 */
@Service
public final class ViewRepresentationIdParameters {

    private final IDiagramIdProvider diagramIdProvider;

    private final IFormIdProvider formIdProvider;

    private final IGanttIdProvider ganttIdProvider;

    private final IDeckIdProvider deckIdProvider;

    public ViewRepresentationIdParameters(IDiagramIdProvider diagramIdProvider, IURLParser urlParser, IFormIdProvider formIdProvider, IGanttIdProvider ganttIdProvider,
            IDeckIdProvider deckIdProvider) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.ganttIdProvider = Objects.requireNonNull(ganttIdProvider);
        this.deckIdProvider = Objects.requireNonNull(deckIdProvider);
    }

    public IDiagramIdProvider getDiagramIdProvider() {
        return this.diagramIdProvider;
    }

    public IFormIdProvider getFormIdProvider() {
        return this.formIdProvider;
    }

    public IGanttIdProvider getGanttIdProvider() {
        return this.ganttIdProvider;
    }

    public IDeckIdProvider getDeckIdProvider() {
        return this.deckIdProvider;
    }

}
