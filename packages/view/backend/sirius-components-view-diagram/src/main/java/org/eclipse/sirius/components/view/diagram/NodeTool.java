/**
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.diagram;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeTool#getSelectionDescription <em>Selection
 * Description</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeTool()
 * @model
 * @generated
 */
public interface NodeTool extends Tool {
    /**
     * Returns the value of the '<em><b>Selection Description</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Description</em>' containment reference.
     * @see #setSelectionDescription(SelectionDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeTool_SelectionDescription()
     * @model containment="true"
     * @generated
     */
    SelectionDescription getSelectionDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getSelectionDescription
     * <em>Selection Description</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Description</em>' containment reference.
     * @see #getSelectionDescription()
     * @generated
     */
    void setSelectionDescription(SelectionDescription value);

} // NodeTool
