/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.task.starter.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.emf.services.api.IDefaultEMFLabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFLabelServiceDelegate;
import org.eclipse.sirius.components.task.provider.TaskItemProviderAdapterFactory;
import org.springframework.stereotype.Service;

/**
 * This class allows to override the default behavior for the labels.
 *
 * @author Laurent Fasani
 */
@Service
public class TaskLabelServiceDelegate implements IEMFLabelServiceDelegate {

    private final IDefaultEMFLabelService defaultEMFLabelService;

    public TaskLabelServiceDelegate(IDefaultEMFLabelService defaultEMFLabelService) {
        this.defaultEMFLabelService = Objects.requireNonNull(defaultEMFLabelService);
    }

    @Override
    public boolean canHandle(EObject self) {
        return self instanceof TaskTag;
    }

    @Override
    public StyledString getStyledLabel(EObject self) {
        var adapter = new TaskItemProviderAdapterFactory().adapt(self, IItemLabelProvider.class);
        if (adapter instanceof IItemLabelProvider itemLabelProvider) {
            return StyledString.of(itemLabelProvider.getText(self));
        }
        return StyledString.of("");
    }

    @Override
    public List<String> getImagePaths(EObject self) {
        return this.defaultEMFLabelService.getImagePaths(self);
    }
}
