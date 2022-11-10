/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Representation Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.RepresentationDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.RepresentationDescription#getDomainType <em>Domain Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.RepresentationDescription#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.RepresentationDescription#getTitleExpression <em>Title
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getRepresentationDescription()
 * @model abstract="true"
 * @generated
 */
public interface RepresentationDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is
     * <code>"NewRepresentationDescription"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getRepresentationDescription_Name()
     * @model default="NewRepresentationDescription"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.RepresentationDescription#getName
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
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getRepresentationDescription_DomainType()
     * @model default=""
     * @generated
     */
    String getDomainType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.RepresentationDescription#getDomainType
     * <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Type</em>' attribute.
     * @see #getDomainType()
     * @generated
     */
    void setDomainType(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getRepresentationDescription_PreconditionExpression()
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Title Expression</em>' attribute.
     * @see #setTitleExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getRepresentationDescription_TitleExpression()
     * @model
     * @generated
     */
    String getTitleExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.RepresentationDescription#getTitleExpression
     * <em>Title Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Title Expression</em>' attribute.
     * @see #getTitleExpression()
     * @generated
     */
    void setTitleExpression(String value);

} // RepresentationDescription
