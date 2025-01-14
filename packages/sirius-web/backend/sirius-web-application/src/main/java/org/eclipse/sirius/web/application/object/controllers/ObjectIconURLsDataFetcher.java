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
package org.eclipse.sirius.web.application.object.controllers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IImageURLSanitizer;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher for the field Object#iconURLs.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Object", field = "iconURLs")
public class ObjectIconURLsDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    private final IImageURLSanitizer imageURLSanitizer;

    private final ILabelService labelService;

    public ObjectIconURLsDataFetcher(IImageURLSanitizer imageURLSanitizer, ILabelService labelService) {
        this.imageURLSanitizer = Objects.requireNonNull(imageURLSanitizer);
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        var object = environment.getSource();

        return this.labelService.getImagePath(object).stream()
                .map(url -> this.imageURLSanitizer.sanitize(URLConstants.IMAGE_BASE_PATH, url))
                .toList();
    }
}
