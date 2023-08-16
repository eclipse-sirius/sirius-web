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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Flexbox Container Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexDirection <em>Flex
 * Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getIsEnabledExpression <em>Is Enabled
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getBorderStyle <em>Border
 * Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getConditionalBorderStyles
 * <em>Conditional Border Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexboxJustifyContent <em>Flexbox
 * Justify Content</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexboxAlignItems <em>Flexbox Align
 * Items</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getMargin <em>Margin</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getPadding <em>Padding</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getGap <em>Gap</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription()
 * @model
 * @generated
 */
public interface FlexboxContainerDescription extends WidgetDescription {

    /**
     * Returns the value of the '<em><b>Children</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.form.FormElementDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Children</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_Children()
     * @model containment="true"
     * @generated
     */
    EList<FormElementDescription> getChildren();

    /**
     * Returns the value of the '<em><b>Flex Direction</b></em>' attribute. The default value is <code>"row"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.form.FlexDirection}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Flex Direction</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.FlexDirection
     * @see #setFlexDirection(FlexDirection)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_FlexDirection()
     * @model default="row" required="true"
     * @generated
     */
    FlexDirection getFlexDirection();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexDirection <em>Flex
     * Direction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Flex Direction</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.FlexDirection
     * @see #getFlexDirection()
     * @generated
     */
    void setFlexDirection(FlexDirection value);

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #getIsEnabledExpression()
     * @generated
     */
    void setIsEnabledExpression(String value);

    /**
     * Returns the value of the '<em><b>Border Style</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Border Style</em>' containment reference.
     * @see #setBorderStyle(ContainerBorderStyle)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_BorderStyle()
     * @model containment="true"
     * @generated
     */
    ContainerBorderStyle getBorderStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getBorderStyle
     * <em>Border Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Style</em>' containment reference.
     * @see #getBorderStyle()
     * @generated
     */
    void setBorderStyle(ContainerBorderStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Border Styles</b></em>' containment reference list. The list
     * contents are of type {@link org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Border Styles</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_ConditionalBorderStyles()
     */
    EList<ConditionalContainerBorderStyle> getConditionalBorderStyles();

    /**
     * Returns the value of the '<em><b>Flexbox Justify Content</b></em>' attribute. The literals are from the
     * enumeration {@link org.eclipse.sirius.components.view.form.JustifyContent}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Flexbox Justify Content</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.JustifyContent
     * @see #setFlexboxJustifyContent(JustifyContent)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_FlexboxJustifyContent()
     * @model
     * @generated
     */
    JustifyContent getFlexboxJustifyContent();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexboxJustifyContent <em>Flexbox
     * Justify Content</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Flexbox Justify Content</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.JustifyContent
     * @see #getFlexboxJustifyContent()
     * @generated
     */
    void setFlexboxJustifyContent(JustifyContent value);

    /**
     * Returns the value of the '<em><b>Flexbox Align Items</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.form.AlignItems}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Flexbox Align Items</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.AlignItems
     * @see #setFlexboxAlignItems(AlignItems)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_FlexboxAlignItems()
     * @model
     * @generated
     */
    AlignItems getFlexboxAlignItems();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexboxAlignItems <em>Flexbox
     * Align Items</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Flexbox Align Items</em>' attribute.
     * @see org.eclipse.sirius.components.view.form.AlignItems
     * @see #getFlexboxAlignItems()
     * @generated
     */
    void setFlexboxAlignItems(AlignItems value);

    /**
     * Returns the value of the '<em><b>Margin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Margin</em>' attribute.
     * @see #setMargin(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_Margin()
     * @model
     * @generated
     */
    String getMargin();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getMargin
     * <em>Margin</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Margin</em>' attribute.
     * @see #getMargin()
     * @generated
     */
    void setMargin(String value);

    /**
     * Returns the value of the '<em><b>Padding</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Padding</em>' attribute.
     * @see #setPadding(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_Padding()
     * @model
     * @generated
     */
    String getPadding();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getPadding
     * <em>Padding</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Padding</em>' attribute.
     * @see #getPadding()
     * @generated
     */
    void setPadding(String value);

    /**
     * Returns the value of the '<em><b>Gap</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Gap</em>' attribute.
     * @see #setGap(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexboxContainerDescription_Gap()
     * @model
     * @generated
     */
    String getGap();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getGap
     * <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Gap</em>' attribute.
     * @see #getGap()
     * @generated
     */
    void setGap(String value);

} // FlexboxContainerDescription
