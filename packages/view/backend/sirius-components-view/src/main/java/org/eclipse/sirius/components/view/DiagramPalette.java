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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Diagram Palette</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DiagramPalette#getDropTool <em>Drop Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DiagramPalette#getNodeTools <em>Node Tools</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramPalette()
 * @model
 * @generated
 */
public interface DiagramPalette extends EObject {
    /**
     * Returns the value of the '<em><b>Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Drop Tool</em>' containment reference.
     * @see #setDropTool(DropTool)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramPalette_DropTool()
     * @model containment="true"
     * @generated
     */
    DropTool getDropTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DiagramPalette#getDropTool <em>Drop Tool</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Drop Tool</em>' containment reference.
     * @see #getDropTool()
     * @generated
     */
    void setDropTool(DropTool value);

    /**
     * Returns the value of the '<em><b>Node Tools</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.NodeTool}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Node Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramPalette_NodeTools()
     * @model containment="true"
     * @generated
     */
    EList<NodeTool> getNodeTools();

} // DiagramPalette
