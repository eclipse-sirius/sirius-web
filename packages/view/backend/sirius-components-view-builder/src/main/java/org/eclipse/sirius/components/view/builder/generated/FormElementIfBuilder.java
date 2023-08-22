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
 * Builder for FormElementIfBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class FormElementIfBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.FormElementIf.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.FormElementIf formElementIf = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createFormElementIf();

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormElementIf.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.FormElementIf getFormElementIf() {
        return this.formElementIf;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormElementIf.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.FormElementIf build() {
        return this.getFormElementIf();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FormElementIfBuilder name(java.lang.String value) {
        this.getFormElementIf().setName(value);
        return this;
    }
    /**
     * Setter for PredicateExpression.
     *
     * @generated
     */
    public FormElementIfBuilder predicateExpression(java.lang.String value) {
        this.getFormElementIf().setPredicateExpression(value);
        return this;
    }
    /**
     * Setter for Children.
     *
     * @generated
     */
    public FormElementIfBuilder children(org.eclipse.sirius.components.view.form.FormElementDescription ... values) {
        for (org.eclipse.sirius.components.view.form.FormElementDescription value : values) {
            this.getFormElementIf().getChildren().add(value);
        }
        return this;
    }


}

