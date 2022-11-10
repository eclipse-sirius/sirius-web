/**
 * Copyright (c) 2021, 2022 Obeo.
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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Create View</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.CreateView#getParentViewExpression <em>Parent View
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.CreateView#getElementDescription <em>Element Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.CreateView#getSemanticElementExpression <em>Semantic Element
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.CreateView#getVariableName <em>Variable Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getCreateView()
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateView_ParentViewExpression()
     * @model default="aql:selectedNode" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     *        required="true"
     * @generated
     */
    String getParentViewExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateView#getParentViewExpression <em>Parent
     * View Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateView_ElementDescription()
     * @model
     * @generated
     */
    DiagramElementDescription getElementDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateView#getElementDescription <em>Element
     * Description</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateView_SemanticElementExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getSemanticElementExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateView#getSemanticElementExpression
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getCreateView_VariableName()
     * @model
     * @generated
     */
    String getVariableName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CreateView#getVariableName <em>Variable
     * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Variable Name</em>' attribute.
     * @see #getVariableName()
     * @generated
     */
    void setVariableName(String value);

} // CreateView
