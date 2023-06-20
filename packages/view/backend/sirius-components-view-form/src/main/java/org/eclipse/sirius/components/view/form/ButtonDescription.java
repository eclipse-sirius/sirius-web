/**
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Button Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.ButtonDescription#getButtonLabelExpression <em>Button Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ButtonDescription#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ButtonDescription#getImageExpression <em>Image
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ButtonDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ButtonDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getButtonDescription()
 * @model
 * @generated
 */
public interface ButtonDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Button Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Button Label Expression</em>' attribute.
     * @see #setButtonLabelExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getButtonDescription_ButtonLabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getButtonLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getButtonLabelExpression
     * <em>Button Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Button Label Expression</em>' attribute.
     * @see #getButtonLabelExpression()
     * @generated
     */
    void setButtonLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getButtonDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Image Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Image Expression</em>' attribute.
     * @see #setImageExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getButtonDescription_ImageExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getImageExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getImageExpression
     * <em>Image Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Image Expression</em>' attribute.
     * @see #getImageExpression()
     * @generated
     */
    void setImageExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(ButtonDescriptionStyle)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getButtonDescription_Style()
     * @model containment="true"
     * @generated
     */
    ButtonDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getStyle <em>Style</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(ButtonDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getButtonDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalButtonDescriptionStyle> getConditionalStyles();

} // ButtonDescription
