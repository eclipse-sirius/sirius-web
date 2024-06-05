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
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Label Style</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelColor <em>Label Color</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle()
 */
public interface NodeLabelStyle extends LabelStyle, BorderStyle {

    /**
     * Returns the value of the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Color</em>' reference.
     * @model required="true"
     * @generated
     * @see #setLabelColor(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_LabelColor()
     */
    UserColor getLabelColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelColor <em>Label
     * Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Color</em>' reference.
     * @generated
     * @see #getLabelColor()
     */
    void setLabelColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Icon</em>' attribute.
     * @model
     * @generated
     * @see #setLabelIcon(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_LabelIcon()
     */
    String getLabelIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelIcon <em>Label
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Icon</em>' attribute.
     * @generated
     * @see #getLabelIcon()
     */
    void setLabelIcon(String value);

    /**
     * Returns the value of the '<em><b>Max Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Max Width Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setMaxWidthExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_MaxWidthExpression()
     */
    String getMaxWidthExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getMaxWidthExpression
     * <em>Max Width Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Max Width Expression</em>' attribute.
     * @generated
     * @see #getMaxWidthExpression()
     */
    void setMaxWidthExpression(String value);

    /**
     * Returns the value of the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background</em>' reference.
     * @model
     * @generated
     * @see #setBackground(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_Background()
     */
    UserColor getBackground();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getBackground
     * <em>Background</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Background</em>' reference.
     * @generated
     * @see #getBackground()
     */
    void setBackground(UserColor value);

    /**
     * Returns the value of the '<em><b>Show Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Show Icon Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setShowIconExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_ShowIconExpression()
     */
    String getShowIconExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getShowIconExpression
     * <em>Show Icon Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Show Icon Expression</em>' attribute.
     * @generated
     * @see #getShowIconExpression()
     */
    void setShowIconExpression(String value);

} // NodeLabelStyle
