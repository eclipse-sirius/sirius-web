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
 * Builder for ReferenceWidgetClearButtonDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetClearButtonDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription referenceWidgetClearButtonDescription = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetClearButtonDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription getReferenceWidgetClearButtonDescription() {
        return this.referenceWidgetClearButtonDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription build() {
        return this.getReferenceWidgetClearButtonDescription();
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ReferenceWidgetClearButtonDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getReferenceWidgetClearButtonDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetClearButtonDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetClearButtonDescription().getBody().add(value);
        }
        return this;
    }


}
