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
package org.eclipse.sirius.components.formdescriptioneditors.renderer;

import java.util.Optional;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.representations.BaseRenderer;
import org.eclipse.sirius.components.representations.Element;

/**
 * Renderer used to create the form description editor from its description and some variables.
 * <p>
 * It will delegate most of its behavior to the abstract renderer which will process the tree of elements to render. The
 * form description editor renderer will mostly be used in order to let the abstract renderer delegate some form
 * description editor-specific behavior such as the instantiation of the form description editor concrete types and the
 * validation of the properties of both the form description editor elements and the form description editor components.
 * </p>
 *
 * @author arichard
 */
public class FormDescriptionEditorRenderer {
    private final BaseRenderer baseRenderer;

    public FormDescriptionEditorRenderer() {
        this.baseRenderer = new BaseRenderer(new FormDescriptionEditorInstancePropsValidator(), new FormDescriptionEditorComponentPropsValidator(), new FormDescriptionEditorElementFactory());
    }

    public FormDescriptionEditor render(Element element) {
        // @formatter:off
        return Optional.of(this.baseRenderer.renderElement(element))
                .filter(FormDescriptionEditor.class::isInstance)
                .map(FormDescriptionEditor.class::cast)
                .orElse(null);
        // @formatter:on
    }
}
