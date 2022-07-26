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
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;

/**
 * The implementation of {@link IFormDescriptionEditorContext}.
 *
 * @author arichard
 */
public class FormDescriptionEditorContext implements IFormDescriptionEditorContext {

    private FormDescriptionEditor formDescriptionEditor;

    public FormDescriptionEditorContext(FormDescriptionEditor initialFormDescriptionEditor) {
        this.formDescriptionEditor = Objects.requireNonNull(initialFormDescriptionEditor);
    }

    @Override
    public FormDescriptionEditor getFormDescriptionEditor() {
        return this.formDescriptionEditor;
    }

    @Override
    public void update(FormDescriptionEditor mutateFormDescriptionEditor) {
        this.formDescriptionEditor = Objects.requireNonNull(mutateFormDescriptionEditor);
    }
}
