/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Objects;

import org.eclipse.sirius.viewpoint.FontFormat;
import org.eclipse.sirius.viewpoint.description.ColorDescription;
import org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription;

/**
 * Used to populate easily a label style description.
 *
 * @author sbegaudeau
 */
public class BasicLabelStyleDescriptionPopulator {
    private final BasicLabelStyleDescription basicLabelStyleDescription;

    public BasicLabelStyleDescriptionPopulator(BasicLabelStyleDescription basicLabelStyleDescription) {
        this.basicLabelStyleDescription = Objects.requireNonNull(basicLabelStyleDescription);
    }

    public BasicLabelStyleDescriptionPopulator labelExpression(String labelExpression) {
        this.basicLabelStyleDescription.setLabelExpression(labelExpression);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator labelSize(int labelSize) {
        this.basicLabelStyleDescription.setLabelSize(labelSize);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator bold() {
        this.basicLabelStyleDescription.getLabelFormat().add(FontFormat.BOLD_LITERAL);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator italic() {
        this.basicLabelStyleDescription.getLabelFormat().add(FontFormat.ITALIC_LITERAL);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator underline() {
        this.basicLabelStyleDescription.getLabelFormat().add(FontFormat.UNDERLINE_LITERAL);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator strikeThrough() {
        this.basicLabelStyleDescription.getLabelFormat().add(FontFormat.STRIKE_THROUGH_LITERAL);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator labelColor(ColorDescription colorDescription) {
        this.basicLabelStyleDescription.setLabelColor(colorDescription);
        return this;
    }

    public BasicLabelStyleDescriptionPopulator iconPath(String iconPath) {
        this.basicLabelStyleDescription.setIconPath(iconPath);
        return this;
    }
}
