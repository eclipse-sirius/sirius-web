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
 * Builder for RichTextDescriptionBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class RichTextDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.RichTextDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.RichTextDescription richTextDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createRichTextDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.RichTextDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.RichTextDescription getRichTextDescription() {
        return this.richTextDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.RichTextDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.RichTextDescription build() {
        return this.getRichTextDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public RichTextDescriptionBuilder name(java.lang.String value) {
        this.getRichTextDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public RichTextDescriptionBuilder labelExpression(java.lang.String value) {
        this.getRichTextDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public RichTextDescriptionBuilder valueExpression(java.lang.String value) {
        this.getRichTextDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public RichTextDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getRichTextDescription().getBody().add(value);
        }
        return this;
    }


}

