/**
 * Copyright (c) 2021, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget Flexbox Layout</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getFlexDirection <em>Flex Direction</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getGap <em>Gap</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getLabelFlex <em>Label Flex</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getValueFlex <em>Value Flex</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetFlexboxLayout()
 * @model abstract="true"
 * @generated
 */
public interface WidgetFlexboxLayout extends EObject {
    /**
     * Returns the value of the '<em><b>Flex Direction</b></em>' attribute. The default value is <code>"column"</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Flex Direction</em>' attribute.
     * @see #setFlexDirection(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetFlexboxLayout_FlexDirection()
     * @model default="column" required="true"
     * @generated
     */
    String getFlexDirection();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getFlexDirection
     * <em>Flex Direction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Flex Direction</em>' attribute.
     * @see #getFlexDirection()
     * @generated
     */
    void setFlexDirection(String value);

    /**
     * Returns the value of the '<em><b>Gap</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Gap</em>' attribute.
     * @see #setGap(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetFlexboxLayout_Gap()
     * @model default="" required="true"
     * @generated
     */
    String getGap();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getGap <em>Gap</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Gap</em>' attribute.
     * @see #getGap()
     * @generated
     */
    void setGap(String value);

    /**
     * Returns the value of the '<em><b>Label Flex</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Flex</em>' attribute.
     * @see #setLabelFlex(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetFlexboxLayout_LabelFlex()
     * @model default="" required="true"
     * @generated
     */
    String getLabelFlex();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getLabelFlex <em>Label
     * Flex</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Flex</em>' attribute.
     * @see #getLabelFlex()
     * @generated
     */
    void setLabelFlex(String value);

    /**
     * Returns the value of the '<em><b>Value Flex</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Value Flex</em>' attribute.
     * @see #setValueFlex(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetFlexboxLayout_ValueFlex()
     * @model default="" required="true"
     * @generated
     */
    String getValueFlex();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetFlexboxLayout#getValueFlex <em>Value
     * Flex</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Flex</em>' attribute.
     * @see #getValueFlex()
     * @generated
     */
    void setValueFlex(String value);

} // WidgetFlexboxLayout
