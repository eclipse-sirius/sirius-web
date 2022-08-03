/**
 * Copyright (c) 2021, 2022 Obeo.
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
 * <li>{@link org.eclipse.sirius.components.view.NodeStyleDescription#getSizeComputationExpression <em>Size Computation
 * Expression</em>}</li>
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
     * @see #setLabelColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription_LabelColor()
     * @model default="black"
     * @generated
     */
    String getLabelColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getLabelColor <em>Label
     * Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Color</em>' attribute.
     * @see #getLabelColor()
     * @generated
     */
    void setLabelColor(String value);

    /**
     * Returns the value of the '<em><b>Size Computation Expression</b></em>' attribute. The default value is
     * <code>"1"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Size Computation Expression</em>' attribute.
     * @see #setSizeComputationExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyleDescription_SizeComputationExpression()
     * @model default="1"
     * @generated
     */
    String getSizeComputationExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.NodeStyleDescription#getSizeComputationExpression <em>Size Computation
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Size Computation Expression</em>' attribute.
     * @see #getSizeComputationExpression()
     * @generated
     */
    void setSizeComputationExpression(String value);

} // NodeStyleDescription
