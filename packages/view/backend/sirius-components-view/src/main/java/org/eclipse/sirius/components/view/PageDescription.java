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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Page Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.PageDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.PageDescription#getLabelExpression <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.PageDescription#getSemanticCandidatesExpression <em>Semantic Candidates
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.PageDescription#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.PageDescription#getGroups <em>Groups</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription()
 * @model
 * @generated
 */
public interface PageDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription_Name()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PageDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription_LabelExpression()
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PageDescription#getLabelExpression <em>Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription_SemanticCandidatesExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PageDescription#getSemanticCandidatesExpression
     * <em>Semantic Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #getSemanticCandidatesExpression()
     * @generated
     */
    void setSemanticCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription_PreconditionExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PageDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Groups</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.GroupDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Groups</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription_Groups()
     * @model containment="true"
     * @generated
     */
    EList<GroupDescription> getGroups();

    /**
     * Returns the value of the '<em><b>Toolbar Actions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.ButtonDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Toolbar Actions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getPageDescription_ToolbarActions()
     * @model containment="true"
     * @generated
     */
    EList<ButtonDescription> getToolbarActions();

} // PageDescription
