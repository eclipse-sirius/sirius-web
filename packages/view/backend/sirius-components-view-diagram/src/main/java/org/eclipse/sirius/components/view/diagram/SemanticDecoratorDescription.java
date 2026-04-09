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
package org.eclipse.sirius.components.view.diagram;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Semantic Decorator Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription#getDomainType <em>Domain
 * Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSemanticDecoratorDescription()
 * @model
 * @generated
 */
public interface SemanticDecoratorDescription extends DecoratorDescription {
    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSemanticDecoratorDescription_DomainType()
     * @model dataType="org.eclipse.sirius.components.view.DomainType"
     * @generated
     */
    String getDomainType();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription#getDomainType <em>Domain
     * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Type</em>' attribute.
     * @see #getDomainType()
     * @generated
     */
    void setDomainType(String value);

} // SemanticDecoratorDescription
