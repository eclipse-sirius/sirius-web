/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers;

import java.security.Principal;
import java.util.Optional;

import graphql.schema.DataFetchingEnvironment;

/**
 * Provides helper methods for the manipulation of the data fetching environment.
 *
 * @author pcdavid
 */
public interface IDataFetchingEnvironmentService {
    <T> T getInput(DataFetchingEnvironment environment, Class<T> inputType);

    Optional<Principal> getPrincipal(DataFetchingEnvironment environment);
}
