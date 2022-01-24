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
package org.eclipse.sirius.web.spring.collaborative.editingcontext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.spring.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;

/**
 * Used to create the instances of the executor service required by Sirius Components.
 *
 * @author sbegaudeau
 */
public class EditingContextEventProcessorExecutorServiceProvider implements IEditingContextEventProcessorExecutorServiceProvider {

    @Override
    public ExecutorService getExecutorService(IEditingContext editingContext) {
        return Executors.newSingleThreadExecutor((Runnable runnable) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setName("Editing context " + editingContext.getId()); //$NON-NLS-1$
            return thread;
        });
    }

}
