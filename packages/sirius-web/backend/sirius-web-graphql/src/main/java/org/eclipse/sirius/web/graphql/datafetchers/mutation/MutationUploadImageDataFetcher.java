/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.mutation;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.graphql.schema.MutationTypeProvider;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.images.ICustomImageImportService;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to create a new image thanks to an UploadInput.
 * <p>
 * It will be used to handle the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Mutation {
 *   uploadImage(input: UploadImageInput!): UploadImagePayload!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@MutationDataFetcher(type = MutationTypeProvider.TYPE, field = "uploadImage")
public class MutationUploadImageDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String FILE = "file"; //$NON-NLS-1$

    private static final String ID = "id"; //$NON-NLS-1$

    private static final String LABEL = "label"; //$NON-NLS-1$

    private static final String EDITING_CONTEXT_ID = "editingContextId"; //$NON-NLS-1$

    private final ICustomImageImportService customImageImportService;

    public MutationUploadImageDataFetcher(ICustomImageImportService customImageImportService) {
        this.customImageImportService = Objects.requireNonNull(customImageImportService);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Map<Object, Object> inputArgument = environment.getArgument(MutationTypeProvider.INPUT_ARGUMENT);

        // We cannot use directly UploadImageInput, the objectMapper cannot handle the file stream.

        // @formatter:off
        UUID id = Optional.ofNullable(inputArgument.get(ID))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .flatMap(new IDParser()::parse)
                .orElse(null);

        String label = Optional.ofNullable(inputArgument.get(LABEL))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(null);

        String editingContextId = Optional.ofNullable(inputArgument.get(EDITING_CONTEXT_ID))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(null);

        UploadFile file = Optional.ofNullable(inputArgument.get(FILE))
                .filter(UploadFile.class::isInstance)
                .map(UploadFile.class::cast)
                .orElse(null);
        // @formatter:on

        return CompletableFuture.completedFuture(this.customImageImportService.importImage(id, editingContextId, label, file));
    }

}
