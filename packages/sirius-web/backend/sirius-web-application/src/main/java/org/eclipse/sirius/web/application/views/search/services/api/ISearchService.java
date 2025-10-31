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
package org.eclipse.sirius.web.application.views.search.services.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.views.search.dto.SearchMatch;
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;

/**
 * Service use to search for elements inside an editing context based on a user-supplied query.
 *
 * @author pcdavid
 */
public interface ISearchService {
    List<SearchMatch> search(IEditingContext editingContext, SearchQuery query);
}
