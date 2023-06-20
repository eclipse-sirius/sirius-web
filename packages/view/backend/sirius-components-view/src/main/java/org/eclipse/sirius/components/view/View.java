/**
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>View</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.View#getDescriptions <em>Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.View#getColorPalettes <em>Color Palettes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getView()
 * @model
 * @generated
 */
public interface View extends EObject {
    /**
     * Returns the value of the '<em><b>Descriptions</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.RepresentationDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getView_Descriptions()
     * @model containment="true"
     * @generated
     */
    EList<RepresentationDescription> getDescriptions();

    /**
     * Returns the value of the '<em><b>Color Palettes</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.ColorPalette}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Color Palettes</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getView_ColorPalettes()
     * @model containment="true"
     * @generated
     */
    EList<ColorPalette> getColorPalettes();

} // View
