/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.reference;

/**
 * Builder for ReferenceWidgetCreateButtonDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetCreateButtonDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetCreateButtonDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetCreateButtonDescription referenceWidgetCreateButtonDescription = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetCreateButtonDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetCreateButtonDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetCreateButtonDescription getReferenceWidgetCreateButtonDescription() {
        return this.referenceWidgetCreateButtonDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetCreateButtonDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetCreateButtonDescription build() {
        return this.getReferenceWidgetCreateButtonDescription();
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ReferenceWidgetCreateButtonDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getReferenceWidgetCreateButtonDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetCreateButtonDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetCreateButtonDescription().getBody().add(value);
        }
        return this;
    }


}

