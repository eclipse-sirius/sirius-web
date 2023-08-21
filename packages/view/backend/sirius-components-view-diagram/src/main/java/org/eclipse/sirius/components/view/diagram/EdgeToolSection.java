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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Tool Section</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection#getEdgeReconnectionTools <em>Edge Reconnection
 * Tools</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeToolSection()
 */
public interface EdgeToolSection extends ToolSection {

    /**
     * Returns the value of the '<em><b>Node Tools</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.NodeTool}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Node Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeToolSection_NodeTools()
     * @model containment="true"
     * @generated
     */
    EList<NodeTool> getNodeTools();

} // EdgeToolSection
