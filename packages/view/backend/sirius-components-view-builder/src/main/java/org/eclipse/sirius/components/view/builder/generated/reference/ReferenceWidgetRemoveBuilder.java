/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
 * Builder for ReferenceWidgetRemoveBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetRemoveBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetRemove.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetRemove referenceWidgetRemove = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetRemove();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetRemove.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetRemove getReferenceWidgetRemove() {
        return this.referenceWidgetRemove;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetRemove.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetRemove build() {
        return this.getReferenceWidgetRemove();
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetRemoveBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetRemove().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ReferenceWidgetRemoveBuilder preconditionExpression(java.lang.String value) {
        this.getReferenceWidgetRemove().setPreconditionExpression(value);
        return this;
    }

}

