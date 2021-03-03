/**
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
 */
package org.eclipse.sirius.web.domain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Relation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.Relation#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.Relation#getTargetType <em>Target Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.domain.DomainPackage#getRelation()
 * @model
 * @generated
 */
public interface Relation extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.web.domain.DomainPackage#getRelation_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.domain.Relation#getName <em>Name</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Target Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Target Type</em>' reference.
     * @see #setTargetType(Entity)
     * @see org.eclipse.sirius.web.domain.DomainPackage#getRelation_TargetType()
     * @model required="true"
     * @generated
     */
    Entity getTargetType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.domain.Relation#getTargetType <em>Target Type</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Target Type</em>' reference.
     * @see #getTargetType()
     * @generated
     */
    void setTargetType(Entity value);

} // Relation
