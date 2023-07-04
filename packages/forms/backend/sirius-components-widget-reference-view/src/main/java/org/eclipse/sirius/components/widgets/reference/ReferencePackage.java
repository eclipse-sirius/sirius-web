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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.form.FormPackage;

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
 * @see org.eclipse.sirius.components.widgets.reference.ReferenceFactory
 */
public interface ReferencePackage extends EPackage {

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "reference";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "https://www.eclipse.org/sirius/widgets/reference";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "reference";
    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
     */
    int REFERENCE_WIDGET_DESCRIPTION = 0;
    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;
    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;
    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;
    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT;
    /**
     * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;
    /**
     * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__BODY = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;
    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ReferencePackage eINSTANCE = org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl.init();
    /**
     * The number of structural features of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription <em>Widget Description</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription
     */
    EClass getReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression
     * <em>Reference Owner Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Owner Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression()
     * @see #getReferenceWidgetDescription()
     */
    EAttribute getReferenceWidgetDescription_ReferenceOwnerExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression
     * <em>Reference Name Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Name Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression()
     * @see #getReferenceWidgetDescription()
     */
    EAttribute getReferenceWidgetDescription_ReferenceNameExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getBody <em>Body</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getBody()
     * @see #getReferenceWidgetDescription()
     */
    EReference getReferenceWidgetDescription_Body();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getIsEnabledExpression <em>Is
     * Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getIsEnabledExpression()
     * @see #getReferenceWidgetDescription()
     */
    EAttribute getReferenceWidgetDescription_IsEnabledExpression();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ReferenceFactory getReferenceFactory();

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
         * '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
         */
        EClass REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Reference Owner Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceOwnerExpression();

        /**
         * The meta object literal for the '<em><b>Reference Name Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceNameExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__BODY = eINSTANCE.getReferenceWidgetDescription_Body();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_IsEnabledExpression();

    }

} // ReferencePackage
