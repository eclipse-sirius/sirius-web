/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetDescription#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetDescription#getHelpExpression <em>Help Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetDescription()
 * @model abstract="true"
 * @generated
 */
public interface WidgetDescription extends FormElementDescription {
    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetDescription_LabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetDescription#getLabelExpression
     * <em>Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Help Expression</em>' attribute.
     * @see #setHelpExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetDescription_HelpExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getHelpExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetDescription#getHelpExpression
     * <em>Help Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Help Expression</em>' attribute.
     * @see #getHelpExpression()
     * @generated
     */
    void setHelpExpression(String value);

    /**
     * Returns the value of the '<em><b>Diagnostics Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Diagnostics Expression</em>' attribute.
     * @see #setDiagnosticsExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetDescription_DiagnosticsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDiagnosticsExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetDescription#getDiagnosticsExpression
     * <em>Diagnostics Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Diagnostics Expression</em>' attribute.
     * @see #getDiagnosticsExpression()
     * @generated
     */
    void setDiagnosticsExpression(String value);

} // WidgetDescription
