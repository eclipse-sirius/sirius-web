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
package org.eclipse.sirius.web.application.i18n.controllers;

import java.util.Locale;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.beans.factory.annotation.Value;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#language.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Viewer", field = "language")
public class ViewerLanguageDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private final Locale locale;

    public ViewerLanguageDataFetcher(@Value("${spring.mvc.locale:en_EN}") Locale locale) {
        this.locale = Objects.requireNonNull(locale);
    }

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        return this.locale.getLanguage();
    }
}
