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
package org.eclipse.sirius.components.forms.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.description.RichTextDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the rich text component.
 *
 * @author pcdavid
 */
public class RichTextComponentProps implements IProps {
    private final VariableManager variableManager;

    private final RichTextDescription richTextDescription;

    public RichTextComponentProps(VariableManager variableManager, RichTextDescription richTextDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.richTextDescription = Objects.requireNonNull(richTextDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public RichTextDescription getRichTextDescription() {
        return this.richTextDescription;
    }
}
