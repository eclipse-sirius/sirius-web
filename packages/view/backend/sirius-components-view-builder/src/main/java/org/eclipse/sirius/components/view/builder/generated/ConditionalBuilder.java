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
 * Builder for org.eclipse.sirius.components.view.Conditional.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public abstract class ConditionalBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.Conditional.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.Conditional getConditional();

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalBuilder condition(java.lang.String value) {
        this.getConditional().setCondition(value);
        return this;
    }

}

