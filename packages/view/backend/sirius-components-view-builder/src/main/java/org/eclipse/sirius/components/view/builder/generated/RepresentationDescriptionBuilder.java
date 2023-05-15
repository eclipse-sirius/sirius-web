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
 * Builder for org.eclipse.sirius.components.view.RepresentationDescription.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public abstract class RepresentationDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.RepresentationDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.RepresentationDescription getRepresentationDescription();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public RepresentationDescriptionBuilder name(java.lang.String value) {
        this.getRepresentationDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public RepresentationDescriptionBuilder domainType(java.lang.String value) {
        this.getRepresentationDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public RepresentationDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getRepresentationDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public RepresentationDescriptionBuilder titleExpression(java.lang.String value) {
        this.getRepresentationDescription().setTitleExpression(value);
        return this;
    }

}

