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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.EdgeDescription#getSourceNodeDescription <em>Source Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.EdgeDescription#getTargetNodeDescription <em>Target Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.EdgeDescription#getSourceNodesExpression <em>Source Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.EdgeDescription#getTargetNodesExpression <em>Target Nodes
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription()
 * @model
 * @generated
 */
public interface EdgeDescription extends DiagramElementDescription {
    /**
     * Returns the value of the '<em><b>Begin Label Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Begin Label Expression</em>' attribute.
     * @see #setBeginLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_BeginLabelExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getBeginLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#getBeginLabelExpression
     * <em>Begin Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Begin Label Expression</em>' attribute.
     * @see #getBeginLabelExpression()
     * @generated
     */
    void setBeginLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>End Label Expression</b></em>' attribute. The default value is <code>""</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Label Expression</em>' attribute.
     * @see #setEndLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_EndLabelExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getEndLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#getEndLabelExpression <em>End
     * Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Label Expression</em>' attribute.
     * @see #getEndLabelExpression()
     * @generated
     */
    void setEndLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Domain Based Edge</em>' attribute.
     * @see #setIsDomainBasedEdge(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_IsDomainBasedEdge()
     * @model
     * @generated
     */
    boolean isIsDomainBasedEdge();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#isIsDomainBasedEdge <em>Is
     * Domain Based Edge</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Domain Based Edge</em>' attribute.
     * @see #isIsDomainBasedEdge()
     * @generated
     */
    void setIsDomainBasedEdge(boolean value);

    /**
     * Returns the value of the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Palette</em>' containment reference.
     * @see #setPalette(EdgePalette)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_Palette()
     * @model containment="true"
     * @generated
     */
    EdgePalette getPalette();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#getPalette <em>Palette</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Palette</em>' containment reference.
     * @see #getPalette()
     * @generated
     */
    void setPalette(EdgePalette value);

    /**
     * Returns the value of the '<em><b>Source Node Descriptions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.NodeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Node Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_SourceNodeDescriptions()
     * @model required="true"
     * @generated
     */
    EList<NodeDescription> getSourceNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Target Node Descriptions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.NodeDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Node Descriptions</em>' reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_TargetNodeDescriptions()
     * @model required="true"
     * @generated
     */
    EList<NodeDescription> getTargetNodeDescriptions();

    /**
     * Returns the value of the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Source Nodes Expression</em>' attribute.
     * @see #setSourceNodesExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_SourceNodesExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSourceNodesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#getSourceNodesExpression
     * <em>Source Nodes Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source Nodes Expression</em>' attribute.
     * @see #getSourceNodesExpression()
     * @generated
     */
    void setSourceNodesExpression(String value);

    /**
     * Returns the value of the '<em><b>Target Nodes Expression</b></em>' attribute. The default value is
     * <code>"aql:self.eCrossReferences()"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Nodes Expression</em>' attribute.
     * @see #setTargetNodesExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_TargetNodesExpression()
     * @model default="aql:self.eCrossReferences()" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTargetNodesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#getTargetNodesExpression
     * <em>Target Nodes Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Target Nodes Expression</em>' attribute.
     * @see #getTargetNodesExpression()
     * @generated
     */
    void setTargetNodesExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(Style)
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_Style()
     * @model containment="true"
     * @generated
     */
    EdgeStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.EdgeDescription#getStyle <em>Style</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(EdgeStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.ConditionalEdgeStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getEdgeDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalEdgeStyle> getConditionalStyles();

} // EdgeDescription
