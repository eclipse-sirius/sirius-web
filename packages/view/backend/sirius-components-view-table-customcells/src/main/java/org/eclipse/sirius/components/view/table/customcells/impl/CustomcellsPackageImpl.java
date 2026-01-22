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
package org.eclipse.sirius.components.view.table.customcells.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.table.TablePackage;
import org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription;
import org.eclipse.sirius.components.view.table.customcells.CustomcellsFactory;
import org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CustomcellsPackageImpl extends EPackageImpl implements CustomcellsPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cellCheckboxWidgetDescriptionEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CustomcellsPackageImpl()
	{
		super(eNS_URI, CustomcellsFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link CustomcellsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CustomcellsPackage init()
	{
		if (isInited) return (CustomcellsPackage)EPackage.Registry.INSTANCE.getEPackage(CustomcellsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCustomcellsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CustomcellsPackageImpl theCustomcellsPackage = registeredCustomcellsPackage instanceof CustomcellsPackageImpl ? (CustomcellsPackageImpl)registeredCustomcellsPackage : new CustomcellsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		TablePackage.eINSTANCE.eClass();
		ViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCustomcellsPackage.createPackageContents();

		// Initialize created meta-data
		theCustomcellsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCustomcellsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CustomcellsPackage.eNS_URI, theCustomcellsPackage);
		return theCustomcellsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCellCheckboxWidgetDescription()
	{
		return cellCheckboxWidgetDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCellCheckboxWidgetDescription_Body()
	{
		return (EReference)cellCheckboxWidgetDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CustomcellsFactory getCustomcellsFactory()
	{
		return (CustomcellsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		cellCheckboxWidgetDescriptionEClass = createEClass(CELL_CHECKBOX_WIDGET_DESCRIPTION);
		createEReference(cellCheckboxWidgetDescriptionEClass, CELL_CHECKBOX_WIDGET_DESCRIPTION__BODY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TablePackage theTablePackage = (TablePackage)EPackage.Registry.INSTANCE.getEPackage(TablePackage.eNS_URI);
		ViewPackage theViewPackage = (ViewPackage)EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		cellCheckboxWidgetDescriptionEClass.getESuperTypes().add(theTablePackage.getCellWidgetDescription());

		// Initialize classes, features, and operations; add parameters
		initEClass(cellCheckboxWidgetDescriptionEClass, CellCheckboxWidgetDescription.class, "CellCheckboxWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCellCheckboxWidgetDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, CellCheckboxWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CustomcellsPackageImpl
