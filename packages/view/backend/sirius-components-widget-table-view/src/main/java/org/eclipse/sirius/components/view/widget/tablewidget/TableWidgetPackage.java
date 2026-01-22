/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.widget.tablewidget;

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
 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetFactory
 * @model kind="package"
 * @generated
 */
public interface TableWidgetPackage extends EPackage {

    /**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    String eNAME = "tablewidget";

    /**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_URI = "https://www.eclipse.org/sirius/widgets/tablewidget";

    /**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_PREFIX = "tablewidget";
    /**
	 * The meta object id for the '{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl <em>Description</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetPackageImpl#getTableWidgetDescription()
	 * @generated
	 */
    int TABLE_WIDGET_DESCRIPTION = 0;
    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;
    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;
    /**
	 * The feature id for the '<em><b>Help Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;
    /**
	 * The feature id for the '<em><b>Diagnostics Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION;
    /**
	 * The feature id for the '<em><b>Column Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;
    /**
	 * The feature id for the '<em><b>Row Description</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;
    /**
	 * The feature id for the '<em><b>Cell Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;
    /**
	 * The feature id for the '<em><b>Use Striped Rows Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;
    /**
	 * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;
    /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    TableWidgetPackage eINSTANCE = org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetPackageImpl.init();
    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
	 * The number of operations of the '<em>Description</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int TABLE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription <em>Description</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription
     */
    EClass getTableWidgetDescription();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getColumnDescriptions <em>Column Descriptions</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Column Descriptions</em>'.
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getColumnDescriptions()
	 * @see #getTableWidgetDescription()
	 * @generated
	 */
    EReference getTableWidgetDescription_ColumnDescriptions();

    /**
	 * Returns the meta object for the containment reference '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getRowDescription <em>Row Description</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Row Description</em>'.
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getRowDescription()
	 * @see #getTableWidgetDescription()
	 * @generated
	 */
    EReference getTableWidgetDescription_RowDescription();

    /**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getCellDescriptions <em>Cell Descriptions</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cell Descriptions</em>'.
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getCellDescriptions()
	 * @see #getTableWidgetDescription()
	 * @generated
	 */
    EReference getTableWidgetDescription_CellDescriptions();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getUseStripedRowsExpression <em>Use Striped Rows Expression</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Striped Rows Expression</em>'.
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getUseStripedRowsExpression()
	 * @see #getTableWidgetDescription()
	 * @generated
	 */
    EAttribute getTableWidgetDescription_UseStripedRowsExpression();

    /**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getIsEnabledExpression <em>Is Enabled Expression</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
	 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getIsEnabledExpression()
	 * @see #getTableWidgetDescription()
	 * @generated
	 */
    EAttribute getTableWidgetDescription_IsEnabledExpression();

    /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
    TableWidgetFactory getTableWidgetFactory();

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
	 * @generated
	 */
    interface Literals {

        /**
		 * The meta object literal for the '{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl <em>Description</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl
		 * @see org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetPackageImpl#getTableWidgetDescription()
		 * @generated
		 */
        EClass TABLE_WIDGET_DESCRIPTION = eINSTANCE.getTableWidgetDescription();

        /**
		 * The meta object literal for the '<em><b>Column Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
        EReference TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS = eINSTANCE.getTableWidgetDescription_ColumnDescriptions();

        /**
         * The meta object literal for the '<em><b>Row Description</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION = eINSTANCE.getTableWidgetDescription_RowDescription();

        /**
         * The meta object literal for the '<em><b>Cell Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS = eINSTANCE.getTableWidgetDescription_CellDescriptions();

        /**
         * The meta object literal for the '<em><b>Use Striped Rows Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION = eINSTANCE.getTableWidgetDescription_UseStripedRowsExpression();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getTableWidgetDescription_IsEnabledExpression();

    }

} // TableWidgetPackage
