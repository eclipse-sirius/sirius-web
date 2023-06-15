/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widgets.reference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.sirius.components.widgets.reference.ReferenceFactory
 * @model kind="package"
 * @generated
 */
public interface ReferencePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "reference";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "https://www.eclipse.org/sirius/widgets/reference";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "reference";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ReferencePackage eINSTANCE = org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget Description</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
     * @generated
     */
    int REFERENCE_WIDGET_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__NAME = ViewPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = ViewPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__HELP_EXPRESSION = ViewPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = ViewPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = ViewPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Widget Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = ViewPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Widget Description</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = ViewPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;


    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription <em>Widget Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Widget Description</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription
     * @generated
     */
    EClass getReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression <em>Reference Owner Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference Owner Expression</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression()
     * @see #getReferenceWidgetDescription()
     * @generated
     */
    EAttribute getReferenceWidgetDescription_ReferenceOwnerExpression();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression <em>Reference Name Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reference Name Expression</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression()
     * @see #getReferenceWidgetDescription()
     * @generated
     */
    EAttribute getReferenceWidgetDescription_ReferenceNameExpression();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ReferenceFactory getReferenceFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget Description</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
         * @generated
         */
        EClass REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Reference Owner Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceOwnerExpression();

        /**
         * The meta object literal for the '<em><b>Reference Name Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceNameExpression();

    }

} //ReferencePackage
