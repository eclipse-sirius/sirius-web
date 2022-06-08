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

import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorFlexboxContainerDescription;
import org.eclipse.sirius.components.representations.IProps;

/**
 * * The properties of the form description editor flexbox container component.
 *
 * @author arichard
 */
public class FormDescriptionEditorFlexboxContainerComponentProps implements IProps {

    private final FormDescriptionEditorFlexboxContainerDescription fdeFlexboxContainerDescription;

    public FormDescriptionEditorFlexboxContainerComponentProps(FormDescriptionEditorFlexboxContainerDescription fdeFlexboxContainerDescription) {
        this.fdeFlexboxContainerDescription = Objects.requireNonNull(fdeFlexboxContainerDescription);
    }

    public FormDescriptionEditorFlexboxContainerDescription getFormDescriptionEditorFlexboxContainerDescription() {
        return this.fdeFlexboxContainerDescription;
    }
}
