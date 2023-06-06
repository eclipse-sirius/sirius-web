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
 * Builder for PageDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class PageDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.PageDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.PageDescription pageDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createPageDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.PageDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.PageDescription getPageDescription() {
        return this.pageDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.PageDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.PageDescription build() {
        return this.getPageDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public PageDescriptionBuilder name(java.lang.String value) {
        this.getPageDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public PageDescriptionBuilder labelExpression(java.lang.String value) {
        this.getPageDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public PageDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getPageDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public PageDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getPageDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for Groups.
     *
     * @generated
     */
    public PageDescriptionBuilder groups(org.eclipse.sirius.components.view.GroupDescription... values) {
        for (org.eclipse.sirius.components.view.GroupDescription value : values) {
            this.getPageDescription().getGroups().add(value);
        }
        return this;
    }

    /**
     * Setter for ToolbarActions.
     *
     * @generated
     */
    public PageDescriptionBuilder toolbarActions(org.eclipse.sirius.components.view.ButtonDescription... values) {
        for (org.eclipse.sirius.components.view.ButtonDescription value : values) {
            this.getPageDescription().getToolbarActions().add(value);
        }
        return this;
    }


}

