/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Diagram Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DiagramDescription#getNodeDescriptions <em>Node Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DiagramDescription#getEdgeDescriptions <em>Edge Descriptions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramDescription()
 * @model
 * @generated
 */
public interface DiagramDescription extends RepresentationDescription {
    /**
     * Returns the value of the '<em><b>Auto Layout</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Auto Layout</em>' attribute.
     * @see #setAutoLayout(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramDescription_AutoLayout()
     * @model required="true"
     * @generated
     */
    boolean isAutoLayout();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DiagramDescription#isAutoLayout <em>Auto
     * Layout</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Auto Layout</em>' attribute.
     * @see #isAutoLayout()
     * @generated
     */
    void setAutoLayout(boolean value);

    /**
     * Returns the value of the '<em><b>Node Descriptions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.NodeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Node Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramDescription_NodeDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<NodeDescription> getNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Edge Descriptions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.EdgeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Edge Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramDescription_EdgeDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<EdgeDescription> getEdgeDescriptions();

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @see #setPalette(DiagramPalette)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDiagramDescription_Palette()
     * @model containment="true"
     * @generated
     */
    DiagramPalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DiagramDescription#getPalette <em>Palette</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Palette</em>' containment reference.
     * @see #getPalette()
     * @generated
     */
    void setPalette(DiagramPalette value);

} // DiagramDescription
