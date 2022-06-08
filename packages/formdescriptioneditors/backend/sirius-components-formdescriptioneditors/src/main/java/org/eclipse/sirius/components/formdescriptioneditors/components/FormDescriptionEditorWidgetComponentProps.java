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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorWidgetDescription;
import org.eclipse.sirius.components.representations.IProps;

/**
 * * The properties of the form description editor widget component.
 *
 * @author arichard
 */
public class FormDescriptionEditorWidgetComponentProps implements IProps {

    private final FormDescriptionEditorWidgetDescription formDescriptionEditorWidgetDescription;

    public FormDescriptionEditorWidgetComponentProps(FormDescriptionEditorWidgetDescription formDescriptionEditorWidgetDescription) {
        this.formDescriptionEditorWidgetDescription = Objects.requireNonNull(formDescriptionEditorWidgetDescription);
    }

    public FormDescriptionEditorWidgetDescription getFormDescriptionEditorWidgetDescription() {
        return this.formDescriptionEditorWidgetDescription;
    }
}
