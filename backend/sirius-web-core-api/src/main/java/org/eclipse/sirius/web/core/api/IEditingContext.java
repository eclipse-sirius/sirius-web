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
package org.eclipse.sirius.web.core.api;

/**
 * Used to contain the semantic data.
 *
 * @author sbegaudeau
 */
public interface IEditingContext {

    /**
     * The name of the variable used to store and retrieve the editing context from a variable manager.
     */
    String EDITING_CONTEXT = "editingContext"; //$NON-NLS-1$

    String getId();

    default void dispose() {
        // Do nothing
    }

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditingContext {

        @Override
        public String getId() {
            return null;
        }

    }
}
