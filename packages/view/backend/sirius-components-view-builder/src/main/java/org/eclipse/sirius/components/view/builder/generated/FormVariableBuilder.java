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
 * Builder for FormVariableBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class FormVariableBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.FormVariable.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.FormVariable formVariable = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createFormVariable();

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormVariable.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.FormVariable getFormVariable() {
        return this.formVariable;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormVariable.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.FormVariable build() {
        return this.getFormVariable();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FormVariableBuilder name(java.lang.String value) {
        this.getFormVariable().setName(value);
        return this;
    }
    /**
     * Setter for DefaultValueExpression.
     *
     * @generated
     */
    public FormVariableBuilder defaultValueExpression(java.lang.String value) {
        this.getFormVariable().setDefaultValueExpression(value);
        return this;
    }

}

