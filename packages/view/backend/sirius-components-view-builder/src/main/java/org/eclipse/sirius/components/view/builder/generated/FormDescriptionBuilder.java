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
 * Builder for FormDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class FormDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.FormDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.FormDescription formDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createFormDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.FormDescription getFormDescription() {
        return this.formDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.FormDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.FormDescription build() {
        return this.getFormDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FormDescriptionBuilder name(java.lang.String value) {
        this.getFormDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public FormDescriptionBuilder domainType(java.lang.String value) {
        this.getFormDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public FormDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getFormDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public FormDescriptionBuilder titleExpression(java.lang.String value) {
        this.getFormDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for Pages.
     *
     * @generated
     */
    public FormDescriptionBuilder pages(org.eclipse.sirius.components.view.form.PageDescription ... values) {
        for (org.eclipse.sirius.components.view.form.PageDescription value : values) {
            this.getFormDescription().getPages().add(value);
        }
        return this;
    }

    /**
     * Setter for FormVariables.
     *
     * @generated
     */
    public FormDescriptionBuilder formVariables(org.eclipse.sirius.components.view.form.FormVariable ... values) {
        for (org.eclipse.sirius.components.view.form.FormVariable value : values) {
            this.getFormDescription().getFormVariables().add(value);
        }
        return this;
    }


}

