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
package org.eclipse.sirius.components.task;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Person</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.task.Person#getAlias <em>Alias</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Person#getBiography <em>Biography</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Person#getImageUrl <em>Image Url</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.task.TaskPackage#getPerson()
 * @model
 * @generated
 */
public interface Person extends Resource {
    /**
     * Returns the value of the '<em><b>Alias</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Alias</em>' attribute.
     * @see #setAlias(String)
     * @see org.eclipse.sirius.components.task.TaskPackage#getPerson_Alias()
     * @model
     * @generated
     */
    String getAlias();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.Person#getAlias <em>Alias</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Alias</em>' attribute.
     * @see #getAlias()
     * @generated
     */
    void setAlias(String value);

    /**
     * Returns the value of the '<em><b>Biography</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Biography</em>' attribute.
     * @see #setBiography(String)
     * @see org.eclipse.sirius.components.task.TaskPackage#getPerson_Biography()
     * @model
     * @generated
     */
    String getBiography();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.Person#getBiography <em>Biography</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Biography</em>' attribute.
     * @see #getBiography()
     * @generated
     */
    void setBiography(String value);

    /**
     * Returns the value of the '<em><b>Image Url</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Image Url</em>' attribute.
     * @see #setImageUrl(String)
     * @see org.eclipse.sirius.components.task.TaskPackage#getPerson_ImageUrl()
     * @model
     * @generated
     */
    String getImageUrl();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.Person#getImageUrl <em>Image Url</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Image Url</em>' attribute.
     * @see #getImageUrl()
     * @generated
     */
    void setImageUrl(String value);

} // Person
