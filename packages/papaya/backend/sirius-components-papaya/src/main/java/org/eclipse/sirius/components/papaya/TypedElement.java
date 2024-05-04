/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Typed Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.TypedElement#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTypedElement()
 * @model abstract="true"
 * @generated
 */
public interface TypedElement extends NamedElement, AnnotableElement {
    /**
     * Returns the value of the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Type</em>' containment reference.
     * @see #setType(GenericType)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTypedElement_Type()
     * @model containment="true" required="true"
     * @generated
     */
    GenericType getType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.TypedElement#getType <em>Type</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Type</em>' containment reference.
     * @see #getType()
     * @generated
     */
    void setType(GenericType value);

} // TypedElement
