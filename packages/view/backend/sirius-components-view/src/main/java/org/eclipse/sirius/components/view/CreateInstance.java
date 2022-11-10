/**
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Create Instance</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.CreateInstance#getTypeName <em>Type Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.CreateInstance#getReferenceName <em>Reference Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.CreateInstance#getVariableName <em>Variable Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getCreateInstance()
 * @model
 * @generated
 */
public interface CreateInstance extends Operation {
    /**
     * Returns the value of the '<em><b>Type Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Type Name</em>' attribute.
     * @see #setTypeName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateInstance_TypeName()
     * @model dataType="org.eclipse.sirius.components.view.DomainType" required="true"
     * @generated
     */
    String getTypeName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateInstance#getTypeName <em>Type Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Type Name</em>' attribute.
     * @see #getTypeName()
     * @generated
     */
    void setTypeName(String value);

    /**
     * Returns the value of the '<em><b>Reference Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Reference Name</em>' attribute.
     * @see #setReferenceName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateInstance_ReferenceName()
     * @model required="true"
     * @generated
     */
    String getReferenceName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateInstance#getReferenceName <em>Reference
     * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Reference Name</em>' attribute.
     * @see #getReferenceName()
     * @generated
     */
    void setReferenceName(String value);

    /**
     * Returns the value of the '<em><b>Variable Name</b></em>' attribute. The default value is
     * <code>"newInstance"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Variable Name</em>' attribute.
     * @see #setVariableName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateInstance_VariableName()
     * @model default="newInstance" required="true"
     * @generated
     */
    String getVariableName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateInstance#getVariableName <em>Variable
     * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Variable Name</em>' attribute.
     * @see #getVariableName()
     * @generated
     */
    void setVariableName(String value);

} // CreateInstance
