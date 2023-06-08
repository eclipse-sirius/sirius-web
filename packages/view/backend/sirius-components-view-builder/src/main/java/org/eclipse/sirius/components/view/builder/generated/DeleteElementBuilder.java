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
 * Builder for DeleteElementBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeleteElementBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.DeleteElement.
     * @generated
     */
    private org.eclipse.sirius.components.view.DeleteElement deleteElement = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createDeleteElement();

    /**
     * Return instance org.eclipse.sirius.components.view.DeleteElement.
     * @generated
     */
    protected org.eclipse.sirius.components.view.DeleteElement getDeleteElement() {
        return this.deleteElement;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.DeleteElement.
     * @generated
     */
    public org.eclipse.sirius.components.view.DeleteElement build() {
        return this.getDeleteElement();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public DeleteElementBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getDeleteElement().getChildren().add(value);
        }
        return this;
    }


}

