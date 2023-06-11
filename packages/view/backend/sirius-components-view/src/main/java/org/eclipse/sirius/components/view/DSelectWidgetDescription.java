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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>DSelect Widget Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DSelectWidgetDescription#getOptionsExpression <em>Options
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DSelectWidgetDescription#getOptionLabelExpression <em>Option Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DSelectWidgetDescription#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDSelectWidgetDescription()
 * @model
 * @generated
 */
public interface DSelectWidgetDescription extends DWidgetDescription {
    /**
     * Returns the value of the '<em><b>Options Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Options Expression</em>' attribute.
     * @see #setOptionsExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDSelectWidgetDescription_OptionsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getOptionsExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DSelectWidgetDescription#getOptionsExpression
     * <em>Options Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Options Expression</em>' attribute.
     * @see #getOptionsExpression()
     * @generated
     */
    void setOptionsExpression(String value);

    /**
     * Returns the value of the '<em><b>Option Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Option Label Expression</em>' attribute.
     * @see #setOptionLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDSelectWidgetDescription_OptionLabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getOptionLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.DSelectWidgetDescription#getOptionLabelExpression <em>Option Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Option Label Expression</em>' attribute.
     * @see #getOptionLabelExpression()
     * @generated
     */
    void setOptionLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(SelectDescriptionStyle)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDSelectWidgetDescription_Style()
     * @model containment="true"
     * @generated
     */
    SelectDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DSelectWidgetDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(SelectDescriptionStyle value);

} // DSelectWidgetDescription
