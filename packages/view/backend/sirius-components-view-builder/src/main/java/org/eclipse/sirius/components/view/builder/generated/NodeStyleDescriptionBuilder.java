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
 * Builder for org.eclipse.sirius.components.view.diagram.NodeStyleDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class NodeStyleDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.NodeStyleDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.NodeStyleDescription getNodeStyleDescription();

    /**
     * Setter for Color.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getNodeStyleDescription().setColor(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder fontSize(java.lang.Integer value) {
        this.getNodeStyleDescription().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder italic(java.lang.Boolean value) {
        this.getNodeStyleDescription().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder bold(java.lang.Boolean value) {
        this.getNodeStyleDescription().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder underline(java.lang.Boolean value) {
        this.getNodeStyleDescription().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder strikeThrough(java.lang.Boolean value) {
        this.getNodeStyleDescription().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getNodeStyleDescription().setBorderColor(value);
        return this;
    }
    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder borderRadius(java.lang.Integer value) {
        this.getNodeStyleDescription().setBorderRadius(value);
        return this;
    }
    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder borderSize(java.lang.Integer value) {
        this.getNodeStyleDescription().setBorderSize(value);
        return this;
    }
    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getNodeStyleDescription().setBorderLineStyle(value);
        return this;
    }
    /**
     * Setter for LabelColor.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder labelColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getNodeStyleDescription().setLabelColor(value);
        return this;
    }
    /**
     * Setter for WidthComputationExpression.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder widthComputationExpression(java.lang.String value) {
        this.getNodeStyleDescription().setWidthComputationExpression(value);
        return this;
    }
    /**
     * Setter for HeightComputationExpression.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder heightComputationExpression(java.lang.String value) {
        this.getNodeStyleDescription().setHeightComputationExpression(value);
        return this;
    }
    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder showIcon(java.lang.Boolean value) {
        this.getNodeStyleDescription().setShowIcon(value);
        return this;
    }

    /**
     * Setter for LabelIcon.
     *
     * @generated
     */
    public NodeStyleDescriptionBuilder labelIcon(java.lang.String value) {
        this.getNodeStyleDescription().setLabelIcon(value);
        return this;
    }

}

