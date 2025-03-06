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
package org.eclipse.sirius.web.application.library.controllers;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.library.dto.LibraryDTO;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.library.services.api.ILibraryApplicationService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Object#library.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "Object", field = "library")
public class ObjectLibraryDataFetcher implements IDataFetcherWithFieldCoordinates<LibraryDTO> {

    private final ILibraryApplicationService libraryApplicationService;

    public ObjectLibraryDataFetcher(ILibraryApplicationService libraryApplicationService) {
        this.libraryApplicationService = Objects.requireNonNull(libraryApplicationService);
    }

    @Override
    public LibraryDTO get(DataFetchingEnvironment environment) throws Exception {
        Object object = environment.getSource();
        var optionalLibraryMetadataAdapter = Optional.of(object)
                .filter(Notifier.class::isInstance)
                .map(Notifier.class::cast)
                .stream()
                .map(Notifier::eAdapters)
                .flatMap(Collection::stream)
                .filter(LibraryMetadataAdapter.class::isInstance)
                .map(LibraryMetadataAdapter.class::cast)
                .findFirst();

        return optionalLibraryMetadataAdapter.flatMap(libraryMetadata -> this.libraryApplicationService.findByNamespaceAndNameAndVersion(libraryMetadata.getNamespace(), libraryMetadata.getName(), libraryMetadata.getVersion()))
                .orElse(null);
    }
}
