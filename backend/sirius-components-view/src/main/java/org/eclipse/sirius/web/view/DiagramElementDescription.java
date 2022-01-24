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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Diagram Element Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getDomainType <em>Domain Type</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getSemanticCandidatesExpression <em>Semantic
 * Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getLabelExpression <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getLabelEditTool <em>Label Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.DiagramElementDescription#getSynchronizationPolicy <em>Synchronization
 * Policy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription()
 * @model abstract="true"
 * @generated
 */
public interface DiagramElementDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is
     * <code>"NewRepresentationDescription"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_Name()
     * @model default="NewRepresentationDescription"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_DomainType()
     * @model
     * @generated
     */
    String getDomainType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getDomainType <em>Domain
     * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Type</em>' attribute.
     * @see #getDomainType()
     * @generated
     */
    void setDomainType(String value);

    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self.eContents()"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_SemanticCandidatesExpression()
     * @model default="aql:self.eContents()"
     * @generated
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #getSemanticCandidatesExpression()
     * @generated
     */
    void setSemanticCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_LabelExpression()
     * @model
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getLabelExpression <em>Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Delete Tool</em>' containment reference.
     * @see #setDeleteTool(DeleteTool)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_DeleteTool()
     * @model containment="true"
     * @generated
     */
    DeleteTool getDeleteTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getDeleteTool <em>Delete
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Delete Tool</em>' containment reference.
     * @see #getDeleteTool()
     * @generated
     */
    void setDeleteTool(DeleteTool value);

    /**
     * Returns the value of the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Label Edit Tool</em>' containment reference.
     * @see #setLabelEditTool(LabelEditTool)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_LabelEditTool()
     * @model containment="true"
     * @generated
     */
    LabelEditTool getLabelEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getLabelEditTool <em>Label
     * Edit Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Edit Tool</em>' containment reference.
     * @see #getLabelEditTool()
     * @generated
     */
    void setLabelEditTool(LabelEditTool value);

    /**
     * Returns the value of the '<em><b>Synchronization Policy</b></em>' attribute. The literals are from the
     * enumeration {@link org.eclipse.sirius.web.view.SynchronizationPolicy}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Synchronization Policy</em>' attribute.
     * @see org.eclipse.sirius.web.view.SynchronizationPolicy
     * @see #setSynchronizationPolicy(SynchronizationPolicy)
     * @see org.eclipse.sirius.web.view.ViewPackage#getDiagramElementDescription_SynchronizationPolicy()
     * @model
     * @generated
     */
    SynchronizationPolicy getSynchronizationPolicy();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getSynchronizationPolicy
     * <em>Synchronization Policy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Synchronization Policy</em>' attribute.
     * @see org.eclipse.sirius.web.view.SynchronizationPolicy
     * @see #getSynchronizationPolicy()
     * @generated
     */
    void setSynchronizationPolicy(SynchronizationPolicy value);

} // DiagramElementDescription
