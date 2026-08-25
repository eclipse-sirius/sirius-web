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
import { expect, test, type Page } from '@playwright/test';
import { PlaywrightDetails } from '../../helpers/PlaywrightDetails';
import { PlaywrightExplorer } from '../../helpers/PlaywrightExplorer';
import { PlaywrightProject } from '../../helpers/PlaywrightProject';

const widgetLabel = 'Test Widget Reference';

const createFormWithWidgetReference = async (
  page: Page,
  domainType: string,
  name: string,
  reference: string
): Promise<void> => {
  const explorer = new PlaywrightExplorer(page);
  const details = new PlaywrightDetails(page);
  await explorer.createNewObject('View', 'descriptions-FormDescription');
  await explorer.select('New Form Description');
  await details.setText('Domain Type', domainType);
  await details.setText('Name', name);
  await details.setText('Title Expression', name);
  await explorer.expand(name);
  await explorer.expand('PageDescription');
  await explorer.createNewObject('GroupDescription', 'children-ReferenceWidgetDescription');

  const referenceNameInput = details.detailsLocator.getByTestId('input-Reference Name Expression');
  await expect(referenceNameInput).toBeVisible();
  await expect(referenceNameInput).toBeEditable();
  await details.setText('Label Expression', widgetLabel);
  await details.setText('Reference Name Expression', reference);
  await explorer.expand(name);
};

let flowProjectId: string;

test.beforeEach(async ({ page, request }) => {
  const flow = await new PlaywrightProject(request).createProject('Widget reference flow', 'flow-template');
  flowProjectId = flow.projectId;
  await page.goto(`/projects/${flowProjectId}/edit`);
});

test.afterEach(async ({ request }) => {
  if (flowProjectId) {
    await new PlaywrightProject(request).deleteProject(flowProjectId);
  }
});

test.describe('forms - widget reference - mono', () => {
  let studioProjectId: string;
  let monoValueForm: string;

  test.beforeAll(async ({ browser, request, baseURL }) => {
    const studio = await new PlaywrightProject(request).createProject(
      'Widget reference mono studio',
      'blank-studio-template'
    );
    studioProjectId = studio.projectId;
    monoValueForm = `WidgetRefMonoValue-${studioProjectId}`;

    const page = await browser.newPage({ baseURL });
    try {
      await page.goto(`/projects/${studioProjectId}/edit`);
      const explorer = new PlaywrightExplorer(page);
      await explorer.createNewModel('ViewDocument', 'view');
      await expect(page.getByTestId('create-new-model')).not.toBeAttached();
      await explorer.expand('ViewDocument');
      await createFormWithWidgetReference(page, 'flow::DataFlow', monoValueForm, 'target');
    } finally {
      await page.close();
    }
  });

  test.afterAll(async ({ request }) => {
    if (studioProjectId) {
      await new PlaywrightProject(request).deleteProject(studioProjectId);
    }
  });

  test('when a mono-valued reference is edited, then its value can be selected, cleared and replaced by drag and drop', async ({
    page,
  }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.expand('DataSource1');
    await explorer.createRepresentation('standard', monoValueForm, 'WidgetRefMonoValue');

    const form = page.locator('[data-representation-kind="form"]');
    const widget = form.getByTestId(widgetLabel);
    await expect(form).toBeVisible();
    await expect(widget).toBeVisible();
    await expect(widget.getByTestId(`${widgetLabel}-clear`)).toBeVisible();
    await widget.getByTestId(`${widgetLabel}-more`).click();

    const browseModal = page.getByTestId('browse-modal');
    await expect(browseModal.getByTestId('tree-root-elements')).toBeVisible();
    await expect(browseModal.getByTestId('selected').getByTestId('Processor1')).toBeVisible();
    await browseModal.getByTestId('CompositeProcessor1').click();
    await page.getByTestId('select-value').click();
    await expect(widget.getByTestId('reference-value-CompositeProcessor1')).toBeVisible();
    await expect(widget.getByTestId('reference-value-Processor1')).not.toBeAttached();

    await widget.getByTestId(`${widgetLabel}-clear`).click();
    await expect(widget.getByTestId('reference-value-CompositeProcessor1')).not.toBeAttached();
    await explorer.dragTo('CompositeProcessor1', widget);
    await expect(widget.getByTestId('reference-value-CompositeProcessor1')).toBeVisible();
    await expect(widget.getByTestId('reference-value-none')).not.toBeAttached();
    await widget.click();
    await page.getByTestId('option-Processor1').click();
    await expect(widget.getByTestId('reference-value-CompositeProcessor1')).not.toBeAttached();
    await expect(widget.getByTestId('reference-value-Processor1')).toBeVisible();
  });
});

