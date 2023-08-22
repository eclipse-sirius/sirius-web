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
 * Builder for FormElementForBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class FormElementForBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.FormElementFor.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.FormElementFor formElementFor = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createFormElementFor();

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormElementFor.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.FormElementFor getFormElementFor() {
        return this.formElementFor;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormElementFor.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.FormElementFor build() {
        return this.getFormElementFor();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FormElementForBuilder name(java.lang.String value) {
        this.getFormElementFor().setName(value);
        return this;
    }
    /**
     * Setter for Iterator.
     *
     * @generated
     */
    public FormElementForBuilder iterator(java.lang.String value) {
        this.getFormElementFor().setIterator(value);
        return this;
    }
    /**
     * Setter for IterableExpression.
     *
     * @generated
     */
    public FormElementForBuilder iterableExpression(java.lang.String value) {
        this.getFormElementFor().setIterableExpression(value);
        return this;
    }
    /**
     * Setter for Children.
     *
     * @generated
     */
    public FormElementForBuilder children(org.eclipse.sirius.components.view.form.FormElementDescription ... values) {
        for (org.eclipse.sirius.components.view.form.FormElementDescription value : values) {
            this.getFormElementFor().getChildren().add(value);
        }
        return this;
    }


}

