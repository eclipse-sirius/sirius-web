/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;

/**
 * Utility class used to help build form description editor descriptions for unit tests.
 *
 * @author arichard
 */
public class TestFormDescriptionEditorDescriptionBuilder {

    public FormDescriptionEditorDescription getFormDescriptionEditorDescription(String formDescriptionEditorDescriptionId) {
        return FormDescriptionEditorDescription.newFormDescriptionEditorDescription(formDescriptionEditorDescriptionId)
                .label("")
                .canCreatePredicate(variableManager -> Boolean.TRUE)
                .targetObjectIdProvider(variableManager -> "targetObjectId")
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }
}
