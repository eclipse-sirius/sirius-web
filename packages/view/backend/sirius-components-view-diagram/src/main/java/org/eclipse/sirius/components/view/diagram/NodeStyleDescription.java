/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Style Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getChildrenLayoutStrategy <em>Children
 * Layout Strategy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeStyleDescription()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface NodeStyleDescription extends BorderStyle {

    /**
     * Returns the value of the '<em><b>Children Layout Strategy</b></em>' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Children Layout Strategy</em>' containment reference.
     * @see #setChildrenLayoutStrategy(LayoutStrategyDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeStyleDescription_ChildrenLayoutStrategy()
     * @model containment="true"
     * @generated
     */
    LayoutStrategyDescription getChildrenLayoutStrategy();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getChildrenLayoutStrategy <em>Children
     * Layout Strategy</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Children Layout Strategy</em>' containment reference.
     * @see #getChildrenLayoutStrategy()
     * @generated
     */
    void setChildrenLayoutStrategy(LayoutStrategyDescription value);

} // NodeStyleDescription
