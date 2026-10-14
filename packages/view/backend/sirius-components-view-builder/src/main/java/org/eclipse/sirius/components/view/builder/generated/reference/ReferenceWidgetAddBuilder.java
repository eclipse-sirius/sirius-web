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
 * Builder for ReferenceWidgetAddBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetAddBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAdd.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAdd referenceWidgetAdd = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetAdd();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAdd.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAdd getReferenceWidgetAdd() {
        return this.referenceWidgetAdd;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAdd.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAdd build() {
        return this.getReferenceWidgetAdd();
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ReferenceWidgetAddBuilder preconditionExpression(java.lang.String value) {
        this.getReferenceWidgetAdd().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetAddBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetAdd().getBody().add(value);
        }
        return this;
    }


}

