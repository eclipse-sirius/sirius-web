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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Date Time Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getStringValueExpression <em>String Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getIsEnabledExpression <em>Is Enabled
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription()
 * @model
 * @generated
 */
public interface DateTimeDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>String Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>String Value Expression</em>' attribute.
     * @see #setStringValueExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription_StringValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getStringValueExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getStringValueExpression <em>String Value
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>String Value Expression</em>' attribute.
     * @see #getStringValueExpression()
     * @generated
     */
    void setStringValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #getIsEnabledExpression()
     * @generated
     */
    void setIsEnabledExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(DateTimeDescriptionStyle)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription_Style()
     * @model containment="true"
     * @generated
     */
    DateTimeDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(DateTimeDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalDateTimeDescriptionStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute. The default value is <code>"DATE_TIME"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.form.DateTimeType}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Type</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.DateTimeType
     * @see #setType(DateTimeType)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescription_Type()
     * @model default="DATE_TIME" required="true"
     * @generated
     */
    DateTimeType getType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescription#getType <em>Type</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Type</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.DateTimeType
     * @see #getType()
     * @generated
     */
    void setType(DateTimeType value);

} // DateTimeDescription
