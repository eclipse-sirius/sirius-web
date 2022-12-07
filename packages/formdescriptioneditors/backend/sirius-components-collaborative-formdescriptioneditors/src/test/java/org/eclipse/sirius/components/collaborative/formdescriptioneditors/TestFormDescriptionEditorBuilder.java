/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

/**
 * Utility class used to help build form description editors for unit tests.
 *
 * @author arichard
 */
public class TestFormDescriptionEditorBuilder {

    public static final String IMAGE_PNG = "/image.png"; //$NON-NLS-1$

    public static final String TOOL_IMAGE_URL = IMAGE_PNG;

    public static final String TOOL_LABEL = "toolLabel"; //$NON-NLS-1$

    public FormDescriptionEditor getFormDescriptionEditor(String id) {
        // @formatter:off
        Group group = Group.newGroup(UUID.randomUUID().toString())
            .label("group1") //$NON-NLS-1$
            .widgets(List.of())
            .toolbarActions(List.of())
            .build();

        return FormDescriptionEditor.newFormDescriptionEditor(id)
                .label("formDescriptionEditorLabel") //$NON-NLS-1$
                .descriptionId(UUID.randomUUID().toString())
                .targetObjectId("formDescriptionEditorTargetObjectId") //$NON-NLS-1$
                .groups(List.of(group))
                .build();
        // @formatter:on
    }
}
