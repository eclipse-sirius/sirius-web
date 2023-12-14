/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>List Layout Strategy Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getAreChildNodesDraggableExpression
 * <em>Are Child Nodes Draggable Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getListLayoutStrategyDescription()
 * @model
 * @generated
 */
public interface ListLayoutStrategyDescription extends LayoutStrategyDescription {

    /**
     * Returns the value of the '<em><b>Are Child Nodes Draggable Expression</b></em>' attribute. The default value is
     * <code>"aql:true"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Are Child Nodes Draggable Expression</em>' attribute.
     * @see #setAreChildNodesDraggableExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getListLayoutStrategyDescription_AreChildNodesDraggableExpression()
     * @model default="aql:true" dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getAreChildNodesDraggableExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getAreChildNodesDraggableExpression
     * <em>Are Child Nodes Draggable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Are Child Nodes Draggable Expression</em>' attribute.
     * @see #getAreChildNodesDraggableExpression()
     * @generated
     */
    void setAreChildNodesDraggableExpression(String value);
} // ListLayoutStrategyDescription
