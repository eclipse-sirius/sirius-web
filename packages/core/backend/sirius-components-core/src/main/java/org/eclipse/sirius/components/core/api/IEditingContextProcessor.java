/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

/**
 * Processor for {@link IEditingContext}.
 *
 * @author arichard
 */
public interface IEditingContextProcessor {

    default void preProcess(IEditingContext editingContext) {
        // Do nothing
    }

    default void postProcess(IEditingContext editingContext) {
        // Do nothing
    }

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IEditingContextProcessor {

        @Override
        public void preProcess(IEditingContext editingContext) {
        }

        @Override
        public void postProcess(IEditingContext editingContext) {
        }
    }
}
