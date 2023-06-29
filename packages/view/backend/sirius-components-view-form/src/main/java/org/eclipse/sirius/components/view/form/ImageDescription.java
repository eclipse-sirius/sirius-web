/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.form;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Image Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.ImageDescription#getUrlExpression <em>Url Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ImageDescription#getMaxWidthExpression <em>Max Width
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getImageDescription()
 * @model
 * @generated
 */
public interface ImageDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Url Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Url Expression</em>' attribute.
     * @see #setUrlExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getImageDescription_UrlExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getUrlExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ImageDescription#getUrlExpression <em>Url
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Url Expression</em>' attribute.
     * @see #getUrlExpression()
     * @generated
     */
    void setUrlExpression(String value);

    /**
     * Returns the value of the '<em><b>Max Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Max Width Expression</em>' attribute.
     * @see #setMaxWidthExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getImageDescription_MaxWidthExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getMaxWidthExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ImageDescription#getMaxWidthExpression
     * <em>Max Width Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Max Width Expression</em>' attribute.
     * @see #getMaxWidthExpression()
     * @generated
     */
    void setMaxWidthExpression(String value);

} // ImageDescription
