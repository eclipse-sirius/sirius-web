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
 * Builder for ConditionalEdgeStyleBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class ConditionalEdgeStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ConditionalEdgeStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.ConditionalEdgeStyle conditionalEdgeStyle = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createConditionalEdgeStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalEdgeStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ConditionalEdgeStyle getConditionalEdgeStyle() {
        return this.conditionalEdgeStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ConditionalEdgeStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.ConditionalEdgeStyle build() {
        return this.getConditionalEdgeStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder condition(java.lang.String value) {
        this.getConditionalEdgeStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for Color.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder color(org.eclipse.sirius.components.view.UserColor value) {
        this.getConditionalEdgeStyle().setColor(value);
        return this;
    }
    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder fontSize(java.lang.Integer value) {
        this.getConditionalEdgeStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder italic(java.lang.Boolean value) {
        this.getConditionalEdgeStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder bold(java.lang.Boolean value) {
        this.getConditionalEdgeStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder underline(java.lang.Boolean value) {
        this.getConditionalEdgeStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getConditionalEdgeStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for LineStyle.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder lineStyle(org.eclipse.sirius.components.view.LineStyle value) {
        this.getConditionalEdgeStyle().setLineStyle(value);
        return this;
    }
    /**
     * Setter for SourceArrowStyle.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder sourceArrowStyle(org.eclipse.sirius.components.view.ArrowStyle value) {
        this.getConditionalEdgeStyle().setSourceArrowStyle(value);
        return this;
    }
    /**
     * Setter for TargetArrowStyle.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder targetArrowStyle(org.eclipse.sirius.components.view.ArrowStyle value) {
        this.getConditionalEdgeStyle().setTargetArrowStyle(value);
        return this;
    }
    /**
     * Setter for EdgeWidth.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder edgeWidth(java.lang.Integer value) {
        this.getConditionalEdgeStyle().setEdgeWidth(value);
        return this;
    }
    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public ConditionalEdgeStyleBuilder showIcon(java.lang.Boolean value) {
        this.getConditionalEdgeStyle().setShowIcon(value);
        return this;
    }

}

