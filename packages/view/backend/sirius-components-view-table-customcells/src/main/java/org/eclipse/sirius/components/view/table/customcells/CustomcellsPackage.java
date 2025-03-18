/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table.customcells;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @model kind="package"
 * @generated
 * @see org.eclipse.sirius.components.view.table.customcells.CustomcellsFactory
 */
public interface CustomcellsPackage extends EPackage {

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "customcells";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/customcells";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "customcells";
    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.table.customcells.impl.CellCheckboxWidgetDescriptionImpl <em>Cell
     * Checkbox Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.customcells.impl.CellCheckboxWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.customcells.impl.CustomcellsPackageImpl#getCellCheckboxWidgetDescription()
     */
    int CELL_CHECKBOX_WIDGET_DESCRIPTION = 0;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY = TablePackage.CELL_WIDGET_DESCRIPTION_FEATURE_COUNT;
    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    CustomcellsPackage eINSTANCE = org.eclipse.sirius.components.view.table.customcells.impl.CustomcellsPackageImpl.init();
    /**
     * The number of structural features of the '<em>Cell Checkbox Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_CHECKBOX_WIDGET_DESCRIPTION_FEATURE_COUNT = TablePackage.CELL_WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Cell Checkbox Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_CHECKBOX_WIDGET_DESCRIPTION_OPERATION_COUNT = TablePackage.CELL_WIDGET_DESCRIPTION_OPERATION_COUNT;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription <em>Cell Checkbox
     * Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cell Checkbox Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription
     */
    EClass getCellCheckboxWidgetDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription#getBody()
     * @see #getCellCheckboxWidgetDescription()
     */
    EReference getCellCheckboxWidgetDescription_Body();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    CustomcellsFactory getCustomcellsFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.table.customcells.impl.CellCheckboxWidgetDescriptionImpl <em>Cell
         * Checkbox Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.customcells.impl.CellCheckboxWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.customcells.impl.CustomcellsPackageImpl#getCellCheckboxWidgetDescription()
         */
        EClass CELL_CHECKBOX_WIDGET_DESCRIPTION = eINSTANCE.getCellCheckboxWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY = eINSTANCE.getCellCheckboxWidgetDescription_Body();

    }

} // CustomcellsPackage
