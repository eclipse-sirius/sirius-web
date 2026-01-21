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
package org.eclipse.sirius.web.application.undo.services;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextChildObjectCreationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextObjectsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetEditingContextActionsInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionFromDescriptionIdInput;
import org.eclipse.sirius.components.collaborative.dto.GetRepresentationDescriptionInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessageInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemContextMenuInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryInput;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.eclipse.sirius.web.application.undo.services.api.IUndoRedoIgnoreInputPredicate;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextExplorerDescriptionsInput;
import org.eclipse.sirius.web.application.views.search.dto.SearchInput;
import org.springframework.stereotype.Service;

/**
 * Used to test if an input should be ignored by the undo-redo recorder.
 *
 * @author gdaniel
 */
@Service
public class UndoRedoIgnoreInputPredicate implements IUndoRedoIgnoreInputPredicate {

    /**
     * Whitelist of input types that should not trigger undo/redo recording, either because it does not make sense
     * (undo/redo operations themselves), because we do not support under/redo operations on them (diagram layout), or
     * because they correspond to known read-only operations where recording can have performance drawbacks for no
     * benefits (as they do not actually change the backend state).
     */
    private static final List<Class<?>> IGNORED_INPUT_TYPES = List.of(
            UndoInput.class,
            RedoInput.class,
            LayoutDiagramInput.class,
            TreePathInput.class,
            TreeItemContextMenuInput.class,
            EditingContextRepresentationDescriptionsInput.class,
            GetEditingContextActionsInput.class,
            GetRepresentationDescriptionInput.class,
            GetPaletteInput.class,
            GetSelectionDescriptionMessageInput.class,
            GetActionsInput.class,
            InitialDirectEditElementLabelInput.class,
            org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelInput.class,
            ExpandAllTreePathInput.class,
            EditingContextExplorerDescriptionsInput.class,
            EditingContextObjectsInput.class,
            EditingContextChildObjectCreationDescriptionsInput.class,
            GetRepresentationDescriptionFromDescriptionIdInput.class,
            SearchInput.class,
            ReferenceValueOptionsQueryInput.class
    );

    @Override
    public boolean test(IInput input) {
        return IGNORED_INPUT_TYPES.stream()
                .anyMatch(type -> type.isInstance(input));
    }
}
