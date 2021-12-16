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
package org.eclipse.sirius.web.spring.collaborative.api;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.representations.IRepresentationMetadata;

/**
 * Used to delete dangling representations.
 *
 * @author sbegaudeau
 */
public interface IDanglingRepresentationDeletionService {

    /**
     * Return <code>true</code> whether the given representation is not attached to a semantic element,
     * <code>false</code> otherwise.
     *
     * @param editingContext
     *            The editing context
     *
     * @param representationMetadata
     *            The metadata of the representation that may be dangling
     * @return <code>true</code> whether the representation is dangling, <code>false</code> otherwise
     */
    boolean isDangling(IEditingContext editingContext, IRepresentationMetadata representationMetadata);

    void deleteDanglingRepresentations(String editingContextId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IDanglingRepresentationDeletionService {

        @Override
        public boolean isDangling(IEditingContext editingContext, IRepresentationMetadata representationMetadata) {
            return false;
        }

        @Override
        public void deleteDanglingRepresentations(String editingContextId) {
        }

    }
}
