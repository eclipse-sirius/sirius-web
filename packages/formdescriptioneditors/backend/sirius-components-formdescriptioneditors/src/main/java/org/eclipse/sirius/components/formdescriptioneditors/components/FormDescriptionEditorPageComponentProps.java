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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import java.util.Objects;

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * * The properties of the form description editor page component.
 *
 * @author arichard
 */
public record FormDescriptionEditorPageComponentProps(VariableManager variableManager, FormDescriptionEditorDescription formDescriptionEditorDescription) implements IProps {

    public FormDescriptionEditorPageComponentProps(VariableManager variableManager, FormDescriptionEditorDescription formDescriptionEditorDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.formDescriptionEditorDescription = Objects.requireNonNull(formDescriptionEditorDescription);
    }
}
