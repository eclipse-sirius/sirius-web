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
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.web.domain;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Entity</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.Entity#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.Entity#getRelations <em>Relations</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.Entity#getSuperType <em>Super Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.domain.DomainPackage#getEntity()
 * @model
 * @generated
 */
public interface Entity extends NamedElement {
    /**
     * Returns the value of the '<em><b>Attributes</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.web.domain.Attribute}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Attributes</em>' containment reference list.
     * @see org.eclipse.sirius.web.domain.DomainPackage#getEntity_Attributes()
     * @model containment="true"
     * @generated
     */
    EList<Attribute> getAttributes();

    /**
     * Returns the value of the '<em><b>Relations</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.web.domain.Relation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Relations</em>' containment reference list.
     * @see org.eclipse.sirius.web.domain.DomainPackage#getEntity_Relations()
     * @model containment="true"
     * @generated
     */
    EList<Relation> getRelations();

    /**
     * Returns the value of the '<em><b>Super Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Super Type</em>' reference.
     * @see #setSuperType(Entity)
     * @see org.eclipse.sirius.web.domain.DomainPackage#getEntity_SuperType()
     * @model
     * @generated
     */
    Entity getSuperType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.domain.Entity#getSuperType <em>Super Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Super Type</em>' reference.
     * @see #getSuperType()
     * @generated
     */
    void setSuperType(Entity value);

} // Entity
