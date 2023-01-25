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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Palette</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.NodePalette#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodePalette#getLabelEditTool <em>Label Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodePalette#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.NodePalette#getEdgeTools <em>Edge Tools</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getNodePalette()
 * @model
 * @generated
 */
public interface NodePalette extends EObject {
    /**
     * Returns the value of the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Delete Tool</em>' containment reference.
     * @see #setDeleteTool(DeleteTool)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodePalette_DeleteTool()
     * @model containment="true"
     * @generated
     */
    DeleteTool getDeleteTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodePalette#getDeleteTool <em>Delete Tool</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Delete Tool</em>' containment reference.
     * @see #getDeleteTool()
     * @generated
     */
    void setDeleteTool(DeleteTool value);

    /**
     * Returns the value of the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Label Edit Tool</em>' containment reference.
     * @see #setLabelEditTool(LabelEditTool)
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodePalette_LabelEditTool()
     * @model containment="true"
     * @generated
     */
    LabelEditTool getLabelEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.NodePalette#getLabelEditTool <em>Label Edit
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Edit Tool</em>' containment reference.
     * @see #getLabelEditTool()
     * @generated
     */
    void setLabelEditTool(LabelEditTool value);

    /**
     * Returns the value of the '<em><b>Node Tools</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.NodeTool}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Node Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodePalette_NodeTools()
     * @model containment="true"
     * @generated
     */
    EList<NodeTool> getNodeTools();

    /**
     * Returns the value of the '<em><b>Edge Tools</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.EdgeTool}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Edge Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getNodePalette_EdgeTools()
     * @model containment="true"
     * @generated
     */
    EList<EdgeTool> getEdgeTools();

} // NodePalette
