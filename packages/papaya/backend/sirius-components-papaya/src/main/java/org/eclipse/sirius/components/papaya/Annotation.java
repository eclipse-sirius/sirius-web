/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Annotation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Annotation#getFields <em>Fields</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends Type {

    /**
     * Returns the value of the '<em><b>Fields</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.AnnotationField}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Fields</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getAnnotation_Fields()
     * @model containment="true"
     * @generated
     */
    EList<AnnotationField> getFields();
} // Annotation
