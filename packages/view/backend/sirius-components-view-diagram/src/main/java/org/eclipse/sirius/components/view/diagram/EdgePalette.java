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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Palette</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getCenterLabelEditTool <em>Center Label Edit
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getBeginLabelEditTool <em>Begin Label Edit
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEndLabelEditTool <em>End Label Edit
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEdgeReconnectionTools <em>Edge Reconnection
 * Tools</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette()
 * @model
 * @generated
 */
public interface EdgePalette extends EObject {
    /**
     * Returns the value of the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Delete Tool</em>' containment reference.
     * @see #setDeleteTool(DeleteTool)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette_DeleteTool()
     * @model containment="true"
     * @generated
     */
    DeleteTool getDeleteTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getDeleteTool <em>Delete
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Delete Tool</em>' containment reference.
     * @see #getDeleteTool()
     * @generated
     */
    void setDeleteTool(DeleteTool value);

    /**
     * Returns the value of the '<em><b>Center Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Center Label Edit Tool</em>' containment reference.
     * @see #setCenterLabelEditTool(LabelEditTool)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette_CenterLabelEditTool()
     * @model containment="true"
     * @generated
     */
    LabelEditTool getCenterLabelEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getCenterLabelEditTool
     * <em>Center Label Edit Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Center Label Edit Tool</em>' containment reference.
     * @see #getCenterLabelEditTool()
     * @generated
     */
    void setCenterLabelEditTool(LabelEditTool value);

    /**
     * Returns the value of the '<em><b>Begin Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Begin Label Edit Tool</em>' containment reference.
     * @see #setBeginLabelEditTool(LabelEditTool)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette_BeginLabelEditTool()
     * @model containment="true"
     * @generated
     */
    LabelEditTool getBeginLabelEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getBeginLabelEditTool
     * <em>Begin Label Edit Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Begin Label Edit Tool</em>' containment reference.
     * @see #getBeginLabelEditTool()
     * @generated
     */
    void setBeginLabelEditTool(LabelEditTool value);

    /**
     * Returns the value of the '<em><b>End Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Label Edit Tool</em>' containment reference.
     * @see #setEndLabelEditTool(LabelEditTool)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette_EndLabelEditTool()
     * @model containment="true"
     * @generated
     */
    LabelEditTool getEndLabelEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEndLabelEditTool <em>End
     * Label Edit Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Label Edit Tool</em>' containment reference.
     * @see #getEndLabelEditTool()
     * @generated
     */
    void setEndLabelEditTool(LabelEditTool value);

    /**
     * Returns the value of the '<em><b>Node Tools</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.NodeTool}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Node Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette_NodeTools()
     * @model containment="true"
     * @generated
     */
    EList<NodeTool> getNodeTools();

    /**
     * Returns the value of the '<em><b>Edge Reconnection Tools</b></em>' containment reference list. The list contents
     * are of type {@link org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Edge Reconnection Tools</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgePalette_EdgeReconnectionTools()
     * @model containment="true"
     * @generated
     */
    EList<EdgeReconnectionTool> getEdgeReconnectionTools();

} // EdgePalette
