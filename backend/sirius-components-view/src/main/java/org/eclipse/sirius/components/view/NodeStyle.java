/**
 * Copyright (c) 2021 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyle#isListMode <em>List Mode</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyle#getShape <em>Shape</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyle#getLabelColor <em>Label Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodeStyle#getSizeComputationExpression <em>Size Computation
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyle()
 * @model
 * @generated
 */
public interface NodeStyle extends Style, LabelStyle, BorderStyle {

    /**
     * Returns the value of the '<em><b>List Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>List Mode</em>' attribute.
     * @see #setListMode(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyle_ListMode()
     * @model required="true"
     * @generated
     */
    boolean isListMode();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyle#isListMode <em>List Mode</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>List Mode</em>' attribute.
     * @see #isListMode()
     * @generated
     */
    void setListMode(boolean value);

    /**
     * Returns the value of the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Shape</em>' attribute.
     * @see #setShape(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyle_Shape()
     * @model
     * @generated
     */
    String getShape();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyle#getShape <em>Shape</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Shape</em>' attribute.
     * @see #getShape()
     * @generated
     */
    void setShape(String value);

    /**
     * Returns the value of the '<em><b>Label Color</b></em>' attribute. The default value is <code>"black"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Color</em>' attribute.
     * @see #setLabelColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyle_LabelColor()
     * @model default="black"
     * @generated
     */
    String getLabelColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyle#getLabelColor <em>Label Color</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodeStyle_SizeComputationExpression()
     * @model default="1"
     * @generated
     */
    String getSizeComputationExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodeStyle#getSizeComputationExpression <em>Size
     * Computation Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Size Computation Expression</em>' attribute.
     * @see #getSizeComputationExpression()
     * @generated
     */
    void setSizeComputationExpression(String value);
} // NodeStyle
