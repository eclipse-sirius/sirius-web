/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of ReferenceWidgetDescriptionSetMigrationParticipant.
 *
 * @author tgiraudet
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReferenceWidgetDescriptionSetMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IResourceLoader resourceLoader;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old reference widget without data, when it is loaded, then empty add and set operations are added")
    public void givenOldReferenceWidgetWithoutDataWhenItIsLoadedThenEmptyAddAndSetOperationsAreAdded() {
        var model = this.givenAddMigratedModel();
        model.remove("migration");
        model.getAsJsonArray("content").get(0).getAsJsonObject().remove("data");
        var widget = this.loadReferenceWidget(model);
        assertThat(widget.getAdd()).isNotNull();
        assertThat(widget.getAdd().getBody()).isEmpty();
        assertThat(widget.getSet()).isNotNull();
        assertThat(widget.getSet().getBody()).isEmpty();
        assertThat(widget.eResource().getErrors()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old reference widget model without a body, when it is loaded, then an empty set is added")
    public void givenOldReferenceWidgetModelWithoutBodyWhenItIsLoadedThenAnEmptySetIsAdded() {
        var widget = this.findReferenceWidget("Default Widget Reference");
        assertThat(widget.getSet()).isNotNull();
        assertThat(widget.getSet().getBody()).isEmpty();
        assertThat(widget.getAdd()).isNotNull();
        assertThat(widget.getAdd().getBody()).isEmpty();
        assertThat(widget.eClass().getEStructuralFeature("body")).isNull();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old reference widget model with a body, when it is loaded, then the body is moved to set and copied to add")
    public void givenOldReferenceWidgetModelWithBodyWhenItIsLoadedThenBodyIsMovedToSetAndCopiedToAdd() {
        var widget = this.findReferenceWidget("Test Widget Reference");
        assertThat(widget.getSet()).isNotNull();
        assertThat(widget.getSet().getBody()).hasSize(1);
        assertThat(widget.getAdd()).isNotNull();
        assertThat(widget.getAdd().getBody()).hasSize(1);
        assertThat(widget.eClass().getEStructuralFeature("body")).isNull();
        var operation = widget.getSet().getBody().get(0);
        assertThat(operation).isInstanceOf(ChangeContext.class).isNotSameAs(widget.getAdd().getBody().get(0));
        assertThat(widget.eResource().getURIFragment(operation)).isEqualTo("609719c4-87ac-4f0a-8be3-e720351a05d3");
        var changeContext = (ChangeContext) operation;
        assertThat(changeContext.getExpression()).isEqualTo("aql:self");
        assertThat(changeContext.getChildren()).hasSize(1);
        var child = changeContext.getChildren().get(0);
        assertThat(child).isInstanceOf(SetValue.class).isNotSameAs(widget.getAdd().getBody().get(0).getChildren().get(0));
        assertThat(widget.eResource().getURIFragment(child)).isEqualTo("c8546d89-d139-4d52-b090-5d23f583ae33");
        assertThat(((SetValue) child).getFeatureName()).isEqualTo("target");
        assertThat(((SetValue) child).getValueExpression()).isEqualTo("aql:newValue");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an add-migrated model without add, when it is loaded, then set preserves the old body and add remains absent")
    public void givenAddMigratedModelWithoutAddWhenItIsLoadedThenSetPreservesOldBodyAndAddRemainsAbsent() {
        var widget = this.loadReferenceWidget(this.givenAddMigratedModel());
        assertThat(widget.getAdd()).isNull();
        assertThat(widget.getSet()).isNotNull();
        assertThat(widget.getSet().getBody()).hasSize(1);
        var operation = widget.getSet().getBody().get(0);
        assertThat(widget.eResource().getURIFragment(operation)).isEqualTo("d71bbe04-9f3b-4d6b-a862-36a5239576ae");
        assertThat(((ChangeContext) operation).getExpression()).isEqualTo("aql:self");
        assertThat(widget.eResource().getErrors()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an add-migrated model with a configured add, when it is loaded, then the add is preserved and the old body moves to set")
    public void givenAddMigratedModelWithConfiguredAddWhenItIsLoadedThenAddIsPreservedAndOldBodyMovesToSet() {
        var model = this.givenAddMigratedModel();
        var data = model.getAsJsonArray("content").get(0).getAsJsonObject().getAsJsonObject("data");
        data.add("add", JsonParser.parseString("""
                {
                  "id": "bf229565-4258-4b81-a088-b238cfb50b86",
                  "eClass": "widget:ReferenceWidgetAdd",
                  "data": {
                    "preconditionExpression": "aql:false",
                    "body": [{"id": "1feb365a-3805-4745-bcd3-65a22840db31", "eClass": "view:ChangeContext", "data": {"expression": "aql:referenceOwner"}}]
                  }
                }
                """));
        var widget = this.loadReferenceWidget(model);
        assertThat(widget.getAdd().getPreconditionExpression()).isEqualTo("aql:false");
        assertThat(widget.eResource().getURIFragment(widget.getAdd())).isEqualTo("bf229565-4258-4b81-a088-b238cfb50b86");
        assertThat(widget.getAdd().getBody()).hasSize(1);
        assertThat(widget.eResource().getURIFragment(widget.getAdd().getBody().get(0))).isEqualTo("1feb365a-3805-4745-bcd3-65a22840db31");
        assertThat(((ChangeContext) widget.getAdd().getBody().get(0)).getExpression()).isEqualTo("aql:referenceOwner");
        assertThat(widget.getSet().getBody()).hasSize(1);
        assertThat(widget.eResource().getURIFragment(widget.getSet().getBody().get(0))).isEqualTo("d71bbe04-9f3b-4d6b-a862-36a5239576ae");
        assertThat(widget.eResource().getErrors()).isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old model with a configured set, when it is loaded, then the set is preserved and the shared body is removed")
    public void givenOldModelWithConfiguredSetWhenItIsLoadedThenSetIsPreservedAndSharedBodyIsRemoved() {
        var model = this.givenAddMigratedModel();
        var data = model.getAsJsonArray("content").get(0).getAsJsonObject().getAsJsonObject("data");
        data.add("set", JsonParser.parseString("""
                {
                  "id": "3f1f4eac-4bb7-44ad-aa1f-b7b8ce4c8f22",
                  "eClass": "widget:ReferenceWidgetSet",
                  "data": {
                    "preconditionExpression": "aql:false",
                    "body": [{"id": "cfd8a6d2-075c-4a61-ae23-f4c9528754f7", "eClass": "view:ChangeContext", "data": {"expression": "aql:newValue"}}]
                  }
                }
                """));
        var widget = this.loadReferenceWidget(model);
        assertThat(widget.getSet().getPreconditionExpression()).isEqualTo("aql:false");
        assertThat(widget.eResource().getURIFragment(widget.getSet())).isEqualTo("3f1f4eac-4bb7-44ad-aa1f-b7b8ce4c8f22");
        assertThat(widget.getSet().getBody()).hasSize(1);
        assertThat(widget.eResource().getURIFragment(widget.getSet().getBody().get(0))).isEqualTo("cfd8a6d2-075c-4a61-ae23-f4c9528754f7");
        assertThat(((ChangeContext) widget.getSet().getBody().get(0)).getExpression()).isEqualTo("aql:newValue");
        assertThat(widget.eClass().getEStructuralFeature("body")).isNull();
        assertThat(widget.eResource().getErrors()).isEmpty();
    }

    private ReferenceWidgetDescription findReferenceWidget(String label) {
        var editingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_REFERENCE_WIDGET_DESCRIPTION_STUDIO.toString()).orElseThrow();
        assertThat(editingContext).isInstanceOf(EditingContext.class);
        return ((EditingContext) editingContext).getViews().stream()
                .flatMap(view -> view.getDescriptions().stream())
                .filter(description -> description.getName().equals("WidgetRefMonoValue"))
                .map(FormDescription.class::cast)
                .flatMap(form -> form.getPages().get(0).getGroups().get(0).getChildren().stream())
                .filter(ReferenceWidgetDescription.class::isInstance)
                .map(ReferenceWidgetDescription.class::cast)
                .filter(widget -> label.equals(widget.getLabelExpression()))
                .findFirst().orElseThrow();
    }

    private JsonObject givenAddMigratedModel() {
        return JsonParser.parseString("""
                {
                  "json": {"version": "1.0", "encoding": "utf-8"},
                  "ns": {
                    "widget": "https://www.eclipse.org/sirius/widgets/reference",
                    "view": "http://www.eclipse.org/sirius-web/view"
                  },
                  "migration": {
                    "lastMigrationPerformed": "ReferenceWidgetDescriptionAddMigrationParticipant",
                    "migrationVersion": "2026.11.0-202610211729"
                  },
                  "content": [{
                    "id": "d33ebc2a-d781-4650-a490-8f5d8dfdb187",
                    "eClass": "widget:ReferenceWidgetDescription",
                    "data": {
                      "referenceNameExpression": "target",
                      "body": [{"id": "d71bbe04-9f3b-4d6b-a862-36a5239576ae", "eClass": "view:ChangeContext", "data": {"expression": "aql:self"}}]
                    }
                  }]
                }
                """).getAsJsonObject();
    }

    private ReferenceWidgetDescription loadReferenceWidget(JsonObject model) {
        var resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(ReferencePackage.eNS_URI, ReferencePackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(ViewPackage.eNS_URI, ViewPackage.eINSTANCE);
        var resource = this.resourceLoader.toResource(resourceSet, "a0d7cc01-0eec-4257-bb2c-2d7934497a27", "Reference widget migration", model.toString(), true, false).orElseThrow();
        assertThat(resource.getContents()).hasSize(1);
        assertThat(resource.getContents().get(0)).isInstanceOf(ReferenceWidgetDescription.class);
        return (ReferenceWidgetDescription) resource.getContents().get(0);
    }
}
