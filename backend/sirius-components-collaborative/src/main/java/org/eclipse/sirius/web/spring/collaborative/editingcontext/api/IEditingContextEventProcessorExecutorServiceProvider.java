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
package org.eclipse.sirius.web.spring.collaborative.editingcontext.api;

import java.util.concurrent.ExecutorService;

import org.eclipse.sirius.web.core.api.IEditingContext;

/**
 * Used to provide a different executor service for the editing context event processor.
 *
 * <p>
 * Thanks to this API, it will be easier to customize the thread management strategy of the editing context event
 * processor.
 * </p>
 *
 * @author sbegaudeau
 */
public interface IEditingContextEventProcessorExecutorServiceProvider {
    ExecutorService getExecutorService(IEditingContext editingContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditingContextEventProcessorExecutorServiceProvider {

        @Override
        public ExecutorService getExecutorService(IEditingContext editingContext) {
            return null;
        }

    }
}
