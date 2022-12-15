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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.api;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;

/**
 * Information used to perform some operations on the form description editor.
 *
 * @author arichard
 */
public interface IFormDescriptionEditorContext {

    /**
     * The name of the variable used to store and retrieve the form description editor context from a variable manager.
     */
    String FORMDESCRIPTIONEDITOR_CONTEXT = "formDescriptionEditorContext";

    FormDescriptionEditor getFormDescriptionEditor();

    void update(FormDescriptionEditor updatedFormDescriptionEditor);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IFormDescriptionEditorContext {

        @Override
        public FormDescriptionEditor getFormDescriptionEditor() {
            return null;
        }

        @Override
        public void update(FormDescriptionEditor updatedFormDescriptionEditor) {
        }
    }
}
