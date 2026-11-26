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
 * Shows Papaya task priorities in the Explorer.
 *
 * @author pcdavid
 */
@Service
@Order(2)
public class PapayaTaskPriorityDecoratorServiceDelegate implements IExplorerDecoratorServiceDelegate {

    @Override
    public boolean canHandle(IEditingContext editingContext, Object object) {
        return object instanceof Task;
    }

    @Override
    public List<TreeItemDecorator> getDecorators(IEditingContext editingContext, Object object) {
        var task = (Task) object;
        return List.of(new TreeItemDecorator("/icons/papaya/task-priority.svg", task.getPriority().getLiteral()));
    }
}
