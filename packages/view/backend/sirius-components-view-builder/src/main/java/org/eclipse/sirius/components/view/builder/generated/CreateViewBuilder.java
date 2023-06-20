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
 * Builder for CreateViewBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CreateViewBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.CreateView.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.CreateView createView = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createCreateView();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.CreateView.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.CreateView getCreateView() {
        return this.createView;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.CreateView.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.CreateView build() {
        return this.getCreateView();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public CreateViewBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCreateView().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for ParentViewExpression.
     *
     * @generated
     */
    public CreateViewBuilder parentViewExpression(java.lang.String value) {
        this.getCreateView().setParentViewExpression(value);
        return this;
    }
    /**
     * Setter for ElementDescription.
     *
     * @generated
     */
    public CreateViewBuilder elementDescription(org.eclipse.sirius.components.view.diagram.DiagramElementDescription value) {
        this.getCreateView().setElementDescription(value);
        return this;
    }
    /**
     * Setter for SemanticElementExpression.
     *
     * @generated
     */
    public CreateViewBuilder semanticElementExpression(java.lang.String value) {
        this.getCreateView().setSemanticElementExpression(value);
        return this;
    }
    /**
     * Setter for VariableName.
     *
     * @generated
     */
    public CreateViewBuilder variableName(java.lang.String value) {
        this.getCreateView().setVariableName(value);
        return this;
    }
    /**
     * Setter for ContainmentKind.
     *
     * @generated
     */
    public CreateViewBuilder containmentKind(org.eclipse.sirius.components.view.diagram.NodeContainmentKind value) {
        this.getCreateView().setContainmentKind(value);
        return this;
    }

}

