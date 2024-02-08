/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * Builder for org.eclipse.sirius.components.view.diagram.NodeLabelStyle.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class NodeLabelStyleBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.NodeLabelStyle.
     *
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.NodeLabelStyle getNodeLabelStyle();

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public NodeLabelStyleBuilder fontSize(java.lang.Integer value) {
        this.getNodeLabelStyle().setFontSize(value);
        return this;
    }

    /**
     * Setter for Italic.
     *
     * @generated
     */
    public NodeLabelStyleBuilder italic(java.lang.Boolean value) {
        this.getNodeLabelStyle().setItalic(value);
        return this;
    }

    /**
     * Setter for Bold.
     *
     * @generated
     */
    public NodeLabelStyleBuilder bold(java.lang.Boolean value) {
        this.getNodeLabelStyle().setBold(value);
        return this;
    }

    /**
     * Setter for Underline.
     *
     * @generated
     */
    public NodeLabelStyleBuilder underline(java.lang.Boolean value) {
        this.getNodeLabelStyle().setUnderline(value);
        return this;
    }

    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public NodeLabelStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getNodeLabelStyle().setStrikeThrough(value);
        return this;
    }

    /**
     * Setter for LabelColor.
     *
     * @generated
     */
    public NodeLabelStyleBuilder labelColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getNodeLabelStyle().setLabelColor(value);
        return this;
    }

    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public NodeLabelStyleBuilder showIcon(java.lang.Boolean value) {
        this.getNodeLabelStyle().setShowIcon(value);
        return this;
    }

    /**
     * Setter for LabelIcon.
     *
     * @generated
     */
    public NodeLabelStyleBuilder labelIcon(java.lang.String value) {
        this.getNodeLabelStyle().setLabelIcon(value);
        return this;
    }

}