test.describe('forms - widget reference - multi', () => {
  let studioProjectId: string;
  let multiValueForm: string;

  test.beforeAll(async ({ browser, request, baseURL }) => {
    const studio = await new PlaywrightProject(request).createProject(
      'Widget reference multi studio',
      'blank-studio-template'
    );
    studioProjectId = studio.projectId;
    multiValueForm = `WidgetRefMultiValueNonContainment-${studioProjectId}`;

    const page = await browser.newPage({ baseURL });
    try {
      await page.goto(`/projects/${studioProjectId}/edit`);
      const explorer = new PlaywrightExplorer(page);
      await explorer.createNewModel('ViewDocument', 'view');
      await expect(page.getByTestId('create-new-model')).not.toBeAttached();
      await explorer.expand('ViewDocument');
      await createFormWithWidgetReference(page, 'flow::CompositeProcessor', multiValueForm, 'incomingFlows');
    } finally {
      await page.close();
    }
  });

  test.afterAll(async ({ request }) => {
    if (studioProjectId) {
      await new PlaywrightProject(request).deleteProject(studioProjectId);
    }
  });

  test('when a multi-valued reference is edited, then its values can be transferred, dropped, cleared and selected without duplicates', async ({
    page,
  }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.createRepresentation('CompositeProcessor1', multiValueForm, 'WidgetRefMultiValueNonContainment');

    const widget = page.locator('[data-representation-kind="form"]').getByTestId(widgetLabel);
    await expect(widget).toBeVisible();
    await expect(widget.getByTestId(`${widgetLabel}-clear`)).toBeVisible();
    await widget.getByTestId(`${widgetLabel}-more`).click();

    const transferModal = page.getByTestId('transfer-modal');
    const candidates = transferModal.getByTestId('tree-root-elements');
    const selectedItems = transferModal.getByTestId('selected-items-list');
    await expect(candidates).toBeVisible();
    await expect(selectedItems).toBeVisible();
    await transferModal.getByTestId('Flow').click();
    await transferModal.getByTestId('expand-all').click();
    await candidates.getByTestId('standard').click();
    await transferModal.getByTestId('move-right').click();
    await expect(selectedItems.getByTestId('standard')).toBeVisible();
    await selectedItems.getByTestId('standard').click();
    await candidates.getByTestId('standard').dragTo(selectedItems);
    await expect(selectedItems.getByTestId('standard')).toHaveCount(1);
    await page.getByTestId('close-transfer-modal').click();
    await expect(widget.getByTestId('reference-value-standard')).toBeVisible();

    await widget.getByTestId(`${widgetLabel}-clear`).click();
    await expect(widget.getByTestId('reference-value-standard')).not.toBeAttached();
    await explorer.expand('DataSource1');
    await explorer.dragTo('standard', widget);
    await expect(widget.getByTestId('reference-value-standard')).toBeVisible();
    await explorer.createNewObject('DataSource1', 'outgoingFlows-DataFlow');
    await widget.click();
    await expect(page.getByTestId('option-unused')).toBeVisible();
    await expect(page.getByTestId('option-standard')).not.toBeAttached();
    await page.getByTestId('option-unused').click();
    await expect(widget.getByTestId('reference-value-standard')).toBeVisible();
    await expect(widget.getByTestId('reference-value-unused')).toBeVisible();
  });

  test('when an object is created through a non-containment reference, then it appears under the selected container and in the reference', async ({
    page,
  }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.expand('NewSystem');
    await explorer.createRepresentation('CompositeProcessor1', multiValueForm, 'WidgetRefMultiValueNonContainment');

    const widget = page.locator('[data-representation-kind="form"]').getByTestId(widgetLabel);
    await expect(widget).toBeVisible();
    await widget.getByTestId(`${widgetLabel}-add`).click();
    const createModal = page.getByTestId('create-modal');
    await expect(createModal).toBeVisible();
    await expect(createModal.getByTestId('tree-root-elements')).toBeVisible();
    const childCreationDescription = createModal.getByTestId('childCreationDescription');
    await expect(childCreationDescription).toBeVisible();
    await createModal.getByTestId('Flow').click();
    await createModal.getByTestId('expand-all').click();
    await expect(createModal.getByTestId('NewSystem')).toBeVisible();
    await expect(createModal.getByTestId('DataSource1')).toBeVisible();
    await createModal.getByTestId('CompositeProcessor1').click();
    await expect(childCreationDescription.getByRole('combobox')).toHaveText(/\S.{1,}/);
    await childCreationDescription.click();
    await expect(page.getByRole('option')).toHaveCount(2);
    await page.locator('[data-value="elements-DataFlow"]').click();
    await createModal.getByTestId('create-object').click();
    await expect(widget.getByTestId('reference-value-unused')).toBeVisible();
    await explorer.expand('DataSource1');
    await expect(await explorer.getTreeItemLabel('unused')).toBeVisible();
  });
});

