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
 * Builder for org.eclipse.sirius.components.view.Operation.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public abstract class OperationBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.Operation.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.Operation getOperation();

    /**
     * Setter for Children.
     *
     * @generated
     */
    public OperationBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getOperation().getChildren().add(value);
        }
        return this;
    }


}

