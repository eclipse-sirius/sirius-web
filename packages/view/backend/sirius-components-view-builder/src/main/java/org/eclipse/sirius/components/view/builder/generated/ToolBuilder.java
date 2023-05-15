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
 * Builder for org.eclipse.sirius.components.view.Tool.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public abstract class ToolBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.Tool.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.Tool getTool();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ToolBuilder name(java.lang.String value) {
        this.getTool().setName(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public ToolBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getTool().getBody().add(value);
        }
        return this;
    }


}

