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
import java.util.Optional;

import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorWidget;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorTextfieldDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the form description editor textfield component.
 *
 * @author arichard
 */
public class FormDescriptionEditorTextfieldComponentProps implements IProps {
    private final VariableManager variableManager;

    private final FormDescriptionEditorTextfieldDescription textfieldDescription;

    private Optional<FormDescriptionEditorWidget> optionalPreviousTextfield;

    public FormDescriptionEditorTextfieldComponentProps(VariableManager variableManager, FormDescriptionEditorTextfieldDescription textfieldDescription,
            Optional<FormDescriptionEditorWidget> optionalPreviousTextfield) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.textfieldDescription = Objects.requireNonNull(textfieldDescription);
        this.optionalPreviousTextfield = Objects.requireNonNull(optionalPreviousTextfield);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public FormDescriptionEditorTextfieldDescription getTextfieldDescription() {
        return this.textfieldDescription;
    }

    public Optional<FormDescriptionEditorWidget> getOptionalPreviousTextfield() {
        return this.optionalPreviousTextfield;
    }
}
