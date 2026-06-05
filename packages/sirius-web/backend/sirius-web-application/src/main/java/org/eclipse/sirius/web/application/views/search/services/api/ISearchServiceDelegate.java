/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;

/**
 * Delegates that searches for elements inside an editing context.
 *
 * @author gdaniel
 */
public interface ISearchServiceDelegate {

    boolean canHandle(IEditingContext editingContext, SearchQuery query);

    List<Object> search(IEditingContext editingContext, SearchQuery query);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gdaniel
     */
    class NoOp implements ISearchServiceDelegate {

        @Override
        public boolean canHandle(IEditingContext editingContext, SearchQuery query) {
            return true;
        }

        @Override
        public List<Object> search(IEditingContext editingContext, SearchQuery query) {
            return List.of();
        }
    }
}
