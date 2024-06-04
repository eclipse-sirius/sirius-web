/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
 * Builder for EdgeStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EdgeStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.EdgeStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.EdgeStyle edgeStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createEdgeStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.EdgeStyle getEdgeStyle() {
        return this.edgeStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.EdgeStyle build() {
        return this.getEdgeStyle();
    }

    /**
     * Setter for Color.
     *
     * @generated
     */
    public EdgeStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getEdgeStyle().setColor(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public EdgeStyleBuilder fontSize(java.lang.Integer value) {
        this.getEdgeStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public EdgeStyleBuilder italic(java.lang.Boolean value) {
        this.getEdgeStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public EdgeStyleBuilder bold(java.lang.Boolean value) {
        this.getEdgeStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public EdgeStyleBuilder underline(java.lang.Boolean value) {
        this.getEdgeStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public EdgeStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getEdgeStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BorderColor.
     *
     * @generated
     */
    public EdgeStyleBuilder borderColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getEdgeStyle().setBorderColor(value);
        return this;
    }

    /**
     * Setter for BorderRadius.
     *
     * @generated
     */
    public EdgeStyleBuilder borderRadius(java.lang.Integer value) {
        this.getEdgeStyle().setBorderRadius(value);
        return this;
    }

    /**
     * Setter for BorderSize.
     *
     * @generated
     */
    public EdgeStyleBuilder borderSize(java.lang.Integer value) {
        this.getEdgeStyle().setBorderSize(value);
        return this;
    }

    /**
     * Setter for BorderLineStyle.
     *
     * @generated
     */
    public EdgeStyleBuilder borderLineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getEdgeStyle().setBorderLineStyle(value);
        return this;
    }

    /**
     * Setter for LineStyle.
     *
     * @generated
     */
    public EdgeStyleBuilder lineStyle(org.eclipse.sirius.components.view.diagram.LineStyle value) {
        this.getEdgeStyle().setLineStyle(value);
        return this;
    }
    /**
     * Setter for SourceArrowStyle.
     *
     * @generated
     */
    public EdgeStyleBuilder sourceArrowStyle(org.eclipse.sirius.components.view.diagram.ArrowStyle value) {
        this.getEdgeStyle().setSourceArrowStyle(value);
        return this;
    }
    /**
     * Setter for TargetArrowStyle.
     *
     * @generated
     */
    public EdgeStyleBuilder targetArrowStyle(org.eclipse.sirius.components.view.diagram.ArrowStyle value) {
        this.getEdgeStyle().setTargetArrowStyle(value);
        return this;
    }
    /**
     * Setter for EdgeWidth.
     *
     * @generated
     */
    public EdgeStyleBuilder edgeWidth(java.lang.Integer value) {
        this.getEdgeStyle().setEdgeWidth(value);
        return this;
    }
    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public EdgeStyleBuilder showIcon(java.lang.Boolean value) {
        this.getEdgeStyle().setShowIcon(value);
        return this;
    }

    /**
     * Setter for LabelIcon.
     *
     * @generated
     */
    public EdgeStyleBuilder labelIcon(java.lang.String value) {
        this.getEdgeStyle().setLabelIcon(value);
        return this;
    }

    /**
     * Setter for Background.
     *
     * @generated
     */
    public EdgeStyleBuilder background(org.eclipse.sirius.components.view.UserColor value) {
        this.getEdgeStyle().setBackground(value);
        return this;
    }

}

