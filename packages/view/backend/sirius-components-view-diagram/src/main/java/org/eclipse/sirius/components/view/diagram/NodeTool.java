/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeTool#getDialogDescription <em>Dialog Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeTool#getIconURLsExpression <em>Icon UR Ls
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeTool#getElementsToSelectExpression <em>Elements To Select
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeTool()
 * @model
 * @generated
 */
public interface NodeTool extends Tool {
    /**
     * Returns the value of the '<em><b>Dialog Description</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Dialog Description</em>' containment reference.
     * @see #setDialogDescription(DialogDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeTool_DialogDescription()
     * @model containment="true"
     * @generated
     */
    DialogDescription getDialogDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getDialogDescription <em>Dialog
     * Description</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Dialog Description</em>' containment reference.
     * @see #getDialogDescription()
     * @generated
     */
    void setDialogDescription(DialogDescription value);

    /**
     * Returns the value of the '<em><b>Icon UR Ls Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Icon UR Ls Expression</em>' attribute.
     * @see #setIconURLsExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeTool_IconURLsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIconURLsExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getIconURLsExpression <em>Icon
     * UR Ls Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Icon UR Ls Expression</em>' attribute.
     * @see #getIconURLsExpression()
     * @generated
     */
    void setIconURLsExpression(String value);

    /**
     * Returns the value of the '<em><b>Elements To Select Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Elements To Select Expression</em>' attribute.
     * @see #setElementsToSelectExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeTool_ElementsToSelectExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getElementsToSelectExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getElementsToSelectExpression
     * <em>Elements To Select Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Elements To Select Expression</em>' attribute.
     * @see #getElementsToSelectExpression()
     * @generated
     */
    void setElementsToSelectExpression(String value);

} // NodeTool
