/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Toolbar</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramToolbar#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramToolbar#isExpandedByDefault <em>Expanded By
 * Default</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramToolbar()
 * @model
 * @generated
 */
public interface DiagramToolbar extends EObject {
    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramToolbar_PreconditionExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramToolbar#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Expanded By Default</b></em>' attribute. The default value is
     * <code>"true"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Expanded By Default</em>' attribute.
     * @see #setExpandedByDefault(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramToolbar_ExpandedByDefault()
     * @model default="true"
     * @generated
     */
    boolean isExpandedByDefault();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramToolbar#isExpandedByDefault
     * <em>Expanded By Default</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Expanded By Default</em>' attribute.
     * @see #isExpandedByDefault()
     * @generated
     */
    void setExpandedByDefault(boolean value);

} // DiagramToolbar
