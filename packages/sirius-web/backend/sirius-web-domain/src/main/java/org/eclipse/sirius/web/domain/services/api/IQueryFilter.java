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
package org.eclipse.sirius.web.domain.services.api;

import java.util.List;
import java.util.Map;

/**
 * Replaces statement in a Query to filter the query result based on different operation.
 *
 * @author gcoutable
 */
public interface IQueryFilter {

    String filterQuery(String query, String context, List<String> attributes, Map<String, Object> filter);
}
