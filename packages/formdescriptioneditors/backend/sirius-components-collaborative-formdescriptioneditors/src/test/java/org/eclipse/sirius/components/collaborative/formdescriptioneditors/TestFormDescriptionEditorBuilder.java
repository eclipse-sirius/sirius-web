/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;

/**
 * Utility class used to help build form description editors for unit tests.
 *
 * @author arichard
 */
public class TestFormDescriptionEditorBuilder {

    public FormDescriptionEditor getFormDescriptionEditor(String id) {
        Group group = Group.newGroup(UUID.randomUUID().toString())
                .label("group1")
                .widgets(List.of())
                .toolbarActions(List.of())
                .build();

        Page page = Page.newPage(UUID.randomUUID().toString())
                .label("page1")
                .groups(List.of(group))
                .toolbarActions(List.of())
                .build();

        return FormDescriptionEditor.newFormDescriptionEditor(id)
                .label("formDescriptionEditorLabel")
                .descriptionId(UUID.randomUUID().toString())
                .targetObjectId("formDescriptionEditorTargetObjectId")
                .pages(List.of(page))
                .build();
    }
}
