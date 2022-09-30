/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.services.explorer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.services.explorer.api.IDeleteTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * Handles representation deletion triggered via a tree item from the explorer.
 *
 * @author pcdavid
 */
@Service
public class DeleteRepresentationTreeItemEventHandler implements IDeleteTreeItemHandler {

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem) {
        return treeItem.getKind().startsWith(IRepresentation.KIND_PREFIX);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(EditingContextEventProcessor.REPRESENTATION_ID, treeItem.getId());
        return new Success(ChangeKind.REPRESENTATION_TO_DELETE, parameters);
    }
}
