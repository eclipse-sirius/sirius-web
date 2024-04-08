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
package org.eclipse.sirius.web.application.object.controllers;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Object#label.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Object", field = "label")
public class ObjectLabelDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private final ILabelService labelService;

    public ObjectLabelDataFetcher(ILabelService labelService) {
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        Object object = environment.getSource();
        return this.labelService.getLabel(object);
    }
}
