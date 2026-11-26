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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.TreeItemDecorator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerDecoratorService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerDecoratorServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Combines all applicable Explorer decorator contributions in Spring bean order.
 *
 * @author pcdavid
 */
@Service
public class ComposedExplorerDecoratorService implements IExplorerDecoratorService {

    private final List<IExplorerDecoratorServiceDelegate> explorerDecoratorServiceDelegates;

    public ComposedExplorerDecoratorService(List<IExplorerDecoratorServiceDelegate> explorerDecoratorServiceDelegates) {
        this.explorerDecoratorServiceDelegates = List.copyOf(explorerDecoratorServiceDelegates);
    }

    @Override
    public List<TreeItemDecorator> getDecorators(IEditingContext editingContext, Object object) {
        if (this.explorerDecoratorServiceDelegates.isEmpty()) {
            return List.of();
        }

        List<TreeItemDecorator> decorators = List.of();
        for (var delegate : this.explorerDecoratorServiceDelegates) {
            if (delegate.canHandle(editingContext, object)) {
                var contribution = delegate.getDecorators(editingContext, object);
                if (!contribution.isEmpty()) {
                    if (decorators.isEmpty()) {
                        decorators = new ArrayList<>();
                    }
                    decorators.addAll(contribution);
                }
            }
        }
        return List.copyOf(decorators);
    }
}
