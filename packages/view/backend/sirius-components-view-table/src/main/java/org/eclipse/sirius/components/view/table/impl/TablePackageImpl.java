/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowContextMenuEntry;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TableFactory;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class TablePackageImpl extends EPackageImpl implements TablePackage {

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass tableDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass columnDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass rowDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cellDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cellWidgetDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cellTextfieldWidgetDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cellLabelWidgetDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cellTextareaWidgetDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass rowFilterDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass rowContextMenuEntryEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.table.TablePackage#eNS_URI
     * @see #init()
     */
    private TablePackageImpl() {
		super(eNS_URI, TableFactory.eINSTANCE);
	}

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TablePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static TablePackage init() {
		if (isInited) return (TablePackage)EPackage.Registry.INSTANCE.getEPackage(TablePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTablePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TablePackageImpl theTablePackage = registeredTablePackage instanceof TablePackageImpl ? (TablePackageImpl)registeredTablePackage : new TablePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTablePackage.createPackageContents();

		// Initialize created meta-data
		theTablePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTablePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TablePackage.eNS_URI, theTablePackage);
		return theTablePackage;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTableDescription() {
		return tableDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTableDescription_UseStripedRowsExpression() {
		return (EAttribute)tableDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTableDescription_ColumnDescriptions() {
		return (EReference)tableDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTableDescription_RowDescription() {
		return (EReference)tableDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTableDescription_CellDescriptions() {
		return (EReference)tableDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTableDescription_EnableSubRows() {
		return (EAttribute)tableDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTableDescription_RowFilters() {
		return (EReference)tableDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTableDescription_PageSizeOptionsExpression() {
		return (EAttribute)tableDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTableDescription_DefaultPageSizeIndexExpression() {
		return (EAttribute)tableDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getColumnDescription() {
		return columnDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_Name() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_DomainType() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_SemanticCandidatesExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_PreconditionExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_HeaderIndexLabelExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_HeaderLabelExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_HeaderIconExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_InitialWidthExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_IsResizableExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_FilterWidgetExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getColumnDescription_IsSortableExpression() {
		return (EAttribute)columnDescriptionEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getRowDescription() {
		return rowDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_Name() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_SemanticCandidatesExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_HeaderLabelExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_HeaderIconExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_HeaderIndexLabelExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_InitialHeightExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_IsResizableExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getRowDescription_ContextMenuEntries() {
		return (EReference)rowDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_DepthLevelExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowDescription_HasChildrenExpression() {
		return (EAttribute)rowDescriptionEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCellDescription() {
		return cellDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCellDescription_Name() {
		return (EAttribute)cellDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCellDescription_PreconditionExpression() {
		return (EAttribute)cellDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCellDescription_SelectedTargetObjectExpression() {
		return (EAttribute)cellDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCellDescription_ValueExpression() {
		return (EAttribute)cellDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCellDescription_TooltipExpression() {
		return (EAttribute)cellDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getCellDescription_CellWidgetDescription() {
		return (EReference)cellDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCellWidgetDescription() {
		return cellWidgetDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCellTextfieldWidgetDescription() {
		return cellTextfieldWidgetDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getCellTextfieldWidgetDescription_Body() {
		return (EReference)cellTextfieldWidgetDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCellLabelWidgetDescription() {
		return cellLabelWidgetDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCellLabelWidgetDescription_IconExpression() {
		return (EAttribute)cellLabelWidgetDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCellTextareaWidgetDescription() {
		return cellTextareaWidgetDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getCellTextareaWidgetDescription_Body() {
		return (EReference)cellTextareaWidgetDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getRowFilterDescription() {
		return rowFilterDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowFilterDescription_Id() {
		return (EAttribute)rowFilterDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowFilterDescription_LabelExpression() {
		return (EAttribute)rowFilterDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowFilterDescription_InitialStateExpression() {
		return (EAttribute)rowFilterDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getRowContextMenuEntry() {
		return rowContextMenuEntryEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowContextMenuEntry_Name() {
		return (EAttribute)rowContextMenuEntryEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowContextMenuEntry_LabelExpression() {
		return (EAttribute)rowContextMenuEntryEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowContextMenuEntry_IconURLExpression() {
		return (EAttribute)rowContextMenuEntryEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getRowContextMenuEntry_PreconditionExpression() {
		return (EAttribute)rowContextMenuEntryEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getRowContextMenuEntry_Body() {
		return (EReference)rowContextMenuEntryEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TableFactory getTableFactory() {
		return (TableFactory)getEFactoryInstance();
	}

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		tableDescriptionEClass = createEClass(TABLE_DESCRIPTION);
		createEAttribute(tableDescriptionEClass, TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION);
		createEReference(tableDescriptionEClass, TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS);
		createEReference(tableDescriptionEClass, TABLE_DESCRIPTION__ROW_DESCRIPTION);
		createEReference(tableDescriptionEClass, TABLE_DESCRIPTION__CELL_DESCRIPTIONS);
		createEAttribute(tableDescriptionEClass, TABLE_DESCRIPTION__ENABLE_SUB_ROWS);
		createEReference(tableDescriptionEClass, TABLE_DESCRIPTION__ROW_FILTERS);
		createEAttribute(tableDescriptionEClass, TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION);
		createEAttribute(tableDescriptionEClass, TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION);

		columnDescriptionEClass = createEClass(COLUMN_DESCRIPTION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__NAME);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__DOMAIN_TYPE);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION);
		createEAttribute(columnDescriptionEClass, COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION);

		rowDescriptionEClass = createEClass(ROW_DESCRIPTION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__NAME);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__HEADER_ICON_EXPRESSION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION);
		createEReference(rowDescriptionEClass, ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION);
		createEAttribute(rowDescriptionEClass, ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION);

		cellDescriptionEClass = createEClass(CELL_DESCRIPTION);
		createEAttribute(cellDescriptionEClass, CELL_DESCRIPTION__NAME);
		createEAttribute(cellDescriptionEClass, CELL_DESCRIPTION__PRECONDITION_EXPRESSION);
		createEAttribute(cellDescriptionEClass, CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION);
		createEAttribute(cellDescriptionEClass, CELL_DESCRIPTION__VALUE_EXPRESSION);
		createEAttribute(cellDescriptionEClass, CELL_DESCRIPTION__TOOLTIP_EXPRESSION);
		createEReference(cellDescriptionEClass, CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION);

		cellWidgetDescriptionEClass = createEClass(CELL_WIDGET_DESCRIPTION);

		cellTextfieldWidgetDescriptionEClass = createEClass(CELL_TEXTFIELD_WIDGET_DESCRIPTION);
		createEReference(cellTextfieldWidgetDescriptionEClass, CELL_TEXTFIELD_WIDGET_DESCRIPTION__BODY);

		cellLabelWidgetDescriptionEClass = createEClass(CELL_LABEL_WIDGET_DESCRIPTION);
		createEAttribute(cellLabelWidgetDescriptionEClass, CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION);

		rowContextMenuEntryEClass = createEClass(ROW_CONTEXT_MENU_ENTRY);
		createEAttribute(rowContextMenuEntryEClass, ROW_CONTEXT_MENU_ENTRY__NAME);
		createEAttribute(rowContextMenuEntryEClass, ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION);
		createEAttribute(rowContextMenuEntryEClass, ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION);
		createEAttribute(rowContextMenuEntryEClass, ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION);
		createEReference(rowContextMenuEntryEClass, ROW_CONTEXT_MENU_ENTRY__BODY);

		cellTextareaWidgetDescriptionEClass = createEClass(CELL_TEXTAREA_WIDGET_DESCRIPTION);
		createEReference(cellTextareaWidgetDescriptionEClass, CELL_TEXTAREA_WIDGET_DESCRIPTION__BODY);

		rowFilterDescriptionEClass = createEClass(ROW_FILTER_DESCRIPTION);
		createEAttribute(rowFilterDescriptionEClass, ROW_FILTER_DESCRIPTION__ID);
		createEAttribute(rowFilterDescriptionEClass, ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION);
		createEAttribute(rowFilterDescriptionEClass, ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION);
	}

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ViewPackage theViewPackage = (ViewPackage)EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tableDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
		cellTextfieldWidgetDescriptionEClass.getESuperTypes().add(this.getCellWidgetDescription());
		cellLabelWidgetDescriptionEClass.getESuperTypes().add(this.getCellWidgetDescription());
		cellTextareaWidgetDescriptionEClass.getESuperTypes().add(this.getCellWidgetDescription());

		// Initialize classes, features, and operations; add parameters
		initEClass(tableDescriptionEClass, TableDescription.class, "TableDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTableDescription_UseStripedRowsExpression(), theViewPackage.getInterpretedExpression(), "useStripedRowsExpression", null, 0, 1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableDescription_ColumnDescriptions(), this.getColumnDescription(), null, "columnDescriptions", null, 0, -1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTableDescription_ColumnDescriptions().getEKeys().add(this.getColumnDescription_Name());
		initEReference(getTableDescription_RowDescription(), this.getRowDescription(), null, "rowDescription", null, 0, 1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTableDescription_RowDescription().getEKeys().add(this.getRowDescription_Name());
		initEReference(getTableDescription_CellDescriptions(), this.getCellDescription(), null, "cellDescriptions", null, 0, -1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTableDescription_CellDescriptions().getEKeys().add(this.getCellDescription_Name());
		initEAttribute(getTableDescription_EnableSubRows(), ecorePackage.getEBoolean(), "enableSubRows", null, 1, 1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableDescription_RowFilters(), this.getRowFilterDescription(), null, "rowFilters", null, 0, -1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTableDescription_RowFilters().getEKeys().add(this.getRowFilterDescription_Id());
		initEAttribute(getTableDescription_PageSizeOptionsExpression(), theViewPackage.getInterpretedExpression(), "pageSizeOptionsExpression", null, 0, 1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableDescription_DefaultPageSizeIndexExpression(), theViewPackage.getInterpretedExpression(), "defaultPageSizeIndexExpression", null, 0, 1, TableDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnDescriptionEClass, ColumnDescription.class, "ColumnDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumnDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_DomainType(), theViewPackage.getDomainType(), "domainType", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", null, 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_HeaderIndexLabelExpression(), theViewPackage.getInterpretedExpression(), "headerIndexLabelExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_HeaderLabelExpression(), theViewPackage.getInterpretedExpression(), "headerLabelExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_HeaderIconExpression(), theViewPackage.getInterpretedExpression(), "headerIconExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_InitialWidthExpression(), theViewPackage.getInterpretedExpression(), "initialWidthExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_IsResizableExpression(), theViewPackage.getInterpretedExpression(), "isResizableExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_FilterWidgetExpression(), theViewPackage.getInterpretedExpression(), "filterWidgetExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumnDescription_IsSortableExpression(), theViewPackage.getInterpretedExpression(), "isSortableExpression", "", 0, 1, ColumnDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rowDescriptionEClass, RowDescription.class, "RowDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRowDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "", 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_HeaderLabelExpression(), theViewPackage.getInterpretedExpression(), "headerLabelExpression", "", 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_HeaderIconExpression(), theViewPackage.getInterpretedExpression(), "headerIconExpression", "", 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_HeaderIndexLabelExpression(), theViewPackage.getInterpretedExpression(), "headerIndexLabelExpression", null, 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_InitialHeightExpression(), theViewPackage.getInterpretedExpression(), "initialHeightExpression", "", 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_IsResizableExpression(), theViewPackage.getInterpretedExpression(), "isResizableExpression", "", 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRowDescription_ContextMenuEntries(), this.getRowContextMenuEntry(), null, "contextMenuEntries", null, 0, -1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_DepthLevelExpression(), theViewPackage.getInterpretedExpression(), "depthLevelExpression", null, 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowDescription_HasChildrenExpression(), theViewPackage.getInterpretedExpression(), "hasChildrenExpression", null, 0, 1, RowDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cellDescriptionEClass, CellDescription.class, "CellDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCellDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, CellDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCellDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", "", 0, 1, CellDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCellDescription_SelectedTargetObjectExpression(), theViewPackage.getInterpretedExpression(), "selectedTargetObjectExpression", "", 0, 1, CellDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCellDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", "", 0, 1, CellDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCellDescription_TooltipExpression(), theViewPackage.getInterpretedExpression(), "tooltipExpression", "", 0, 1, CellDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCellDescription_CellWidgetDescription(), this.getCellWidgetDescription(), null, "cellWidgetDescription", null, 0, 1, CellDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cellWidgetDescriptionEClass, CellWidgetDescription.class, "CellWidgetDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(cellTextfieldWidgetDescriptionEClass, CellTextfieldWidgetDescription.class, "CellTextfieldWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCellTextfieldWidgetDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, CellTextfieldWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cellLabelWidgetDescriptionEClass, CellLabelWidgetDescription.class, "CellLabelWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCellLabelWidgetDescription_IconExpression(), theViewPackage.getInterpretedExpression(), "iconExpression", "", 0, 1, CellLabelWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rowContextMenuEntryEClass, RowContextMenuEntry.class, "RowContextMenuEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRowContextMenuEntry_Name(), theViewPackage.getIdentifier(), "name", null, 1, 1, RowContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowContextMenuEntry_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, RowContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowContextMenuEntry_IconURLExpression(), theViewPackage.getInterpretedExpression(), "iconURLExpression", null, 0, 1, RowContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowContextMenuEntry_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, RowContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRowContextMenuEntry_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, RowContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cellTextareaWidgetDescriptionEClass, CellTextareaWidgetDescription.class, "CellTextareaWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCellTextareaWidgetDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, CellTextareaWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rowFilterDescriptionEClass, RowFilterDescription.class, "RowFilterDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRowFilterDescription_Id(), theViewPackage.getIdentifier(), "id", null, 1, 1, RowFilterDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowFilterDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, RowFilterDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRowFilterDescription_InitialStateExpression(), theViewPackage.getInterpretedExpression(), "initialStateExpression", "", 0, 1, RowFilterDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // TablePackageImpl
