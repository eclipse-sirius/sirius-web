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
 * Builder for org.eclipse.sirius.components.view.gantt.TaskStyleDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class TaskStyleDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.gantt.TaskStyleDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.gantt.TaskStyleDescription getTaskStyleDescription();

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder fontSize(java.lang.Integer value) {
        this.getTaskStyleDescription().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder italic(java.lang.Boolean value) {
        this.getTaskStyleDescription().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder bold(java.lang.Boolean value) {
        this.getTaskStyleDescription().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder underline(java.lang.Boolean value) {
        this.getTaskStyleDescription().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder strikeThrough(java.lang.Boolean value) {
        this.getTaskStyleDescription().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for LabelColorExpression.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder labelColorExpression(java.lang.String value) {
        this.getTaskStyleDescription().setLabelColorExpression(value);
        return this;
    }
    /**
     * Setter for BackgroundColorExpression.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder backgroundColorExpression(java.lang.String value) {
        this.getTaskStyleDescription().setBackgroundColorExpression(value);
        return this;
    }
    /**
     * Setter for ProgressColorExpression.
     *
     * @generated
     */
    public TaskStyleDescriptionBuilder progressColorExpression(java.lang.String value) {
        this.getTaskStyleDescription().setProgressColorExpression(value);
        return this;
    }

}

