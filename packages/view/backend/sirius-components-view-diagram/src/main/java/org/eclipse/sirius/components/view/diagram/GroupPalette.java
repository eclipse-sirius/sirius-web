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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Group Palette</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.GroupPalette#getNodeTools <em>Node Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.GroupPalette#getQuickAccessTools <em>Quick Access Tools</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.GroupPalette#getToolSections <em>Tool Sections</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getGroupPalette()
 * @model
 * @generated
 */
public interface GroupPalette extends EObject {
    /**
	 * Returns the value of the '<em><b>Node Tools</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeTool}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Node Tools</em>' containment reference list.
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getGroupPalette_NodeTools()
	 * @model containment="true" keys="name"
	 * @generated
	 */
    EList<NodeTool> getNodeTools();

    /**
     * Returns the value of the '<em><b>Quick Access Tools</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.NodeTool}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Quick Access Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getGroupPalette_QuickAccessTools()
     * @model containment="true" keys="name"
     * @generated
     */
    EList<NodeTool> getQuickAccessTools();

    /**
     * Returns the value of the '<em><b>Tool Sections</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.diagram.ToolSection}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Tool Sections</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getGroupPalette_ToolSections()
     * @model containment="true" keys="name"
     * @generated
     */
    EList<ToolSection> getToolSections();

} // GroupPalette
