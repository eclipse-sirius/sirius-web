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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.views.explorer.services.api.IRenameTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * Handles domain objet renaming triggered via a tree item from the explorer.
 *
 * @author frouene
 */
@Service
public class RenameObjectTreeItemEventHandler implements IRenameTreeItemHandler {

    private final IObjectService objectService;

    private final IEditService editService;

    public RenameObjectTreeItemEventHandler(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        return treeItem.getKind().startsWith(SemanticKindConstants.PREFIX);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        var optionalObject = this.objectService.getObject(editingContext, treeItem.getId());
        if (optionalObject.isPresent()) {
            Object object = optionalObject.get();
            var optionalLabelField = this.objectService.getLabelField(object);
            if (optionalLabelField.isPresent()) {
                String labelField = optionalLabelField.get();
                this.editService.editLabel(object, labelField, newLabel);
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
            }
        }

        return new Failure("Something went wrong while handling this rename action.");
    }

}
