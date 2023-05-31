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
package org.eclipse.sirius.components.view.emf.configuration;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Bundles the common dependencies that view tool services need into a single object for convenience.
 *
 * @author frouene
 */
@Service
public class ViewReconnectionToolsExecutorParameters {

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IObjectService objectService;

    public ViewReconnectionToolsExecutorParameters(IURLParser urlParser, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IObjectService objectService) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    public IURLParser getUrlParser() {
        return this.urlParser;
    }

    public IViewRepresentationDescriptionPredicate getViewRepresentationDescriptionPredicate() {
        return this.viewRepresentationDescriptionPredicate;
    }

    public IViewRepresentationDescriptionSearchService getViewRepresentationDescriptionSearchService() {
        return this.viewRepresentationDescriptionSearchService;
    }

    public IObjectService getObjectService() {
        return this.objectService;
    }
}
