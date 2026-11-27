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
package org.eclipse.sirius.web.papaya.services;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.papaya.Task;
import org.eclipse.sirius.components.trees.TreeItemDecorator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerDecoratorServiceDelegate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Marks completed Papaya tasks in the Explorer.
 *
 * @author pcdavid
 */
@Service
@Order(1)
public class PapayaTaskCompletionDecoratorServiceDelegate implements IExplorerDecoratorServiceDelegate {

    private static final List<TreeItemDecorator> COMPLETION_DECORATORS = List.of(new TreeItemDecorator("/icons/papaya/task-completed.svg", null));

    @Override
    public boolean canHandle(IEditingContext editingContext, Object object) {
        return object instanceof Task task && task.isDone();
    }

    @Override
    public List<TreeItemDecorator> getDecorators(IEditingContext editingContext, Object object) {
        return COMPLETION_DECORATORS;
    }
}
