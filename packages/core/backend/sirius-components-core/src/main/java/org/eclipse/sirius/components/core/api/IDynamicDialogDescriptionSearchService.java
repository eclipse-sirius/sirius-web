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
package org.eclipse.sirius.components.core.api;

import java.util.Optional;

/**
 * Used to find dynamic dialog descriptions.
 *
 * @author lfasani
 */
public interface IDynamicDialogDescriptionSearchService {
    Optional<IDynamicDialogDescription> findById(IEditingContext editingContext, String dynamicDialogDescriptionId);


    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author lfasani
     */
    class NoOp implements IDynamicDialogDescriptionSearchService {

        @Override
        public Optional<IDynamicDialogDescription> findById(IEditingContext editingContext, String dynamicDialogDescriptionId) {
            return Optional.empty();
        }
    }
}
