/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.table;

/**
 * Builder for org.eclipse.sirius.components.view.table.TableElementDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class TableElementDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.table.TableElementDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.table.TableElementDescription getTableElementDescription();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TableElementDescriptionBuilder name(java.lang.String value) {
        this.getTableElementDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public TableElementDescriptionBuilder domainType(java.lang.String value) {
        this.getTableElementDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public TableElementDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getTableElementDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public TableElementDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getTableElementDescription().setPreconditionExpression(value);
        return this;
    }

}

