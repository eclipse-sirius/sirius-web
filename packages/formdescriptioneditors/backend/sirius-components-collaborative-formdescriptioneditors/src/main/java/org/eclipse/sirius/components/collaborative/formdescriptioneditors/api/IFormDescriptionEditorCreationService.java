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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;

/**
 * Used to create and refresh a form description editor.
 *
 * @author arichard
 */
public interface IFormDescriptionEditorCreationService {
    FormDescriptionEditor create(String label, Object targetObject, FormDescriptionEditorDescription formDescriptionEditorDescription, IEditingContext editingContext);

    FormDescriptionEditor refresh(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IFormDescriptionEditorCreationService {

        @Override
        public FormDescriptionEditor create(String label, Object targetObject, FormDescriptionEditorDescription formDescriptionEditorDescription, IEditingContext editingContext) {
            return null;
        }

        @Override
        public FormDescriptionEditor refresh(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext) {
            return null;
        }

    }
}
