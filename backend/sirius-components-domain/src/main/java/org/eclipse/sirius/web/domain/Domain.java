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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Domain</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.Domain#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.domain.DomainPackage#getDomain()
 * @model
 * @generated
 */
public interface Domain extends NamedElement {
    /**
     * Returns the value of the '<em><b>Types</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.web.domain.Entity}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Types</em>' containment reference list.
     * @see org.eclipse.sirius.web.domain.DomainPackage#getDomain_Types()
     * @model containment="true"
     * @generated
     */
    EList<Entity> getTypes();

} // Domain
