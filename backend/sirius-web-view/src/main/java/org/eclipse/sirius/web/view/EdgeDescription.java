/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodeDescription <em>Source Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodeDescription <em>Target Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodesExpression <em>Source Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodesExpression <em>Target Nodes
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription()
 * @model
 * @generated
 */
public interface EdgeDescription extends DiagramElementDescription {
    /**
     * Returns the value of the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Domain Based Edge</em>' attribute.
     * @see #setIsDomainBasedEdge(boolean)
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_IsDomainBasedEdge()
     * @model
     * @generated
     */
    boolean isIsDomainBasedEdge();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based
     * Edge</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Domain Based Edge</em>' attribute.
     * @see #isIsDomainBasedEdge()
     * @generated
     */
    void setIsDomainBasedEdge(boolean value);

    /**
     * Returns the value of the '<em><b>Source Node Description</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Source Node Description</em>' reference.
     * @see #setSourceNodeDescription(NodeDescription)
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_SourceNodeDescription()
     * @model required="true"
     * @generated
     */
    NodeDescription getSourceNodeDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodeDescription <em>Source
     * Node Description</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source Node Description</em>' reference.
     * @see #getSourceNodeDescription()
     * @generated
     */
    void setSourceNodeDescription(NodeDescription value);

    /**
     * Returns the value of the '<em><b>Target Node Description</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Target Node Description</em>' reference.
     * @see #setTargetNodeDescription(NodeDescription)
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_TargetNodeDescription()
     * @model required="true"
     * @generated
     */
    NodeDescription getTargetNodeDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodeDescription <em>Target
     * Node Description</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Target Node Description</em>' reference.
     * @see #getTargetNodeDescription()
     * @generated
     */
    void setTargetNodeDescription(NodeDescription value);

    /**
     * Returns the value of the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Source Nodes Expression</em>' attribute.
     * @see #setSourceNodesExpression(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_SourceNodesExpression()
     * @model
     * @generated
     */
    String getSourceNodesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodesExpression <em>Source
     * Nodes Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_TargetNodesExpression()
     * @model default="aql:self.eCrossReferences()"
     * @generated
     */
    String getTargetNodesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodesExpression <em>Target
     * Nodes Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_Style()
     * @model containment="true"
     * @generated
     */
    EdgeStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getStyle <em>Style</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(EdgeStyle value);

    /**
     * Returns the value of the '<em><b>Edge Tools</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.web.view.EdgeTool}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Edge Tools</em>' containment reference list.
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_EdgeTools()
     * @model containment="true"
     * @generated
     */
    EList<EdgeTool> getEdgeTools();

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.web.view.ConditionalEdgeStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalEdgeStyle> getConditionalStyles();

} // EdgeDescription
