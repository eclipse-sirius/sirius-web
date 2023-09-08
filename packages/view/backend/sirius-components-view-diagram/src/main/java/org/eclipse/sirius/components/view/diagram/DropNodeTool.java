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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Drop Node Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DropNodeTool#getAcceptedNodeTypes <em>Accepted Node
 * Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDropNodeTool()
 * @model
 * @generated
 */
public interface DropNodeTool extends Tool {

    /**
     * Returns the value of the '<em><b>Accepted Node Types</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Accepted Node Types</em>' reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDropNodeTool_AcceptedNodeTypes()
     * @model
     * @generated
     */
    EList<NodeDescription> getAcceptedNodeTypes();
} // DropNodeTool
