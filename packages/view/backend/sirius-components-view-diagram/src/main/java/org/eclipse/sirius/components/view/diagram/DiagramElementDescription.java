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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Element Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getDomainType <em>Domain
 * Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSynchronizationPolicy
 * <em>Synchronization Policy</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getLabelExpression <em>Label
 * Expression</em>}</li>
 * </ul>
 *
 * @model abstract="true"
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramElementDescription()
 */
public interface DiagramElementDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is
     * <code>"NewRepresentationDescription"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @model default="NewRepresentationDescription" dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramElementDescription_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.DomainType"
     * @generated
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramElementDescription_DomainType()
     */
    String getDomainType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getDomainType
     * <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Domain Type</em>' attribute.
     * @generated
     * @see #getDomainType()
     */
    void setDomainType(String value);

    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self.eContents()"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @model default="aql:self.eContents()" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramElementDescription_SemanticCandidatesExpression()
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSemanticCandidatesExpression
     * <em>Semantic Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @generated
     * @see #getSemanticCandidatesExpression()
     */
    void setSemanticCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramElementDescription_PreconditionExpression()
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Precondition Expression</em>' attribute.
     * @generated
     * @see #getPreconditionExpression()
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Synchronization Policy</b></em>' attribute. The literals are from the
     * enumeration {@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Synchronization Policy</em>' attribute.
     * @model
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * @see #setSynchronizationPolicy(SynchronizationPolicy)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramElementDescription_SynchronizationPolicy()
     */
    SynchronizationPolicy getSynchronizationPolicy();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSynchronizationPolicy
     * <em>Synchronization Policy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Synchronization Policy</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * @see #getSynchronizationPolicy()
     */
    void setSynchronizationPolicy(SynchronizationPolicy value);

} // DiagramElementDescription
