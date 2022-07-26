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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;

/**
 * Mock of the form description editor creation service.
 *
 * @author arichard
 */
public class MockFormDescriptionEditorCreationService implements IFormDescriptionEditorCreationService {

    private int count;

    private FormDescriptionEditor formDescriptionEditor;

    public MockFormDescriptionEditorCreationService(FormDescriptionEditor formDescriptionEditor) {
        this.formDescriptionEditor = Objects.requireNonNull(formDescriptionEditor);
    }

    @Override
    public FormDescriptionEditor create(String label, Object targetObject, FormDescriptionEditorDescription formDescriptionEditorDescription, IEditingContext editingContext) {
        return this.formDescriptionEditor;
    }

    @Override
    public FormDescriptionEditor refresh(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext) {
        this.count = this.count + 1;

        // @formatter:off
        this.formDescriptionEditor = FormDescriptionEditor.newFormDescriptionEditor(this.formDescriptionEditor)
                .label(String.valueOf(this.count))
                .build();
        // @formatter:on
        return this.formDescriptionEditor;
    }

}
