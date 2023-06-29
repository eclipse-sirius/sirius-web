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

import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Create View</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.CreateView#getParentViewExpression <em>Parent View
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.CreateView#getElementDescription <em>Element
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.CreateView#getSemanticElementExpression <em>Semantic Element
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.CreateView#getVariableName <em>Variable Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.CreateView#getContainmentKind <em>Containment Kind</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getCreateView()
 * @model
 * @generated
 */
public interface CreateView extends Operation {
    /**
     * Returns the value of the '<em><b>Parent View Expression</b></em>' attribute. The default value is
     * <code>"aql:selectedNode"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Parent View Expression</em>' attribute.
     * @see #setParentViewExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getCreateView_ParentViewExpression()
     * @model default="aql:selectedNode" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     *        required="true"
     * @generated
     */
    String getParentViewExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.CreateView#getParentViewExpression
     * <em>Parent View Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Parent View Expression</em>' attribute.
     * @see #getParentViewExpression()
     * @generated
     */
    void setParentViewExpression(String value);

    /**
     * Returns the value of the '<em><b>Element Description</b></em>' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Element Description</em>' reference.
     * @see #setElementDescription(DiagramElementDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getCreateView_ElementDescription()
     * @model
     * @generated
     */
    DiagramElementDescription getElementDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.CreateView#getElementDescription
     * <em>Element Description</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Element Description</em>' reference.
     * @see #getElementDescription()
     * @generated
     */
    void setElementDescription(DiagramElementDescription value);

    /**
     * Returns the value of the '<em><b>Semantic Element Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Element Expression</em>' attribute.
     * @see #setSemanticElementExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getCreateView_SemanticElementExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getSemanticElementExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.CreateView#getSemanticElementExpression
     * <em>Semantic Element Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Semantic Element Expression</em>' attribute.
     * @see #getSemanticElementExpression()
     * @generated
     */
    void setSemanticElementExpression(String value);

    /**
     * Returns the value of the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Variable Name</em>' attribute.
     * @see #setVariableName(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getCreateView_VariableName()
     * @model
     * @generated
     */
    String getVariableName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.CreateView#getVariableName <em>Variable
     * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Variable Name</em>' attribute.
     * @see #getVariableName()
     * @generated
     */
    void setVariableName(String value);

    /**
     * Returns the value of the '<em><b>Containment Kind</b></em>' attribute. The default value is
     * <code>"CHILD_NODE"</code>. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Containment Kind</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
     * @see #setContainmentKind(NodeContainmentKind)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getCreateView_ContainmentKind()
     * @model default="CHILD_NODE" required="true"
     * @generated
     */
    NodeContainmentKind getContainmentKind();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.CreateView#getContainmentKind
     * <em>Containment Kind</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Containment Kind</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
     * @see #getContainmentKind()
     * @generated
     */
    void setContainmentKind(NodeContainmentKind value);

} // CreateView
