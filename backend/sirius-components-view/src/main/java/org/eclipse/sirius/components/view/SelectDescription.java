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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Select Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.SelectDescription#getValueExpression <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.SelectDescription#getCandidatesExpression <em>Candidates
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.SelectDescription#getCandidateLabelExpression <em>Candidate Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.SelectDescription#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription()
 * @model
 * @generated
 */
public interface SelectDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @see #setValueExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription_ValueExpression()
     * @model
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.SelectDescription#getValueExpression <em>Value
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Expression</em>' attribute.
     * @see #getValueExpression()
     * @generated
     */
    void setValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Candidates Expression</em>' attribute.
     * @see #setCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription_CandidatesExpression()
     * @model
     * @generated
     */
    String getCandidatesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.SelectDescription#getCandidatesExpression
     * <em>Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Candidates Expression</em>' attribute.
     * @see #getCandidatesExpression()
     * @generated
     */
    void setCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Candidate Label Expression</em>' attribute.
     * @see #setCandidateLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription_CandidateLabelExpression()
     * @model
     * @generated
     */
    String getCandidateLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.SelectDescription#getCandidateLabelExpression
     * <em>Candidate Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Candidate Label Expression</em>' attribute.
     * @see #getCandidateLabelExpression()
     * @generated
     */
    void setCandidateLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(SelectDescriptionStyle)
     * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription_Style()
     * @model containment="true"
     * @generated
     */
    SelectDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.SelectDescription#getStyle <em>Style</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(SelectDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getSelectDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalSelectDescriptionStyle> getConditionalStyles();

} // SelectDescription
