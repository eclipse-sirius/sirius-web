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
package org.eclipse.sirius.components.core.api;

import org.eclipse.sirius.components.events.ICause;

/**
 * Interface used to save the editing context when a change has been performed.
 *
 * @author sbegaudeau
 */
public interface IEditingContextPersistenceService {
    void persist(ICause cause, IEditingContext editingContext);

    /**
     * Empty implementation, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IEditingContextPersistenceService {

        @Override
        public void persist(ICause cause, IEditingContext editingContext) {
            // Do nothing
        }

    }
}
