/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.task.IDeckIdProvider;
import org.eclipse.sirius.components.view.emf.task.IGanttIdProvider;
import org.eclipse.sirius.web.services.editingcontext.api.IViewLoader;
import org.springframework.stereotype.Service;

/**
 * This class is used to group multiple parameters.
 *
 * @author lfasani
 */
@Service
public final class ViewRepresentationDescriptionSearchServiceParameters {

    private final IViewLoader viewLoader;

    private final IDiagramIdProvider diagramIdProvider;

    private final IURLParser urlParser;

    private final IFormIdProvider formIdProvider;

    private final IGanttIdProvider ganttIdProvider;

    private final IDeckIdProvider deckIdProvider;

    private final IObjectService objectService;

    public ViewRepresentationDescriptionSearchServiceParameters(IViewLoader viewLoader, EPackage.Registry ePackageRegistry, IURLParser urlParser, IObjectService objectService,
            ViewRepresentationIdParameters representationIdParameters) {
        Objects.requireNonNull(representationIdParameters);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewLoader = Objects.requireNonNull(viewLoader);
        this.diagramIdProvider = Objects.requireNonNull(representationIdParameters.getDiagramIdProvider());
        this.formIdProvider = Objects.requireNonNull(representationIdParameters.getFormIdProvider());
        this.ganttIdProvider = Objects.requireNonNull(representationIdParameters.getGanttIdProvider());
        this.deckIdProvider = Objects.requireNonNull(representationIdParameters.getDeckIdProvider());
        this.objectService = Objects.requireNonNull(objectService);
    }

    public IViewLoader getViewLoader() {
        return this.viewLoader;
    }

    public IDiagramIdProvider getDiagramIdProvider() {
        return this.diagramIdProvider;
    }

    public IURLParser getUrlParser() {
        return this.urlParser;
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

    public IObjectService getObjectService() {
        return this.objectService;
    }
}
