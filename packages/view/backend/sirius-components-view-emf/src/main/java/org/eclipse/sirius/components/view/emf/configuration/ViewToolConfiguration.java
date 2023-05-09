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

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.springframework.context.annotation.Configuration;

/**
 * Bundles the common dependencies that view tool services need into a single object for convenience.
 *
 * @author frouene
 */
@Configuration
public class ViewToolConfiguration {

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IObjectService objectService;

    public ViewToolConfiguration(IURLParser urlParser, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IObjectService objectService) {
        this.urlParser = urlParser;
        this.viewRepresentationDescriptionPredicate = viewRepresentationDescriptionPredicate;
        this.viewRepresentationDescriptionSearchService = viewRepresentationDescriptionSearchService;
        this.objectService = objectService;
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
