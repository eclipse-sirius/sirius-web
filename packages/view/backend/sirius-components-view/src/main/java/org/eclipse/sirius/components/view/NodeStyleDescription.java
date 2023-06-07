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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Style Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyleDescription#getLabelColor <em>Label Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyleDescription#getWidthComputationExpression <em>Width
 * Computation Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyleDescription#getHeightComputationExpression <em>Height
 * Computation Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyleDescription#isShowIcon <em>Show Icon</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface NodeStyleDescription extends Style, LabelStyle, BorderStyle {
    /**
     * Returns the value of the '<em><b>Label Color</b></em>' attribute. The default value is <code>"black"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Color</em>' attribute.
     * @model default="black"
     * @generated
     * @see #setLabelColor(UserColor)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription_LabelColor()
     */
    UserColor getLabelColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getLabelColor <em>Label
     * Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Color</em>' reference.
     * @see #getLabelColor()
     * @generated
     */
    void setLabelColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Width Computation Expression</b></em>' attribute. The default value is
     * <code>"1"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Width Computation Expression</em>' attribute.
     * @see #setWidthComputationExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription_WidthComputationExpression()
     * @model default="1" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getWidthComputationExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getWidthComputationExpression <em>Width
     * Computation Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Width Computation Expression</em>' attribute.
     * @see #getWidthComputationExpression()
     * @generated
     */
    void setWidthComputationExpression(String value);

    /**
     * Returns the value of the '<em><b>Height Computation Expression</b></em>' attribute. The default value is
     * <code>"1"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Height Computation Expression</em>' attribute.
     * @see #setHeightComputationExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription_HeightComputationExpression()
     * @model default="1" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getHeightComputationExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getHeightComputationExpression <em>Height
     * Computation Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Height Computation Expression</em>' attribute.
     * @see #getHeightComputationExpression()
     * @generated
     */
    void setHeightComputationExpression(String value);

    /**
     * Returns the value of the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Show Icon</em>' attribute.
     * @see #setShowIcon(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription_ShowIcon()
     * @model
     * @generated
     */
    boolean isShowIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyleDescription#isShowIcon <em>Show
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Show Icon</em>' attribute.
     * @see #isShowIcon()
     * @generated
     */
    void setShowIcon(boolean value);

} // NodeStyleDescription
