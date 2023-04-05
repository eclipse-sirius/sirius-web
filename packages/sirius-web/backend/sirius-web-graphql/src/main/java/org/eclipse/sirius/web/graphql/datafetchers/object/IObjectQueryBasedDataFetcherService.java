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
package org.eclipse.sirius.web.graphql.datafetchers.object;

import java.util.Map;

import graphql.schema.DataFetchingEnvironment;

/**
 * A service providing useful method common to ObjectQueryBasedDataFetcher.
 *
 * @author fbarbin
 */
public interface IObjectQueryBasedDataFetcherService {

    /**
     * From the providing environment, compute the new variable map resulting from the merge of the current local
     * context variables (defined by ancestors) and the potential new variable defined by the parent.
     *
     * @param environment
     *            the DataFetchingEnvironment.
     * @return the new variables map.
     */
    Map<String, Object> computeNewVariables(DataFetchingEnvironment environment);
}
