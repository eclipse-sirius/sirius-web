/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.ViewPackage;

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
 * @see org.eclipse.sirius.components.view.form.FormFactory
 * @model kind="package"
 * @generated
 */
public interface FormPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "form";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/form";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "form";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    FormPackage eINSTANCE = org.eclipse.sirius.components.view.form.impl.FormPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.FormDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.FormDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getFormDescription()
     * @generated
     */
    int FORM_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__NAME = ViewPackage.REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__DOMAIN_TYPE = ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__PRECONDITION_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__TITLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Pages</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION__PAGES = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FORM_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl <em>Page
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getPageDescription()
     * @generated
     */
    int PAGE_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__LABEL_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__DOMAIN_TYPE = 2;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__PRECONDITION_EXPRESSION = 4;

    /**
     * The feature id for the '<em><b>Groups</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__GROUPS = 5;

    /**
     * The feature id for the '<em><b>Toolbar Actions</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION__TOOLBAR_ACTIONS = 6;

    /**
     * The number of structural features of the '<em>Page Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION_FEATURE_COUNT = 7;

    /**
     * The number of operations of the '<em>Page Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PAGE_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl <em>Group
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getGroupDescription()
     * @generated
     */
    int GROUP_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__LABEL_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Display Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__DISPLAY_MODE = 2;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Toolbar Actions</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__TOOLBAR_ACTIONS = 4;

    /**
     * The feature id for the '<em><b>Widgets</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION__WIDGETS = 5;

    /**
     * The number of structural features of the '<em>Group Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION_FEATURE_COUNT = 6;

    /**
     * The number of operations of the '<em>Group Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int GROUP_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl <em>Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getWidgetDescription()
     * @generated
     */
    int WIDGET_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION__LABEL_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION__HELP_EXPRESSION = 2;

    /**
     * The number of structural features of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl <em>Bar
     * Chart Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getBarChartDescription()
     * @generated
     */
    int BAR_CHART_DESCRIPTION = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Values Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__VALUES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Keys Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__KEYS_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>YAxis Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__WIDTH = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Height</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION__HEIGHT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Bar Chart Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Bar Chart Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl <em>Button
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getButtonDescription()
     * @generated
     */
    int BUTTON_DESCRIPTION = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Button Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Image Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__IMAGE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Button Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Button Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionImpl
     * <em>Checkbox Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getCheckboxDescription()
     * @generated
     */
    int CHECKBOX_DESCRIPTION = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Checkbox Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Checkbox Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl
     * <em>Flexbox Container Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getFlexboxContainerDescription()
     * @generated
     */
    int FLEXBOX_CONTAINER_DESCRIPTION = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Flex Direction</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Flexbox Container Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Flexbox Container Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FLEXBOX_CONTAINER_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.ImageDescriptionImpl <em>Image
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ImageDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getImageDescription()
     * @generated
     */
    int IMAGE_DESCRIPTION = 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Url Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__URL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Max Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Image Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Image Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.LabelDescriptionImpl <em>Label
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.LabelDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLabelDescription()
     * @generated
     */
    int LABEL_DESCRIPTION = 9;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Label Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.LinkDescriptionImpl <em>Link
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.LinkDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLinkDescription()
     * @generated
     */
    int LINK_DESCRIPTION = 10;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Link Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Link Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.ListDescriptionImpl <em>List
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ListDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getListDescription()
     * @generated
     */
    int LIST_DESCRIPTION = 11;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Display Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__DISPLAY_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Deletable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>List Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>List Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl
     * <em>Multi Select Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getMultiSelectDescription()
     * @generated
     */
    int MULTI_SELECT_DESCRIPTION = 12;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Multi Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Multi Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.PieChartDescriptionImpl <em>Pie
     * Chart Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.PieChartDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getPieChartDescription()
     * @generated
     */
    int PIE_CHART_DESCRIPTION = 13;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Values Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__VALUES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Keys Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__KEYS_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Pie Chart Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Pie Chart Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.RadioDescriptionImpl <em>Radio
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.RadioDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getRadioDescription()
     * @generated
     */
    int RADIO_DESCRIPTION = 14;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__CANDIDATES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Radio Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Radio Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl <em>Rich
     * Text Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getRichTextDescription()
     * @generated
     */
    int RICH_TEXT_DESCRIPTION = 15;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Rich Text Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Rich Text Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RICH_TEXT_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.SelectDescriptionImpl <em>Select
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.SelectDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getSelectDescription()
     * @generated
     */
    int SELECT_DESCRIPTION = 16;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Select Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Select Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.TextAreaDescriptionImpl <em>Text
     * Area Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.TextAreaDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextAreaDescription()
     * @generated
     */
    int TEXT_AREA_DESCRIPTION = 17;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Text Area Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Text Area Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXT_AREA_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionImpl
     * <em>Textfield Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextfieldDescription()
     * @generated
     */
    int TEXTFIELD_DESCRIPTION = 18;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__NAME = WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__LABEL_EXPRESSION = WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__HELP_EXPRESSION = WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__VALUE_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__BODY = WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__STYLE = WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__CONDITIONAL_STYLES = WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION__IS_ENABLED_EXPRESSION = WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Textfield Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_FEATURE_COUNT = WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Textfield Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_OPERATION_COUNT = WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionStyleImpl
     * <em>Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.WidgetDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getWidgetDescriptionStyle()
     * @generated
     */
    int WIDGET_DESCRIPTION_STYLE = 19;

    /**
     * The number of structural features of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionStyleImpl
     * <em>Bar Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.BarChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getBarChartDescriptionStyle()
     * @generated
     */
    int BAR_CHART_DESCRIPTION_STYLE = 20;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Bars Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Bar Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Bar Chart Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BAR_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl <em>Conditional Bar
     * Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalBarChartDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE = 21;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Bars Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Bar Chart Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Bar Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionStyleImpl
     * <em>Button Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ButtonDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getButtonDescriptionStyle()
     * @generated
     */
    int BUTTON_DESCRIPTION_STYLE = 22;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Button Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Button Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BUTTON_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalButtonDescriptionStyleImpl <em>Conditional Button
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalButtonDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalButtonDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE = 23;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Button Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Button Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_BUTTON_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl
     * <em>Checkbox Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getCheckboxDescriptionStyle()
     * @generated
     */
    int CHECKBOX_DESCRIPTION_STYLE = 24;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Checkbox Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Checkbox Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHECKBOX_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl <em>Conditional
     * Checkbox Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalCheckboxDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE = 25;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Conditional Checkbox Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Conditional Checkbox Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.LabelDescriptionStyleImpl
     * <em>Label Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.LabelDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLabelDescriptionStyle()
     * @generated
     */
    int LABEL_DESCRIPTION_STYLE = 26;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Label Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Label Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalLabelDescriptionStyleImpl <em>Conditional Label
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalLabelDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalLabelDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE = 27;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Label Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Label Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LABEL_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.LinkDescriptionStyleImpl <em>Link
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.LinkDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLinkDescriptionStyle()
     * @generated
     */
    int LINK_DESCRIPTION_STYLE = 28;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Link Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Link Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LINK_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalLinkDescriptionStyleImpl <em>Conditional Link
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalLinkDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalLinkDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE = 29;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Link Description Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Link Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LINK_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.ListDescriptionStyleImpl <em>List
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ListDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getListDescriptionStyle()
     * @generated
     */
    int LIST_DESCRIPTION_STYLE = 30;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>List Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>List Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalListDescriptionStyleImpl <em>Conditional List
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalListDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalListDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE = 31;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional List Description Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional List Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_LIST_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl
     * <em>Multi Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getMultiSelectDescriptionStyle()
     * @generated
     */
    int MULTI_SELECT_DESCRIPTION_STYLE = 32;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Multi Select Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Multi Select Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int MULTI_SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalMultiSelectDescriptionStyleImpl <em>Conditional
     * Multi Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalMultiSelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalMultiSelectDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE = 33;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Conditional Multi Select Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Conditional Multi Select Description Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.PieChartDescriptionStyleImpl
     * <em>Pie Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.PieChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getPieChartDescriptionStyle()
     * @generated
     */
    int PIE_CHART_DESCRIPTION_STYLE = 34;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Colors</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__COLORS = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Stroke Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Stroke Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Pie Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Pie Chart Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PIE_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalPieChartDescriptionStyleImpl <em>Conditional Pie
     * Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalPieChartDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalPieChartDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE = 35;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Colors</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__COLORS = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Stroke Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Stroke Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Conditional Pie Chart Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Conditional Pie Chart Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.RadioDescriptionStyleImpl
     * <em>Radio Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.RadioDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getRadioDescriptionStyle()
     * @generated
     */
    int RADIO_DESCRIPTION_STYLE = 36;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE__COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Radio Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Radio Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RADIO_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalRadioDescriptionStyleImpl <em>Conditional Radio
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalRadioDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalRadioDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE = 37;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Radio Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Radio Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_RADIO_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.SelectDescriptionStyleImpl
     * <em>Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.SelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getSelectDescriptionStyle()
     * @generated
     */
    int SELECT_DESCRIPTION_STYLE = 38;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE__SHOW_ICON = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Select Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Select Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalSelectDescriptionStyleImpl <em>Conditional Select
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalSelectDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalSelectDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE = 39;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE__SHOW_ICON = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Conditional Select Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Conditional Select Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_SELECT_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.TextareaDescriptionStyleImpl
     * <em>Textarea Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.TextareaDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextareaDescriptionStyle()
     * @generated
     */
    int TEXTAREA_DESCRIPTION_STYLE = 40;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Textarea Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Textarea Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTAREA_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl <em>Conditional
     * Textarea Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalTextareaDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE = 41;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Textarea Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Textarea Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionStyleImpl
     * <em>Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextfieldDescriptionStyle()
     * @generated
     */
    int TEXTFIELD_DESCRIPTION_STYLE = 42;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__FONT_SIZE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__ITALIC = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__BOLD = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__UNDERLINE = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__STRIKE_THROUGH = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Textfield Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE_FEATURE_COUNT = WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Textfield Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TEXTFIELD_DESCRIPTION_STYLE_OPERATION_COUNT = WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextfieldDescriptionStyleImpl <em>Conditional
     * Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.impl.ConditionalTextfieldDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalTextfieldDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE = 43;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Conditional Textfield Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Conditional Textfield Description Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.FlexDirection <em>Flex
     * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.FlexDirection
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getFlexDirection()
     * @generated
     */
    int FLEX_DIRECTION = 44;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.form.GroupDisplayMode <em>Group Display
     * Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.form.GroupDisplayMode
     * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getGroupDisplayMode()
     * @generated
     */
    int GROUP_DISPLAY_MODE = 45;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.FormDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @see org.eclipse.sirius.components.view.form.FormDescription
     * @generated
     */
    EClass getFormDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.FormDescription#getPages <em>Pages</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Pages</em>'.
     * @see org.eclipse.sirius.components.view.form.FormDescription#getPages()
     * @see #getFormDescription()
     * @generated
     */
    EReference getFormDescription_Pages();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.PageDescription <em>Page
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Page Description</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription
     * @generated
     */
    EClass getPageDescription();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.form.PageDescription#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getName()
     * @see #getPageDescription()
     * @generated
     */
    EAttribute getPageDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PageDescription#getLabelExpression <em>Label Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getLabelExpression()
     * @see #getPageDescription()
     * @generated
     */
    EAttribute getPageDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PageDescription#getDomainType <em>Domain Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getDomainType()
     * @see #getPageDescription()
     * @generated
     */
    EAttribute getPageDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PageDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getSemanticCandidatesExpression()
     * @see #getPageDescription()
     * @generated
     */
    EAttribute getPageDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PageDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getPreconditionExpression()
     * @see #getPageDescription()
     * @generated
     */
    EAttribute getPageDescription_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.PageDescription#getGroups <em>Groups</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Groups</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getGroups()
     * @see #getPageDescription()
     * @generated
     */
    EReference getPageDescription_Groups();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.PageDescription#getToolbarActions <em>Toolbar Actions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Toolbar Actions</em>'.
     * @see org.eclipse.sirius.components.view.form.PageDescription#getToolbarActions()
     * @see #getPageDescription()
     * @generated
     */
    EReference getPageDescription_ToolbarActions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.GroupDescription <em>Group
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Group Description</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription
     * @generated
     */
    EClass getGroupDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.GroupDescription#getName <em>Name</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription#getName()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.GroupDescription#getLabelExpression <em>Label Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription#getLabelExpression()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.GroupDescription#getDisplayMode <em>Display Mode</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Display Mode</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription#getDisplayMode()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_DisplayMode();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.GroupDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription#getSemanticCandidatesExpression()
     * @see #getGroupDescription()
     * @generated
     */
    EAttribute getGroupDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.GroupDescription#getToolbarActions <em>Toolbar Actions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Toolbar Actions</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription#getToolbarActions()
     * @see #getGroupDescription()
     * @generated
     */
    EReference getGroupDescription_ToolbarActions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.GroupDescription#getWidgets <em>Widgets</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Widgets</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDescription#getWidgets()
     * @see #getGroupDescription()
     * @generated
     */
    EReference getGroupDescription_Widgets();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.WidgetDescription <em>Widget
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description</em>'.
     * @see org.eclipse.sirius.components.view.form.WidgetDescription
     * @generated
     */
    EClass getWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.WidgetDescription#getName <em>Name</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.form.WidgetDescription#getName()
     * @see #getWidgetDescription()
     * @generated
     */
    EAttribute getWidgetDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.WidgetDescription#getLabelExpression <em>Label Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.WidgetDescription#getLabelExpression()
     * @see #getWidgetDescription()
     * @generated
     */
    EAttribute getWidgetDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.WidgetDescription#getHelpExpression <em>Help Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Help Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.WidgetDescription#getHelpExpression()
     * @see #getWidgetDescription()
     * @generated
     */
    EAttribute getWidgetDescription_HelpExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.BarChartDescription <em>Bar
     * Chart Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Bar Chart Description</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription
     * @generated
     */
    EClass getBarChartDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getValuesExpression <em>Values
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Values Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getValuesExpression()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_ValuesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getKeysExpression <em>Keys Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Keys Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getKeysExpression()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_KeysExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getYAxisLabelExpression <em>YAxis Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>YAxis Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getYAxisLabelExpression()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_YAxisLabelExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getStyle()
     * @see #getBarChartDescription()
     * @generated
     */
    EReference getBarChartDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getConditionalStyles()
     * @see #getBarChartDescription()
     * @generated
     */
    EReference getBarChartDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getWidth <em>Width</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Width</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getWidth()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_Width();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getHeight <em>Height</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Height</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription#getHeight()
     * @see #getBarChartDescription()
     * @generated
     */
    EAttribute getBarChartDescription_Height();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ButtonDescription <em>Button
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Button Description</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription
     * @generated
     */
    EClass getButtonDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getButtonLabelExpression <em>Button Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Button Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription#getButtonLabelExpression()
     * @see #getButtonDescription()
     * @generated
     */
    EAttribute getButtonDescription_ButtonLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription#getBody()
     * @see #getButtonDescription()
     * @generated
     */
    EReference getButtonDescription_Body();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getImageExpression <em>Image Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Image Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription#getImageExpression()
     * @see #getButtonDescription()
     * @generated
     */
    EAttribute getButtonDescription_ImageExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription#getStyle()
     * @see #getButtonDescription()
     * @generated
     */
    EReference getButtonDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription#getConditionalStyles()
     * @see #getButtonDescription()
     * @generated
     */
    EReference getButtonDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.ButtonDescription#getIsEnabledExpression()
     * @see #getButtonDescription()
     */
    EAttribute getButtonDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.CheckboxDescription
     * <em>Checkbox Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Checkbox Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription
     */
    EClass getCheckboxDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescription#getValueExpression <em>Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription#getValueExpression()
     * @see #getCheckboxDescription()
     * @generated
     */
    EAttribute getCheckboxDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription#getBody()
     * @see #getCheckboxDescription()
     * @generated
     */
    EReference getCheckboxDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription#getStyle()
     * @see #getCheckboxDescription()
     * @generated
     */
    EReference getCheckboxDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription#getConditionalStyles()
     * @see #getCheckboxDescription()
     * @generated
     */
    EReference getCheckboxDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription#getIsEnabledExpression()
     * @see #getCheckboxDescription()
     */
    EAttribute getCheckboxDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription
     * <em>Flexbox Container Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Flexbox Container Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.FlexboxContainerDescription
     */
    EClass getFlexboxContainerDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getChildren <em>Children</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getChildren()
     * @see #getFlexboxContainerDescription()
     * @generated
     */
    EReference getFlexboxContainerDescription_Children();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexDirection <em>Flex
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Flex Direction</em>'.
     * @see org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getFlexDirection()
     * @see #getFlexboxContainerDescription()
     * @generated
     */
    EAttribute getFlexboxContainerDescription_FlexDirection();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.FlexboxContainerDescription#getIsEnabledExpression()
     * @see #getFlexboxContainerDescription()
     */
    EAttribute getFlexboxContainerDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ImageDescription <em>Image
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Image Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.ImageDescription
     */
    EClass getImageDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ImageDescription#getUrlExpression <em>Url Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Url Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ImageDescription#getUrlExpression()
     * @see #getImageDescription()
     * @generated
     */
    EAttribute getImageDescription_UrlExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ImageDescription#getMaxWidthExpression <em>Max Width
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Max Width Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ImageDescription#getMaxWidthExpression()
     * @see #getImageDescription()
     * @generated
     */
    EAttribute getImageDescription_MaxWidthExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.LabelDescription <em>Label
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Description</em>'.
     * @see org.eclipse.sirius.components.view.form.LabelDescription
     * @generated
     */
    EClass getLabelDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.LabelDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.LabelDescription#getValueExpression()
     * @see #getLabelDescription()
     * @generated
     */
    EAttribute getLabelDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.LabelDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.LabelDescription#getStyle()
     * @see #getLabelDescription()
     * @generated
     */
    EReference getLabelDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.LabelDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.LabelDescription#getConditionalStyles()
     * @see #getLabelDescription()
     * @generated
     */
    EReference getLabelDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.LinkDescription <em>Link
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Link Description</em>'.
     * @see org.eclipse.sirius.components.view.form.LinkDescription
     * @generated
     */
    EClass getLinkDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.LinkDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.LinkDescription#getValueExpression()
     * @see #getLinkDescription()
     * @generated
     */
    EAttribute getLinkDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.LinkDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.LinkDescription#getStyle()
     * @see #getLinkDescription()
     * @generated
     */
    EReference getLinkDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.LinkDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.LinkDescription#getConditionalStyles()
     * @see #getLinkDescription()
     * @generated
     */
    EReference getLinkDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ListDescription <em>List
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Description</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription
     * @generated
     */
    EClass getListDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription#getValueExpression()
     * @see #getListDescription()
     * @generated
     */
    EAttribute getListDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getDisplayExpression <em>Display
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Display Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription#getDisplayExpression()
     * @see #getListDescription()
     * @generated
     */
    EAttribute getListDescription_DisplayExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getIsDeletableExpression <em>Is Deletable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Deletable Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription#getIsDeletableExpression()
     * @see #getListDescription()
     * @generated
     */
    EAttribute getListDescription_IsDeletableExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription#getBody()
     * @see #getListDescription()
     * @generated
     */
    EReference getListDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription#getStyle()
     * @see #getListDescription()
     * @generated
     */
    EReference getListDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescription#getConditionalStyles()
     * @see #getListDescription()
     * @generated
     */
    EReference getListDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.ListDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.ListDescription#getIsEnabledExpression()
     * @see #getListDescription()
     */
    EAttribute getListDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription
     * <em>Multi Select Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Select Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription
     */
    EClass getMultiSelectDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getValueExpression <em>Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getValueExpression()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EAttribute getMultiSelectDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getCandidatesExpression <em>Candidates
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getCandidatesExpression()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EAttribute getMultiSelectDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getCandidateLabelExpression <em>Candidate
     * Label Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidate Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getCandidateLabelExpression()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EAttribute getMultiSelectDescription_CandidateLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getBody <em>Body</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getBody()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EReference getMultiSelectDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getStyle()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EReference getMultiSelectDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getConditionalStyles()
     * @see #getMultiSelectDescription()
     * @generated
     */
    EReference getMultiSelectDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription#getIsEnabledExpression()
     * @see #getMultiSelectDescription()
     */
    EAttribute getMultiSelectDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.PieChartDescription <em>Pie
     * Chart Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Pie Chart Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.PieChartDescription
     */
    EClass getPieChartDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescription#getValuesExpression <em>Values
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Values Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescription#getValuesExpression()
     * @see #getPieChartDescription()
     * @generated
     */
    EAttribute getPieChartDescription_ValuesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescription#getKeysExpression <em>Keys Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Keys Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescription#getKeysExpression()
     * @see #getPieChartDescription()
     * @generated
     */
    EAttribute getPieChartDescription_KeysExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescription#getStyle()
     * @see #getPieChartDescription()
     * @generated
     */
    EReference getPieChartDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescription#getConditionalStyles()
     * @see #getPieChartDescription()
     * @generated
     */
    EReference getPieChartDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.RadioDescription <em>Radio
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Radio Description</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription
     * @generated
     */
    EClass getRadioDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getValueExpression()
     * @see #getRadioDescription()
     * @generated
     */
    EAttribute getRadioDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getCandidatesExpression <em>Candidates
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getCandidatesExpression()
     * @see #getRadioDescription()
     * @generated
     */
    EAttribute getRadioDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getCandidateLabelExpression <em>Candidate Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidate Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getCandidateLabelExpression()
     * @see #getRadioDescription()
     * @generated
     */
    EAttribute getRadioDescription_CandidateLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getBody <em>Body</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getBody()
     * @see #getRadioDescription()
     * @generated
     */
    EReference getRadioDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getStyle()
     * @see #getRadioDescription()
     * @generated
     */
    EReference getRadioDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getConditionalStyles()
     * @see #getRadioDescription()
     * @generated
     */
    EReference getRadioDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.RadioDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.RadioDescription#getIsEnabledExpression()
     * @see #getRadioDescription()
     */
    EAttribute getRadioDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.RichTextDescription <em>Rich
     * Text Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Rich Text Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.RichTextDescription
     */
    EClass getRichTextDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.RichTextDescription#getValueExpression <em>Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.RichTextDescription#getValueExpression()
     * @see #getRichTextDescription()
     * @generated
     */
    EAttribute getRichTextDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.RichTextDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.RichTextDescription#getBody()
     * @see #getRichTextDescription()
     * @generated
     */
    EReference getRichTextDescription_Body();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.RichTextDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.RichTextDescription#getIsEnabledExpression()
     * @see #getRichTextDescription()
     */
    EAttribute getRichTextDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.SelectDescription <em>Select
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Select Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.SelectDescription
     */
    EClass getSelectDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getValueExpression()
     * @see #getSelectDescription()
     * @generated
     */
    EAttribute getSelectDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getCandidatesExpression <em>Candidates
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getCandidatesExpression()
     * @see #getSelectDescription()
     * @generated
     */
    EAttribute getSelectDescription_CandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getCandidateLabelExpression <em>Candidate Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Candidate Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getCandidateLabelExpression()
     * @see #getSelectDescription()
     * @generated
     */
    EAttribute getSelectDescription_CandidateLabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getBody()
     * @see #getSelectDescription()
     * @generated
     */
    EReference getSelectDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getStyle()
     * @see #getSelectDescription()
     * @generated
     */
    EReference getSelectDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getConditionalStyles()
     * @see #getSelectDescription()
     * @generated
     */
    EReference getSelectDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.SelectDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.SelectDescription#getIsEnabledExpression()
     * @see #getSelectDescription()
     */
    EAttribute getSelectDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.TextAreaDescription <em>Text
     * Area Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Text Area Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription
     */
    EClass getTextAreaDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.TextAreaDescription#getValueExpression <em>Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription#getValueExpression()
     * @see #getTextAreaDescription()
     * @generated
     */
    EAttribute getTextAreaDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.TextAreaDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription#getBody()
     * @see #getTextAreaDescription()
     * @generated
     */
    EReference getTextAreaDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.TextAreaDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription#getStyle()
     * @see #getTextAreaDescription()
     * @generated
     */
    EReference getTextAreaDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.TextAreaDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription#getConditionalStyles()
     * @see #getTextAreaDescription()
     * @generated
     */
    EReference getTextAreaDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.TextAreaDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription#getIsEnabledExpression()
     * @see #getTextAreaDescription()
     */
    EAttribute getTextAreaDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.TextfieldDescription
     * <em>Textfield Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Textfield Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.TextfieldDescription
     */
    EClass getTextfieldDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescription#getValueExpression <em>Value
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescription#getValueExpression()
     * @see #getTextfieldDescription()
     * @generated
     */
    EAttribute getTextfieldDescription_ValueExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescription#getBody()
     * @see #getTextfieldDescription()
     * @generated
     */
    EReference getTextfieldDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescription#getStyle()
     * @see #getTextfieldDescription()
     * @generated
     */
    EReference getTextfieldDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescription#getConditionalStyles()
     * @see #getTextfieldDescription()
     * @generated
     */
    EReference getTextfieldDescription_ConditionalStyles();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescription#getIsEnabledExpression <em>Is Enabled
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.TextfieldDescription#getIsEnabledExpression()
     * @see #getTextfieldDescription()
     */
    EAttribute getTextfieldDescription_IsEnabledExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.WidgetDescriptionStyle
     * <em>Widget Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.form.WidgetDescriptionStyle
     */
    EClass getWidgetDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle
     * <em>Bar Chart Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Bar Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescriptionStyle
     * @generated
     */
    EClass getBarChartDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle#getBarsColor <em>Bars Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Bars Color</em>'.
     * @see org.eclipse.sirius.components.view.form.BarChartDescriptionStyle#getBarsColor()
     * @see #getBarChartDescriptionStyle()
     * @generated
     */
    EAttribute getBarChartDescriptionStyle_BarsColor();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle <em>Conditional Bar Chart
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Bar Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle
     * @generated
     */
    EClass getConditionalBarChartDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ButtonDescriptionStyle
     * <em>Button Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Button Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescriptionStyle
     * @generated
     */
    EClass getButtonDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescriptionStyle#getBackgroundColor()
     * @see #getButtonDescriptionStyle()
     * @generated
     */
    EReference getButtonDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.form.ButtonDescriptionStyle#getForegroundColor()
     * @see #getButtonDescriptionStyle()
     * @generated
     */
    EReference getButtonDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle <em>Conditional Button
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Button Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle
     * @generated
     */
    EClass getConditionalButtonDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle
     * <em>Checkbox Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Checkbox Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle
     * @generated
     */
    EClass getCheckboxDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle#getColor <em>Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle#getColor()
     * @see #getCheckboxDescriptionStyle()
     * @generated
     */
    EReference getCheckboxDescriptionStyle_Color();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle <em>Conditional Checkbox
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Checkbox Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle
     * @generated
     */
    EClass getConditionalCheckboxDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.LabelDescriptionStyle <em>Label
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.LabelDescriptionStyle
     * @generated
     */
    EClass getLabelDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.LabelDescriptionStyle#getColor <em>Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.form.LabelDescriptionStyle#getColor()
     * @see #getLabelDescriptionStyle()
     * @generated
     */
    EReference getLabelDescriptionStyle_Color();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle <em>Conditional Label
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Label Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle
     * @generated
     */
    EClass getConditionalLabelDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.LinkDescriptionStyle <em>Link
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Link Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.LinkDescriptionStyle
     * @generated
     */
    EClass getLinkDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.LinkDescriptionStyle#getColor <em>Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.form.LinkDescriptionStyle#getColor()
     * @see #getLinkDescriptionStyle()
     * @generated
     */
    EReference getLinkDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle
     * <em>Conditional Link Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Link Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle
     * @generated
     */
    EClass getConditionalLinkDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ListDescriptionStyle <em>List
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescriptionStyle
     * @generated
     */
    EClass getListDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.ListDescriptionStyle#getColor <em>Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.form.ListDescriptionStyle#getColor()
     * @see #getListDescriptionStyle()
     * @generated
     */
    EReference getListDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle
     * <em>Conditional List Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional List Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle
     * @generated
     */
    EClass getConditionalListDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle
     * <em>Multi Select Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Multi Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle
     * @generated
     */
    EClass getMultiSelectDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle#getBackgroundColor()
     * @see #getMultiSelectDescriptionStyle()
     * @generated
     */
    EReference getMultiSelectDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle#getForegroundColor()
     * @see #getMultiSelectDescriptionStyle()
     * @generated
     */
    EReference getMultiSelectDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle#isShowIcon <em>Show Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle#isShowIcon()
     * @see #getMultiSelectDescriptionStyle()
     * @generated
     */
    EAttribute getMultiSelectDescriptionStyle_ShowIcon();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle <em>Conditional Multi
     * Select Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Multi Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle
     * @generated
     */
    EClass getConditionalMultiSelectDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle
     * <em>Pie Chart Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Pie Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescriptionStyle
     * @generated
     */
    EClass getPieChartDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle#getColors <em>Colors</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Colors</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescriptionStyle#getColors()
     * @see #getPieChartDescriptionStyle()
     * @generated
     */
    EAttribute getPieChartDescriptionStyle_Colors();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle#getStrokeWidth <em>Stroke Width</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Stroke Width</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescriptionStyle#getStrokeWidth()
     * @see #getPieChartDescriptionStyle()
     * @generated
     */
    EAttribute getPieChartDescriptionStyle_StrokeWidth();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle#getStrokeColor <em>Stroke Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Stroke Color</em>'.
     * @see org.eclipse.sirius.components.view.form.PieChartDescriptionStyle#getStrokeColor()
     * @see #getPieChartDescriptionStyle()
     * @generated
     */
    EReference getPieChartDescriptionStyle_StrokeColor();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle <em>Conditional Pie Chart
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Pie Chart Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle
     * @generated
     */
    EClass getConditionalPieChartDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.RadioDescriptionStyle <em>Radio
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Radio Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescriptionStyle
     * @generated
     */
    EClass getRadioDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.RadioDescriptionStyle#getColor <em>Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.form.RadioDescriptionStyle#getColor()
     * @see #getRadioDescriptionStyle()
     * @generated
     */
    EReference getRadioDescriptionStyle_Color();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle <em>Conditional Radio
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Radio Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle
     * @generated
     */
    EClass getConditionalRadioDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle
     * <em>Select Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescriptionStyle
     * @generated
     */
    EClass getSelectDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescriptionStyle#getBackgroundColor()
     * @see #getSelectDescriptionStyle()
     * @generated
     */
    EReference getSelectDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescriptionStyle#getForegroundColor()
     * @see #getSelectDescriptionStyle()
     * @generated
     */
    EReference getSelectDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle#isShowIcon <em>Show Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @see org.eclipse.sirius.components.view.form.SelectDescriptionStyle#isShowIcon()
     * @see #getSelectDescriptionStyle()
     * @generated
     */
    EAttribute getSelectDescriptionStyle_ShowIcon();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle <em>Conditional Select
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Select Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle
     * @generated
     */
    EClass getConditionalSelectDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle
     * <em>Textarea Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Textarea Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.TextareaDescriptionStyle
     * @generated
     */
    EClass getTextareaDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.form.TextareaDescriptionStyle#getBackgroundColor()
     * @see #getTextareaDescriptionStyle()
     * @generated
     */
    EReference getTextareaDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.form.TextareaDescriptionStyle#getForegroundColor()
     * @see #getTextareaDescriptionStyle()
     * @generated
     */
    EReference getTextareaDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle <em>Conditional Textarea
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Textarea Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle
     * @generated
     */
    EClass getConditionalTextareaDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle
     * <em>Textfield Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Textfield Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle
     * @generated
     */
    EClass getTextfieldDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle#getBackgroundColor()
     * @see #getTextfieldDescriptionStyle()
     * @generated
     */
    EReference getTextfieldDescriptionStyle_BackgroundColor();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle#getForegroundColor <em>Foreground
     * Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Foreground Color</em>'.
     * @see org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle#getForegroundColor()
     * @see #getTextfieldDescriptionStyle()
     * @generated
     */
    EReference getTextfieldDescriptionStyle_ForegroundColor();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle <em>Conditional Textfield
     * Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Textfield Description Style</em>'.
     * @see org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle
     * @generated
     */
    EClass getConditionalTextfieldDescriptionStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.form.FlexDirection <em>Flex
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Flex Direction</em>'.
     * @see org.eclipse.sirius.components.view.form.FlexDirection
     * @generated
     */
    EEnum getFlexDirection();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.form.GroupDisplayMode <em>Group
     * Display Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Group Display Mode</em>'.
     * @see org.eclipse.sirius.components.view.form.GroupDisplayMode
     * @generated
     */
    EEnum getGroupDisplayMode();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    FormFactory getFormFactory();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.FormDescriptionImpl
         * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.FormDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getFormDescription()
         * @generated
         */
        EClass FORM_DESCRIPTION = eINSTANCE.getFormDescription();

        /**
         * The meta object literal for the '<em><b>Pages</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference FORM_DESCRIPTION__PAGES = eINSTANCE.getFormDescription_Pages();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl
         * <em>Page Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.PageDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getPageDescription()
         * @generated
         */
        EClass PAGE_DESCRIPTION = eINSTANCE.getPageDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PAGE_DESCRIPTION__NAME = eINSTANCE.getPageDescription_Name();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PAGE_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getPageDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PAGE_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getPageDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getPageDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PAGE_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getPageDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PAGE_DESCRIPTION__GROUPS = eINSTANCE.getPageDescription_Groups();

        /**
         * The meta object literal for the '<em><b>Toolbar Actions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PAGE_DESCRIPTION__TOOLBAR_ACTIONS = eINSTANCE.getPageDescription_ToolbarActions();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl
         * <em>Group Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.GroupDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getGroupDescription()
         * @generated
         */
        EClass GROUP_DESCRIPTION = eINSTANCE.getGroupDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__NAME = eINSTANCE.getGroupDescription_Name();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getGroupDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Display Mode</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__DISPLAY_MODE = eINSTANCE.getGroupDescription_DisplayMode();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getGroupDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Toolbar Actions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GROUP_DESCRIPTION__TOOLBAR_ACTIONS = eINSTANCE.getGroupDescription_ToolbarActions();

        /**
         * The meta object literal for the '<em><b>Widgets</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference GROUP_DESCRIPTION__WIDGETS = eINSTANCE.getGroupDescription_Widgets();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl
         * <em>Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getWidgetDescription()
         * @generated
         */
        EClass WIDGET_DESCRIPTION = eINSTANCE.getWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute WIDGET_DESCRIPTION__NAME = eINSTANCE.getWidgetDescription_Name();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute WIDGET_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getWidgetDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Help Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute WIDGET_DESCRIPTION__HELP_EXPRESSION = eINSTANCE.getWidgetDescription_HelpExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl
         * <em>Bar Chart Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getBarChartDescription()
         * @generated
         */
        EClass BAR_CHART_DESCRIPTION = eINSTANCE.getBarChartDescription();

        /**
         * The meta object literal for the '<em><b>Values Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__VALUES_EXPRESSION = eINSTANCE.getBarChartDescription_ValuesExpression();

        /**
         * The meta object literal for the '<em><b>Keys Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__KEYS_EXPRESSION = eINSTANCE.getBarChartDescription_KeysExpression();

        /**
         * The meta object literal for the '<em><b>YAxis Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION = eINSTANCE.getBarChartDescription_YAxisLabelExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BAR_CHART_DESCRIPTION__STYLE = eINSTANCE.getBarChartDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getBarChartDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Width</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__WIDTH = eINSTANCE.getBarChartDescription_Width();

        /**
         * The meta object literal for the '<em><b>Height</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION__HEIGHT = eINSTANCE.getBarChartDescription_Height();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl
         * <em>Button Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getButtonDescription()
         * @generated
         */
        EClass BUTTON_DESCRIPTION = eINSTANCE.getButtonDescription();

        /**
         * The meta object literal for the '<em><b>Button Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION = eINSTANCE.getButtonDescription_ButtonLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION__BODY = eINSTANCE.getButtonDescription_Body();

        /**
         * The meta object literal for the '<em><b>Image Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION__IMAGE_EXPRESSION = eINSTANCE.getButtonDescription_ImageExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION__STYLE = eINSTANCE.getButtonDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getButtonDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getButtonDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionImpl
         * <em>Checkbox Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getCheckboxDescription()
         */
        EClass CHECKBOX_DESCRIPTION = eINSTANCE.getCheckboxDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CHECKBOX_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getCheckboxDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION__BODY = eINSTANCE.getCheckboxDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION__STYLE = eINSTANCE.getCheckboxDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getCheckboxDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CHECKBOX_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getCheckboxDescription_IsEnabledExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl <em>Flexbox Container
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.FlexboxContainerDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getFlexboxContainerDescription()
         */
        EClass FLEXBOX_CONTAINER_DESCRIPTION = eINSTANCE.getFlexboxContainerDescription();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN = eINSTANCE.getFlexboxContainerDescription_Children();

        /**
         * The meta object literal for the '<em><b>Flex Direction</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION = eINSTANCE.getFlexboxContainerDescription_FlexDirection();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getFlexboxContainerDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.ImageDescriptionImpl
         * <em>Image Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.ImageDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getImageDescription()
         */
        EClass IMAGE_DESCRIPTION = eINSTANCE.getImageDescription();

        /**
         * The meta object literal for the '<em><b>Url Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_DESCRIPTION__URL_EXPRESSION = eINSTANCE.getImageDescription_UrlExpression();

        /**
         * The meta object literal for the '<em><b>Max Width Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION = eINSTANCE.getImageDescription_MaxWidthExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.LabelDescriptionImpl
         * <em>Label Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.LabelDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLabelDescription()
         * @generated
         */
        EClass LABEL_DESCRIPTION = eINSTANCE.getLabelDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getLabelDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LABEL_DESCRIPTION__STYLE = eINSTANCE.getLabelDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LABEL_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getLabelDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.LinkDescriptionImpl
         * <em>Link Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.LinkDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLinkDescription()
         * @generated
         */
        EClass LINK_DESCRIPTION = eINSTANCE.getLinkDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LINK_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getLinkDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LINK_DESCRIPTION__STYLE = eINSTANCE.getLinkDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LINK_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getLinkDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.ListDescriptionImpl
         * <em>List Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ListDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getListDescription()
         * @generated
         */
        EClass LIST_DESCRIPTION = eINSTANCE.getListDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getListDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Display Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__DISPLAY_EXPRESSION = eINSTANCE.getListDescription_DisplayExpression();

        /**
         * The meta object literal for the '<em><b>Is Deletable Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION = eINSTANCE.getListDescription_IsDeletableExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION__BODY = eINSTANCE.getListDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION__STYLE = eINSTANCE.getListDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getListDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getListDescription_IsEnabledExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl <em>Multi Select
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getMultiSelectDescription()
         */
        EClass MULTI_SELECT_DESCRIPTION = eINSTANCE.getMultiSelectDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getMultiSelectDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getMultiSelectDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Candidate Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = eINSTANCE.getMultiSelectDescription_CandidateLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION__BODY = eINSTANCE.getMultiSelectDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION__STYLE = eINSTANCE.getMultiSelectDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getMultiSelectDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getMultiSelectDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.PieChartDescriptionImpl
         * <em>Pie Chart Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.PieChartDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getPieChartDescription()
         */
        EClass PIE_CHART_DESCRIPTION = eINSTANCE.getPieChartDescription();

        /**
         * The meta object literal for the '<em><b>Values Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION__VALUES_EXPRESSION = eINSTANCE.getPieChartDescription_ValuesExpression();

        /**
         * The meta object literal for the '<em><b>Keys Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION__KEYS_EXPRESSION = eINSTANCE.getPieChartDescription_KeysExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PIE_CHART_DESCRIPTION__STYLE = eINSTANCE.getPieChartDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PIE_CHART_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getPieChartDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.RadioDescriptionImpl
         * <em>Radio Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.RadioDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getRadioDescription()
         * @generated
         */
        EClass RADIO_DESCRIPTION = eINSTANCE.getRadioDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getRadioDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getRadioDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Candidate Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = eINSTANCE.getRadioDescription_CandidateLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION__BODY = eINSTANCE.getRadioDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION__STYLE = eINSTANCE.getRadioDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getRadioDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RADIO_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getRadioDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl
         * <em>Rich Text Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getRichTextDescription()
         */
        EClass RICH_TEXT_DESCRIPTION = eINSTANCE.getRichTextDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getRichTextDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference RICH_TEXT_DESCRIPTION__BODY = eINSTANCE.getRichTextDescription_Body();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getRichTextDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.SelectDescriptionImpl
         * <em>Select Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.SelectDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getSelectDescription()
         */
        EClass SELECT_DESCRIPTION = eINSTANCE.getSelectDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getSelectDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__CANDIDATES_EXPRESSION = eINSTANCE.getSelectDescription_CandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Candidate Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION = eINSTANCE.getSelectDescription_CandidateLabelExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION__BODY = eINSTANCE.getSelectDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION__STYLE = eINSTANCE.getSelectDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getSelectDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getSelectDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.TextAreaDescriptionImpl
         * <em>Text Area Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.TextAreaDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextAreaDescription()
         */
        EClass TEXT_AREA_DESCRIPTION = eINSTANCE.getTextAreaDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXT_AREA_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getTextAreaDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXT_AREA_DESCRIPTION__BODY = eINSTANCE.getTextAreaDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXT_AREA_DESCRIPTION__STYLE = eINSTANCE.getTextAreaDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXT_AREA_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getTextAreaDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXT_AREA_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getTextAreaDescription_IsEnabledExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionImpl
         * <em>Textfield Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextfieldDescription()
         */
        EClass TEXTFIELD_DESCRIPTION = eINSTANCE.getTextfieldDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTFIELD_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getTextfieldDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION__BODY = eINSTANCE.getTextfieldDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION__STYLE = eINSTANCE.getTextfieldDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getTextfieldDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TEXTFIELD_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getTextfieldDescription_IsEnabledExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionStyleImpl <em>Widget Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.form.impl.WidgetDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getWidgetDescriptionStyle()
         */
        EClass WIDGET_DESCRIPTION_STYLE = eINSTANCE.getWidgetDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionStyleImpl <em>Bar Chart Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.BarChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getBarChartDescriptionStyle()
         * @generated
         */
        EClass BAR_CHART_DESCRIPTION_STYLE = eINSTANCE.getBarChartDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Bars Color</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR = eINSTANCE.getBarChartDescriptionStyle_BarsColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl <em>Conditional
         * Bar Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalBarChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalBarChartDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE = eINSTANCE.getConditionalBarChartDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionStyleImpl <em>Button Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ButtonDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getButtonDescriptionStyle()
         * @generated
         */
        EClass BUTTON_DESCRIPTION_STYLE = eINSTANCE.getButtonDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getButtonDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getButtonDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalButtonDescriptionStyleImpl <em>Conditional
         * Button Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalButtonDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalButtonDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_BUTTON_DESCRIPTION_STYLE = eINSTANCE.getConditionalButtonDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl <em>Checkbox Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getCheckboxDescriptionStyle()
         * @generated
         */
        EClass CHECKBOX_DESCRIPTION_STYLE = eINSTANCE.getCheckboxDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference CHECKBOX_DESCRIPTION_STYLE__COLOR = eINSTANCE.getCheckboxDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl <em>Conditional
         * Checkbox Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalCheckboxDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE = eINSTANCE.getConditionalCheckboxDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.LabelDescriptionStyleImpl <em>Label Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.LabelDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLabelDescriptionStyle()
         * @generated
         */
        EClass LABEL_DESCRIPTION_STYLE = eINSTANCE.getLabelDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference LABEL_DESCRIPTION_STYLE__COLOR = eINSTANCE.getLabelDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalLabelDescriptionStyleImpl <em>Conditional
         * Label Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalLabelDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalLabelDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_LABEL_DESCRIPTION_STYLE = eINSTANCE.getConditionalLabelDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.LinkDescriptionStyleImpl
         * <em>Link Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.LinkDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getLinkDescriptionStyle()
         * @generated
         */
        EClass LINK_DESCRIPTION_STYLE = eINSTANCE.getLinkDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference LINK_DESCRIPTION_STYLE__COLOR = eINSTANCE.getLinkDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalLinkDescriptionStyleImpl <em>Conditional Link
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalLinkDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalLinkDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_LINK_DESCRIPTION_STYLE = eINSTANCE.getConditionalLinkDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.impl.ListDescriptionStyleImpl
         * <em>List Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ListDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getListDescriptionStyle()
         * @generated
         */
        EClass LIST_DESCRIPTION_STYLE = eINSTANCE.getListDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference LIST_DESCRIPTION_STYLE__COLOR = eINSTANCE.getListDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalListDescriptionStyleImpl <em>Conditional List
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalListDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalListDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_LIST_DESCRIPTION_STYLE = eINSTANCE.getConditionalListDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl <em>Multi Select
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getMultiSelectDescriptionStyle()
         * @generated
         */
        EClass MULTI_SELECT_DESCRIPTION_STYLE = eINSTANCE.getMultiSelectDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getMultiSelectDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getMultiSelectDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON = eINSTANCE.getMultiSelectDescriptionStyle_ShowIcon();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalMultiSelectDescriptionStyleImpl
         * <em>Conditional Multi Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalMultiSelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalMultiSelectDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE = eINSTANCE.getConditionalMultiSelectDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.PieChartDescriptionStyleImpl <em>Pie Chart Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.PieChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getPieChartDescriptionStyle()
         * @generated
         */
        EClass PIE_CHART_DESCRIPTION_STYLE = eINSTANCE.getPieChartDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Colors</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION_STYLE__COLORS = eINSTANCE.getPieChartDescriptionStyle_Colors();

        /**
         * The meta object literal for the '<em><b>Stroke Width</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH = eINSTANCE.getPieChartDescriptionStyle_StrokeWidth();

        /**
         * The meta object literal for the '<em><b>Stroke Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR = eINSTANCE.getPieChartDescriptionStyle_StrokeColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalPieChartDescriptionStyleImpl <em>Conditional
         * Pie Chart Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalPieChartDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalPieChartDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE = eINSTANCE.getConditionalPieChartDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.RadioDescriptionStyleImpl <em>Radio Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.RadioDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getRadioDescriptionStyle()
         * @generated
         */
        EClass RADIO_DESCRIPTION_STYLE = eINSTANCE.getRadioDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference RADIO_DESCRIPTION_STYLE__COLOR = eINSTANCE.getRadioDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalRadioDescriptionStyleImpl <em>Conditional
         * Radio Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalRadioDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalRadioDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_RADIO_DESCRIPTION_STYLE = eINSTANCE.getConditionalRadioDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.SelectDescriptionStyleImpl <em>Select Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.SelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getSelectDescriptionStyle()
         * @generated
         */
        EClass SELECT_DESCRIPTION_STYLE = eINSTANCE.getSelectDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getSelectDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getSelectDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECT_DESCRIPTION_STYLE__SHOW_ICON = eINSTANCE.getSelectDescriptionStyle_ShowIcon();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalSelectDescriptionStyleImpl <em>Conditional
         * Select Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalSelectDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalSelectDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_SELECT_DESCRIPTION_STYLE = eINSTANCE.getConditionalSelectDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.TextareaDescriptionStyleImpl <em>Textarea Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.TextareaDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextareaDescriptionStyle()
         * @generated
         */
        EClass TEXTAREA_DESCRIPTION_STYLE = eINSTANCE.getTextareaDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getTextareaDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getTextareaDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl <em>Conditional
         * Textarea Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalTextareaDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE = eINSTANCE.getConditionalTextareaDescriptionStyle();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionStyleImpl <em>Textfield Description
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.TextfieldDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getTextfieldDescriptionStyle()
         * @generated
         */
        EClass TEXTFIELD_DESCRIPTION_STYLE = eINSTANCE.getTextfieldDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR = eINSTANCE.getTextfieldDescriptionStyle_BackgroundColor();

        /**
         * The meta object literal for the '<em><b>Foreground Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR = eINSTANCE.getTextfieldDescriptionStyle_ForegroundColor();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextfieldDescriptionStyleImpl <em>Conditional
         * Textfield Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.impl.ConditionalTextfieldDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getConditionalTextfieldDescriptionStyle()
         * @generated
         */
        EClass CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE = eINSTANCE.getConditionalTextfieldDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.FlexDirection <em>Flex
         * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.FlexDirection
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getFlexDirection()
         * @generated
         */
        EEnum FLEX_DIRECTION = eINSTANCE.getFlexDirection();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.form.GroupDisplayMode <em>Group
         * Display Mode</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.form.GroupDisplayMode
         * @see org.eclipse.sirius.components.view.form.impl.FormPackageImpl#getGroupDisplayMode()
         * @generated
         */
        EEnum GROUP_DISPLAY_MODE = eINSTANCE.getGroupDisplayMode();

    }

} // FormPackage
