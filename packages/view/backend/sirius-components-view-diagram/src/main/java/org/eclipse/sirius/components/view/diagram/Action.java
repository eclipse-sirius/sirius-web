/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Action</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.Action#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.Action#getTooltipExpression <em>Tooltip Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.Action#getIconURLsExpression <em>Icon UR Ls
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.Action#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.Action#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.Action#isReadOnlyVisible <em>Read Only Visible</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction()
 * @model
 * @generated
 */
public interface Action extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is <code>"Tool"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction_Name()
     * @model default="Tool" dataType="org.eclipse.sirius.components.view.Identifier" required="true"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.Action#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Tooltip Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Tooltip Expression</em>' attribute.
     * @see #setTooltipExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction_TooltipExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTooltipExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.Action#getTooltipExpression <em>Tooltip
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tooltip Expression</em>' attribute.
     * @see #getTooltipExpression()
     * @generated
     */
    void setTooltipExpression(String value);

    /**
     * Returns the value of the '<em><b>Icon UR Ls Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Icon UR Ls Expression</em>' attribute.
     * @see #setIconURLsExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction_IconURLsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIconURLsExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.Action#getIconURLsExpression <em>Icon UR
     * Ls Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Icon UR Ls Expression</em>' attribute.
     * @see #getIconURLsExpression()
     * @generated
     */
    void setIconURLsExpression(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction_PreconditionExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.Action#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Read Only Visible</b></em>' attribute. The default value is
     * <code>"false"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Read Only Visible</em>' attribute.
     * @see #setReadOnlyVisible(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getAction_ReadOnlyVisible()
     * @model default="false"
     * @generated
     */
    boolean isReadOnlyVisible();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.Action#isReadOnlyVisible <em>Read Only
     * Visible</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Read Only Visible</em>' attribute.
     * @see #isReadOnlyVisible()
     * @generated
     */
    void setReadOnlyVisible(boolean value);

} // Action
