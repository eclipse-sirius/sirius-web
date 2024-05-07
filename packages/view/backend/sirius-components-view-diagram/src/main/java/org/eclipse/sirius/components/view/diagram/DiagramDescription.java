/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.view.RepresentationDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#isAutoLayout <em>Auto Layout</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getNodeDescriptions <em>Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getEdgeDescriptions <em>Edge
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getArrangeLayoutDirection <em>Arrange Layout
 * Direction</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramDescription()
 */
public interface DiagramDescription extends RepresentationDescription {

    /**
     * Returns the value of the '<em><b>Auto Layout</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Auto Layout</em>' attribute.
     * @model required="true"
     * @generated
     * @see #setAutoLayout(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramDescription_AutoLayout()
     */
    boolean isAutoLayout();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#isAutoLayout <em>Auto
     * Layout</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Auto Layout</em>' attribute.
     * @generated
     * @see #isAutoLayout()
     */
    void setAutoLayout(boolean value);

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setPalette(DiagramPalette)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramDescription_Palette()
     */
    DiagramPalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getPalette
     * <em>Palette</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Palette</em>' containment reference.
     * @generated
     * @see #getPalette()
     */
    void setPalette(DiagramPalette value);

    /**
     * Returns the value of the '<em><b>Node Descriptions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Node Descriptions</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramDescription_NodeDescriptions()
     */
    EList<NodeDescription> getNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Edge Descriptions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.diagram.EdgeDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Edge Descriptions</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramDescription_EdgeDescriptions()
     */
    EList<EdgeDescription> getEdgeDescriptions();

    /**
     * Returns the value of the '<em><b>Arrange Layout Direction</b></em>' attribute. The default value is
     * <code>"UNDEFINED"</code>. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Arrange Layout Direction</em>' attribute.
     * @model default="UNDEFINED" required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
     * @see #setArrangeLayoutDirection(ArrangeLayoutDirection)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramDescription_ArrangeLayoutDirection()
     */
    ArrangeLayoutDirection getArrangeLayoutDirection();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getArrangeLayoutDirection <em>Arrange
     * Layout Direction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Arrange Layout Direction</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
     * @see #getArrangeLayoutDirection()
     */
    void setArrangeLayoutDirection(ArrangeLayoutDirection value);

} // DiagramDescription
