/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { decorate, inject } from 'inversify';
import { EditLabelUI, getAbsoluteClientBounds, getZoom, isEditableLabel, SModelRoot } from 'sprotty';
import { Label } from '../Diagram.types';
import { JSONResponse } from './model';

const initialDirectEditElementLabelOp = `
  query initialDirectEditElementLabel($editingContextId: ID!, $diagramId: ID!, $labelId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              initialDirectEditElementLabel(labelId: $labelId)
            }
          }
        }
      }
    }
  }
  `;

export class EditLabelUIWithInitialContent extends EditLabelUI {
  constructor(private readonly httpOrigin: string, private readonly editingContextId: string) {
    super();
  }

  override show(root: Readonly<SModelRoot>, ...contextElementIds: string[]): void {
    if (!hasEditableLabel(contextElementIds, root) || this.isActive) {
      return;
    }
    this.activeElement = document.activeElement;
    if (!this.containerElement) {
      if (!this.initialize()) return;
    }
    this.onBeforeShow(this.containerElement, root, ...contextElementIds).then(() => {
      this.setContainerVisible(true);
      this.isActive = true;
    });
  }

  override hide(): void {
    if (this.label instanceof Label) {
      this.label.initialText = undefined;
      this.label.preSelect = true;
    }
    super.hide();
  }

  protected override async onBeforeShow(
    containerElement: HTMLElement,
    root: Readonly<SModelRoot>,
    ...contextElementIds: string[]
  ): Promise<void> {
    this.label = getEditableLabels(contextElementIds, root)[0];
    this.previousLabelContent = this.label.text;
    this.setPosition(containerElement);
    await this.applyTextContents();
    this.applyFontStyling();
    this.editControl.style.visibility = 'visible';
    this.editControl.focus();
  }

  protected override async applyTextContents() {
    if (this.label instanceof Label) {
      const label = this.label;
      if (label.initialText) {
        this.editControl.value = label.initialText;
      } else {
        this.editControl.value = '';

        try {
          const editLabelContent = await fetchEditLabelContent(
            this.httpOrigin,
            this.editingContextId,
            label.root.id,
            this.labelId
          );

          this.editControl.value = editLabelContent;

          if (label.preSelect) {
            this.editControl.setSelectionRange(0, this.editControl.value.length);
          }
        } catch (error) {
          // We should probably tells Srotty an error has occurred to give feedback to the user. Hope he will not wait to long !!
        }
      }
    } else {
      super.applyTextContents();
    }
  }

  /**
   * Overriden to have the same editing area size and to center it with the edited label
   */
  protected override setPosition(containerElement: HTMLElement) {
    let x = 0;
    let y = 0;
    let width = 100;
    let height = 20;
    // used to avoid the scrollbar
    const extraWidth: number = 50;
    const extraHeight: number = 10;

    if (this.label) {
      const nbLines: number = this.label.text.split('\n').length;
      const zoom = getZoom(this.label);
      const bounds = getAbsoluteClientBounds(this.label, this.domHelper, this.viewerOptions);
      // make the edit area centered on the label
      x = bounds.x + (bounds.width * (1 - 1 / zoom)) / 2;
      y = bounds.y;
      height = height * nbLines + extraHeight;
      width = bounds.width / zoom + extraWidth;
    }

    containerElement.style.left = `${x}px`;
    containerElement.style.top = `${y}px`;
    containerElement.style.width = `${width}px`;
    this.editControl.style.width = `${width}px`;
    containerElement.style.height = `${height}px`;
    this.editControl.style.height = `${height}px`;
  }

  /**
   * Overriden to keep the same font size whatever the zoom and to center the text
   */
  protected override applyFontStyling() {
    // super.applyFontStyling();
    if (this.label) {
      this.labelElement = document.getElementById(this.domHelper.createUniqueDOMElementId(this.label));
      if (this.labelElement) {
        this.labelElement.style.visibility = 'hidden';
        const style = window.getComputedStyle(this.labelElement);
        this.editControl.style.font = style.font;
        this.editControl.style.fontStyle = style.fontStyle;
        this.editControl.style.fontFamily = style.fontFamily;
        this.editControl.style.fontWeight = style.fontWeight;
        this.editControl.style.textAlign = 'center';
      }
    }
  }
}

decorate(inject('httpOrigin') as ParameterDecorator, EditLabelUIWithInitialContent, 0);
decorate(inject('editingContextId') as ParameterDecorator, EditLabelUIWithInitialContent, 1);

const hasEditableLabel = (contextElementIds: string[], root: Readonly<SModelRoot>) => {
  return getEditableLabels(contextElementIds, root).length === 1;
};

const getEditableLabels = (contextElementIds: string[], root: Readonly<SModelRoot>) => {
  return contextElementIds.map((id) => root.index.getById(id)).filter(isEditableLabel);
};

const fetchEditLabelContent = async (
  httpOrigin: string,
  editingContextId: string,
  representationId: string,
  labelId: string
): Promise<string> => {
  const requestBody = {
    operationName: 'initialDirectEditElementLabel',
    query: initialDirectEditElementLabelOp,
    variables: {
      editingContextId,
      diagramId: representationId,
      labelId,
    },
  };

  const csrfToken = getCookie('XSRF-TOKEN');

  const response = await fetch(`${httpOrigin}/api/graphql`, {
    method: 'POST',
    mode: 'cors',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
      'X-XSRF-TOKEN': csrfToken,
    },
    body: JSON.stringify(requestBody),
  });
  const { data, errors }: JSONResponse = await response.json();
  if (response.ok) {
    const editLabelContent = data?.viewer?.editingContext?.representation?.description.initialDirectEditElementLabel;
    if (editLabelContent) {
      return Promise.resolve(editLabelContent);
    } else {
      return Promise.reject(new Error(`An error has occurred.`));
    }
  } else {
    const error = new Error(errors?.map((e) => e.message).join('\n') ?? 'unknown');
    return Promise.reject(error);
  }
};

const getCookie = (name: string): string => {
  let cookieValue: string = null;
  if (document.cookie && document.cookie !== '') {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      // Does this cookie string begin with the name we want?
      if (cookie.substring(0, name.length + 1) === name + '=') {
        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
        break;
      }
    }
  }
  return cookieValue;
};
