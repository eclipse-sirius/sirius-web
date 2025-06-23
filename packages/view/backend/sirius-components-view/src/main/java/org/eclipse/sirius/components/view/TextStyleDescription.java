/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Text Style Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.TextStyleDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.TextStyleDescription#getForegroundColorExpression <em>Foreground Color
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.TextStyleDescription#getBackgroundColorExpression <em>Background Color
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.TextStyleDescription#getIsBoldExpression <em>Is Bold
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.TextStyleDescription#getIsItalicExpression <em>Is Italic
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.TextStyleDescription#getIsUnderlineExpression <em>Is Underline
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription()
 * @model
 * @generated
 */
public interface TextStyleDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription_Name()
     * @model dataType="org.eclipse.sirius.components.view.Identifier" required="true"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.TextStyleDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Foreground Color Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Foreground Color Expression</em>' attribute.
     * @see #setForegroundColorExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription_ForegroundColorExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getForegroundColorExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.TextStyleDescription#getForegroundColorExpression <em>Foreground Color
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Foreground Color Expression</em>' attribute.
     * @see #getForegroundColorExpression()
     * @generated
     */
    void setForegroundColorExpression(String value);

    /**
     * Returns the value of the '<em><b>Background Color Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background Color Expression</em>' attribute.
     * @see #setBackgroundColorExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription_BackgroundColorExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getBackgroundColorExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.TextStyleDescription#getBackgroundColorExpression <em>Background Color
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color Expression</em>' attribute.
     * @see #getBackgroundColorExpression()
     * @generated
     */
    void setBackgroundColorExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Bold Expression</b></em>' attribute. The default value is
     * <code>"aql:false"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Is Bold Expression</em>' attribute.
     * @see #setIsBoldExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription_IsBoldExpression()
     * @model default="aql:false" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsBoldExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.TextStyleDescription#getIsBoldExpression <em>Is
     * Bold Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Bold Expression</em>' attribute.
     * @see #getIsBoldExpression()
     * @generated
     */
    void setIsBoldExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Italic Expression</b></em>' attribute. The default value is
     * <code>"aql:false"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Is Italic Expression</em>' attribute.
     * @see #setIsItalicExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription_IsItalicExpression()
     * @model default="aql:false" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsItalicExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.TextStyleDescription#getIsItalicExpression
     * <em>Is Italic Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Italic Expression</em>' attribute.
     * @see #getIsItalicExpression()
     * @generated
     */
    void setIsItalicExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Underline Expression</b></em>' attribute. The default value is
     * <code>"aql:false"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Is Underline Expression</em>' attribute.
     * @see #setIsUnderlineExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextStyleDescription_IsUnderlineExpression()
     * @model default="aql:false" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsUnderlineExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.TextStyleDescription#getIsUnderlineExpression
     * <em>Is Underline Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Underline Expression</em>' attribute.
     * @see #getIsUnderlineExpression()
     * @generated
     */
    void setIsUnderlineExpression(String value);

} // TextStyleDescription
