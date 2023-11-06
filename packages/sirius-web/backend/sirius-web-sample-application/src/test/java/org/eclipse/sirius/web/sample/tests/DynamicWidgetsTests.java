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
package org.eclipse.sirius.web.sample.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import fr.obeo.dsl.designer.sample.flow.FlowFactory;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.System;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IDefaultObjectService;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.services.ComposedObjectService;
import org.eclipse.sirius.components.emf.services.DefaultObjectService;
import org.eclipse.sirius.components.emf.services.EMFKindService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.util.DiagramAdapterFactory;
import org.eclipse.sirius.components.view.emf.ViewConverter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.util.FormAdapterFactory;
import org.eclipse.sirius.components.view.util.ViewAdapterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Tests the rendering of View-based Form descriptions which use the For & If constructs.
 *
 * @author pcdavid
 */
public class DynamicWidgetsTests {

    private View viewFixture;

    private EditingContext editingContext;

    private System system;

    private ComposedAdapterFactory composedAdapterFactory;

    @BeforeEach
    public void setup() {
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        this.composedAdapterFactory = new ComposedAdapterFactory();
        this.composedAdapterFactory.addAdapterFactory(new ViewAdapterFactory());
        this.composedAdapterFactory.addAdapterFactory(new DiagramAdapterFactory());
        this.composedAdapterFactory.addAdapterFactory(new FormAdapterFactory());
        this.composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        this.composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        editingDomain.setAdapterFactory(this.composedAdapterFactory);
        editingDomain.getResourceSet().getPackageRegistry().put(FlowPackage.eNS_URI, FlowPackage.eINSTANCE);
        this.editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of());

        this.system = FlowFactory.eINSTANCE.createSystem();
        this.system.setName("Robot");
        this.system.eAdapters().add(new IDAdapter(UUID.randomUUID()));

