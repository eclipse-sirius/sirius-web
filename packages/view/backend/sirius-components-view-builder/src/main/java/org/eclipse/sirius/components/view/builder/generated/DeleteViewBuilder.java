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
 * Builder for DeleteViewBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeleteViewBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DeleteView.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DeleteView deleteView = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDeleteView();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DeleteView.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DeleteView getDeleteView() {
        return this.deleteView;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DeleteView.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DeleteView build() {
        return this.getDeleteView();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public DeleteViewBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeleteView().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for ViewExpression.
     *
     * @generated
     */
    public DeleteViewBuilder viewExpression(java.lang.String value) {
        this.getDeleteView().setViewExpression(value);
        return this;
    }

}

