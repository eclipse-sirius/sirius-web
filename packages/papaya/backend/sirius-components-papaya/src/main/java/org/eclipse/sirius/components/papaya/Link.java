/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.papaya;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Link</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.Link#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.Link#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getLink()
 * @model abstract="true"
 * @generated
 */
public interface Link extends EObject {
    /**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see #setKind(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getLink_Kind()
	 * @model required="true"
	 * @generated
	 */
    String getKind();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Link#getKind <em>Kind</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Kind</em>' attribute.
     * @see #getKind()
     * @generated
     */
    void setKind(String value);

    /**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.sirius.components.papaya.ModelElement#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(ModelElement)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getLink_Source()
	 * @see org.eclipse.sirius.components.papaya.ModelElement#getLinks
	 * @model opposite="links" required="true" transient="false"
	 * @generated
	 */
    ModelElement getSource();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Link#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
    void setSource(ModelElement value);

} // Link
