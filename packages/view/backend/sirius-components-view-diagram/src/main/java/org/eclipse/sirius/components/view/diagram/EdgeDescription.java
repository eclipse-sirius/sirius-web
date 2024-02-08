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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression <em>Begin Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression <em>End Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based
 * Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette <em>Palette</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodeDescriptions <em>Source Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodeDescriptions <em>Target Node
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression <em>Source Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression <em>Target Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription()
 */
public interface EdgeDescription extends DiagramElementDescription {

    /**
     * Returns the value of the '<em><b>Begin Label Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Begin Label Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setBeginLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_BeginLabelExpression()
     */
    String getBeginLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression
     * <em>Begin Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Begin Label Expression</em>' attribute.
     * @generated
     * @see #getBeginLabelExpression()
     */
    void setBeginLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Center Label Expression</b></em>' attribute. The default value is
     * <code>"aql:self.name"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Center Label Expression</em>' attribute.
     * @model default="aql:self.name" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setCenterLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_CenterLabelExpression()
     */
    String getCenterLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getCenterLabelExpression
     * <em>Center Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Center Label Expression</em>' attribute.
     * @generated
     * @see #getCenterLabelExpression()
     */
    void setCenterLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>End Label Expression</b></em>' attribute. The default value is <code>""</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Label Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setEndLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_EndLabelExpression()
     */
    String getEndLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression
     * <em>End Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>End Label Expression</em>' attribute.
     * @generated
     * @see #getEndLabelExpression()
     */
    void setEndLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Domain Based Edge</em>' attribute.
     * @model
     * @generated
     * @see #setIsDomainBasedEdge(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_IsDomainBasedEdge()
     */
    boolean isIsDomainBasedEdge();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge
     * <em>Is Domain Based Edge</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Is Domain Based Edge</em>' attribute.
     * @generated
     * @see #isIsDomainBasedEdge()
     */
    void setIsDomainBasedEdge(boolean value);

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setPalette(EdgePalette)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_Palette()
     */
    EdgePalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette
     * <em>Palette</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Palette</em>' containment reference.
     * @generated
     * @see #getPalette()
     */
    void setPalette(EdgePalette value);

    /**
     * Returns the value of the '<em><b>Source Node Descriptions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Node Descriptions</em>' reference list.
     * @model required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_SourceNodeDescriptions()
     */
    EList<NodeDescription> getSourceNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Target Node Descriptions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.diagram.NodeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Node Descriptions</em>' reference list.
     * @model required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_TargetNodeDescriptions()
     */
    EList<NodeDescription> getTargetNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Source Nodes Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setSourceNodesExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_SourceNodesExpression()
     */
    String getSourceNodesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression
     * <em>Source Nodes Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Source Nodes Expression</em>' attribute.
     * @generated
     * @see #getSourceNodesExpression()
     */
    void setSourceNodesExpression(String value);

    /**
     * Returns the value of the '<em><b>Target Nodes Expression</b></em>' attribute. The default value is
     * <code>"aql:self.eCrossReferences()"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Nodes Expression</em>' attribute.
     * @model default="aql:self.eCrossReferences()" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setTargetNodesExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_TargetNodesExpression()
     */
    String getTargetNodesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression
     * <em>Target Nodes Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Target Nodes Expression</em>' attribute.
     * @generated
     * @see #getTargetNodesExpression()
     */
    void setTargetNodesExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(EdgeStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_Style()
     */
    EdgeStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Style</em>' containment reference.
     * @generated
     * @see #getStyle()
     */
    void setStyle(EdgeStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeDescription_ConditionalStyles()
     */
    EList<ConditionalEdgeStyle> getConditionalStyles();

} // EdgeDescription