        this.viewFixture = this.loadFixture(editingDomain, "DynamicWidgetsFixture.view");
    }

    @Test
    void testConditionalWidgetRendering() {
        var formDescription = this.findFormByName("Test: Single If");

        // Render on a system with a space in its name: we expect a warning label
        this.system.setName("Sample System");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("The system name should not contain space characters"));

        // "Fix" the warning and re-render: the label widget should no longer be rendered
        this.system.setName("Sample_System");
        form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of());
    }

    @Test
    void testSimpleFor() {
        var formDescription = this.findFormByName("Test: Simple For");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("foo:Robot", "bar:Robot"));
    }

    @Test
    void testForWithTwoIfs() {
        var formDescription = this.findFormByName("Test: For with two Ifs");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("got foo", "foo:Robot", "bar:Robot"));
    }

    @Test
    void testNestedFors() {
        var formDescription = this.findFormByName("Test: nested For");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("(a, a)", "(b, b)", "(c, c)"));
    }

    @Test
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    void testForOnModelContent() {
        this.system.setName("Sample");
        var formDescription = this.findFormByName("Test: for each letter");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("S", "a", "m", "p", "l", "e"));
        // All the letter are different, and the widget's name include the letter, so all widgets should have distinct ids
        assertThat(form.getPages().get(0).getGroups().get(0).getWidgets().stream().map(AbstractWidget::getId).distinct()).hasSize(form.getPages().get(0).getGroups().get(0).getWidgets().size());
        String letter6Id = form.getPages().get(0).getGroups().get(0).getWidgets().get(5).getId();

        this.system.setName("Renamed");
        form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("R", "e", "n", "a", "m", "e", "d"));
        String letter2Id = form.getPages().get(0).getGroups().get(0).getWidgets().get(1).getId();
        String renamedLetter6Id = form.getPages().get(0).getGroups().get(0).getWidgets().get(5).getId();
        // All labels with for "e" have the same widget label, so the same id...
        assertThat(letter6Id).isEqualTo(letter2Id);
        assertThat(letter6Id).isEqualTo(renamedLetter6Id);
    }

    @Test
    void testNestedIf() throws Exception {
        // Valid name => widget rendered
        this.system.setName("Sample");
        var formDescription = this.findFormByName("Test: nested If");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of("Sample"));

        // Name too short => widget NOT rendered
        this.system.setName("e");
        form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of());

        // Name long enough but without an 'e' => widget NOT rendered
        this.system.setName("Big Robot");
        form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        this.assertHasLabelValues(form, List.of());
    }

    @Test
    void testFlexbox() throws Exception {
        var formDescription = this.findFormByName("Test: Flexbox");
        Form form = this.render(formDescription, this.system);
        assertThat(form).isNotNull();
        var renderedWidgets = form.getPages().get(0).getGroups().get(0).getWidgets();
        // 1 Flexbox
        assertThat(renderedWidgets).hasSize(1);
        assertThat(renderedWidgets.get(0)).isInstanceOf(FlexboxContainer.class);
        FlexboxContainer topFlexbox = (FlexboxContainer) renderedWidgets.get(0);

        // with two sub-flexboxes, 1 horizontal and one vertical
        assertThat(topFlexbox.getChildren()).hasSize(2);
        assertThat(topFlexbox.getChildren().get(0)).isInstanceOf(FlexboxContainer.class);
        assertThat(topFlexbox.getChildren().get(1)).isInstanceOf(FlexboxContainer.class);
        FlexboxContainer hFlexbox = (FlexboxContainer) topFlexbox.getChildren().get(0);
        assertThat(hFlexbox.getFlexDirection()).isEqualTo("row");
        FlexboxContainer vFlexbox = (FlexboxContainer) topFlexbox.getChildren().get(1);
        assertThat(vFlexbox.getFlexDirection()).isEqualTo("column");

        // each with a label and a button
        assertThat(hFlexbox.getChildren()).hasSize(2);
        assertThat(hFlexbox.getChildren().get(0)).isInstanceOf(LabelWidget.class);
        assertThat(((LabelWidget) hFlexbox.getChildren().get(0)).getValue()).isEqualTo(this.system.getName());
        assertThat(hFlexbox.getChildren().get(1)).isInstanceOf(Button.class);

        assertThat(vFlexbox.getChildren()).hasSize(2);
        assertThat(vFlexbox.getChildren().get(0)).isInstanceOf(LabelWidget.class);
        assertThat(((LabelWidget) vFlexbox.getChildren().get(0)).getValue()).isEqualTo(this.system.getName());
        assertThat(vFlexbox.getChildren().get(1)).isInstanceOf(Button.class);
    }

    private void assertHasLabelValues(Form form, List<String> labelValues) {
        assertThat(form).isNotNull();
        var renderedWidgets = form.getPages().get(0).getGroups().get(0).getWidgets();
        assertThat(renderedWidgets).hasSize(labelValues.size());
        for (int i = 0; i < renderedWidgets.size(); i++) {
            assertThat(renderedWidgets.get(i)).isInstanceOf(LabelWidget.class);
            LabelWidget widget = (LabelWidget) renderedWidgets.get(i);
            assertThat(widget.getValue()).isEqualTo(labelValues.get(i));
        }
    }

    private FormDescription findFormByName(String name) {
        return (FormDescription) this.viewFixture.getDescriptions().stream()
                .filter(description -> name.equals(description.getName()))
                .findFirst()
                .orElseGet(() -> {
                    fail("Could not find a Form description named " + name);
                    return null;
                });
    }

    private View loadFixture(AdapterFactoryEditingDomain editingDomain, String viewResourcePath) {
        ResourceSet resourceSet = editingDomain.getResourceSet();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        Resource resource = null;
        ClassPathResource classPathResource = new ClassPathResource(viewResourcePath);
        try (var inputStream = classPathResource.getInputStream()) {
            resource = new XMIResourceImpl(URI.createURI(classPathResource.getFilename()));
            resource.load(inputStream, new EMFResourceUtils().getXMILoadOptions());
            resourceSet.getResources().add(resource);
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
        assertThat(resource).isNotNull();
        assertThat(resource.getContents()).hasSize(1);
        assertThat(resource.getContents().get(0)).isInstanceOf(View.class);
        return (View) resource.getContents().get(0);
    }


    private Form render(FormDescription formDescription, Object target) {
        // Wrap into a View, as expected by ViewConverter
        View wrapperView = ViewFactory.eINSTANCE.createView();
        wrapperView.getDescriptions().add(formDescription);

        IDefaultObjectService defaultObjectService = new DefaultObjectService(new EMFKindService(new URLParser()), this.composedAdapterFactory, new LabelFeatureProviderRegistry());
        IObjectService objectService = new ComposedObjectService(List.of(), defaultObjectService);
        IEditService.NoOp editService = new IEditService.NoOp();
        ViewFormDescriptionConverter formDescriptionConverter = new ViewFormDescriptionConverter(objectService, editService, new IFormIdProvider.NoOp(), List.of(), new IFeedbackMessageService.NoOp());

        var applicationContext = new StaticApplicationContext();
        applicationContext.registerBean(DefaultObjectService.class, new EMFKindService(new URLParser()), this.composedAdapterFactory, new LabelFeatureProviderRegistry());
        applicationContext.registerBean(ComposedObjectService.class, List.of(), defaultObjectService);
        applicationContext.registerBean(editService.getClass());

        var viewConverter = new ViewConverter(List.of(), List.of(formDescriptionConverter), applicationContext, objectService);
        List<IRepresentationDescription> conversionResult = viewConverter.convert(List.of(wrapperView), List.of(EcorePackage.eINSTANCE));
        assertThat(conversionResult).hasSize(1);
        assertThat(conversionResult.get(0)).isInstanceOf(org.eclipse.sirius.components.forms.description.FormDescription.class);
        org.eclipse.sirius.components.forms.description.FormDescription convertedFormDescription = (org.eclipse.sirius.components.forms.description.FormDescription) conversionResult.get(0);

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, target);
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.editingContext);

        FormRenderer formRenderer = new FormRenderer(List.of());
        FormComponentProps props = new FormComponentProps(variableManager, convertedFormDescription, List.of());
        Element element = new Element(FormComponent.class, props);
        return formRenderer.render(element);
    }

}