test.describe('forms - widget reference - containment', () => {
  let studioProjectId: string;
  let containmentForm: string;

  test.beforeAll(async ({ browser, request, baseURL }) => {
    const studio = await new PlaywrightProject(request).createProject(
      'Widget reference containment studio',
      'blank-studio-template'
    );
    studioProjectId = studio.projectId;
    containmentForm = `WidgetRefContainment-${studioProjectId}`;

    const page = await browser.newPage({ baseURL });
    try {
      await page.goto(`/projects/${studioProjectId}/edit`);
      const explorer = new PlaywrightExplorer(page);
      await explorer.createNewModel('ViewDocument', 'view');
      await expect(page.getByTestId('create-new-model')).not.toBeAttached();
      await explorer.expand('ViewDocument');
      await createFormWithWidgetReference(page, 'flow::System', containmentForm, 'powerOutputs');
    } finally {
      await page.close();
    }
  });

  test.afterAll(async ({ request }) => {
    if (studioProjectId) {
      await new PlaywrightProject(request).deleteProject(studioProjectId);
    }
  });

  test('when a contained object is deleted from the chip or widget, then cancellation preserves it and confirmation removes it', async ({
    page,
  }) => {
    const explorer = new PlaywrightExplorer(page);
    await explorer.expand('Flow');
    await explorer.createRepresentation('NewSystem', containmentForm, 'WidgetRefContainment');

    const widget = page.locator('[data-representation-kind="form"]').getByTestId(widgetLabel);
    const reference = widget.getByTestId('reference-value-1000');
    const treeItem = await explorer.getTreeItemLabel('1000');
    await expect(widget).toBeVisible();
    await expect(treeItem).not.toBeAttached();

    for (const deletionSource of ['chip', 'widget']) {
      await test.step(`create a contained object and delete it using the ${deletionSource}`, async () => {
        await widget.getByTestId(`${widgetLabel}-add`).click();
        const createModal = page.getByTestId('create-modal');
        await expect(createModal).toBeVisible();
        await expect(createModal.getByTestId('tree-root-elements')).not.toBeAttached();
        const childCreationDescription = createModal.getByTestId('childCreationDescription');
        await expect(childCreationDescription.getByRole('combobox')).toHaveText(/\S.{1,}/);
        await childCreationDescription.click();
        await expect(page.getByRole('option')).toHaveCount(1);
        await page.locator('[data-value="powerOutputs-PowerOutput"]').click();
        await createModal.getByTestId('create-object').click();
        await expect(reference).toBeVisible();
        await expect(treeItem).toBeVisible();

        const deleteButton =
          deletionSource === 'chip'
            ? widget.locator('.MuiChip-deleteIcon')
            : widget.getByTestId(`${widgetLabel}-clear`);
        await deleteButton.click();
        await expect(page.getByTestId('confirmation-dialog')).toBeVisible();
        await page.getByTestId('confirmation-dialog-button-cancel').click();
        await expect(page.getByTestId('confirmation-dialog')).not.toBeAttached();
        await expect(reference).toBeVisible();
        await expect(treeItem).toBeVisible();

        await deleteButton.click();
        await expect(page.getByTestId('confirmation-dialog')).toBeVisible();
        await page.getByTestId('confirmation-dialog-button-ok').click();
        await expect(page.getByTestId('confirmation-dialog')).not.toBeAttached();
        await expect(reference).not.toBeAttached();
        await expect(treeItem).not.toBeAttached();
      });
    }
  });
});
