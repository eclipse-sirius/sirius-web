/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.web.customwidgets;

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
 * @see org.eclipse.sirius.web.customwidgets.CustomwidgetsFactory
 * @model kind="package"
 * @generated
 */
public interface CustomwidgetsPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "customwidgets";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/customwidgets";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "customwidgets";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    CustomwidgetsPackage eINSTANCE = org.eclipse.sirius.web.customwidgets.impl.CustomwidgetsPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl <em>Slider
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl
     * @see org.eclipse.sirius.web.customwidgets.impl.CustomwidgetsPackageImpl#getSliderDescription()
     * @generated
     */
    int SLIDER_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Min Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Max Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Current Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__BODY = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Slider Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Slider Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SLIDER_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.web.customwidgets.SliderDescription <em>Slider
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Slider Description</em>'.
     * @see org.eclipse.sirius.web.customwidgets.SliderDescription
     * @generated
     */
    EClass getSliderDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.web.customwidgets.SliderDescription#getMinValueExpression <em>Min Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Min Value Expression</em>'.
     * @see org.eclipse.sirius.web.customwidgets.SliderDescription#getMinValueExpression()
     * @see #getSliderDescription()
     * @generated
     */
    EAttribute getSliderDescription_MinValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.web.customwidgets.SliderDescription#getMaxValueExpression <em>Max Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Max Value Expression</em>'.
     * @see org.eclipse.sirius.web.customwidgets.SliderDescription#getMaxValueExpression()
     * @see #getSliderDescription()
     * @generated
     */
    EAttribute getSliderDescription_MaxValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.web.customwidgets.SliderDescription#getCurrentValueExpression <em>Current Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Current Value Expression</em>'.
     * @see org.eclipse.sirius.web.customwidgets.SliderDescription#getCurrentValueExpression()
     * @see #getSliderDescription()
     * @generated
     */
    EAttribute getSliderDescription_CurrentValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.web.customwidgets.SliderDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.web.customwidgets.SliderDescription#getBody()
     * @see #getSliderDescription()
     * @generated
     */
    EReference getSliderDescription_Body();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.web.customwidgets.SliderDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.sirius.web.customwidgets.SliderDescription#getIsEnabledExpression()
     * @see #getSliderDescription()
     * @generated
     */
    EAttribute getSliderDescription_IsEnabledExpression();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    CustomwidgetsFactory getCustomwidgetsFactory();

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
         * The meta object literal for the '{@link org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl
         * <em>Slider Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl
         * @see org.eclipse.sirius.web.customwidgets.impl.CustomwidgetsPackageImpl#getSliderDescription()
         * @generated
         */
        EClass SLIDER_DESCRIPTION = eINSTANCE.getSliderDescription();

        /**
         * The meta object literal for the '<em><b>Min Value Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION = eINSTANCE.getSliderDescription_MinValueExpression();

        /**
         * The meta object literal for the '<em><b>Max Value Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION = eINSTANCE.getSliderDescription_MaxValueExpression();

        /**
         * The meta object literal for the '<em><b>Current Value Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION = eINSTANCE.getSliderDescription_CurrentValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SLIDER_DESCRIPTION__BODY = eINSTANCE.getSliderDescription_Body();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getSliderDescription_IsEnabledExpression();

    }

} // CustomwidgetsPackage
