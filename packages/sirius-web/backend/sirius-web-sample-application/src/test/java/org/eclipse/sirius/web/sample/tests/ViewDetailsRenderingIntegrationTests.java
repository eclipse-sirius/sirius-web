/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import static org.eclipse.sirius.components.forms.tests.FormAssertions.assertThat;

import fr.obeo.dsl.designer.sample.flow.FlowPackage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.EMFKindService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.components.emf.services.ObjectService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.CompletionRequest;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.util.DiagramAdapterFactory;
import org.eclipse.sirius.components.view.emf.AQLTextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.DomainTypeTextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.ITextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.ViewPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.view.emf.configuration.ViewPropertiesDescriptionServiceConfiguration;
import org.eclipse.sirius.components.view.form.util.FormAdapterFactory;
import org.eclipse.sirius.components.view.util.ViewAdapterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Tests used to validate the behavior of the details representation for View elements.
 *
 * @author pcdavid
 */
public class ViewDetailsRenderingIntegrationTests {

    private AdapterFactoryEditingDomain editingDomain;

    private View view;

    private PageDescription viewPropertiesFormDescription;

    private EditingContext editingContext;

    @BeforeEach
    public void setup() {
        this.editingDomain = new EditingDomainFactory().create();

        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new ViewAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new DiagramAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new FormAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        this.editingDomain.setAdapterFactory(composedAdapterFactory);
        this.editingDomain.getResourceSet().getPackageRegistry().put(FlowPackage.eNS_URI, FlowPackage.eINSTANCE);
        this.editingContext = new EditingContext(UUID.randomUUID().toString(), this.editingDomain, Map.of());

        this.view = this.loadFixture("ViewCompletionFixture.xmi");

        EMFKindService emfKindService = new EMFKindService(new URLParser());
        IObjectService objectService = new ObjectService(emfKindService, composedAdapterFactory, new LabelFeatureProviderRegistry());
        ViewPropertiesDescriptionServiceConfiguration parameters = new ViewPropertiesDescriptionServiceConfiguration(objectService, new IEditService.NoOp(), emfKindService, new IFeedbackMessageService.NoOp());

        // @formatter:off
        List<ITextfieldCustomizer> customizers = List.of(
                new AQLTextfieldCustomizer(new StaticApplicationContext(), List.of()),
                new DomainTypeTextfieldCustomizer()
        );
        // @formatter:on

        // @formatter:off
        new ViewPropertiesDescriptionRegistryConfigurer(
                parameters,
                composedAdapterFactory,
                new IPropertiesValidationProvider.NoOp(),
                new IEMFMessageService.NoOp(),
                customizers)
            .addPropertiesDescriptions(formDescription -> this.viewPropertiesFormDescription = formDescription);
        // @formatter:on
    }

    @Test
    public void testDiagramDescriptionProperties() {
        RepresentationDescription diagramDescription = this.view.getDescriptions().get(0);
        Form propertiesForm = this.renderViewProperties(diagramDescription, this.viewPropertiesFormDescription);
        assertThat(propertiesForm).isNotNull();

        this.checkDomainTypeField(propertiesForm, "Domain Type");
        this.checkExpressionField(propertiesForm, "Title Expression");
    }

    @Test
    public void testFormDescriptionProperties() {
        RepresentationDescription formDescription = this.view.getDescriptions().get(1);
        Form propertiesForm = this.renderViewProperties(formDescription, this.viewPropertiesFormDescription);
        assertThat(propertiesForm).isNotNull();

        this.checkDomainTypeField(propertiesForm, "Domain Type");
        this.checkExpressionField(propertiesForm, "Title Expression");
    }

    private void checkDomainTypeField(Form form, String fieldName) {
        var field = this.getTextareaByLabel(form, fieldName);
        assertThat(field.isSupportsCompletion()).isTrue();
        assertThat(field.getCompletionProposalsProvider()).isNotNull();
        assertThat(field.getStyle()).isNotNull();
        assertThat(field.getStyle().getBackgroundColor()).isEqualTo("#e6f4ee");

        var proposals = this.requestCompletion(field, "flow:", 5);
        assertThat(proposals).hasSize(16);

        proposals = this.requestCompletion(field, "flow::PowerLink", "flow::Power".length());
        List<EClass> candidates = List.of(FlowPackage.Literals.POWER_INPUT, FlowPackage.Literals.POWER_LINK, FlowPackage.Literals.POWER_OUTPUT, FlowPackage.Literals.POWERED);
        this.checkProposalsText(proposals, candidates.stream().map(this::domainTypeName).toList());
    }

    private void checkExpressionField(Form form, String fieldName) {
        var field = this.getTextareaByLabel(form, fieldName);
        assertThat(field.isSupportsCompletion()).isTrue();
        assertThat(field.getCompletionProposalsProvider()).isNotNull();
        assertThat(field.getStyle()).isNotNull();
        assertThat(field.getStyle().getBackgroundColor()).isEqualTo("#fff8e5");
    }

    private View loadFixture(String viewResourcePath) {
        ResourceSet resourceSet = this.editingDomain.getResourceSet();
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

    private List<CompletionProposal> requestCompletion(Textarea textarea, String text, int cursorPosition) {
        assertThat(textarea.isSupportsCompletion()).isTrue();
        var completionProvider = textarea.getCompletionProposalsProvider();
        assertThat(completionProvider).isNotNull();
        var request = new CompletionRequest(text, cursorPosition);
        return completionProvider.apply(request);
    }

    private void checkProposalsText(List<CompletionProposal> actualProposals, List<String> expectedProposalsText) {
        assertThat(actualProposals).hasSize(expectedProposalsText.size());
        for (String expectedText : expectedProposalsText) {
            assertThat(actualProposals).filteredOn(proposal -> proposal.getTextToInsert().equals(expectedText)).singleElement();
        }
    }

    private Textarea getTextareaByLabel(Form form, String label) {
        List<AbstractWidget> widgets = form.getPages().get(0).getGroups().get(0).getWidgets();
        // @formatter:off
        var textareas = widgets.stream()
                .filter(Textarea.class::isInstance)
                .map(Textarea.class::cast)
                .filter(textarea -> Objects.equals(label, textarea.getLabel()))
                .toList();
        assertThat(textareas).hasSize(1);
        return textareas.get(0);
    }

    private Form renderViewProperties(EObject eObject, PageDescription pageDescription) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, List.of(eObject));
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.editingContext);

        FormRenderer formRenderer = new FormRenderer(List.of());

        var formDescription = FormDescription.newFormDescription(UUID.nameUUIDFromBytes("view_properties_description".getBytes()).toString())
                .label("View properties description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(vm -> "Properties")
                .targetObjectIdProvider(vm -> "")
                .canCreatePredicate(vm -> false)
                .pageDescriptions(List.of(pageDescription))
                .build();

        FormComponentProps props = new FormComponentProps(variableManager, formDescription, List.of());
        Element element = new Element(FormComponent.class, props);
        return formRenderer.render(element);
    }

    private String domainTypeName(EClass klass) {
        return String.format("%s::%s", klass.getEPackage().getName(), klass.getName());
    }
}
