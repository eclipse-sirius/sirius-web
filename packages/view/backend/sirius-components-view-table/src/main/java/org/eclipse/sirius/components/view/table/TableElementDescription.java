/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Element Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.TableElementDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.TableElementDescription#getDomainType <em>Domain Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.TableElementDescription#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.TableElementDescription#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * </ul>
 *
 * @model abstract="true"
 * @generated
 * @see org.eclipse.sirius.components.view.table.TablePackage#getTableElementDescription()
 */
public interface TableElementDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableElementDescription_Name()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.DomainType"
     * @generated
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableElementDescription_DomainType()
     */
    String getDomainType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getDomainType
     * <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Type</em>' attribute.
     * @see #getDomainType()
     * @generated
     */
    void setDomainType(String value);

    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableElementDescription_SemanticCandidatesExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getSemanticCandidatesExpression
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
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableElementDescription_PreconditionExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

} // TableElementDescription
