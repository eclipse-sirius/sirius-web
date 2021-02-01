/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.project;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.ModelerTypeProvider;
import org.eclipse.sirius.web.services.api.modelers.Modeler;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the editing context of a modeler.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Modeler {
 *   editingContext: EditingContext!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = ModelerTypeProvider.TYPE, field = ModelerTypeProvider.EDITING_CONTEXT_FIELD)
public class ModelerEditingContextDataFetcher implements IDataFetcherWithFieldCoordinates<Object> {

    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        Modeler modeler = environment.getSource();
        return modeler.getProject();
    }

}
