/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.schema.EditingContextTypeProvider;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the root object creation descriptions accessible in an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   rootObjectCreationDescriptions(domainId: ID!, suggested: Boolean!): [ChildCreationDescription!]!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = EditingContextTypeProvider.TYPE, field = EditingContextTypeProvider.ROOT_OBJECT_CREATION_DESCRIPTIONS_FIELD)
public class EditingContextRootObjectCreationDescriptionsDataFetcher implements IDataFetcherWithFieldCoordinates<List<ChildCreationDescription>> {
    private final IEditService editService;

    public EditingContextRootObjectCreationDescriptionsDataFetcher(IEditService editService) {
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public List<ChildCreationDescription> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String domainId = environment.getArgument(EditingContextTypeProvider.DOMAIN_ID_ARGUMENT);
        Boolean suggested = environment.getArgument(EditingContextTypeProvider.SUGGESTED_ARGUMENT);

        return this.editService.getRootCreationDescriptions(editingContextId, domainId, suggested);
    }
}
