/**
 * Copyright (c) 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.web.domain;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Attribute</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.Attribute#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.domain.DomainPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends Feature {
    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.web.domain.DataType}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Type</em>' attribute.
     * @see org.eclipse.sirius.web.domain.DataType
     * @see #setType(DataType)
     * @see org.eclipse.sirius.web.domain.DomainPackage#getAttribute_Type()
     * @model
     * @generated
     */
    DataType getType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.domain.Attribute#getType <em>Type</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Type</em>' attribute.
     * @see org.eclipse.sirius.web.domain.DataType
     * @see #getType()
     * @generated
     */
    void setType(DataType value);

} // Attribute
