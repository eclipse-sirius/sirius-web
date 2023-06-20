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
 * Builder for LabelEditToolBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LabelEditToolBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.LabelEditTool.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.LabelEditTool labelEditTool = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createLabelEditTool();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.LabelEditTool.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.LabelEditTool getLabelEditTool() {
        return this.labelEditTool;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.LabelEditTool.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.LabelEditTool build() {
        return this.getLabelEditTool();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public LabelEditToolBuilder name(java.lang.String value) {
        this.getLabelEditTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public LabelEditToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getLabelEditTool().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for InitialDirectEditLabelExpression.
     *
     * @generated
     */
    public LabelEditToolBuilder initialDirectEditLabelExpression(java.lang.String value) {
        this.getLabelEditTool().setInitialDirectEditLabelExpression(value);
        return this;
    }

}

