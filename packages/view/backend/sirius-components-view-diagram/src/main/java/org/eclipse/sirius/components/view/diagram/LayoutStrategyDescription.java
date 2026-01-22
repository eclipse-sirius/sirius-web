/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Layout Strategy Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription#getOnWestAtCreationBorderNodes <em>On West At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription#getOnEastAtCreationBorderNodes <em>On East At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription#getOnSouthAtCreationBorderNodes <em>On South At Creation Border Nodes</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription#getOnNorthAtCreationBorderNodes <em>On North At Creation Border Nodes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLayoutStrategyDescription()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface LayoutStrategyDescription extends EObject {

    /**
	 * Returns the value of the '<em><b>On West At Creation Border Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>On West At Creation Border Nodes</em>' reference list.
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLayoutStrategyDescription_OnWestAtCreationBorderNodes()
	 * @model
	 * @generated
	 */
    EList<NodeDescription> getOnWestAtCreationBorderNodes();

    /**
	 * Returns the value of the '<em><b>On East At Creation Border Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>On East At Creation Border Nodes</em>' reference list.
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLayoutStrategyDescription_OnEastAtCreationBorderNodes()
	 * @model
	 * @generated
	 */
    EList<NodeDescription> getOnEastAtCreationBorderNodes();

    /**
	 * Returns the value of the '<em><b>On South At Creation Border Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>On South At Creation Border Nodes</em>' reference list.
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLayoutStrategyDescription_OnSouthAtCreationBorderNodes()
	 * @model
	 * @generated
	 */
    EList<NodeDescription> getOnSouthAtCreationBorderNodes();

    /**
	 * Returns the value of the '<em><b>On North At Creation Border Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>On North At Creation Border Nodes</em>' reference list.
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLayoutStrategyDescription_OnNorthAtCreationBorderNodes()
	 * @model
	 * @generated
	 */
    EList<NodeDescription> getOnNorthAtCreationBorderNodes();
} // LayoutStrategyDescription
