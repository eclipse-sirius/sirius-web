/*******************************************************************************
* Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for org.eclipse.sirius.components.view.LabelStyle.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public abstract class LabelStyleBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.LabelStyle.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.LabelStyle getLabelStyle();

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public LabelStyleBuilder fontSize(java.lang.Integer value) {
        this.getLabelStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public LabelStyleBuilder italic(java.lang.Boolean value) {
        this.getLabelStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public LabelStyleBuilder bold(java.lang.Boolean value) {
        this.getLabelStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public LabelStyleBuilder underline(java.lang.Boolean value) {
        this.getLabelStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public LabelStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getLabelStyle().setStrikeThrough(value);
        return this;
    }

}

