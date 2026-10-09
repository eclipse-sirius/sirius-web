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
 * Builder for ReferenceWidgetAddBodyBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetAddBodyBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAddBody.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAddBody referenceWidgetAddBody = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetAddBody();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAddBody.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAddBody getReferenceWidgetAddBody() {
        return this.referenceWidgetAddBody;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAddBody.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetAddBody build() {
        return this.getReferenceWidgetAddBody();
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetAddBodyBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetAddBody().getBody().add(value);
        }
        return this;
    }


}

