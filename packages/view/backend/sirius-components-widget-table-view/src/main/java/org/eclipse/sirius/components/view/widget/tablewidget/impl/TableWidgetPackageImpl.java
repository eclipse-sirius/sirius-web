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
package org.eclipse.sirius.components.view.widget.tablewidget.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.table.TablePackage;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetFactory;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class TableWidgetPackageImpl extends EPackageImpl implements TableWidgetPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass tableWidgetDescriptionEClass = null;
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#eNS_URI
     * @see #init()
     */
    private TableWidgetPackageImpl() {
        super(eNS_URI, TableWidgetFactory.eINSTANCE);
    }

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link TableWidgetPackage#eINSTANCE} when that field is accessed. Clients
     * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     */
    public static TableWidgetPackage init() {
        if (isInited)
            return (TableWidgetPackage) EPackage.Registry.INSTANCE.getEPackage(TableWidgetPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredTableWidgetPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        TableWidgetPackageImpl theTableWidgetPackage = registeredTableWidgetPackage instanceof TableWidgetPackageImpl ? (TableWidgetPackageImpl) registeredTableWidgetPackage
                : new TableWidgetPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        FormPackage.eINSTANCE.eClass();
        ViewPackage.eINSTANCE.eClass();
        TablePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theTableWidgetPackage.createPackageContents();

        // Initialize created meta-data
        theTableWidgetPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theTableWidgetPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(TableWidgetPackage.eNS_URI, theTableWidgetPackage);
        return theTableWidgetPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTableWidgetDescription() {
        return this.tableWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTableWidgetDescription_ColumnDescriptions() {
        return (EReference) this.tableWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTableWidgetDescription_RowDescription() {
        return (EReference) this.tableWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTableWidgetDescription_CellDescriptions() {
        return (EReference) this.tableWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTableWidgetDescription_UseStripedRowsExpression() {
        return (EAttribute) this.tableWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTableWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.tableWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TableWidgetFactory getTableWidgetFactory() {
        return (TableWidgetFactory) this.getEFactoryInstance();
    }

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.tableWidgetDescriptionEClass = this.createEClass(TABLE_WIDGET_DESCRIPTION);
        this.createEReference(this.tableWidgetDescriptionEClass, TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS);
        this.createEReference(this.tableWidgetDescriptionEClass, TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION);
        this.createEReference(this.tableWidgetDescriptionEClass, TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS);
        this.createEAttribute(this.tableWidgetDescriptionEClass, TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION);
        this.createEAttribute(this.tableWidgetDescriptionEClass, TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
    }

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        FormPackage theFormPackage = (FormPackage) EPackage.Registry.INSTANCE.getEPackage(FormPackage.eNS_URI);
        TablePackage theTablePackage = (TablePackage) EPackage.Registry.INSTANCE.getEPackage(TablePackage.eNS_URI);
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.tableWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.tableWidgetDescriptionEClass, TableWidgetDescription.class, "TableWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTableWidgetDescription_ColumnDescriptions(), theTablePackage.getColumnDescription(), null, "columnDescriptions", null, 0, -1, TableWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTableWidgetDescription_RowDescription(), theTablePackage.getRowDescription(), null, "rowDescription", null, 0, 1, TableWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTableWidgetDescription_CellDescriptions(), theTablePackage.getCellDescription(), null, "cellDescriptions", null, 0, -1, TableWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTableWidgetDescription_UseStripedRowsExpression(), theViewPackage.getInterpretedExpression(), "useStripedRowsExpression", null, 0, 1, TableWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTableWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, TableWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        this.createResource(eNS_URI);
    }

} // TableWidgetPackageImpl
