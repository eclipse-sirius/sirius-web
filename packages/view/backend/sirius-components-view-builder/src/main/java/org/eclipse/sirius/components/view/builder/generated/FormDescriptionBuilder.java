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
 * Builder for FormDescriptionBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class FormDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.FormDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.FormDescription formDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createFormDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.FormDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.FormDescription getFormDescription() {
        return this.formDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.FormDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.FormDescription build() {
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
    public FormDescriptionBuilder pages(org.eclipse.sirius.components.view.PageDescription ... values) {
        for (org.eclipse.sirius.components.view.PageDescription value : values) {
            this.getFormDescription().getPages().add(value);
        }
        return this;
    }


}

