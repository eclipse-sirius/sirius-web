/**
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.EdgeTool#getTargetElementDescriptions <em>Target Element
 * Descriptions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeTool()
 * @model
 * @generated
 */
public interface EdgeTool extends Tool {

    /**
     * Returns the value of the '<em><b>Target Element Descriptions</b></em>' reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.DiagramElementDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Target Element Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeTool_TargetElementDescriptions()
     * @model
     * @generated
     */
    EList<DiagramElementDescription> getTargetElementDescriptions();
} // EdgeTool
