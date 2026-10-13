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
 * Builder for ReferenceWidgetSetBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetSetBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetSet.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetSet referenceWidgetSet = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetSet();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetSet.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetSet getReferenceWidgetSet() {
        return this.referenceWidgetSet;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetSet.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetSet build() {
        return this.getReferenceWidgetSet();
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ReferenceWidgetSetBuilder preconditionExpression(java.lang.String value) {
        this.getReferenceWidgetSet().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetSetBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetSet().getBody().add(value);
        }
        return this;
    }


}
